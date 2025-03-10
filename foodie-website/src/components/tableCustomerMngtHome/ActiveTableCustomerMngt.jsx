import "../../css/mngtTableHome.css";
import { DataGrid } from '@mui/x-data-grid';
//data
//import { customerMngtColumns, activeCustomerMngtRows } from "../../data/dataCustomerMngtHome";
import { Link } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const ActiveTableCustomerMngt = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/account/1`);
            // Lọc dữ liệu có status là "Active" trước khi cập nhật vào state
            const activeAccounts = response.data.filter(account => account.account_status.status === 'Active');
            setData(activeAccounts);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching data:', error);
            setLoading(false);
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
        const accountId = params.row.id;
        const currentStatus = params.row.account_status?.status;// Sử dụng optional chaining để tránh lỗi nếu 'status_account' không được định nghĩa
        setShowProgressBar(true);
        try {
            if (currentStatus === 'Active') {
                const response = await axios.put(`${backendUrl}/api/account/block/${accountId}`);

                if (response.status === 200) {
                    setMessage('Khóa tài khoản thành công.!');
                    fetchData();
                } else {
                    setMessage('Khóa tài khoản thất bại.');
                }
            } else if (currentStatus === 'Blocked') {
                const response = await axios.put(`${backendUrl}/api/account/open/${accountId}`);

                if (response.status === 200) {
                    setMessage('Mở tài khoản thành công.!');
                    fetchData();
                } else {
                    setMessage('Khóa tài khoản thất bại.');
                }
            }
            // Gọi lại API để cập nhật dữ liệu
            fetchData();
        } catch (error) {
            console.error('Error updating account status:', error);
        }
    };
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const filteredData = data.filter(account =>
        account.firstname.toLowerCase().includes(searchTerm.toLowerCase()) ||
        account.lastname.toLowerCase().includes(searchTerm.toLowerCase()) ||
        account.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
        account.phonenumber.toLowerCase().includes(searchTerm.toLowerCase())
        // (account.phonenumber === parseFloat(searchTerm))

    );
    const handleReset = async () => {
        setSearchTerm('');
        fetchData();
    };
    const columns = [
        { field: "id", headerName: "ID", width: 80 },
        // { field: 'useName', headerName: 'Tên tài khoản', width: 150 },
        { field: 'firstname', headerName: 'Họ', width: 100 },
        { field: 'lastname', headerName: 'Tên', width: 100 },

        { field: 'phonenumber', headerName: 'Số điện thoại', width: 150 },

        { field: 'email', headerName: 'Email', width: 150 },
        {
            field: 'address', headerName: 'Địa chỉ', width: 240,
            renderCell: (params) => (
                <div>
                    {params.value ? (
                        <div>
                            <p>{params.value.street ? params.value.street + ', ' : '_, '}
                                {params.value.ward ? params.value.ward + ', ' : '_, '}
                                {params.value.district ? params.value.district + ', ' : '_, '}
                                {params.value.province ? params.value.province + '. ' : '_'}
                            </p>
                        </div>
                    ) : (
                        <p></p>
                    )}
                </div>
            ),
        },

        {
            field: "account_status.status",
            headerName: "Trạng thái",
            width: 90,
            renderCell: (params) => {
                const status = params.row.account_status?.status; // Sử dụng optional chaining để tránh lỗi nếu 'account_status' không được định nghĩa
                return (
                    <div className={`cellWithStatus ${status}`}>
                        {status === 'Active' ? 'Hoạt động' : 'Khóa'}
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
                const currentStatus = params.row.account_status?.status;
                return (
                    <div className="cellAction">
                        <Link to={`/admin/customerManagement/customerDetail/${params.row.id}`} style={{ textDecoration: "none" }}>
                            <div className="viewButton">Xem chi tiết</div>
                        </Link>

                        <div className="viewButton" onClick={() => handleLockUnlock(params)}>
                            {currentStatus === 'Active' ? <LockIcon /> : <LockOpenIcon />}
                        </div>

                        <div
                            className="deleteButton"
                            onClick={() => handleDelete(params.row.id)}
                        >
                            <DeleteIcon />
                        </div>
                    </div>
                );
            },
        },
    ];

    if (loading) {
        return <div>Loading...</div>;
    }
    const handleDelete = async (id) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/account/${id}`, {
                headers: {
                    'Content-Type': 'application/json', // Đảm bảo rằng yêu cầu sử dụng JSON
                }
            });
            if (response.status === 200) {
                setMessage('Xóa khách hàng thành công!');
                setData(data.filter((item) => item.id !== id));

            } else {
                setMessage('Xóa khách hàng thất bại.');
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
    return (
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
                    placeholder="Tìm kiếm theo họ, tên, SĐT, email"
                    value={searchTerm}
                    onChange={handleSearchChange}

                />
                <RefreshIcon
                    title="Tải lại dữ liệu"
                    onClick={handleReset}
                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                />
            </div>
            <div style={{ height: 526, width: '100%' }}>
                <DataGrid className="datagrid"
                    rows={filteredData}
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
    )
}

export default ActiveTableCustomerMngt