import "../../css/mngtDetail.css";
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import { Link, useLocation, useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from "axios";

import TableOrderBy from "../../components/table/TableOrderBy";

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import Star from "../../components/img/ReviewStar.PNG"
import DeleteIcon from '@mui/icons-material/Delete';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const CustomerDetail = () => {
    const { customerId } = useParams();

    const [customer, setCustomer] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const [orders, setOrders] = useState([]);

    const [reviews, setReviews] = useState([]);
    const [visibleReviews, setVisibleReviews] = useState(5); // Số lượng đánh giá hiển thị ban đầu
    const [amountReview, setAmountReview] = useState(0);
    const [stats, setStats] = useState({});
    const [selectedStar, setSelectedStar] = useState(null); //loc danh gia theo so sao
    const [textStar, setTextStar] = useState('Tất cả đánh giá');

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;


    const calculateStats = reviews => {
        const totalReviews = reviews.length;
        const totalStars = reviews.reduce((acc, review) => acc + review.numberofstar, 0);
        const averageStars = totalStars / totalReviews || 0;

        // Tính số lượng đánh giá cho mỗi số sao
        const starsCount = {};
        for (let i = 1; i <= 5; i++) {
            const count = reviews.filter(review => review.numberofstar === i).length;
            starsCount[i] = count;
        }
        return {
            totalReviews,
            averageStars,
            starsCount
        };
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/account/get/${customerId}`);
                if (response.status === 200) {
                    const contentType = response.headers['content-type'];
                    if (contentType && contentType.includes('application/json')) {
                        setCustomer(response.data);
                    } else {
                        throw new Error('Invalid content type');
                    }
                } else {
                    throw new Error('Network response was not ok');
                }
                setLoading(false);
            } catch (error) {
                console.error('Error fetching product data:', error);
                setError('Error fetching product data. Please try again later.');
                setLoading(false);
            }
        };

        const fetchOrdersData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/order/all`);
                const foundOrderAcc = response.data.filter((order) => order.account.id === parseInt(customerId));
                // setOrders(foundOrderAcc);
                const sortedOrders = foundOrderAcc.sort((a, b) => b.id - a.id); // Sắp xếp các đơn hàng theo ID giảm dần
                const top5Orders = sortedOrders.slice(0, 5); // Chọn 5 đơn hàng đầu tiên
                setOrders(top5Orders);

                // console.log(foundOrderAcc)
                // console.log("dl")
                setLoading(false);
            } catch (error) {
                setError('Error fetching orders data. Please try again later.');
                setLoading(false);
            }
        };
        const fetchReviews = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/review/account/${customerId}`);
                // setReviews(response.data);
                setAmountReview(response.data.length); // Đếm số lượng từ dữ liệu nhận được
                const sortedReviews = response.data.sort((a, b) => b.id - a.id); // Sắp xếp đánh giá theo id giảm dần
                setReviews(sortedReviews);
                const stats = calculateStats(sortedReviews);
                setStats(stats);
            } catch (error) {
                console.error('Error fetching reviews:', error);
            }
        };

        fetchReviews();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000);
            return () => clearTimeout(timer);
        }

        fetchData();
        fetchOrdersData();
    }, [customerId, showProgressBar]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (!customer) {
        return <div>Không tìm thấy sản phẩm.</div>;
    }

    const handleDeleteReview = async (reviewId) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/review/${reviewId}`);
            if (response.status === 200) {
                setMessage('Xóa đánh giá thành công!');
                setReviews(reviews.filter(review => review.id !== reviewId));
            } else {
                setMessage('Xóa đánh giá thất bại.');
            }
        } catch (error) {
            console.error('Error deleting review:', error);
            setMessage('Lỗi!');
        }
    };
    const handleShowMoreReviews = () => {
        setVisibleReviews(visibleReviews + 5); // Hiển thị thêm 5 đánh giá khi nhấn nút "Xem thêm"
    };
    const handleStarClick = (star) => {
        setSelectedStar(star);
        setVisibleReviews(5); // Reset số lượng đánh giá hiển thị khi chọn số sao
        setTextStar(`Các đánh giá ${star} sao`);
    };
    // Lọc đánh giá theo số sao
    const filteredReviews = selectedStar ? reviews.filter(review => review.numberofstar === selectedStar) : reviews;

    const customerHomeLink = role === "admin"
        ? `/admin/customerManagement`
        : role === "staff"
            ? `/staff/customerManagement`
            : `/`;

    return (
        <div className="single home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="singleContainer homeContainer">
                <Navbar />
                <Link to={customerHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="top">
                    <div className="left">
                        <h1 className="title">Thông tin khách hàng</h1>
                        <div className="item">
                            <div className="details">
                                <div className="detailItem">
                                    <span className="itemKey">Họ:</span>
                                    <span className="itemValue">{customer.firstname}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Tên:</span>
                                    <span className="itemValue">{customer.lastname}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Số điện thoại:</span>
                                    <span className="itemValue">{customer.phonenumber}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Email:</span>
                                    <span className="itemValue">{customer.email}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Địa chỉ:</span>
                                    <span className="itemValue">
                                        {customer.address ? (
                                            <span>
                                                {/* {staff.address.street}, {staff.address.ward}, {staff.address.district}, {staff.address.province} */}
                                                {customer.address.street ? customer.address.street + ', ' : '_, '}
                                                {customer.address.ward ? customer.address.ward + ', ' : '_, '}
                                                {customer.address.district ? customer.address.district + ', ' : '_, '}
                                                {customer.address.province ? customer.address.province + '.' : '_'}
                                            </span>
                                        ) : (
                                            <span></span>
                                        )}
                                    </span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Trạng thái:</span>
                                    <span className="itemValue">{customer.account_status.status === 'Active' ? 'Hoạt động' : 'Khóa'}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bottom">
                    <div className="bottom2">
                        <h1 className="title">Đơn hàng gần nhất</h1>
                        <TableOrderBy orders={orders} />
                    </div>
                    <div className="bottom2">
                        {message && (
                            <div className="success-message">
                                {message}
                                {showProgressBar && <div className="progress-bar" />}
                            </div>
                        )}
                        <h1 className="title">Đánh giá ({amountReview})</h1>
                        {stats.totalReviews > 0 && (
                            <div className="stats-titleDetail">
                                <p>Số sao trung bình: {stats.averageStars.toFixed(1)}</p>
                                {stats && stats.starsCount && (
                                    <div className="star-container">
                                        {Object.entries(stats.starsCount).map(([star, count]) => (
                                            <div
                                                key={star}
                                                className="star-countDetail"
                                                onClick={() => handleStarClick(parseInt(star))}
                                            >
                                                <p>{star} sao:</p>
                                                <p>({count})</p>
                                            </div>
                                        ))}
                                    </div>
                                )}
                            </div>
                        )}
                        <p className="textStar">{textStar}</p>
                        {filteredReviews.slice(0, visibleReviews).map((review, index) => (
                            <div className="detailsReview" key={review.id}>

                                <h1 className="itemTitleCmt">{review.account.firstname} {review.account.lastname}</h1>
                                <div className="itemReview">
                                    <div className="detailItemReview">
                                        <span className="starRating">
                                            {[...Array(review.numberofstar)].map((star, index) => (
                                                <img src={Star} alt="star" key={index} />
                                            ))}
                                        </span>
                                    </div>
                                    <div className="detailItemReview">
                                        <span className="itemValueDate">{review.datereview}</span>
                                    </div>
                                    <div className="detailItemReview ">
                                        <span className="itemValueContent">{review.content} </span>
                                    </div>
                                    <div
                                        className="deleteButtonReview"
                                        onClick={() => handleDeleteReview(review.id)}
                                    >
                                        <DeleteIcon />
                                    </div>
                                </div>
                                {index !== filteredReviews.length - 1 && <hr className="separator" />} {/* Gạch ngang phân cách */}
                            </div>
                        ))}
                        {visibleReviews < filteredReviews.length && (
                            <div className="showMoreButton" onClick={handleShowMoreReviews}>Xem thêm</div>
                        )}

                    </div>
                </div>
            </div>
        </div>
    );
};

export default CustomerDetail;
