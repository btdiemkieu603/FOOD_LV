
import "../../css/mngtTableHome.css";
import { DataGrid } from '@mui/x-data-grid';
import { Link } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const AllOrderTableMngtHome = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [searchStatus, setSearchStatus] = useState('');
    // const [searchCheckout, setSearchCheckout] = useState('');
    const [searchTable, setSearchTable] = useState(""); // State for selected table
    const [tables, setTables] = useState([]); // List of all tables from `tableInfo`

    const [dataAll, setDataAll] = useState([]);
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/orderTable/all`);
            setData(response.data);
            const accountsResponse = await axios.get(`${backendUrl}/api/account/2`);
            const tablesResponse = await axios.get(`${backendUrl}/api/tableInfo/all`);
            const enrichedData = response.data
                .filter((order) => order.orderTableId) // Lọc chỉ lấy các order đã tồn tại
                .map((order) => {
                    const account = accountsResponse.data.find(
                        (acc) => acc.id === order.accountId
                    );
                    const table = tablesResponse.data.find(
                        (tbl) => tbl.tableId === order.tableId
                    );
                    // // Chuyển đổi createdAt thành chỉ ngày `dd/MM/yyyy`
                    // const [day, month, year] = order.createdAt.split(' ')[0].split('-');
                    // const formattedDate = `${day}/${month}/${year}`; // Chuẩn hóa thành dd/MM/yyyy


                    // Chuyển đổi `createdAt` thành định dạng `dd/MM/yyyy HH:mm`
                    const [datePart, timePart] = order.createdAt.split(' '); // Tách ngày và giờ
                    const [day, month, year] = datePart.split('-'); // Tách ngày, tháng, năm
                    const formattedDate = `${day}/${month}/${year} ${timePart}`; // Kết hợp lại

                    return {
                        ...order,
                        id: order.orderTableId,
                        account,
                        table,
                        // formattedCreatedAt: formatDateTime(order.createdAt),
                        formattedDate,
                    };
                });
            setDataAll(enrichedData);
            setTables(tablesResponse.data);
            console.log("data", data);
            console.log("dataAll", dataAll);
            console.log("table", tables);
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

    // const handleSearchCheckoutChange = (event) => {
    //     setSearchCheckout(event.target.value);
    // };
    const handleSearchTableChange = (event) => {
        setSearchTable(event.target.value); // Cập nhật giá trị bàn được chọn
    };
    const [searchFirstName, searchLastName] = searchTerm.toLowerCase().split(' ');

    const filteredData = dataAll.filter(order => {
        // Tìm kiếm theo họ tên, SĐT, ngày mua, mã giảm giá
        const isInSearchTerm = (
            // Giả sử bạn có thêm các trường như firstname, lastname, phonenumber, etc.
            (order.account && order.account.firstname && order.account.firstname.toLowerCase().includes(searchTerm.toLowerCase())) ||
            (order.account && order.account.lastname && order.account.lastname.toLowerCase().includes(searchTerm.toLowerCase())) ||
            (order.table?.tableNumber.toString().includes(searchTerm.toLowerCase())) ||
            // (order.createdAt && order.createdAt.toLowerCase().includes(searchTerm.toLowerCase()))
            //(order.formattedCreatedAt && order.formattedCreatedAt.toLowerCase().includes(searchTerm.toLowerCase()))
            //(order.formattedDate.includes(searchTerm))
            order.formattedDate.split(' ')[0].includes(searchTerm.toLowerCase())
        );

        // Tìm kiếm theo trạng thái đơn hàng
        let isInSearchStatus = true;
        if (searchStatus) {
            switch (searchStatus) {
                case 'Đặt món':
                    isInSearchStatus = order.orderStatusId === 3; // Giả sử 3 là 'Đang đến'
                    break;
                case 'Hoàn thành':
                    isInSearchStatus = order.orderStatusId === 4; // Giả sử 4 là 'Đã giao'
                    break;
                case 'Đã hủy':
                    isInSearchStatus = order.orderStatusId === 5; // Giả sử 5 là 'Đã hủy'
                    break;
                default:
                    isInSearchStatus = true;
            }
        }
        // let isInSearchCheckout = true;
        // if (searchCheckout) {
        //     switch (searchCheckout) {
        //         case '1':
        //             isInSearchCheckout =  order.checkout?.method === 'cash';
        //             break;
        //         case 'Ví ngân hàng':
        //             isInSearchCheckout = order.checkout?.method === 'credit card';
        //             break;
        //         case 'Ví Momo':
        //             isInSearchCheckout = order.checkout?.method === 'momo';
        //             break;
        //         default:
        //             isInSearchTable = true;
        //     }
        // }
        // Tìm kiếm theo hình thức thanh toán
        const isInSearchTable =
            !searchTable || order.table?.tableId.toString() === searchTable;


        return isInSearchTerm && isInSearchStatus && isInSearchTable;
    });

    const handleReset = async () => {
        setSearchTerm('');
        setSearchStatus('');
        // setSearchCheckout('');
        setSearchTable("");
        fetchData();
    };
    //lỗi, không chuyển được định dạng
    // const formatDateTime = (dateString) => {
    //     const date = new Date(dateString); // Chuyển đổi từ chuỗi gốc sang đối tượng Date
    //     const formattedDate = new Intl.DateTimeFormat('vi-VN', {
    //         day: '2-digit',
    //         month: '2-digit',
    //         year: 'numeric',
    //         hour: '2-digit',
    //         minute: '2-digit',
    //         hour12: false,
    //     }).format(date);
    //     return formattedDate;
    // };

    //dạng 12:23 17/11/2024
    // const formatDateTime = (dateString) => {
    //     try {
    //         // Chuyển đổi định dạng "DD-MM-YYYY HH:mm" thành "YYYY-MM-DDTHH:mm"
    //         const [datePart, timePart] = dateString.split(' ');
    //         const [day, month, year] = datePart.split('-');
    //         const isoString = `${year}-${month}-${day}T${timePart}`;
    //         const date = new Date(isoString); // Tạo đối tượng Date từ chuỗi ISO
    //         const formattedDate = new Intl.DateTimeFormat('vi-VN', {
    //             day: '2-digit',
    //             month: '2-digit',
    //             year: 'numeric',
    //             hour: '2-digit',
    //             minute: '2-digit',
    //             hour12: false,
    //         }).format(date);
    //         return formattedDate;
    //     } catch (error) {
    //         console.error("Error formatting date:", error, "Input:", dateString);
    //         return dateString; // Trả về chuỗi gốc nếu có lỗi
    //     }
    // };
    const columns = [
        { field: "orderTableId", headerName: "ID", width: 70 },
        {
            field: 'fullName',
            headerName: 'Nhân viên order',
            description: 'firstname lastname',
            sortable: false,
            width: 150,
            valueGetter: (params) =>
                `${params.row.account?.firstname || ''} ${params.row.account?.lastname || ''}`,
        },
        // { field: 'tableId', headerName: 'Bàn', width: 80 },
        {
            field: "tableNumber",
            headerName: "Bàn",
            width: 50,
            valueGetter: (params) => params.row.table?.tableNumber || "",
        },
        {
            field: 'formattedDate', headerName: 'Ngày đặt hàng', width: 150,
            // field: 'createdAt', headerName: 'Ngày đặt hàng', width: 150,
            // valueGetter: (params) => params.row.formattedCreatedAt || '',
        },
        {
            field: 'totalAmount',
            headerName: 'Tổng tiền',
            width: 120,
            valueGetter: (params) =>
                `${params.row.totalAmount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}`,
        },
        {
            field: "orderStatusId",
            headerName: "Trạng thái",
            width: 150,
            renderCell: (params) => {
                const statusId = params.row.orderStatusId;
                let statusText = '';

                switch (statusId) {
                    case 3:
                        statusText = 'Đặt món';
                        break;
                    case 4:
                        statusText = 'Hoàn thành';
                        break;
                    case 5:
                        statusText = 'Đã hủy';
                        break;
                    default:
                        statusText = '';
                }

                return (
                    <div className={`cellWithStatus status-${statusId}`}>
                        {statusText}
                    </div>
                );
            },
        },
    ];

    const actionColumn = [
        {
            field: "action",
            headerName: "",
            width: 350,
            renderCell: (params) => {
                const statusId = params.row.orderStatusId;
                let statusText = '';

                switch (statusId) {
                    case 3:
                        statusText = 'Thanh toán';
                        break;
                    // case 4:
                    //     statusText = 'Hoàn thành';
                    //     break;
                    // case 5:
                    //     statusText = 'Hủy';
                    // break;
                    // Thêm các trạng thái khác nếu cần
                    default:
                        statusText = '';
                }

                const handleDelete = async (id) => {
                    setShowProgressBar(true);
                    try {
                        const response = await axios.delete(`${backendUrl}/api/orderTable/${id}`, {
                            headers: {
                                'Content-Type': 'application/json',
                            }
                        });
                        if (response.status === 200) {
                            setMessage('Xóa đơn hàng thành công!');
                            setData(data.filter((item) => item.orderTableId !== id));
                        } else {
                            setMessage('Xóa đơn hàng thất bại.');
                        }
                    } catch (error) {
                        console.error('Error deleting order:', error);
                        setMessage('Lỗi khi xóa đơn hàng!');
                    }
                };

                const handleUpdateStatus = async (id, statusId) => {
                    let newStatusId = statusId;

                    switch (statusId) {
                        case 3:
                            newStatusId = 4; // Chờ lấy hàng
                            break;
                        // case 2:
                        //     newStatusId = 3; // Đang đến
                        //     break;
                        // case 3:
                        //     newStatusId = 4; // Đã giao
                        //     break;
                        // Thêm các chuyển đổi trạng thái khác nếu cần
                        default:
                            return;
                    }
                    setShowProgressBar(true);
                    try {
                        const response = await axios.put(`${backendUrl}/api/orderTable/${id}/status/${newStatusId}`
                            //     , {
                            //     orderStatusId: newStatusId
                            // }
                        );

                        if (response.status === 200) {
                            setMessage('Cập nhật trạng thái thành công!');

                            // Cập nhật trạng thái trong state
                            setData(data.map(order => {
                                if (order.orderTableId === id) {
                                    return { ...order, orderStatusId: newStatusId };
                                }
                                return order;
                            }));
                        } else {
                            setMessage('Cập nhật trạng thái thất bại.');
                        }
                    } catch (error) {
                        console.error('Error updating status:', error);
                        setMessage('Lỗi khi cập nhật trạng thái!');
                    }
                };

                const handleCancelOrder = async (id) => {
                    setShowProgressBar(true);
                    try {
                        const response = await axios.put(`${backendUrl}/api/orderTable/${id}/status/5`);

                        if (response.status === 200) {
                            setMessage('Hủy đơn hàng thành công!');
                            setData(data.map(order => {
                                if (order.orderTableId === id) {
                                    return { ...order, orderStatusId: 5 }; // Đã hủy
                                }
                                return order;
                            }));
                        } else {
                            setMessage('Hủy đơn hàng thất bại.');
                        }
                    } catch (error) {
                        console.error('Error cancelling order:', error);
                        setMessage('Lỗi khi hủy đơn hàng!');
                    }
                };

                return (
                    <div className="cellAction">
                        {/* <Link to={`/admin/orderTableManagement/orderTableDetail/${params.row.orderTableId}`} style={{ textDecoration: "none" }}>
                            <div className="viewButton">Xem chi tiết</div>
                        </Link> */}
                        {/* <div
                            className="deleteButton"
                            onClick={() => handleDelete(params.row.orderTableId)}
                        >
                            <DeleteIcon />
                        </div> */}
                        {statusId !== 4 && statusId !== 5 && (
                            <div
                                className="activeButton"
                                onClick={() => handleUpdateStatus(params.row.orderTableId, statusId)}
                            >
                                {statusText}
                            </div>
                        )}
                        {statusId === 3 && (
                            <div
                                className="editButton"
                                onClick={() => handleCancelOrder(params.row.orderTableId)}
                            >
                                Hủy
                            </div>
                        )}
                    </div>
                );
            },
        },
    ];

    const sortModel = [
        {
            field: 'orderTableId',
            sort: 'desc',
        },
    ];

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="datatable">
            {message && (
                <div className="success-message">
                    {message}
                    {showProgressBar && <div className="progress-bar" />}
                </div>
            )}
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Tìm kiếm theo họ tên nhân viên, ngày mua"
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <p className="textStatus">Trạng thái </p>
                <select className="selectStatus" value={searchStatus} onChange={handleSearchStatusChange}>
                    <option value="">Tất cả</option>
                    <option value="Đặt món">Đặt món</option>
                    <option value="Hoàn thành">Hoàn thành</option>
                    <option value="Đã hủy">Đã hủy</option>
                </select>
                <p className="textStatus">Số bàn</p>
                <select className="selectStatus" value={searchTable} onChange={handleSearchTableChange}>
                    <option value="">Tất cả</option>
                    {tables.map((table) => (
                        <option key={table.tableId} value={table.tableId}>
                            Bàn {table.tableNumber}
                        </option>
                    ))}
                </select>
                <RefreshIcon
                    title="Tải lại dữ liệu"
                    onClick={handleReset}
                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                />
            </div>
            <div style={{ height: 578, width: '100%' }}>
                <DataGrid
                    className="datagrid"
                    rows={filteredData}
                    columns={columns.concat(actionColumn)}
                    getRowId={(row) => row.orderTableId} // Đảm bảo DataGrid sử dụng đúng ID
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 10 },
                        },
                    }}
                    pageSizeOptions={[10, 20, 50]}
                    checkboxSelection
                    sortModel={sortModel}
                />
            </div>
        </div>
    )
}

export default AllOrderTableMngtHome;
