import React, { useEffect, useState } from 'react';
import "../statistical/statistical.css"
import Sidebar from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';
import Widget from '../../components/widget/Widget';
import Featured from '../../components/featured/Featured';
import Chart from '../../components/chart/Chart';
import TableOrderBy from '../../components/table/TableOrderBy';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import SidebarStaff from '../../components/sidebar/SidebarStaff';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const Home = () => {
    const [orders, setOrders] = useState([]);
    const [revenue, setRevenue] = useState(null);
    const [revenueToday, setRevenueToday] = useState(0);
    const [expectedRevenue, setexpRevenue] = useState(500000);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;


    useEffect(() => {
        fetchTodayRevenue();
    }, []);
    const fetchTodayRevenue = async () => {
        try {
            const today = new Date();
            console.log('getdateToday', today)
            const response = await axios.get(`${backendUrl}/api/order/all`);
            console.log('data', response.data)
            const sortedOrders = response.data.sort((a, b) => b.id - a.id); // Sắp xếp các đơn hàng theo ID giảm dần
            const top5Orders = sortedOrders.slice(0, 5); // Chọn 5 đơn hàng đầu tiên
            setOrders(top5Orders);
            if (!Array.isArray(response.data)) {
                throw new Error('Data returned from API is not an array');
            }
            const todayOrders = response.data.filter(order => {
                if (order.orderStatus && order.orderStatus.status === "Completed") {
                    //định dạng ngày trong bill: dd/mm/yyyy (18/04/2024)
                    // const [day, month, year] = order.issuedate.split('/');
                    // const orderDate = new Date(`${year}-${month}-${day}`);

                    //định dạng ngày trong bill: dd/mm/yyyy hh:pp:ss (18/04/2024 16:44:20)
                    // Phân tách ngày, tháng, năm từ issuedate
                    const [datePart, timePart] = order.orderdate.split(' '); // Tách phần ngày và phần giờ
                    const [day, month, year] = datePart.split('/');
                    //const [hours, minutes, seconds] = timePart.split(':'); // Tách giờ, phút, giây

                    // Tạo đối tượng Date từ thông tin được phân tách
                    //const orderDate = new Date(year, month - 1, day, hours, minutes, seconds); // Month trong JavaScript bắt đầu từ 0
                    const orderDate = new Date(`${year}-${month}-${day}`);  //như này cũng đúng

                    console.log('dateInBill', orderDate)
                    return (
                        orderDate.getDate() === today.getDate() &&
                        orderDate.getMonth() === today.getMonth() &&
                        orderDate.getFullYear() === today.getFullYear()
                    );
                }
                return false;
            });
            // Kiểm tra xem có đơn hàng nào trong ngày không
            if (todayOrders.length === 0) {
                setRevenueToday(null); // Trả về null nếu không có đơn hàng nào trong ngày
                return;
            }
            const todayRevenue = todayOrders.reduce((total, order) => total + order.bill.totalprice, 0);
            console.log('to', todayRevenue)
            setRevenueToday(todayRevenue);
        } catch (error) {
            setError('Error fetching today revenue. Please try again later.');
        }
    };
    if (error) {
        return <div>{error}</div>;
    }
    return (
        <div className='home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="homeContainer">
                <Navbar title="FOODIE-ADMIN" />
                <div className="widgets">
                    <Widget type="product" />
                    <Widget type="order" />
                    <Widget type="discount" />
                    <Widget type="user" />
                    <Widget type="review" />
                </div>
                <div className="charts">
                    <Featured revenue={revenueToday} expected={expectedRevenue} type="revenueToday" />
                    <Chart />
                </div>

                <div className="listContainer">
                    <div className="listTitle">Đơn hàng gần đây (5)</div>
                    <TableOrderBy orders={orders} />
                </div>
            </div>
        </div>
    )
}

export default Home