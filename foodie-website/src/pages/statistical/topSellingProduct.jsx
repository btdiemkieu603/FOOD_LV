import React, { useEffect, useState } from 'react';
import "./statistical.css"
import Sidebar from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';
import Featured from '../../components/featured/Featured';
import axios from 'axios';
import TableProduct from '../../components/table/TableProduct';

import ProductPieChart from '../../components/chart/PieChart';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const TopSellingProduct = () => {
    const [topSelling, setTopSelling] = useState([]);
    const [error, setError] = useState(null);
    const [dailySales, setDailySales] = useState(0);
    const [expectedRevenue, setexpRevenue] = useState(80);
    const [totalSales, setTotalSales] = useState(0);

    const [amountPro, setAmountPro] = useState(0);
    useEffect(() => {
        const fetchOrdersData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/order/all`);
                console.log('All orders:', response.data);

                // Lọc chỉ lấy các đơn hàng đã hoàn thành
                const completedOrders = response.data.filter(order =>
                    order.orderStatus && order.orderStatus.status === "Completed"
                );

                // Lấy danh sách tất cả các sản phẩm
                const responseProducts = await axios.get(`${backendUrl}/api/product/`);
                const products = responseProducts.data;
                setAmountPro(products.length);
                // Tạo một đối tượng để lưu tổng số lượng đã bán của từng sản phẩm
                const productSales = [];

                // Duyệt qua từng sản phẩm để tính tổng số lượng đã bán
                products.forEach(product => {
                    let totalQuantitySold = 0;

                    completedOrders.forEach(order => {
                        order.items.forEach(item => {
                            if (item.product.id === product.id) {
                                totalQuantitySold += item.quantity;
                            }
                        });
                    });

                    // Thêm thông tin về sản phẩm và số lượng đã bán vào mảng productSales
                    productSales.push({
                        id: product.id,
                        name: product.name,
                        price: product.price,
                        description: product.description,
                        category: product.category,
                        //url_image_product: product.url_image_product,
                        value: totalQuantitySold
                    });
                });

                // Sắp xếp lại các sản phẩm theo thứ tự từ cao đến thấp dựa trên số lượng đã bán
                const sortedProductData = productSales.sort((a, b) => b.value - a.value);
                setTopSelling(sortedProductData);
                // Tính tổng số lượng đã bán của tất cả sản phẩm
                const totalSales = productSales.reduce((total, product) => total + product.value, 0);
                setTotalSales(totalSales);

                // console.log('Product sales:', productSales);
                // console.log('Total sales:', totalSales);

                // Cập nhật state hoặc thực hiện các thao tác khác dựa trên dữ liệu lấy được
            } catch (error) {
                console.error('Error fetching product sales:', error);
            }
        };


        const fetchDailySales = async () => {
            try {
                const today = new Date()//.toLocaleDateString("en-GB"); // Lấy ngày hiện tại (dd/mm/yyyy)
                const response = await axios.get(`${backendUrl}/api/order/all`);
                //console.log('today', today)
                if (response.status === 200) {
                    //ngày trong order: dd/mm/yyyy (18/04/2024)
                    // const salesToday = response.data.filter(order => order.orderdate === today);

                    //ngày trong order: dd/mm/yyyy (18/04/2024 17:13:20)
                    const salesToday = response.data.filter(order => {
                        if (order.orderStatus && order.orderStatus.status === "Completed") {
                            const [datePart, timePart] = order.orderdate.split(' '); // Tách phần ngày và phần giờ
                            const [day, month, year] = datePart.split('/');
                            // const [hours, minutes, seconds] = timePart.split(':');
                            const orderDate = new Date(`${year}-${month}-${day}`);
                            //console.log('orderDate', orderDate)
                            return (
                                orderDate.getDate() === today.getDate() &&
                                orderDate.getMonth() === today.getMonth() &&
                                orderDate.getFullYear() === today.getFullYear()
                            );

                        }
                        return false;
                    });
                    // Kiểm tra xem có đơn hàng nào trong ngày không
                    if (salesToday.length === 0) {
                        setDailySales(null); // Trả về null nếu không có đơn hàng nào trong ngày
                        return;
                    }
                    const totalQuantity = salesToday.reduce((acc, order) => {
                        return acc + order.items.reduce((acc, item) => acc + item.quantity, 0);
                    }, 0);
                    setDailySales(totalQuantity);

                } else {
                    throw new Error('Network response was not ok');
                }
            } catch (error) {
                console.error('Error fetching daily sales:', error);
                setError('Error fetching daily sales. Please try again later.');
            }
        };

        fetchDailySales();
        fetchOrdersData();
    }, []);
    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className='home'>
            <Sidebar />
            <div className="homeContainer">
                <Navbar title="Thống kê món ăn bán chạy" />
                <div className="charts">
                    <Featured revenue={dailySales} expected={expectedRevenue} type="totalProToday" />
                    <ProductPieChart />
                </div>

                <div className="listContainer">
                    <div className="listTitle">Danh sách mức độ bán chạy của các Món ăn ({amountPro})</div>
                    <TableProduct orders={topSelling} totalPro={totalSales} />
                </div>
            </div>
        </div >
    )
}

export default TopSellingProduct