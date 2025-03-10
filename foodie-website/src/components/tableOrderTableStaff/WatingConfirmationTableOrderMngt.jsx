import "../../css/mngtTableHome.css";
import { DataGrid } from '@mui/x-data-grid';
//data
//import { allOrderMngtRows, orderMngtColumns } from "../../data/dataOrderMngtHome";
import { Link } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const WatingConfirmationTableOrderMngt = () => {

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/order/all`);
            const Processing = response.data.filter(order => order.orderStatus.status === 'watingConfirmation');
            setData(Processing);
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
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };
    const [searchFirstName, searchLastName] = searchTerm.toLowerCase().split(' ');
    const filteredData = data.filter(order =>
        order.account.firstname.toLowerCase().includes(searchTerm.toLowerCase()) ||
        order.account.lastname.toLowerCase().includes(searchTerm.toLowerCase()) ||
        ((order.account.firstname.toLowerCase().includes(searchFirstName)) &&
            (order.account.lastname.toLowerCase().includes(searchLastName))) ||

        order.orderdate.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (order.discount && order.discount.code.toLowerCase().includes(searchTerm.toLowerCase())) ||
        order.account.phonenumber.toLowerCase().includes(searchTerm.toLowerCase())

    );
    const handleReset = async () => {
        setSearchTerm('');
        fetchData();
    };
    const columns = [
        { field: "id", headerName: "ID", width: 50 },
        {
            field: 'fullName',
            headerName: 'Khách hàng',
            description: 'firstname lastname',
            sortable: false,
            width: 130,
            valueGetter: (params) =>
                `${params.row.account.firstname || ''} ${params.row.account.lastname || ''}`,
        },

        {
            field: 'phonenumber', headerName: 'Số điện thoại', width: 120,
            valueGetter: (params) =>
                `${params.row.account.phonenumber}`,
        },

        // { field: 'email', headerName: 'Email', width: 150 },
        { field: 'orderdate', headerName: 'Ngày đặt hàng', width: 120 },
        {
            field: 'totalprice', headerName: 'Tổng tiền', width: 100,
            valueGetter: (params) =>
                `${params.row.bill.totalprice}`,
        },
        {
            field: 'discount_code', headerName: 'Khuyến mãi áp dụng', width: 120,
            valueGetter: (params) => {
                const discount = params.row.discount;
                if (discount) {
                    return `${params.row.discount.code}`;
                }
                return '';
            },
        },
        {
            field: 'checkout', headerName: 'Hình thức thanh toán', width: 120,
            valueGetter: (params) => {
                const method = params.row.checkout?.method;
                let methodText = '';

                switch (method) {
                    case 'credit card':
                        methodText = 'Ví ngân hàng';
                        break;
                    case 'cash':
                        methodText = 'Khi nhận hàng';
                        break;
                    case 'momo':
                        methodText = 'Ví Momo';
                        break;
                    default:
                        methodText = '';
                }

                return methodText;
            },
        },
        {
            field: "account_status.status",
            headerName: "Trạng thái",
            width: 120,
            renderCell: (params) => {
                const status = params.row.orderStatus?.status;
                let statusText = '';

                switch (status) {
                    case 'watingConfirmation':
                        statusText = 'Chờ xác nhận';
                        break;
                    case 'Processing':
                        statusText = 'Chờ lấy hàng';
                        break;
                    case 'beingTransported':
                        statusText = 'Đang đến';
                        break;
                    case 'Completed':
                        statusText = 'Đã giao';
                        break;
                    case 'Cancel':
                        statusText = 'Hủy';
                        break;
                    default:
                        statusText = '';
                }

                return (
                    <div className={`cellWithStatus ${status}`}>
                        {statusText}
                    </div>
                );
            },
        },

    ]
    const actionColumn = [
        {
            field: "action",
            headerName: "",
            width: 320,
            renderCell: (params) => {
                const status = params.row.orderStatus?.status;
                let statusText = '';

                switch (status) {
                    case 'watingConfirmation':
                        statusText = 'Xác nhận';
                        break;
                    case 'Processing':
                        statusText = 'Lấy hàng';
                        break;
                    case 'beingTransported':
                        statusText = 'Đã giao';
                        break;
                    // case 'Completed':
                    //     statusText = 'Đã giao';
                    //     break;
                    // case 'Cancel':
                    //     statusText = 'Hủy';
                    //     break;
                    default:
                        statusText = '';
                }
                if (status !== 'Completed' && status !== 'Cancel') {
                    return (

                        <div className="cellAction">
                            <Link to={`/admin/orderManagement/orderDetail/${params.row.id}`} style={{ textDecoration: "none" }}>
                                <div className="viewButton">Xem chi tiết</div>
                            </Link>
                            <div
                                className="deleteButton"
                                onClick={() => handleDelete(params.row.id)}
                            >
                                <DeleteIcon />
                            </div>
                            <div
                                className="activeButton"
                                onClick={() => handleUpdateStatus(params.row.id, params.row.orderStatus.status)}
                            >
                                {statusText}
                            </div>

                            {/* <div
                            className="deleteButton"
                        //onClick={() => handleUpdateStatus(params.row.id)}
                        >
                            {status == "watingConfirmation" ? "Hủy" : ``}
                        </div> */}
                            {status === 'watingConfirmation' && (
                                <div className="editButton" onClick={() => handleCancelOrder(params.row.id)}
                                >
                                    Hủy
                                </div>
                            )}

                        </div>
                    );
                }
                else {

                    return (
                        <div className="cellAction">
                            <Link to={`/admin/orderManagement/orderDetail/${params.row.id}`} style={{ textDecoration: "none" }}>
                                <div className="viewButton">Xem chi tiết</div>
                            </Link>
                            <div
                                className="deleteButton"
                                onClick={() => handleDelete(params.row.id)}
                            >
                                <DeleteIcon />
                            </div>
                        </div>
                    );
                }
            },
        },
    ];
    if (loading) {
        return <div>Loading...</div>;
    }
    const handleDelete = async (id) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/order/${id}`, {
                headers: {
                    'Content-Type': 'application/json', // Đảm bảo rằng yêu cầu sử dụng JSON
                }
            });
            if (response.status === 200) {
                setMessage('Xóa đơn hàng thành công!');
                setData(data.filter((item) => item.id !== id));

            } else {
                setMessage('Xóa đơn hàng thất bại.');
            }
        } catch (error) {
            console.error('Error deleting product:', error);
            setMessage('Lỗi!');
        }
    };
    const handleCancelOrder = async (id) => {
        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/order/${id}/5`)
            if (response.status === 200) {
                setMessage('Hủy đơn hàng thành công!');
            } else {
                setMessage('Hủy đơn hàng thất bại.');
            }
        } catch (error) {
            console.error('loi huy don hang:', error);
            setMessage('Lỗi!');
        }
    }
    const handleUpdateStatus = async (id, status) => {
        let statusUpdate = 1;

        switch (status) {
            case 'watingConfirmation':
                statusUpdate = 2;
                break;
            //case 'waitingPickup':
            case 'Processing':
                statusUpdate = 3;
                break;
            case 'beingTransported':
                statusUpdate = 4;
                break;
            default:
                statusUpdate = 0;
        }
        console.log(statusUpdate)
        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/order/${id}/${statusUpdate}`)
            if (response.status === 200) {
                setMessage('Xác nhận đơn hàng thành công!');
            } else {
                setMessage('Xác nhận đơn hàng thất bại.');
            }
        } catch (error) {
            console.error('Error deleting product:', error);
            setMessage('Lỗi!');
        }
    }
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
                    placeholder="Tìm kiếm theo họ tên, SĐT, ngày mua, khuyến mãi áp dụng"
                    value={searchTerm}
                    onChange={handleSearchChange}

                />
                <RefreshIcon
                    title="Tải lại dữ liệu"
                    onClick={handleReset}
                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                />
            </div>
            <div style={{ height: 578, width: '100%' }}>
                <DataGrid className="datagrid"
                    rows={filteredData}
                    columns={columns.concat(actionColumn)}

                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 9 },
                        },
                    }}
                    pageSizeOptions={[9]}
                    checkboxSelection
                    sortModel={sortModel}
                />
            </div>
        </div>
    )
}

export default WatingConfirmationTableOrderMngt