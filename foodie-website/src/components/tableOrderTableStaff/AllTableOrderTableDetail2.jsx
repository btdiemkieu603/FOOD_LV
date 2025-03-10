
import "../../css/mngtTableHome.css";
import { DataGrid } from '@mui/x-data-grid';
import { Link } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const AllTableOrderTableDetail2 = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [searchStatus, setSearchStatus] = useState('');
    const [searchCheckout, setSearchCheckout] = useState('');


    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/orderTable/all`);
            const accountsResponse = await axios.get(`${backendUrl}/api/account/2`);
            const tablesResponse = await axios.get(`${backendUrl}/api/tableInfo/all`);

            //const enrichedData = response.data.map((order) => {
            const enrichedData = response.data
                .filter((order) => order.orderTableId) // Lọc chỉ lấy các order đã tồn tại
                .map((order) => {
                    const account = accountsResponse.data.find(
                        (acc) => acc.id === order.accountId
                    );
                    const table = tablesResponse.data.find(
                        (tbl) => tbl.tableId === order.tableId
                    );

                    return {
                        ...order,
                        id: order.orderTableId,
                        account,
                        table,
                    };
                });

            setData(enrichedData);
            setLoading(false);
        } catch (error) {
            console.error("Error fetching data:", error);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const filteredData = data.filter((order) => {
        const search = searchTerm.toLowerCase();
        return (
            order.account?.firstname.toLowerCase().includes(search) ||
            order.account?.lastname.toLowerCase().includes(search) ||
            order.table?.tableNumber.toString().includes(search) ||
            order.createdAt.toLowerCase().includes(search)
        );
    });

    const handleReset = () => {
        setSearchTerm("");
        fetchData();
    };

    const columns = [
        { field: "orderTableId", headerName: "ID", width: 50 },
        {
            field: "fullName",
            headerName: "Nhân viên",
            width: 150,
            valueGetter: (params) =>
                `${params.row.account?.firstname || ""} ${params.row.account?.lastname || ""}`,
        },
        {
            field: "tableNumber",
            headerName: "Bàn",
            width: 50,
            valueGetter: (params) => params.row.table?.tableNumber || "",
        },
        { field: "createdAt", headerName: "Ngày đặt", width: 150 },
        { field: "totalAmount", headerName: "Tổng tiền", width: 120 },
    ];

    const actionColumn = [
        {
            field: "action",
            headerName: "",
            width: 200,
            renderCell: (params) => (
                <div className="cellAction">
                    <Link
                        to={`/admin/orderManagement/orderDetail/${params.row.orderTableId}`}
                        style={{ textDecoration: "none" }}
                    >
                        <div className="viewButton">Xem chi tiết</div>
                    </Link>
                    <div
                        className="deleteButton"
                        onClick={() => handleDelete(params.row.orderTableId)}
                    >
                        <DeleteIcon />
                    </div>
                </div>
            ),
        },
    ];

    const handleDelete = async (id) => {
        try {
            await axios.delete(`${backendUrl}/api/orderTable/${id}`);
            setData(data.filter((item) => item.orderTableId !== id));
        } catch (error) {
            console.error("Error deleting order:", error);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="datatable">
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Tìm kiếm theo họ tên, số bàn, ngày đặt"
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <RefreshIcon
                    title="Tải lại dữ liệu"
                    onClick={handleReset}
                    style={{ cursor: "pointer", marginLeft: "10px" }}
                />
            </div>
            <div style={{ height: 578, width: "100%" }}>
                <DataGrid
                    className="datagrid"
                    rows={filteredData}
                    columns={columns.concat(actionColumn)}
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 9 },
                        },
                    }}
                    pageSizeOptions={[9]}
                    checkboxSelection
                />
            </div>
        </div>
    );
};

export default AllTableOrderTableDetail2;
