import React, { useEffect, useState } from 'react';
import "./statistical.css"
import Sidebar from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';

import TableOrderBy from '../../components/table/TableOrderBy';
import axios from 'axios';
import RevenueDateChart from '../../components/chart/RevenueDateChart';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const Revenue = () => {
    const [orders, setOrders] = useState([]);
    const [revenueDate, setRevenueDate] = useState([]);
    const [error, setError] = useState(null);
    useEffect(() => {
        const fetchOrdersData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/order/all`);
                // Lọc chỉ lấy các đơn hàng đã hoàn thành
                const completedOrders = response.data.filter(order =>
                    order.orderStatus && order.orderStatus.status === "Completed"
                );
                const sortedOrders = completedOrders.sort((a, b) => b.id - a.id); // Sắp xếp các đơn hàng theo ID giảm dần
                const top5Orders = sortedOrders.slice(0, 5); // Chọn 5 đơn hàng đầu tiên
                setOrders(top5Orders);
                // Lấy danh sách các ID của các hóa đơn từ các đơn hàng đã hoàn thành
                const billIds = completedOrders.map(order => order.bill.id);

                // Lấy tất cả các hóa đơn dựa trên danh sách ID hóa đơn
                const responseBills = await Promise.all(billIds.map(async id => {
                    const billResponse = await axios.get(`${backendUrl}/api/bill/${id}`);
                    return billResponse.data;
                }));
                setRevenueDate(responseBills);
                // Đặt danh sách hóa đơn vào state hoặc thực hiện các thao tác khác với dữ liệu này
                console.log("All bills from completed orders:", responseBills);

            } catch (error) {
                setError('Error fetching orders data. Please try again later.');
            }
        };
        fetchOrdersData();
    }, []);
    if (error) {
        return <div>{error}</div>;
    }
    return (
        <div className='home'>
            <Sidebar />
            <div className="homeContainer">
                <Navbar title="Thống kê doanh thu" />
                <div className="listRevenue">
                    < RevenueDateChart data={revenueDate} />
                </div>

                <div className="listContainer">
                    <div className="listTitle">Đơn hàng đã giao gần đây (5)</div>
                    <TableOrderBy orders={orders} />
                </div>
            </div>
        </div>
    )
}

export default Revenue