import React, { useEffect, useState } from 'react';
import "./statistical.css"
import Sidebar from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';
import axios from 'axios';

import CustomerStatDate from '../../components/chart/CustomerStatDate';
import LoyalCustomer from '../../components/table/tableLoyalCustomer';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const CustomerStat = () => {
    const [customerOrder, setCustomerOrder] = useState([]);
    const [error, setError] = useState(null);
    useEffect(() => {
        const fetchOrdersData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/order/all`);
                // Lọc chỉ lấy các đơn hàng đã hoàn thành
                const completedOrders = response.data.filter(order =>
                    order.orderStatus && order.orderStatus.status === "Completed"
                );
                setCustomerOrder(completedOrders);
                // console.log(completedOrders)
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
                <Navbar title="Thống kê khách hàng" />
                <div className="listRevenue">
                    < CustomerStatDate data={customerOrder} />
                </div>
                <div className="listContainer">
                    <div className="listTitle">Bảng thống kê các khách hàng thân thiết</div>
                    <LoyalCustomer />
                </div>
            </div>
        </div>
    )
}

export default CustomerStat;