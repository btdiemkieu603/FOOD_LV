
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import "../../css/mngtHome.css"
// import Navbar from '../../components/navbar/Navbar';
// import Sidebar from '../../components/sidebar/Sidebar';
// import { Link } from "react-router-dom";

// import TabContent from "../../components/tab-content/tab-content";
// import AllTableStaffMngt from "../../components/tableStaffMngtHome/AllTableStaffMngt";
// import ActiveTableStaffMngt from "../../components/tableStaffMngtHome/ActiveTableStaffMngt";
// import BlockTableStaffMngt from "../../components/tableStaffMngtHome/BlockTableStaffMngt";
// import SidebarStaff from "../../components/sidebar/SidebarStaff";
// import FoodDisplay from "../../components/FoodDisplay/FoodDisplay";
// import ExploreMenu from "../../components/ExploreMenu/ExploreMenu";
// const backendUrl = process.env.REACT_APP_BACKEND_URL;
// const StaffOrderMenu = () => {
//     const [category, setCategory] = useState("All");
//     const [topSelling, setTopSelling] = useState([]);
//     const [error, setError] = useState(null);
//     const [dailySales, setDailySales] = useState(0);
//     const [expectedRevenue, setexpRevenue] = useState(80);
//     const [totalSales, setTotalSales] = useState(0);

//     const [amountPro, setAmountPro] = useState(0);
//     useEffect(() => {
//         const fetchOrdersData = async () => {
//             try {
//                 const response = await axios.get(`${backendUrl}/api/order/all`);
//                 console.log('All orders:', response.data);

//                 // Lọc chỉ lấy các đơn hàng đã hoàn thành
//                 const completedOrders = response.data.filter(order =>
//                     order.orderStatus && order.orderStatus.status === "Completed"
//                 );

//                 // Lấy danh sách tất cả các sản phẩm
//                 const responseProducts = await axios.get(`${backendUrl}/api/product/`);
//                 const products = responseProducts.data;
//                 setAmountPro(products.length);
//                 // Tạo một đối tượng để lưu tổng số lượng đã bán của từng sản phẩm
//                 const productSales = [];

//                 // Duyệt qua từng sản phẩm để tính tổng số lượng đã bán
//                 products.forEach(product => {
//                     let totalQuantitySold = 0;

//                     completedOrders.forEach(order => {
//                         order.items.forEach(item => {
//                             if (item.product.id === product.id) {
//                                 totalQuantitySold += item.quantity;
//                             }
//                         });
//                     });

//                     // Thêm thông tin về sản phẩm và số lượng đã bán vào mảng productSales
//                     productSales.push({
//                         id: product.id,
//                         name: product.name,
//                         price: product.price,
//                         description: product.description,
//                         category: product.category,
//                         url_image_product: product.url_image_product,
//                         value: totalQuantitySold
//                     });
//                 });

//                 // Sắp xếp lại các sản phẩm theo thứ tự từ cao đến thấp dựa trên số lượng đã bán
//                 const sortedProductData = productSales.sort((a, b) => b.value - a.value);
//                 setTopSelling(sortedProductData);
//                 // Tính tổng số lượng đã bán của tất cả sản phẩm
//                 const totalSales = productSales.reduce((total, product) => total + product.value, 0);
//                 setTotalSales(totalSales);

//                 // console.log('Product sales:', productSales);
//                 // console.log('Total sales:', totalSales);

//                 // Cập nhật state hoặc thực hiện các thao tác khác dựa trên dữ liệu lấy được
//             } catch (error) {
//                 console.error('Error fetching product sales:', error);
//             }
//         };


//         const fetchDailySales = async () => {
//             try {
//                 const today = new Date()//.toLocaleDateString("en-GB"); // Lấy ngày hiện tại (dd/mm/yyyy)
//                 const response = await axios.get(`${backendUrl}/api/order/all`);
//                 //console.log('today', today)
//                 if (response.status === 200) {
//                     //ngày trong order: dd/mm/yyyy (18/04/2024)
//                     // const salesToday = response.data.filter(order => order.orderdate === today);

//                     //ngày trong order: dd/mm/yyyy (18/04/2024 17:13:20)
//                     const salesToday = response.data.filter(order => {
//                         if (order.orderStatus && order.orderStatus.status === "Completed") {
//                             const [datePart, timePart] = order.orderdate.split(' '); // Tách phần ngày và phần giờ
//                             const [day, month, year] = datePart.split('/');
//                             // const [hours, minutes, seconds] = timePart.split(':');
//                             const orderDate = new Date(`${year}-${month}-${day}`);
//                             //console.log('orderDate', orderDate)
//                             return (
//                                 orderDate.getDate() === today.getDate() &&
//                                 orderDate.getMonth() === today.getMonth() &&
//                                 orderDate.getFullYear() === today.getFullYear()
//                             );

//                         }
//                         return false;
//                     });
//                     // Kiểm tra xem có đơn hàng nào trong ngày không
//                     if (salesToday.length === 0) {
//                         setDailySales(null); // Trả về null nếu không có đơn hàng nào trong ngày
//                         return;
//                     }
//                     const totalQuantity = salesToday.reduce((acc, order) => {
//                         return acc + order.items.reduce((acc, item) => acc + item.quantity, 0);
//                     }, 0);
//                     setDailySales(totalQuantity);

//                 } else {
//                     throw new Error('Network response was not ok');
//                 }
//             } catch (error) {
//                 console.error('Error fetching daily sales:', error);
//                 setError('Error fetching daily sales. Please try again later.');
//             }
//         };

//         fetchDailySales();
//         fetchOrdersData();
//     }, []);
//     if (error) {
//         return <div>{error}</div>;
//     }
//     return (
//         <div className='list home'>
//             <SidebarStaff />
//             <div className="listContainer homeContainer">
//                 <Navbar title="Đặt bàn ăn" />
//                 <div className="headerStaff">
//                     {/* <div className="search">
//                         <input type="text" placeholder="Search..." />
//                         <div className='icon'>
//                             <SearchOutlinedIcon />
//                         </div>
//                     </div> */}
//                     {/* <div className="linkAdd">
//                         <Link to="/admin/staffManagement/addStaff" style={{ textDecoration: "none" }}>
//                             <div className="textAdd">Thêm nhân viên</div>
//                         </Link>
//                     </div> */}
//                     <ExploreMenu category={category} setCategory={setCategory} />
//                     <FoodDisplay category={category} />
//                 </div>

//             </div>
//         </div>
//     )
// }

// export default StaffOrderMenu


import React, { useContext, useState } from "react";
import "../../css/mngtHome.css";
import Navbar from "../../components/navbar/Navbar";
import SidebarOrderTable from "../../components/sidebar/SidebarOrderTable";
import ExploreMenu from "../../components/ExploreMenu/ExploreMenu";
import FoodDisplay from "../../components/FoodDisplay/FoodDisplay";
import SidebarStaff from "../../components/sidebar/SidebarStaff";
import { Link, useLocation, useParams } from "react-router-dom";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

import { StoreContext } from "../../components/context/StoreContext";
import axios from "axios";
import Sidebar from "../../components/sidebar/Sidebar";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const StaffOrderMenu = () => {
    const [category, setCategory] = useState("All");
    const [showSidebar, setShowSidebar] = useState(false);

    const { cartItems, clearTableItems } = useContext(StoreContext);
    const { tableId } = useParams();
    console.log("cartitemRRR", cartItems);
    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    const handleBackClick = async () => {
        // Kiểm tra nếu có item trong giỏ
        if (Object.keys(cartItems).length > 0) {
            try {
                // Gọi API để xóa từng item trong backend
                // for (const itemId of Object.keys(cartItems)) {
                await axios.delete(`${process.env.REACT_APP_BACKEND_URL}/api/tableItem/delete/${tableId}`);
                // console.log("itemIdRRR", itemId);
                // }

                // Xóa item trong state local
                clearTableItems();
                console.log("All items cleared successfully.", cartItems);
            } catch (error) {
                console.error("Error deleting items:", error);
            }
        }
        // Điều hướng về trang staff
        // navigate("/staff");
    };

    const handleAddItem = () => {
        setShowSidebar(true); // Hiển thị sidebar khi thêm món ăn
    };

    const handleCloseSidebar = () => {
        setShowSidebar(false); // Ẩn sidebar
    };
    const orderTableLink = role === "admin"
        ? `/admin/orderTable/${tableId}`
        : role === "staff"
            ? `/staff/orderTable/${tableId}`
            : `/`;
    return (
        <div className="list home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <SidebarOrderTable isVisible={showSidebar} onClose={handleCloseSidebar} />
            <div className="listContainer homeContainer">
                <Navbar title={`Đặt món tại bàn ${tableId}`} />
                <Link to={orderTableLink} onClick={handleBackClick} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />

                </Link>
                <div className="headerStaff2">
                    <ExploreMenu category={category} setCategory={setCategory} />
                    <FoodDisplay category={category} onAddItem={handleAddItem} />
                </div>
            </div>
        </div>
    );
};

export default StaffOrderMenu;
