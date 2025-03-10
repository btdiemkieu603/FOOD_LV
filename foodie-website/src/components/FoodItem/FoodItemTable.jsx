//////nháp

import React, { useContext, useEffect, useState } from "react";
import "./FoodItem.css";

import axios from 'axios';
// import { assets } from "../../assets/assets";
// import { StoreContext } from "../context/StoreContext";

//const FoodItem = ({ id, name, price, description, url_image_product }) => {
const FoodItemTable = ({ id, name, description }) => {
  //   const { cartItems, addToCart, removeFromCart } = useContext(StoreContext);

  return (
    <div className="food-item" key={id}>
      <div className="food-item-img-container">
        <img src={url_image_product} alt="image" className="food-item-img" />

      </div>
      <div className="food-item-info">
        <div className="food-item-name-rating">
          <p>{name}</p>
          {/* <img src={assets.rating_starts} alt="rating_starts" /> */}
        </div>
        <p className="food-item-desc">{description}</p>
        <p className="food-item-price">${price}</p>
      </div>
    </div>
  );
};

export default FoodItemTable;
