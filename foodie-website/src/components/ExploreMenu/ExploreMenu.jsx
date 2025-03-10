import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "./ExploreMenu.css";
//import { menu_list } from "../../assets/assets";
const backendUrl = process.env.REACT_APP_BACKEND_URL;
const ExploreMenu = ({ category, setCategory }) => {
  const [menuList, setMenuList] = useState([]);
  console.log("ca", category);
  useEffect(() => {

    const fetchCategories = async () => {
      try {
        const response = await axios.get(`${backendUrl}/api/category/`);
        const filteredCategories = response.data.filter(category => category.isExist === true);

        setMenuList(filteredCategories); // Cập nhật danh mục từ API
        console.log("menuC", menuList);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();
  }, []);
  return (
    <div className="explore-menu" id="explore-menu">
      <h1>Danh mục menu</h1>
      <p className="explore-menu-text">
        Mỗi thực đơn bao gồm nhiều món ăn hấp dẫn. Hãy chọn thực đơn yêu thích của bạn và trải nghiệm những món ăn tuyệt vời mà chúng tôi đã chuẩn bị.
      </p>
      <p className="explore-menu-text">
        Chỉ cần là bạn thích - có ngay món ngon!!!
      </p>
      <div className="explore-menu-list">
        <div
          className="explore-menu-list-item"
          onClick={() => setCategory("All")} // Khi nhấp vào "All", sẽ reset danh mục về "All"
        >
          <div class="image-container">
            <p>Tất cả</p> {/* Thêm mục "Tất cả" để reset danh mục */}
          </div>
        </div>
        {menuList.map((item, index) => {
          return (
            <div
              key={index}
              className="explore-menu-list-item"
              onClick={() =>
                setCategory((prev) =>
                  prev === item.title ? "All" : item.title
                )
              }
            >
              <div class="image-container">
                <img
                  src={item.url_image_category}
                  className={category === item.title ? "active" : ""}
                  alt="menu_image"
                />
              </div>
              <p>{item.title}</p>
            </div>
          );
        })}
      </div>
      <hr />
    </div >
  );
};

export default ExploreMenu;
