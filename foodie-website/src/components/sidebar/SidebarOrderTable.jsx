// import React, { useContext } from 'react';
// import { StoreContext } from '../context/StoreContext';
// import "./SidebarMenu.css"; // File CSS để định dạng sidebar

// const SidebarOrderTable = () => {
//     const { cartItems, food_list, getTotalCartAmount, getTotalQuantity } = useContext(StoreContext); // Lấy thông tin giỏ hàng từ context

//     const getCartItemsList = () => {
//         return Object.keys(cartItems).map(itemId => {
//             const foodItem = food_list.find(item => item.id === itemId);
//             return (
//                 <div key={itemId} className="cart-item">
//                     <p>{foodItem.name}</p>
//                     <p>{cartItems[itemId]} x {foodItem.price} VND</p>
//                 </div>
//             );
//         });
//     };

//     return (
//         <div className="sidebar">
//             <h3>Giỏ hàng</h3>
//             {getCartItemsList()} {/* Hiển thị các món ăn đã chọn */}
//             <div className="total">
//                 <p>Tổng cộng: {getTotalCartAmount()} VND</p>
//             </div>
//             <button onClick={() => alert("Đặt hàng thành công!")}>Đặt hàng</button>
//         </div>
//     );
// };

// export default SidebarOrderTable;


// // import React, { useContext } from "react";
// // import { StoreContext } from "../context/StoreContext";
// // import "./sidebarMenu.css";

// // const SidebarOrderTable = ({ isVisible, onClose }) => {
// //     const { cartItems, food_list, getTotalCartAmount, getTotalQuantity } = useContext(StoreContext);

// //     const getCartItemsList = () => {
// //         if (Object.keys(cartItems).length === 0) {
// //             return <p>Giỏ hàng của bạn đang trống.</p>;
// //         }

// //         return Object.keys(cartItems).map((itemId) => {
// //             const foodItem = food_list.find((item) => item.id === itemId);
// //             if (!foodItem) return null;

// //             return (
// //                 <div key={itemId} className="cart-item">
// //                     <p className="cart-item-name">{foodItem.name}</p>
// //                     <p className="cart-item-details">
// //                         {cartItems[itemId]} x {foodItem.price.toLocaleString()} VND
// //                     </p>
// //                 </div>
// //             );
// //         });
// //     };

// //     if (!isVisible) return null; // Không hiển thị sidebar nếu `isVisible` là false

// //     return (
// //         <div className="sidebar2">
// //             <button className="close-button" onClick={onClose}>
// //                 ×
// //             </button>
// //             <h3>Món ăn</h3>
// //             <div className="cart-items">{getCartItemsList()}</div>
// //             <div className="cart-summary">
// //                 <p>Tổng số món: {getTotalQuantity()}</p>
// //                 <p>Tổng cộng: {getTotalCartAmount().toLocaleString()} VND</p>
// //             </div>
// //             <button className="order-button" onClick={() => alert("Đặt hàng thành công!")}>
// //                 Đặt hàng
// //             </button>
// //         </div>
// //     );
// // };

// // export default SidebarOrderTable;


import { StoreContext } from "../context/StoreContext";
import { useLocation, useNavigate, useParams } from "react-router-dom";
// import { deliveryFee } from "../Cart/Cart"; // Dùng lại phí giao hàng
import "./sidebarOrderTable.css"; // File CSS riêng để định dạng giao diện
import { assets } from "../../assets/assets";
//import "./cart.css";


import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
const backendUrl = process.env.REACT_APP_BACKEND_URL;
export const deliveryFee = 20000;
const SidebarOrderTable = ({ isVisible, onClose }) => {
    const {
        cartItems,
        food_list,
        addToCart,
        removeFromCart,
        getTotalCartAmount,
        getTotalQuantity,
        clearTableItems,
    } = useContext(StoreContext);

    const [tableItems, setTableItems] = useState({});

    console.log("cartT", cartItems);
    console.log("food_l", food_list);

    const totalQuantity = getTotalQuantity();
    const totalCartAmount = getTotalCartAmount();
    // const isLoggedIn = false; // Thay đổi logic đăng nhập nếu cần

    console.log("totalQuantity", totalQuantity);
    console.log("totalCartAmount", totalCartAmount)



    const [showProgressBar, setShowProgressBar] = useState(false);
    const [message, setMessage] = useState('');
    const [orderTable, setOrderTable] = useState({});
    const { tableId } = useParams();
    const staffID = localStorage.getItem(`staffID`);
    const navigate = useNavigate();

    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    useEffect(() => {
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 1500);
            return () => clearTimeout(timer);
        }
        // clearTableItems();
    }, [showProgressBar]);

    // const handleRemoveItem = async () => {

    // }

    const handleCheckout = async () => {

        try {
            const response = await axios.post(`${backendUrl}/api/orderTable/add`, {
                accountId: staffID,
                orderStatusId: 3,
                tableId: tableId,
            });
            // setMenuList(response.data); // Cập nhật danh mục từ API
            if (response.status === 201) {
                setMessage('Đặt món thành công!');
                setShowProgressBar(true);
                clearTableItems();
                setTimeout(() => {
                    if (role === "admin") {
                        navigate("/admin/orderTable");
                    } else if (role === "staff") {
                        navigate("/staff/orderTable");
                    }
                }, 2000);

            } else {
                setMessage('Đặt món thất bại.');
            }
        } catch (error) {
            console.error('Lỗi khi cập nhật món ăn:', error);
            setMessage('Có lỗi xảy ra.');
        }

    };

    // if (!isVisible) return null;


    const handleAddToCart = async (id) => {
        addToCart(id);
        // onAddItem(); // Gọi hàm để hiển thị sidebar
        // Lấy số lượng món ăn hiện tại từ StoreContext
        const quantity = cartItems[id] ? cartItems[id] + 1 : 1;
        // const quantity = cartItems[id]?.quantity ? cartItems[id].quantity + 1 : 1;

        if (tableItems[id]) {
            try {
                const response = await axios.put(`${backendUrl}/api/tableItem/${tableItems[id]}`, {
                    quantity: quantity.toString(),
                });
                if (response.status === 200) {
                    setMessage('Cập nhật món ăn thành công!');
                    setShowProgressBar(true);
                    // console.log("staffId", staffID);
                } else {
                    setMessage('Cập nhật món ăn thất bại.');
                }
            } catch (error) {
                console.error('Lỗi khi cập nhật món ăn:', error);
                setMessage('Có lỗi xảy ra.');
            }
        } else {
            // Gửi dữ liệu số lượng lên API
            try {
                const response = await axios.post(`${backendUrl}/api/tableItem/add/${tableId}/${id}`, {
                    quantity: quantity.toString(), // Chuyển số lượng thành chuỗi nếu cần
                });
                if (response.status === 201) {
                    const { tableItemId } = response.data; // API trả về tableItemId
                    // Lưu tableItemId để sử dụng khi cập nhật
                    setTableItems((prev) => ({
                        ...prev,
                        [id]: tableItemId,
                    }));
                    setMessage('Thêm món ăn thành công!');
                    setShowProgressBar(true);
                } else {
                    setMessage('Thêm món ăn thất bại.');
                }
            } catch (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
                setMessage('Có lỗi xảy ra.');
            }
        };
    }

    const handleRemoveItem = async (id) => {
        const tableItemId = tableItems[id];
        if (!tableItemId) {
            console.error("Không tìm thấy tableItemId để cập nhật!");
            return;
        }
        removeFromCart(id);
        const quantity = cartItems[id] ? cartItems[id] - 1 : 0;
        // const quantity = cartItems[id]?.quantity ? cartItems[id].quantity - 1 : 0;

        console.log("quantityRE", quantity);
        // Chỉ gửi yêu cầu nếu số lượng lớn hơn 0
        if (quantity > 0) {
            try {
                const response = await axios.put(`${backendUrl}/api/tableItem/${tableItemId}`, {
                    quantity: quantity.toString(),
                });
                if (response.status === 200) {
                    setMessage('Cập nhật món ăn thành công!');
                    setShowProgressBar(true);
                } else {
                    setMessage('Cập nhật món ăn thất bại.');
                }
            } catch (error) {
                console.error('Lỗi khi gửi dữ liệu:', error);
                setMessage('Có lỗi xảy ra.');
            }
        } else {
            // Xử lý nếu muốn xóa hoàn toàn món ăn khỏi bảng `ItemTable`
            try {
                const response = await axios.delete(`${backendUrl}/api/tableItem/${tableItemId}`);
                if (response.status === 200) {
                    setMessage('Xóa món ăn thành công!');
                    setShowProgressBar(true);
                    // Sau khi xóa, xóa tableItemId khỏi state
                    setTableItems((prev) => {
                        const newTableItems = { ...prev };
                        delete newTableItems[id]; // Xóa tableItemId của món ăn này
                        return newTableItems;
                    });
                } else {
                    setMessage('Xóa món ăn thất bại.');
                }
            } catch (error) {
                console.error('Lỗi khi xóa món ăn:', error);
                setMessage('Có lỗi xảy ra.');
            }
        }
    }
    return (
        <div className="sidebar2">
            <div className="sidebar-header">
                <h3>Món ăn đã chọn</h3>
                <hr></hr>
                {/* <button className="close-btn" onClick={onClose}>
                    ×
                </button> */}
            </div>

            <div className="cart-items">
                {/* <div className="cart-items-title cart-heading">
                    <p>Items</p>
                    <p>Title</p>
                    <p>Price</p>
                    <p>Quantity</p>
                    <p>Total</p>
                    <p>Remove</p>
                </div>
                <br />
                <hr /> */}
                {totalQuantity === 0 ? (
                    <p className="empty-cart">Không có món ăn được chọn.</p>
                ) : (
                    food_list.map((item) => {
                        if (cartItems[item.id] > 0) {
                            return (
                                <div className="cart-item" key={item.id}>
                                    <img src={item.url_image_product} alt={item.name} className="cart-item-img" />
                                    <div className="cart-item-details">
                                        <p className="cart-item-name">{item.name}</p>
                                        <p className="cart-item-price">{item.price}VNĐ</p>
                                        <p className="cart-item-quantity">
                                            Số lượng: {cartItems[item.id]}
                                        </p>
                                        <p className="cart-item-total">
                                            Tổng giá: {item.price * cartItems[item.id]}VNĐ
                                        </p>
                                    </div>
                                    {/* 
                                    {!cartItems[item.id] ? (
                                    <img
                                        src={assets.add_icon_white}
                                        alt="add_icon_white"
                                        className="add"
                                        onClick={handleAddToCart(item.id)}
                                    />
                                    ) : (
                                    <div className="food-item-counter">
                                        <img
                                            src={assets.remove_icon_red}
                                            alt="remove_icon_red"
                                            onClick={handleRemoveItem(item.id)}
                                        />
                                        <p>{cartItems[item.id]}</p>
                                      
                                        <img
                                            src={assets.add_icon_green}
                                            alt="add_icon_green"
                                            onClick={handleAddToCart(item.id)}
                                        />
                                    </div>
                                     )}  */}
                                    {/* <button
                                        className="remove-btn"
                                        // onClick={() => removeFromCart(item.id, tableId)}
                                        onClick={() => removeFromCart(item.id)}
                                    >
                                        Xóa
                                    </button> */}
                                    <div className="food-item-counter2">
                                        <img
                                            src={assets.remove_icon_red}
                                            alt="remove_icon_red"
                                            onClick={() => removeFromCart(item.id)}
                                        />
                                        <p>{cartItems[item.id]}</p>
                                        <img
                                            src={assets.add_icon_green}
                                            alt="add_icon_green"
                                        // onClick={handleAddToCart(item.id)}
                                        />
                                    </div>
                                    {/* <div className="food-item-counter">
                                        <img
                                            src={assets.remove_icon_red}
                                            alt="remove_icon_red"
                                            onClick={() => removeFromCart(item.id)}
                                        />
                                        <p>{cartItems[item.id]}</p>
                                    </div> */}
                                </div>
                            );
                        }
                    })
                )}
            </div>

            <div className="cart-summary">
                <div className="cart-summary-details">
                    <p>Tổng đơn hàng:</p>
                    <p>{totalCartAmount}VNĐ</p>
                </div>
                {/* <div className="cart-summary-details">
                    <p>Phí giao hàng:</p>
                    <p>{totalCartAmount === 0 ? 0 : deliveryFee}VNĐ</p>
                </div> */}
                {/* <div className="cart-summary-details">
                    <b>Tổng:</b>
                    <b>{totalCartAmount + (totalCartAmount === 0 ? 0 : deliveryFee)}VNĐ</b>
                </div> */}
                <button
                    className="checkout-btn"
                    disabled={totalCartAmount === 0}
                    onClick={handleCheckout}
                >
                    Đặt món
                </button>
            </div>
            {message && (
                <div className="success-message">
                    {message}
                    {showProgressBar && <div className="progress-bar" />}
                </div>
            )}
            {/* <div className="promo-code">
                <p>If you have a promo code, enter it here:</p>
                <div className="promo-code-input">
                    <input type="text" placeholder="Promo Code" />
                    <button>Submit</button>
                </div>
            </div> */}
        </div >
    );
};

export default SidebarOrderTable;

