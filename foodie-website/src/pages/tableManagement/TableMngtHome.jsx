
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

const TableMngtHome = () => {
    const [data, setData] = useState([]);

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [searchStatus, setSearchStatus] = useState('');
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/tableInfo/all`);
            const dataWithIds = response.data.map(row => ({
                ...row,
                id: row.tableId,
            }));
            setData(dataWithIds);
            console.log("table", dataWithIds);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };
    const location = useLocation();
    const pathname = location.pathname;

    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    const addTableLink = role === "admin"
        ? "/admin/tableManagement/addTable"
        : role === "staff"
            ? "/staff/tableManagement/addTable"
            : "/";

    // const updateTableStatus = async (tableId, status) => {
    //     try {
    //         const response = await axios.put(`${backendUrl}/api/tableInfo/updateStatus/${tableId}`, null, {
    //             params: { status },
    //         });

    //         if (response.status === 200) {
    //             console.log(`Cập nhật trạng thái bàn ${tableId} thành công.`);
    //             fetchData();
    //         }
    //     } catch (error) {
    //         console.error(`Lỗi khi cập nhật trạng thái bàn ${tableId}:`, error);
    //     }
    // };
    const updateTableStatus = async (tableId, status) => {
        try {
            const response = await axios.put(`${backendUrl}/api/tableInfo/updateStatus/${tableId}`, null, {
                params: { status },
            });

            if (response.status === 200) {
                console.log(`Updated status of table ${tableId} successfully.`);
                fetchData(); // Refresh table data after update
            }
        } catch (error) {
            console.error(`Error updating table ${tableId} status:`, error);
        }
    };

    // Lock or unlock table status  // có thể thêm trạng thái đặt trước (reserved)
    const handleLockUnlock = async (params) => {
        const tableId = params.row.tableId; // Fetch table ID from row
        setShowProgressBar(true);
        try {
            const newStatus = params.row.status === "available" ? "ordering" : "available";
            await updateTableStatus(tableId, newStatus); // Call update function
            setMessage(`Cập nhật trạng thái bàn ${tableId}thành công.`);
        } catch (error) {
            console.error('Lỗi cập nhật:', error);
            setMessage(`Cập nhật trạng thái bàn thất bại.`);
        } finally {
            setShowProgressBar(false);
        }
    };

    // Lock or unlock table status
    const handleHidden = async (params) => {
        const tableId = params.row.tableId; // Fetch table ID from row
        setShowProgressBar(true);
        // const newExist = params.row.isExist === "true" ? "ordering" : "available";
        try {
            const response = await axios.put(`${backendUrl}/api/tableInfo/hidden/${tableId}`);

            if (response.status === 200) {
                console.log(`Cập nhật tình trạng bàn ${tableId} thành công.`);
                fetchData();
                setMessage(`Cập nhật tình trạng bàn ${tableId} thành công.`);
            }
        } catch (error) {
            console.error(`lỗi cập nhật tình trạng bàn ${tableId} status:`, error);
            setMessage(`Cập nhật tình trạng bàn ${tableId} thất bại.`);
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

    // const handleLockUnlock = async (params) => {
    //     const tableId = params.row.tableId;
    //     console.log("updateTableid", tableId);
    //     const status = params.row.status;
    //     setShowProgressBar(true);
    //     try {
    //         const response = await axios.put(`${backendUrl}/api/tableInfo/updateStatus/${tableId}`, null, {
    //             params: "ordering",
    //         });
    //         if (response.status === 200) {
    //             setMessage('Cập nhật trạng thái thành công.!');
    //         } else {
    //             setMessage('Cập nhật trạng thái thất bại.');
    //         }

    //         fetchData();
    //     } catch (error) {
    //         console.error('Error updating account status:', error);
    //     }
    // };
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };
    const handleSearchStatusChange = (event) => {
        setSearchStatus(event.target.value);
    };
    const searchTermAsNumber = isNaN(parseFloat(searchTerm)) ? searchTerm : parseFloat(searchTerm) / 100;

    const filteredData = data.filter(discount => {
        // const isInSearchTerm = discount.code.toLowerCase().includes(searchTerm.toLowerCase()) ||
        //     discount.startdate.toLowerCase().includes(searchTerm.toLowerCase()) ||
        //     discount.enddate.toLowerCase().includes(searchTerm.toLowerCase()) ||
        //     discount.percent === searchTermAsNumber
        // // discount.percent.toLowerCase().includes(searchTerm.toLowerCase())
        // // (account.phonenumber === parseFloat(searchTerm))
        // if (searchStatus === 'Đang áp dụng') {
        //     return isInSearchTerm && discount.isExist === true;
        // } else if (searchStatus === 'Hết mã') {
        //     return isInSearchTerm && discount.isExist === false;
        // } else {
        //     return isInSearchTerm;
        // }
    });


    const handleReset = async () => {
        setSearchTerm('');
        setSearchStatus('');
        fetchData();
    };
    const columns = [
        // { field: "tableId", headerName: "ID", width: 80 },
        { field: 'tableNumber', headerName: 'Bàn', width: 150 },
        {
            field: 'seatingCapacity', headerName: 'Số chỗ', width: 150,
            // valueGetter: (params) =>
            //     `${params.row.percent * 100}%`,
        },

        // {
        //     field: 'startdate', headerName: 'Ngày bắt đầu', width: 200,
        //     valueGetter: (params) => params.row.startdate,
        // },

        // {
        //     field: 'enddate', headerName: 'Ngày kết thúc', width: 200,
        //     valueGetter: (params) => params.row.enddate,
        // },
        {
            field: "status",
            headerName: "Trạng thái",
            width: 120,
            renderCell: (params) => {
                const status = params.row?.status; // Sử dụng optional chaining để tránh lỗi nếu 'account_status' không được định nghĩa
                // console.log(`${status}`);
                return (
                    <div className={`cellWithStatus ${status}`}>
                        {status === "available" ? 'Trống' : 'Có khách '}
                        {/* {status} */}
                    </div>
                );
            },
        },
        {
            field: "isExist",
            headerName: "Tình trạng",
            width: 120,
            renderCell: (params) => {
                const isExist = params.row?.isExist; // Sử dụng optional chaining để tránh lỗi nếu 'account_status' không được định nghĩa
                // console.log(`${status}`);
                return (
                    <div className={`cellWithStatus ${isExist}`}>
                        {isExist === true ? 'Đang sử dụng' : 'Không sử dụng'}
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
                const currentStatus = params.row?.status;
                const currentExist = params.row?.isExist;
                const tableDetailLink = role === "admin"
                    ? `/admin/tableManagement/tableDetail/${params.row.tableId}`
                    : `/staff/tableManagement/tableDetail/${params.row.tableId}`;
                const editTableLink = role === "admin"
                    ? `/admin/tableManagement/editTable/${params.row.tableId}`
                    : `/staff/tableManagement/editTable/${params.row.tableId}`;

                return (
                    <div className="cellAction">
                        {/* <Link to={`/admin/discountManagement/discountDetail/${params.row.code}`} style={{ textDecoration: "none" }}>
                            <div className="viewButton">Xem chi tiết</div>
                        </Link>*/}
                        {/* <div className="viewButton" onClick={() => handleLockUnlock(params)}>
                            {currentStatus === "available" ? <LockIcon /> : <LockOpenIcon />}
                        </div> */}
                        <div className="viewButton" onClick={() => handleHidden(params)}>
                            {currentExist === true ? <LockIcon /> : <LockOpenIcon />}
                        </div>

                        {/* if status:Hoạt động => button: Khóa (Khóa => Mở khóa) */}
                        {/* <div
                            className="blockButton"
                        //onClick={() => handleBlock(params.row.id)}
                        >
                            Khóa
                        </div> */}
                        {/* <Link to={editTableLink} className="editButton" style={{ textDecoration: "none" }}>
                            <EditIcon />
                        </Link> */}

                        {/* <div
                            className="deleteButton"
                            onClick={() => handleDelete(params.row.tableId)}
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
            const response = await axios.delete(`${backendUrl}/api/tableInfo/${id}`, {
                headers: {
                    'Content-Type': 'application/json', // Đảm bảo rằng yêu cầu sử dụng JSON
                }
            });
            if (response.status === 200) {
                setMessage('Xóa bàn thành công!');
                setData(data.filter((item) => item.id !== id));

            } else {
                setMessage('Xóa bàn thất bại.');
            }
        } catch (error) {
            console.error('Error deleting product:', error);
            setMessage('Lỗi!');
        }
    };

    const sortModel = [
        {
            field: 'tableId',
            sort: 'desc', // Sắp xếp giảm dần
        },
    ];

    return (
        <div className='list home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title="Quản lý bàn" />
                <div className="headerMngt">
                    <div className="linkAdd">
                        <Link to={addTableLink} style={{ textDecoration: "none" }}>
                            <div className="textAdd">Thêm bàn</div>
                        </Link>
                    </div>
                </div>
                {/* <TabContent input={contents} /> */}
                <div className="lp-tab-content">
                    <div className="tab">
                        <button
                            className={`tab-link`}
                        >
                            Các bàn
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
                                    placeholder="Tìm kiếm theo tên"
                                    value={searchTerm}
                                    onChange={handleSearchChange}
                                />
                                <p className="textStatus">Trạng thái </p>
                                <select className="selectStatus" value={searchStatus} onChange={handleSearchStatusChange}>
                                    <option value="">Tất cả</option>
                                    <option value="Trống">Trống</option>
                                    <option value="Có khách">Có khách</option>
                                </select>
                                <RefreshIcon
                                    title="Tải lại dữ liệu"
                                    onClick={handleReset}
                                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                                />
                            </div>
                            <div style={{ height: 526, width: '100%' }}>
                                <DataGrid className="datagrid"
                                    rows={data}  //Data  {filteredData}
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

export default TableMngtHome