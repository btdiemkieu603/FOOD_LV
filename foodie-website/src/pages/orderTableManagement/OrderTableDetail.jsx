import "../../css/mngtDetail.css";
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import { Link, useLocation, useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from "axios";

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const OrderTableDetail = () => {
    const { orderTableId } = useParams();
    const [orderTable, setOrderTable] = useState(null);

    const location = useLocation();
    const pathname = location.pathname;

    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;
    const OrderHomeLink = role === "admin"
        ? "/admin/orderManagement"
        : role === "staff"
            ? "/staff/orderManagement"
            : "/";

    useEffect(() => {
        const fetchData = async () => {
            console.log("orderTableId", orderTableId);
            try {
                const response = await axios.get(`${backendUrl}/api/orderTable/${orderTableId}`);
                setOrderTable(response.data);

            } catch (error) {
                console.error('Error fetching order table data:', error);
            }
        };
        fetchData();
    }, [orderTableId]);

    if (!orderTable) {
        return <div>Không tìm thấy thông tin đặt món.</div>;
    }

    const calculateTotalPrice = () => {
        let totalPrice = 0;
        orderTable.tableItems.forEach(item => {
            totalPrice += item.price;
        });
        return totalPrice;
    };

    const consolidatedItems = (items) => {
        const consolidated = {};
        items.forEach(item => {
            if (consolidated[item.productId]) {
                consolidated[item.productId].quantity += item.quantity;
                consolidated[item.productId].price += item.price;
            } else {
                consolidated[item.productId] = { ...item };
            }
        });
        return Object.values(consolidated);
    };

    return (
        <div className="single home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="singleContainer homeContainer">
                <Navbar title={`Đặt món tại bàn ${orderTable.tableId}`} />
                <Link to={OrderHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="top">
                    <div className="left">
                        <h1 className="title">Thông tin đặt món</h1>

                        <h3 className="itemTitle2">Mã đơn: {orderTable.orderTableId}</h3>
                        <div className="item2">
                            <div className="details2">
                                <div className="detailItem2">
                                    <span className="itemKey2">Bàn:</span>
                                    <span className="itemValue">{orderTable.tableId}</span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Tổng tiền:</span>
                                    <span className="itemValue">{calculateTotalPrice().toLocaleString()} đ</span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Ngày đặt:</span>
                                    <span className="itemValue">{orderTable.createdAt}</span>
                                </div>
                            </div>
                        </div>

                        <div>
                            <TableContainer component={Paper} className="table">
                                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell className="tableCell">STT</TableCell>
                                            <TableCell className="tableCell">Mã món</TableCell>
                                            <TableCell className="tableCell">Số lượng</TableCell>
                                            <TableCell className="tableCell">Tổng tiền</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {consolidatedItems(orderTable.tableItems).map((item, index) => (
                                            <TableRow key={index}>
                                                <TableCell>{index + 1}</TableCell>
                                                <TableCell>{item.productId}</TableCell>
                                                <TableCell>{item.quantity}</TableCell>
                                                <TableCell>{item.price.toLocaleString()} đ</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>
                    </div>
                </div>
                <div className="bottom">
                    <h1 className="title">Trạng thái đơn hàng</h1>
                    <div className="status">
                        <p>{orderTable.orderStatusId === 4 ? "Đã thanh toán" : "Đặt món"}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrderTableDetail;
