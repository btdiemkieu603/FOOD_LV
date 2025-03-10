
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "../../css/mngtForm.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const AddProduct = () => {
    const navigate = useNavigate();

    const [title, setTitle] = useState('');
    const [url_image_category, setImg] = useState('');

    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState('');
    const [name, setName] = useState('');
    const [urlImage, setUrlImage] = useState('');
    const [price, setPrice] = useState('');
    const [description, setDescription] = useState('');
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);
    const [isExist, setIsExist] = useState('true');
    useEffect(() => {
        fetchData();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 1500);
            return () => clearTimeout(timer);
        }
    }, [showProgressBar]);

    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/category/`);
            const filteredCategories = response.data.filter(category => category.isExist === true);

            setCategories(filteredCategories);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };
    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handleImgChange = (event) => {
        setImg(event.target.value);

    };
    const handleSubmitAddCategory = async (event) => {
        event.preventDefault();
        setShowProgressBar(true);
        try {
            const response = await axios.post(`${backendUrl}/api/category/add`, { title, url_image_category, isExist });
            if (response.status === 201) {
                setMessage('Tạo Danh mục món ăn thành công!');
                setTitle('');
                setImg('');
                fetchData();
            } else {
                setMessage('Tạo thất bại.');
            }
        } catch (error) {
            console.error('Error adding category:', error);
            setMessage('Có lỗi xảy ra.');
        }
    };


    const handleSelectChange = (event) => {
        setSelectedCategory(event.target.value);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleUrlImageChange = (event) => {
        setUrlImage(event.target.value);
    };

    const handlePriceChange = (event) => {
        setPrice(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleSubmitAddProduct = async (event) => {
        if (!selectedCategory || !name) {
            setMessage('Vui lòng chọn danh mục và nhập tên món ăn.');
            return;
        }
        event.preventDefault();

        setShowProgressBar(true); // Hiển thị thanh tiến trình khi bắt đầu submit

        try {
            const response = await axios.post(`${backendUrl}/api/product/add/${selectedCategory}`, { name, url_image_product: urlImage, price, description, isExist });
            if (response.status === 201) {
                setMessage('Tạo món ăn thành công!');
                setName('');
                setSelectedCategory('');
                setUrlImage('');
                setPrice('');
                setDescription('');

                setTimeout(() => {

                    navigate('/admin/productManagement');
                }, 2000);

            } else {
                setMessage('Tạo món ăn thất bại.');
            }
        } catch (error) {
            console.error('Error adding product:', error);
            setMessage('Có lỗi xảy ra khi tạo món ăn.');
        }
    };
    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;
    const ProductHomeLink = role === "admin"
        ? "/admin/productManagement"
        : role === "staff"
            ? "/staff/productManagement"
            : "/";
    return (
        <div className="new home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="newContainer homeContainer">
                <Navbar />
                <Link to={ProductHomeLink} style={{ textDecoration: "none" }}>
                    <div className="arrow-back-icon">
                        <ArrowBackIcon />
                    </div>
                </Link>
                <div className="bottom">
                    <div className="column2">

                        <div className="column">
                            <div className="title top">
                                <h1>Thêm Danh mục</h1>
                            </div>
                            <form onSubmit={handleSubmitAddCategory}>
                                <div className="formInput">
                                    <label >Tên danh mục:</label>
                                    <input type="text" value={title} onChange={handleTitleChange} required />
                                </div>
                                <div className="formInput">
                                    <label>Đường dẫn hình ảnh danh mục:</label>
                                    <input type="text" value={url_image_category} onChange={handleImgChange} required />
                                </div>
                                <div className="formInput">

                                </div>
                                <button type="submit" >Tạo Danh Mục</button>
                            </form>
                        </div>

                        <div className="column">
                            <div className="title top">
                                <h1>Thêm Món ăn</h1>
                            </div>
                            <form onSubmit={handleSubmitAddProduct}>
                                <div className="formInput">
                                    <label>Tên món ăn:</label>
                                    <input id="name" type="text" value={name} onChange={handleNameChange} />
                                </div>
                                <div className="formInput">
                                    <label>Đường dẫn hình ảnh món ăn:</label>
                                    <input id="urlImage" type="text" value={urlImage} onChange={handleUrlImageChange} />
                                </div>
                                <div className="formInput">
                                    <label>Giá:</label>
                                    <input id="price" type="text" value={price} onChange={handlePriceChange} />
                                </div>
                                <div className="formInput">
                                    <label>Danh mục:</label>
                                    <select id="category" value={selectedCategory} onChange={handleSelectChange}>
                                        <option>Chọn 1 trong số các danh mục sau</option> {/* Mục mặc định */}
                                        {categories.map((category) => (
                                            <option key={category.id} value={category.id}>{category.title}</option>
                                        ))}
                                    </select>
                                </div>

                                <div className="formInput">
                                    <label>Mô tả:</label>
                                    <textarea id="description" value={description} onChange={handleDescriptionChange} />
                                </div>
                                <button type="submit">Tạo Món Ăn</button>

                            </form>

                            {message && (
                                <div className="success-message">
                                    {message}
                                    {showProgressBar && <div className="progress-bar" />}
                                </div>
                            )}
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default AddProduct;
