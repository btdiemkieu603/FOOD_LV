
import React, { useState, useEffect } from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Legend, Tooltip } from 'recharts';
import axios from 'axios';
import "./chart.css";

const backendUrl = process.env.REACT_APP_BACKEND_URL;
const ProductPieChart = () => {
    const [productData, setProductData] = useState([]);
    const [totalSales, setTotalSales] = useState(0);
    useEffect(() => {
        //sai vì tính all số suất chưa hoàn thành
        // const fetchData = async () => {
        //     try {
        //         const response = await axios.get('http://localhost:8080/api/product/');
        //         const productSalesPromises = response.data.map(async product => {
        //             const salesResponse = await axios.get(`http://localhost:8080/api/order/purchases/${product.id}`);
        //             return {
        //                 name: product.name,
        //                 value: salesResponse.data
        //             };
        //         });
        //         // Chờ tất cả các Promise hoàn thành
        //         const productSalesData = await Promise.all(productSalesPromises);

        //         const filteredProductData = productSalesData.filter(product => product.value > 0);
        //         setProductData(filteredProductData);
        //         const total = filteredProductData.reduce((acc, cur) => acc + parseInt(cur.value), 0);
        //         setTotalSales(total);
        //     } catch (error) {
        //         console.error('Error fetching product sales:', error);
        //     }
        // };
        const fetchData = async () => {
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
                        value: totalQuantitySold
                    });
                });

                const filteredProductData = productSales.filter(product => product.value > 0);
                setProductData(filteredProductData);
                // Tính tổng số lượng đã bán của tất cả sản phẩm
                const totalSales = productSales.reduce((total, product) => total + product.value, 0);
                setTotalSales(totalSales);

                console.log('Product sales:', productSales);
                console.log('Total sales:', totalSales);

                // Cập nhật state hoặc thực hiện các thao tác khác dựa trên dữ liệu lấy được
            } catch (error) {
                console.error('Error fetching product sales:', error);
            }
        };

        fetchData();
    }, []);

    // const COLORS = [
    //     '#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#ef4a4a', '#8794ec', '#f0b0f6', '#f0e369'
    //     // 'rgba(255, 99, 132, 0.2)',
    //     // 'rgba(54, 162, 235, 0.2)',
    //     // 'rgba(255, 206, 86, 0.2)',
    //     // 'rgba(75, 192, 192, 0.2)',
    //     // 'rgba(153, 102, 255, 0.2)',
    //     // 'rgba(255, 159, 64, 0.2)',
    // ];
    const COLORS = [
        '#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#ef4a4a', '#8794ec', '#f0b0f6', '#f0e369',
        '#7fc97f', '#beaed4', '#fdc086', '#ffff99', '#386cb0', '#f0027f', '#bf5b17', '#666666'
    ];
    const RADIAN = Math.PI / 180;
    const renderLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent }) => {
        const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
        const x = cx + radius * Math.cos(-midAngle * RADIAN);
        const y = cy + radius * Math.sin(-midAngle * RADIAN);

        return (
            <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} dominantBaseline="central">
                {`${(percent * 100).toFixed(1)}%`}
            </text>
        );
    };

    return (
        <div className='chart'>
            <h1 className="title">Đánh giá tỷ lệ bán của các món ăn</h1>
            <ResponsiveContainer width="100%" aspect={5 / 2}>
                <PieChart>
                    <Pie
                        data={productData}
                        cx="50%"
                        cy="50%"
                        labelLine={false}
                        label={renderLabel}
                        outerRadius={150}
                        fill="#8884d8"
                        dataKey="value"
                    >
                        {productData.map((entry, index) => (
                            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                        ))}
                    </Pie>
                    <Legend
                        align="right"
                        verticalAlign="middle"
                        layout="vertical"
                        // height={36}
                        formatter={(value, entry, index) => <span style={{ color: COLORS[index] }}>{value}</span>}
                    />
                    {/* <Tooltip formatter={(value, name, props) => [`${name}: ${value}`, `Tổng số tiền: ${props.value} VNĐ`]} /> */}
                    <Tooltip
                        formatter={(value, name, props) => {
                            const percent = ((props.value / productData.reduce((acc, cur) => acc + cur.value, 0)) * 100).toFixed(1);
                            //return [`${name}: ${value}`, `Tỉ lệ: ${percent}%`];  
                            return [`${name}: ${value} suất chiếm ${percent}%`];
                        }}
                    />
                </PieChart>
                <div className="total-sales">
                    Tổng số suất đã bán: {totalSales} suất
                </div>
            </ResponsiveContainer>

        </div >
    );
}

export default ProductPieChart;
