import "../../css/mngtTableHome.css";
import { DataGrid } from '@mui/x-data-grid';
//data
//import { allOrderMngtRows, orderMngtColumns } from "../../data/dataOrderMngtHome";
import { Link, useLocation } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const AllTableOrderMngt = () => {

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [searchStatus, setSearchStatus] = useState('');
    const [searchCheckout, setSearchCheckout] = useState('');

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;


    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/order/all`);
            setData(response.data);
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
    const handleSearchStatusChange = (event) => {
        setSearchStatus(event.target.value);
    };
    const handleSearchCheckoutChange = (event) => {
        setSearchCheckout(event.target.value);
    };
    const [searchFirstName, searchLastName] = searchTerm.toLowerCase().split(' ');

    //không tìm được Hình thức thanh toán 
    //C1
    // const searchPaymentMethod = searchTerm.toLowerCase() === 'Ví Momo' ? 'momo'
    //     : searchTerm.toLowerCase() === 'Khi nhận hàng' ? 'cash'
    //         : searchTerm.toLowerCase() === 'Ví ngân hàng' ? 'credit card'
    //             : searchTerm.toLowerCase();

    //C2
    // let searchPaymentMethod;
    // switch (searchTerm.toLowerCase()) {
    //     case 'Ví Momo':
    //         searchPaymentMethod = 'momo';
    //         break;
    //     case 'Khi nhận hàng':
    //         searchPaymentMethod = 'cash';
    //         break;
    //     case 'Ví ngân hàng':
    //         searchPaymentMethod = 'credit card';
    //         break;
    //     default:
    //         searchPaymentMethod = searchTerm.toLowerCase();
    // }
    const filteredData = data.filter(order => {
        const isInSearchTerm = order.account.firstname.toLowerCase().includes(searchTerm.toLowerCase()) ||
            order.account.lastname.toLowerCase().includes(searchTerm.toLowerCase()) ||
            ((order.account.firstname.toLowerCase().includes(searchFirstName)) &&
                (order.account.lastname.toLowerCase().includes(searchLastName))) ||

            order.orderdate.toLowerCase().includes(searchTerm.toLowerCase()) ||
            (order.discount && order.discount.code.toLowerCase().includes(searchTerm.toLowerCase())) ||
            order.account.phonenumber.toLowerCase().includes(searchTerm.toLowerCase())

        //không tìm được Hình thức thanh toán 
        //(order.checkout && order.checkout.method.toLowerCase().includes(searchPaymentMethod))
        let isInSearchStatus = true;
        if (searchStatus === 'Chờ xác nhận') {
            return isInSearchStatus = order.orderStatus?.status === 'watingConfirmation';
        } else if (searchStatus === 'Chờ lấy hàng') {
            return isInSearchStatus = order.orderStatus?.status === 'Processing';
        } else if (searchStatus === 'Đang đến') {
            return isInSearchStatus = order.orderStatus?.status === 'beingTransported';
        } else if (searchStatus === 'Đã giao') {
            return isInSearchStatus = order.orderStatus?.status === 'Completed';
        } else if (searchStatus === 'Đã hủy') {
            return isInSearchStatus = order.orderStatus?.status === 'Cancel';
        }
        let isInSearchCheckout = true;
        if (searchCheckout === 'Khi nhận hàng') {
            return isInSearchCheckout = order.checkout?.method === 'cash';
        } else if (searchCheckout === 'Ví ngân hàng') {
            return isInSearchCheckout = order.checkout?.method === 'credit card';
        } else if (searchCheckout === 'Ví Momo') {
            return isInSearchCheckout = order.checkout?.method === 'momo';
        }

        return isInSearchTerm && isInSearchStatus && isInSearchCheckout;

    });
    const handleReset = async () => {
        setSearchTerm('');
        setSearchStatus('');
        setSearchCheckout('');
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
        // {
        //     field: 'address', headerName: 'Địa chỉ', width: 120,
        //     valueGetter: (params) => {
        //         const address = params.row.account?.address;
        //         //console.log(address)
        //         if (address) {
        //             //return `${address.street || '_'}, ${address.ward || '_'}, ${address.district || '_'}, ${address.province || '_'}`;
        //             return `${address.district || '_'}, ${address.province || '_'}`;

        //         }
        //         return '';
        //         // <div>
        //         //     {params.row.account.address ? (
        //         //         <div>
        //         //             <p>{params.row.account.address.street ? params.row.account.address.street + ', ' : '_, '}
        //         //                 {params.row.account.address.ward ? params.row.account.address.ward + ', ' : '_, '}
        //         //                 {params.row.account.address.district ? params.row.account.address.district + ', ' : '_, '}
        //         //                 {params.row.account.address.province ? params.row.account.address.province + '. ' : '_'}
        //         //             </p>
        //         //         </div>
        //         //     ) : (
        //         //         <p></p>
        //         //     )}
        //         // </div>
        //     },
        // },
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
                    const tableDetailLink = role === "admin"
                        ? `/admin/orderManagement/orderDetail/${params.row.id}`
                        : `/staff/orderManagement/orderDetail/${params.row.id}`;
                    return (
                        <div className="cellAction">
                            <Link to={tableDetailLink} style={{ textDecoration: "none" }}>
                                <div className="viewButton">Xem chi tiết</div>
                            </Link>
                            {/* <div
                                className="deleteButton"
                                onClick={() => handleDelete(params.row.id)}
                            >
                                <DeleteIcon />
                            </div> */}
                            <div
                                className="activeButton"
                                onClick={() => handleUpdateStatus(params.row.id, params.row.orderStatus.status)}
                            >
                                {statusText}
                            </div>
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
                            {/* <div
                                className="deleteButton"
                                onClick={() => handleDelete(params.row.id)}
                            >
                                <DeleteIcon />
                            </div> */}
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
            const cancelOrderResponse = await axios.put(`${backendUrl}/api/order/${id}/5`)

            if (cancelOrderResponse.status === 200) {
                setMessage('Hủy đơn hàng thành công!');
                //setData(data.filter((item) => item.id !== id));
                // const billId = cancelOrderResponse.data.bill.id;
                // console.log('idbill', billId)
                // // Gửi yêu cầu API để xóa id của hóa đơn
                // const deleteBillIdResponse = await axios.delete(`http://localhost:8080/api/bill/${billId}`);

                // if (deleteBillIdResponse.status === 200) {
                //     // Nếu xóa id của hóa đơn thành công, hiển thị thông báo thành công
                //     setMessage('Hủy đơn hàng thành công!');
                //     // Cập nhật danh sách đơn đặt hàng hoặc thực hiện các thao tác cần thiết khác
                // }

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
                <p className="textStatus">Trạng thái </p>
                <select className="selectStatus" value={searchStatus} onChange={handleSearchStatusChange}>
                    <option value="">Tất cả</option>
                    <option value="Chờ xác nhận">Chờ xác nhận</option>
                    <option value="Chờ lấy hàng">Chờ lấy hàng</option>
                    <option value="Đang đến">Đang đến</option>
                    <option value="Đã giao">Đã giao</option>
                    <option value="Đã hủy">Đã hủy</option>
                </select>
                <p className="textStatus">Hình thức thanh toán </p>
                <select className="selectStatus" value={searchCheckout} onChange={handleSearchCheckoutChange}>
                    <option value="">Tất cả</option>
                    <option value="Khi nhận hàng">Khi nhận hàng</option>
                    <option value="Ví ngân hàng">Ví ngân hàng</option>
                    <option value="Ví Momo">Ví Momo</option>

                </select>
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

export default AllTableOrderMngt