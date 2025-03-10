// import React, { useContext, useEffect, useState } from "react";
// import "./FoodItem.css";

// import axios from 'axios';
// import { assets } from "../../assets/assets";
// import { StoreContext } from "../context/StoreContext";

// //const FoodItem = ({ id, name, price, description, url_image_product }) => {
// const FoodItem = ({ id, name, description, price, url_image_product }) => {
//   const { cartItems, addToCart, removeFromCart } = useContext(StoreContext);

//   return (
//     <div className="food-item" key={id}>
//       <div className="food-item-img-container">
//         <img src={url_image_product} alt="image" className="food-item-img" />
//         {/* Kiểm tra món ăn đã có trong giỏ chưa, nếu có thì cho phép thay đổi số lượng */}
//         {!cartItems[id] ? (
//           <img
//             src={assets.add_icon_white}
//             alt="add_icon_white"
//             className="add"
//             onClick={() => addToCart(id)}
//           />
//         ) : (
//           <div className="food-item-counter">
//             <img
//               src={assets.remove_icon_red}
//               alt="remove_icon_red"
//               onClick={() => removeFromCart(id)}
//             />
//             <p>{cartItems[id]}</p>
//             <img
//               src={assets.add_icon_green}
//               alt="add_icon_green"
//               onClick={() => addToCart(id)}
//             />
//           </div>
//         )}
//       </div>
//       <div className="food-item-info">
//         <div className="food-item-name-rating">
//           <p>{name}</p>
//           {/* <img src={assets.rating_starts} alt="rating_starts" /> */}
//         </div>
//         <p className="food-item-desc">{description}</p>
//         <p className="food-item-price">${price}</p>
//       </div>
//     </div>
//   );
// };

// export default FoodItem;

import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';

import "./FoodItem.css";

import { StoreContext } from "../context/StoreContext";
import { assets } from "../../assets/assets";
import { useParams } from 'react-router-dom';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const FoodItem = ({ id, name, description, price, url_image_product, onAddItem }) => {
  const { cartItems, addToCart, removeFromCart } = useContext(StoreContext);
  const [showProgressBar, setShowProgressBar] = useState(false);
  const [message, setMessage] = useState('');
  const [tableItems, setTableItems] = useState({});
  const { tableId } = useParams();

  useEffect(() => {
    if (showProgressBar) {
      const timer = setTimeout(() => {
        setMessage('');
        setShowProgressBar(false);
      }, 1500);
      return () => clearTimeout(timer);
    }
    // setTableItems({});
    // }, [showProgressBar, tableId]);
  }, [showProgressBar]);

  // const handleAddItemTable = async (event) => {
  //   event.preventDefault();
  //   setShowProgressBar(true);
  //   try {
  //     const response = await axios.post(`${backendUrl}/api/tableItem/add/${tableId}/${id}`, {});
  //     if (response.status === 201) {
  //       setMessage('Thêm món ăn thành công!');

  //     } else {
  //       setMessage('Thêm món ăn thất bại.');
  //     }
  //   } catch (error) {
  //     console.error('Error adding category:', error);
  //     setMessage('Có lỗi xảy ra.');
  //   }
  // };

  const handleAddToCart = async () => {
    addToCart(id);
    onAddItem(); // Gọi hàm để hiển thị sidebar
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

  const handleRemoveItem = async () => {
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
    <div className="food-item" key={id}>
      <div className="food-item-img-container">
        <img src={url_image_product} alt="image" className="food-item-img" />
        {!cartItems[id] ? (

          <img
            src={assets.add_icon_white}
            alt="add_icon_white"
            className="add"
            // onClick={handleAddToCart}
            onClick={() => handleAddToCart()}
          />
        ) : (
          <div className="food-item-counter">
            <img
              src={assets.remove_icon_red}
              alt="remove_icon_red"
              // onClick={handleRemoveItem}
              onClick={() => handleRemoveItem()}
            />
            <p>{cartItems[id]}</p>
            {/* <p>{cartItems[id]?.quantity}</p> */}

            <img
              src={assets.add_icon_green}
              alt="add_icon_green"
              //onClick={handleAddToCart}
              onClick={() => handleAddToCart()}
            />
          </div>
        )}
      </div>
      <div className="food-item-info">
        <div className="food-item-name-rating">
          <p>{name}</p>
        </div>
        <p className="food-item-desc">{description}</p>
        <p className="food-item-price">{price}VNĐ</p>
      </div>

      {message && (
        <div className="success-message">
          {message}
          {showProgressBar && <div className="progress-bar" />}
        </div>
      )}
    </div>

  );
};

export default FoodItem;
