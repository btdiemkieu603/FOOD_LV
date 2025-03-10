
// { useEffect } from "react";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";

import CustomerDetail from "./pages/customerManagement/CustomerDetail";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import ChangePassword from "./pages/changePassword/ChangePassword";
import CustomerMngtHome from "./pages/customerManagement/CustomerMngtHome";

import StaffMngHome from "./pages/staffManagement/StaffMngtHome";
import StaffDetail from "./pages/staffManagement/StaffDetail";
import AddStaff from "./pages/staffManagement/AddStaff";
import EditStaff from "./pages/staffManagement/EditStaff";
import DiscountMngtHome from "./pages/discountManagement/DiscountMngtHome";
import DiscountDetail from "./pages/discountManagement/DiscountDetail";

import AddDiscount from "./pages/discountManagement/AddDiscount";
import EditDiscount from "./pages/discountManagement/EditDiscount";
import ProductMngtHome from "./pages/productManagement/ProductMngtHome";
import ProductDetail from "./pages/productManagement/ProductDetail";
import AddProduct from "./pages/productManagement/AddProduct";
import EditProduct from "./pages/productManagement/EditProduct";
import OrderMngtHome from "./pages/orderManagement/OrderMngtHome";
import OrderDetail from "./pages/orderManagement/OrderDetail";
//import EditOrder from "./pages/orderManagement/EditOrder";
// import AddCategory from "./pages/productManagement/AddCategory";
import EditCategory from "./pages/productManagement/EditCategory";
//import { useEffect, useState } from "react";
import TopSellingProduct from "./pages/statistical/topSellingProduct";
import Revenue from "./pages/statistical/revenue";
import Review from "./pages/statistical/review";
import CustomerStat from "./pages/statistical/customer";
import StaffHome from "./pages/staffHome/StaffHome";

import StaffOrderMenu from "./pages/staffHome/StaffOrderMenu";
import Order2 from "./pages/staffHome/StaffOrder";
import OrderTableMngtHome from "./pages/orderTableManagement/OrderTableMngtHome";

import StaffOrderTable from "./pages/staffHome/StaffOrder";
import TableMngtHome from "./pages/tableManagement/TableMngtHome";
import AddTable from "./pages/tableManagement/AddTable";
import OrderTableDetail from "./pages/orderTableManagement/OrderTableDetail";
import OrderTableHome from "./pages/staffHome/OrderTableHome";


//import PrivateRoute from "./PrivateRoute.JS";

function App() {
  //const [isLoggedIn, setisLoggedIn] = useState(false); // Giả định ban đầu người dùng chưa đăng nhập

  // Hàm kiểm tra trạng thái đăng nhập
  // const checkLogin = () => {
  //   // Logic kiểm tra trạng thái đăng nhập ở đây, bạn có thể sử dụng localStorage, sessionStorage hoặc các cơ chế lưu trữ khác
  //   // Ví dụ: 
  //   const loggedInUser = localStorage.getItem("isLoggedIn");
  //   console.log(loggedInUser)
  //   if (loggedInUser) {
  //     setisLoggedIn(true);
  //   } else {
  //     setisLoggedIn(false);
  //   }
  // };

  // // Kiểm tra trạng thái đăng nhập mỗi khi render
  // useEffect(() => {
  //   checkLogin();
  // }, []);

  const isLoggedInAdmin = localStorage.getItem("isLoggedInAdmin");
  const isLoggedInStaff = localStorage.getItem("isLoggedInStaff");
  console.log("loginstaff", isLoggedInStaff);
  console.log("loginAdmin", isLoggedInAdmin);

  // console.log(isLoggedIn)



  //const [isLoggedIn, setIsLoggedIn] = useState(false);

  // useEffect(() => {
  //   // Kiểm tra trạng thái đăng nhập khi ứng dụng khởi chạy
  //   const loggedIn = localStorage.getItem('isLoggedIn');
  //   setIsLoggedIn(!!loggedIn); // Chuyển đổi thành kiểu boolean và cập nhật state

  //   // Lắng nghe sự thay đổi trong localStorage
  //   const storageListener = () => {
  //     const newLoggedIn = localStorage.getItem('isLoggedIn');
  //     setIsLoggedIn(!!newLoggedIn); // Chuyển đổi thành kiểu boolean và cập nhật state
  //   };

  //   window.addEventListener('storage', storageListener);

  //   return () => {
  //     window.removeEventListener('storage', storageListener);
  //   };
  // }, []);


  // lỗi deployrancher----TT
  // useEffect(() => {
  //   const socket = new WebSocket(process.env.REACT_APP_WEBSOCKET_URL);

  //   socket.onopen = () => {
  //     console.log('Connected to WebSocket server');
  //   };

  //   socket.onmessage = (event) => {
  //     console.log('Message from server ', event.data);
  //   };

  //   socket.onerror = (error) => {
  //     console.log('WebSocket error ', error);
  //   };

  //   socket.onclose = () => {
  //     console.log('WebSocket connection closed');
  //   };

  //   return () => {
  //     socket.close();
  //   };
  // }, []);

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>

          <Route path="/login" element={<Login />} />
          {/* {isLoggedInAdmin ? ( */}
          <Route path="/admin" >


            <Route index element={<Home />} />


            <Route path="staffManagement">
              <Route index element={<StaffMngHome />} />
              <Route path="staffDetail/:staffId" element={<StaffDetail />} />
              <Route path="addStaff" element={<AddStaff />} />
              <Route path="editStaff/:staffId" element={<EditStaff />} />
            </Route>

            <Route path="discountManagement">
              <Route index element={<DiscountMngtHome />} />
              <Route path="discountDetail/:discountCode" element={<DiscountDetail />} />
              <Route path="addDiscount" element={<AddDiscount title="Thêm khuyến mãi" />} />
              <Route path="editDiscount/:discountId" element={<EditDiscount />} />
            </Route>

            <Route path="customerManagement" >
              <Route index element={<CustomerMngtHome />} />
              <Route path="customerDetail/:customerId" element={<CustomerDetail />} />
            </Route>

            <Route path="tableManagement">
              <Route index element={<TableMngtHome />} />
              <Route path="tableDetail/:discountCode" element={<DiscountDetail />} />
              <Route path="addTable" element={<AddTable title="Thêm bàn" />} />
              <Route path="editDiscount/:discountId" element={<EditDiscount />} />
            </Route>

            <Route path="productManagement">
              <Route index element={<ProductMngtHome />} />
              <Route path="productDetail/:productId" element={<ProductDetail />} />
              <Route path="addProduct" element={<AddProduct />} />
              <Route path="editProduct/:productId" element={<EditProduct />} />
              <Route path="editCategory/:categoryId" element={<EditCategory />} />
            </Route>

            <Route path="orderManagement">
              <Route index element={<OrderMngtHome />} />
              <Route path="orderDetail/:orderId" element={<OrderDetail />} />
            </Route>

            <Route path="setting">
              <Route path="changePassword" element={<ChangePassword title="Thay đổi mật khẩu" />} />
            </Route>

            <Route path="statistical" >
              <Route path="topSelling" element={<TopSellingProduct />} />
              <Route path="revenue" element={<Revenue />} />

              <Route path="review">
                <Route index element={<Review />} />
              </Route>

              <Route path="customerStat" element={<CustomerStat />} />
            </Route>

            <Route path="orderTable" >
              <Route index element={<StaffHome />} />
              <Route path=":tableId" element={<StaffOrderTable />} />
              <Route path="menu/:tableId" element={<StaffOrderMenu />} />
            </Route>


            <Route path="orderTableManagement">
              <Route index element={<OrderTableMngtHome />} />
              <Route path="orderTableDetail/:orderId" element={<OrderTableDetail />} />
            </Route>

          </Route>
          {/* ) : ( */}

          {/* <Route path="/login" element={<Login />} /> */}
          {/* )} */}

          {/* {isLoggedInStaff ? ( */}

          <Route path="/staff" >

            <Route path="orderTable">
              <Route index element={<StaffHome />} />
              <Route path=":tableId" element={<StaffOrderTable />} />
              <Route path="menu/:tableId" element={<StaffOrderMenu />} />
            </Route>

            <Route path="orderTableManagement">
              <Route index element={<OrderTableMngtHome />} />
              <Route path="orderTableDetail/:orderId" element={<OrderTableDetail />} />
            </Route>

            <Route path="tableManagement">
              <Route index element={<TableMngtHome />} />
              <Route path="tableDetail/:tableCode" element={<DiscountDetail />} />
              <Route path="addTable" element={<AddTable title="Thêm bàn" />} />
              <Route path="editTable/:tableId" element={<EditDiscount />} />
            </Route>

            <Route path="productManagement">
              <Route index element={<ProductMngtHome />} />
              <Route path="productDetail/:productId" element={<ProductDetail />} />
              <Route path="addProduct" element={<AddProduct />} />
              <Route path="editProduct/:productId" element={<EditProduct />} />
              <Route path="editCategory/:categoryId" element={<EditCategory />} />
            </Route>

            <Route path="orderManagement">
              <Route index element={<OrderMngtHome />} />
              <Route path="orderDetail/:orderId" element={<OrderDetail />} />
            </Route>

            <Route path="setting">
              <Route path="changePassword" element={<ChangePassword title="Thay đổi mật khẩu" />} />
            </Route>

            <Route path="discountManagement">
              <Route index element={<DiscountMngtHome />} />
              <Route path="discountDetail/:discountCode" element={<DiscountDetail />} />
              <Route path="addDiscount" element={<AddDiscount title="Thêm khuyến mãi" />} />
              <Route path="editDiscount/:discountId" element={<EditDiscount />} />
            </Route>
            <Route path="customerManagement" >
              <Route index element={<CustomerMngtHome />} />
              <Route path="customerDetail/:customerId" element={<CustomerDetail />} />
            </Route>
          </Route>
          {/* ) : ( */}

          {/* <Route path="/login" element={<Login />} /> */}
          {/* )} */}
        </Routes>
      </BrowserRouter>
    </div >
  );
}

export default App;

