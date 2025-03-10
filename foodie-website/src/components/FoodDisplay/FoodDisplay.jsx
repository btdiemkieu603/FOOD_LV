
// import React, { useContext } from "react";
// import "./FoodDisplay.css";
// import { StoreContext } from "../context/StoreContext";
// import FoodItem from "../FoodItem/FoodItem";

// const FoodDisplay = ({ category }) => {
//   const { food_list } = useContext(StoreContext); // Lấy danh sách món ăn từ StoreContext
//   //const filteredFoodList = food_list.filter(item => category === "All" || category === item.categoryTitle);

//   // Lọc món ăn theo category hoặc hiển thị tất cả khi category là "All"
//   const filteredFoodList = category === "All"
//     ? food_list
//     : food_list.filter(item => category === item.categoryTitle);

//   return (
//     <div className="food-display2" id="food-display2">
//       <h2>Danh sách món ăn</h2>
//       <div className="food-display-list2">
//         {/* {filteredFoodList.map((item) => ( */}
//         {/* {filteredFoodList
//           .filter((item) => category === "All" || category === item.categoryTitle) // Lọc món ăn theo category
//           .map((item) => ( */}
//         {filteredFoodList.map((item) => (
//           <FoodItem
//             key={item.id}
//             id={item.id}
//             name={item.name}
//             description={item.description}
//             price={item.price}
//             url_image_product={item.url_image_product}
//           />
//         ))}
//       </div>
//     </div>
//   );
// };

// export default FoodDisplay;

import React, { createContext, useContext, useEffect, useState } from "react";
// import React, { useContext } from "react";
import axios from 'axios';

import "./FoodDisplay.css";
import { StoreContext } from "../context/StoreContext";
import FoodItem from "../FoodItem/FoodItem";
const backendUrl = process.env.REACT_APP_BACKEND_URL;
const FoodDisplay = ({ category, onAddItem }) => {
  const { cartItems } = useContext(StoreContext);
  const [loading, setLoading] = useState(true);
  const [food_list, setFoodList] = useState([]);
  // const [cartItems, setCartItems] = useState(());

  useEffect(() => {
    localStorage.setItem("cartItems", JSON.stringify(cartItems));
    const fetchData = async () => {
      try {
        // const response = await axios.get(`${backendUrl}/api/product/`);
        // const filteredProducts = response.data.filter(product => product.isExist === true);

        // const categoryResponse = await axios.get(`${backendUrl}/api/category/`);
        // const filteredCategories = categoryResponse.data.filter(category => category.isExist === true); // Lọc danh mục có isExist = true

        // const updatedData = filteredProducts.map(product => ({

        //   ...product,
        //   categoryTitle: product.category.title
        // }));
        // Lấy danh sách danh mục
        const categoryResponse = await axios.get(`${backendUrl}/api/category/`);
        const filteredCategories = categoryResponse.data.filter(category => category.isExist === true); // Lọc danh mục có isExist = true

        // Tạo danh sách ID của danh mục hợp lệ
        const validCategoryIds = filteredCategories.map(category => category.id);

        // Lấy danh sách sản phẩm
        const productResponse = await axios.get(`${backendUrl}/api/product/`);
        const filteredProducts = productResponse.data.filter(
          product => product.isExist === true && validCategoryIds.includes(product.category.id) // Lọc sản phẩm theo danh mục hợp lệ
        );

        // Gắn tiêu đề danh mục cho sản phẩm
        const updatedData = filteredProducts.map(product => ({
          ...product,
          categoryTitle: filteredCategories.find(category => category.id === product.category.id)?.title || 'Unknown'
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

  const filteredFoodList = category === "All"
    ? food_list
    : food_list.filter((item) => category === item.categoryTitle);

  return (
    <div className="food-display2" id="food-display2">
      <h2>Danh sách món ăn</h2>
      <div className="food-display-list2">
        {filteredFoodList.map((item) => (
          <FoodItem
            key={item.id}
            id={item.id}
            name={item.name}
            description={item.description}
            price={item.price}
            url_image_product={item.url_image_product}
            onAddItem={onAddItem}
          />
        ))}
      </div>
    </div>
  );
};

export default FoodDisplay;
