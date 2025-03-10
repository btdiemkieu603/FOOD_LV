import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "../../css/mngtForm.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const EditCategory = () => {
    const { categoryId } = useParams();
    const navigate = useNavigate();

    //const [categories, setCategories] = useState([]);
    const [category, setCategory] = useState({
        title: '',
        url_image_category: '',
    });

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    useEffect(() => {
        fetchData();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000);
            return () => clearTimeout(timer);
        }
    }, [categoryId, showProgressBar]);

    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/category/${categoryId}`);
            setCategory(response.data);
        } catch (error) {
            console.error('Error fetching product data:', error);
            setMessage('Có lỗi xảy ra khi lấy dữ liệu');
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCategory({
            ...category,
            [name]: value
        });
    };

    const handleSubmitEditCategory = async (event) => {
        event.preventDefault();
        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/category/${categoryId}`, category);
            if (response.status === 200) {
                setMessage('Cập nhật danh mục thành công.');
                setTimeout(() => {
                    if (role === "admin") {
                        navigate(`/admin/productManagement`);
                    } else if (role === "staff") {
                        navigate(`/staff/productManagement`);
                    }
                }, 1500);
            } else {
                setMessage('Cập nhật danh mục thất bại.');
            }
        } catch (error) {
            console.error('Error updating product:', error);
            setMessage('Có lỗi xảy ra khi lấy dữ liệu.');
        }
    };
    const ProductHomeLink = role === "admin"
        ? `/admin/productManagement`
        : `/staff/productManagement`;

    return (
        <div className="new home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="newContainer homeContainer">
                <Navbar />
                <Link to={ProductHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="bottom">
                    <div className="column2">
                        <div className="column">
                            <div className="title top">
                                <h1>Chỉnh sửa danh mục</h1>
                            </div>
                            <form onSubmit={handleSubmitEditCategory}>
                                <div className="formInput">
                                    <label>Tên danh mục:</label>
                                    <input type="text" name="title" value={category.title} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Đường dẫn hình ảnh món ăn:</label>
                                    <input type="text" name="url_image_category" value={category.url_image_category} onChange={handleChange} />
                                </div>

                                <button type="submit">Cập nhật</button>
                            </form>
                            {message && (
                                <div className="success-message">
                                    {message}
                                    {showProgressBar && <div className="progress-bar" />}
                                </div>
                            )}
                            {/* {error && <div className="error-message">{error}</div>} */}

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EditCategory;
