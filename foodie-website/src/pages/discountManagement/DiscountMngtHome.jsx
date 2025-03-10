
import "../../css/mngtHome.css"
import Navbar from '../../components/navbar/Navbar';
import Sidebar from '../../components/sidebar/Sidebar';
import { Link, useLocation } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';

import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const DiscountMngtHome = () => {
    const [data, setData] = useState([]);

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [searchStatus, setSearchStatus] = useState('');
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/discount/all`);
            setData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    useEffect(() => {
        fetchData();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000);
            return () => clearTimeout(timer);
        }
    }, [showProgressBar]);

    const handleLockUnlock = async (params) => {
        const discountId = params.row.id;
        // const currentStatus = params.row.isExist;
        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/discount/changeStatus/${discountId}`);
            if (response.status === 200) {
                setMessage('Cập nhật trạng thái thành công.!');
            } else {
                setMessage('Cập nhật trạng thái thất bại.');
            }

            // Gọi lại API để cập nhật dữ liệu
            fetchData();
        } catch (error) {
            console.error('Error updating account status:', error);
        }
    };

    // const formatDate = (dateString) => {
    //     const [year, month, day] = dateString.split('-');
    //     return `${day}/${month}/${year}`;
    // };
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };
    const handleSearchStatusChange = (event) => {
        setSearchStatus(event.target.value);
    };
    const searchTermAsNumber = isNaN(parseFloat(searchTerm)) ? searchTerm : parseFloat(searchTerm) / 100;

    const filteredData = data.filter(discount => {
        const isInSearchTerm = discount.code.toLowerCase().includes(searchTerm.toLowerCase()) ||
            discount.startdate.toLowerCase().includes(searchTerm.toLowerCase()) ||
            discount.enddate.toLowerCase().includes(searchTerm.toLowerCase()) ||
            discount.percent === searchTermAsNumber
        // discount.percent.toLowerCase().includes(searchTerm.toLowerCase())
        // (account.phonenumber === parseFloat(searchTerm))
        if (searchStatus === 'Đang áp dụng') {
            return isInSearchTerm && discount.isExist === true;
        } else if (searchStatus === 'Hết mã') {
            return isInSearchTerm && discount.isExist === false;
        } else {
            return isInSearchTerm;
        }
    });
    const handleReset = async () => {
        setSearchTerm('');
        setSearchStatus('');
        fetchData();
    };
    const columns = [
        { field: "id", headerName: "ID", width: 80 },
        { field: 'code', headerName: 'Tên', width: 150 },
        {
            field: 'percent', headerName: 'Giá trị (%)', width: 150,
            valueGetter: (params) =>
                `${params.row.percent * 100}%`,
        },

        {
            field: 'startdate', headerName: 'Ngày bắt đầu', width: 200,
            valueGetter: (params) => params.row.startdate,
        },

        {
            field: 'enddate', headerName: 'Ngày kết thúc', width: 200,
            valueGetter: (params) => params.row.enddate,
        },
        {
            field: "isExist",
            headerName: "Trạng thái",
            width: 120,
            renderCell: (params) => {
                const status = params.row?.isExist; // Sử dụng optional chaining để tránh lỗi nếu 'account_status' không được định nghĩa
                // console.log(`${status}`);
                return (
                    <div className={`cellWithStatus ${status}`}>
                        {status === true ? 'Đang áp dụng' : 'Hết mã '}
                        {/* {status} */}
                    </div>
                );
            },
        },

    ]
    const actionColumn = [
        {
            field: "action",
            headerName: "",
            width: 300,
            renderCell: (params) => {
                const currentStatus = params.row?.isExist;
                const discountDetailLink = role === "admin"
                    ? `/admin/discountManagement/discountDetail/${params.row.code}`
                    : `/staff/discountManagement/discountDetail/${params.row.code}`;
                const discountEditLink = role === "admin"
                    ? `/admin/discountManagement/editDiscount/${params.row.id}`
                    : `/staff/discountManagement/editDiscount/${params.row.id}`;
                return (
                    <div className="cellAction">
                        <Link to={discountDetailLink} style={{ textDecoration: "none" }}>
                            <div className="viewButton">Xem chi tiết</div>
                        </Link>
                        <div className="viewButton" onClick={() => handleLockUnlock(params)}>
                            {currentStatus === true ? <LockIcon /> : <LockOpenIcon />}
                        </div>

                        {/* if status:Hoạt động => button: Khóa (Khóa => Mở khóa) */}
                        {/* <div
                            className="blockButton"
                        //onClick={() => handleBlock(params.row.id)}
                        >
                            Khóa
                        </div> */}
                        <Link to={discountEditLink} className="editButton" style={{ textDecoration: "none" }}>
                            <EditIcon />
                        </Link>

                        {/* <div
                            className="deleteButton"
                            onClick={() => handleDelete(params.row.id)}
                        >
                            <DeleteIcon />
                        </div> */}
                    </div>
                );
            },
        },
    ];

    const handleDelete = async (id) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/discount/${id}`, {
                headers: {
                    'Content-Type': 'application/json', // Đảm bảo rằng yêu cầu sử dụng JSON
                }
            });
            if (response.status === 200) {
                setMessage('Xóa khuyến mãi thành công!');
                setData(data.filter((item) => item.id !== id));

            } else {
                setMessage('Xóa khuyến mãi thất bại.');
            }
        } catch (error) {
            console.error('Error deleting product:', error);
            setMessage('Lỗi!');
        }
    };

    const sortModel = [
        {
            field: 'id',
            sort: 'desc', // Sắp xếp giảm dần
        },
    ];

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;


    const addDiscountLink = role === "admin"
        ? "/admin/discountManagement/addDiscount"
        : role === "staff"
            ? "/staff/discountManagement/addDiscount"
            : "/";

    return (
        <div className='list home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title="Quản lý khuyến mãi" />
                <div className="headerMngt">
                    <div className="linkAdd">
                        <Link to={addDiscountLink} style={{ textDecoration: "none" }}>
                            <div className="textAdd">Thêm khuyến mãi</div>
                        </Link>
                    </div>
                </div>
                {/* <TabContent input={contents} /> */}
                <div className="lp-tab-content">
                    <div className="tab">
                        <button
                            className={`tab-link`}
                        >
                            Các khuyến mãi
                        </button>
                    </div>

                    <div className="tab-content-wrapper">
                        <div className="datatable" >
                            {message && (
                                <div className="success-message">
                                    {message}
                                    {showProgressBar && <div className="progress-bar" />}
                                </div>
                            )}
                            <div className="search-bar">
                                <input
                                    type="text"
                                    placeholder="Tìm kiếm theo tên, giá trị, ngày bắt đầu, ngày kết thúc"
                                    value={searchTerm}
                                    onChange={handleSearchChange}
                                />
                                <p className="textStatus">Trạng thái </p>
                                <select className="selectStatus" value={searchStatus} onChange={handleSearchStatusChange}>
                                    <option value="">Tất cả</option>
                                    <option value="Đang áp dụng">Đang áp dụng</option>
                                    <option value="Hết mã">Hết mã</option>
                                </select>
                                <RefreshIcon
                                    title="Tải lại dữ liệu"
                                    onClick={handleReset}
                                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                                />
                            </div>
                            <div style={{ height: 526, width: '100%' }}>
                                <DataGrid className="datagrid"
                                    rows={filteredData}  //Data
                                    columns={columns.concat(actionColumn)}

                                    initialState={{
                                        pagination: {
                                            paginationModel: { page: 0, pageSize: 8 },
                                        },
                                    }}
                                    pageSizeOptions={[8]}
                                    checkboxSelection
                                    sortModel={sortModel}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default DiscountMngtHome