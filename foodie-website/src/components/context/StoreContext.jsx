import React, { createContext, useEffect, useState } from "react";
//import { food_list } from "../assets/assets";
import axios from 'axios';
import { useParams } from "react-router-dom";
export const StoreContext = createContext(null);
const backendUrl = process.env.REACT_APP_BACKEND_URL;
const StoreContextProvider = (props) => {

  const [isSidebarVisible, setSidebarVisible] = useState(false);

  const toggleSidebar = () => setSidebarVisible(!isSidebarVisible);
  const showSidebar = () => setSidebarVisible(true);
  const hideSidebar = () => setSidebarVisible(false);


  const [food_list, setFoodList] = useState([]);
  //const [cartItems, setCartItems] = useState({});
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');
  const [showProgressBar, setShowProgressBar] = useState(false);
  const [cartItems, setCartItems] = useState(() => {
    // Lấy dữ liệu giỏ hàng từ localStorage nếu có
    const storedCart = localStorage.getItem("cartItems");
    return storedCart ? JSON.parse(storedCart) : {};
  });


  useEffect(() => {
    localStorage.setItem("cartItems", JSON.stringify(cartItems));
    const fetchData = async () => {
      try {
        const response = await axios.get(`${backendUrl}/api/product/`);
        const filteredProducts = response.data.filter(product => product.isExist === true);

        const categoryResponse = await axios.get(`${backendUrl}/api/category/`);
        const filteredCategories = categoryResponse.data.filter(category => category.isExist === true); // Lọc danh mục có isExist = true

        const updatedData = filteredProducts.map(product => ({

          ...product,
          categoryTitle: product.category.title
        }));
        setFoodList(updatedData);
        console.log("food_listContext", updatedData);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false);
      }
    };
    fetchData();
  }, [cartItems]);

  // const addToCart = (itemId) => {
  //   if (!cartItems[itemId]) {
  //     setCartItems((prev) => ({ ...prev, [itemId]: 1 }));
  //   } else {
  //     setCartItems((prev) => ({ ...prev, [itemId]: prev[itemId] + 1 }));
  //   }
  // };


  const addToCart = (itemId) => {
    if (!itemId) {
      console.error("Không có món ăn được chọn:", itemId);
      return;
    }
    setCartItems((prev) => {
      if (!prev[itemId]) {
        // Nếu sản phẩm chưa có trong giỏ, thêm mới với số lượng là 1
        return { ...prev, [itemId]: 1 };
      } else {
        // Nếu sản phẩm đã có trong giỏ, chỉ tăng số lượng lên 1
        return { ...prev, [itemId]: prev[itemId] + 1 };
      }
    });
  };

  const removeFromCart = (itemId) => {
    if (cartItems[itemId] === 1) {
      // Remove the item if the quantity becomes zero
      const newCartItems = { ...cartItems };
      delete newCartItems[itemId];
      setCartItems(newCartItems);
    } else {
      setCartItems((prev) => ({ ...prev, [itemId]: prev[itemId] - 1 }));
    }
  };

  // const addToCart = (itemId, tableId) => {
  //   if (!itemId || !tableId) {
  //     console.error("Missing itemId or tableId:", { itemId, tableId });
  //     return;
  //   }

  //   setCartItems((prev) => {
  //     if (!prev[itemId] || prev[itemId].tableId !== tableId) {
  //       // Nếu món chưa tồn tại trong giỏ hoặc thuộc bàn khác, thêm mới
  //       return { ...prev, [itemId]: { tableId, quantity: 1 } };
  //     } else {
  //       // Nếu món đã có trong giỏ, tăng số lượng
  //       return {
  //         ...prev,
  //         [itemId]: {
  //           ...prev[itemId],
  //           quantity: prev[itemId].quantity + 1,
  //         },
  //       };
  //     }
  //   });
  // };
  // const removeFromCart = (itemId, tableId) => {
  //   if (!itemId || !tableId) {
  //     console.error("Missing itemId or tableId:", { itemId, tableId });
  //     return;
  //   }

  //   setCartItems((prev) => {
  //     const item = prev[itemId];
  //     if (!item || item.tableId !== tableId) {
  //       console.warn("Item not found or belongs to a different table:", itemId);
  //       return prev;
  //     }

  //     if (item.quantity === 1) {
  //       // Nếu số lượng là 1, xóa hẳn món đó khỏi giỏ
  //       const { [itemId]: _, ...rest } = prev; // Loại bỏ itemId khỏi cartItems
  //       return rest;
  //     } else {
  //       // Nếu số lượng > 1, giảm số lượng
  //       return {
  //         ...prev,
  //         [itemId]: {
  //           ...item,
  //           quantity: item.quantity - 1,
  //         },
  //       };
  //     }
  //   });
  // };


  // const getTotalQuantity = () => {
  //   let totalQuantity = 0;
  //   for (const itemId in cartItems) {
  //     totalQuantity += cartItems[itemId];
  //   }
  //   return totalQuantity;
  // };

  // const getTotalCartAmount = () => {
  //   let totalAmount = 0;
  //   for (const item in cartItems) {
  //     if (cartItems[item] > 0) {
  //       let itemInfo = food_list.find((product) => product._id === item);
  //       totalAmount += itemInfo.price * cartItems[item];
  //     }
  //   }
  //   return totalAmount;
  // };
  const getTotalCartAmount = () => {
    let totalAmount = 0;


    for (const itemId in cartItems) {
      const numericItemId = Number(itemId);
      // if (isNaN(numericItemId)) {
      //   console.warn("Invalid item ID:", itemId);
      //   continue;
      // }
      const itemInfo = food_list.find((product) => product.id === numericItemId);
      if (itemInfo) {
        totalAmount += itemInfo.price * cartItems[itemId];
      } else {
        console.warn("Item not found in food_list:", numericItemId);
      }
    }
    return totalAmount;
  };

  const getTotalUniqueItems = () => {
    return Object.keys(cartItems).length;
  };

  const getTotalQuantity = () => {
    return Object.values(cartItems).reduce((acc, qty) => acc + qty, 0);
  };

  // const clearTableItems = (tableId) => {
  //   setCartItems((prevCartItems) => {
  //     const updatedCartItems = { ...prevCartItems };
  //     Object.keys(updatedCartItems).forEach((key) => {
  //       if (updatedCartItems[key].tableId === tableId) {
  //         delete updatedCartItems[key];
  //       }
  //     });
  //     return updatedCartItems;
  //   });
  // };

  const clearTableItems = () => {
    setCartItems({});
  };
  // const clearTableItems = (tableId) => {
  //   setCartItems((prev) => {
  //     const updatedCartItems = Object.fromEntries(
  //       Object.entries(prev).filter(([_, item]) => item.tableId !== tableId)
  //     );
  //     return updatedCartItems;
  //   });
  // };
  const contextValue = {
    food_list,
    cartItems,
    setCartItems,
    addToCart,
    removeFromCart,
    getTotalCartAmount,
    getTotalQuantity,
    getTotalUniqueItems,
    isSidebarVisible,
    toggleSidebar,
    showSidebar,
    hideSidebar,
    clearTableItems,
  };

  return (
    <StoreContext.Provider value={contextValue}>
      {props.children}
    </StoreContext.Provider>
  );
};

export default StoreContextProvider;
