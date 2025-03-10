import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './reviewStats.css';
import ReviewStats from './ReviewStats';

import DeleteIcon from '@mui/icons-material/Delete';
import Star from "../../components/img/ReviewStar.PNG"

const backendUrl = process.env.REACT_APP_BACKEND_URL;
// Hàm chuyển đổi định dạng ngày từ yyyy-mm-dd sang dd/mm/yyyy
const formatDateForDatabase = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
};
const formatDate = (dateString) => {
    const [day, month, year] = dateString.split('/');
    return `${year}-${month}-${day}`;
};
const ReviewMonth = ({ reviews }) => {
    const [error, setError] = useState(null);

    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [stats, setStats] = useState({});
    const [showReviewDate, setShowReviewDate] = useState(false); // Biến trạng thái để kiểm soát hiển thị của ReviewDate

    //const [filteredReviews, setFilteredReviews] = useState([]);

    const [visibleReviews, setVisibleReviews] = useState(5); // Số lượng đánh giá hiển thị ban đầu
    const [selectedStar, setSelectedStar] = useState(null); //loc danh gia theo so sao
    const [reviewData, setReviewData] = useState({ reviews });
    const [textStar, setTextStar] = useState('');

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const handleSearch = () => {
        if (!startDate || !endDate) {
            showProgressBar(true)
            message("Vui lòng nhập cả ngày bắt đầu và kết thúc!");
            return;
        }

        //(lỗi 29/3/2024 -> 6/4/2024 thì lỗi formattedStartDate > formattedEndDate)
        //nên chuyển về dạng yyyy-mm-dd để so sánh ngày thì không bị lỗi
        // const formattedStartDate = formatDateForDatabase(startDate);
        // const formattedEndDate = formatDateForDatabase(endDate);

        // if (formattedStartDate > formattedEndDate) {
        //     alert("Ngày bắt đầu phải trước ngày kết thúc!");
        //     return;
        // }

        const filteredReviews = reviews.filter(review => {
            const reviewDate = formatDate(review.datereview);
            return reviewDate >= startDate && reviewDate <= endDate;
        });
        //thêm sắp xếp theo ngày gần nhất thì số sao thống kê bị sai?? không sửa được
        //  const sortedReviews = reviews.sort((a, b) => b.id - a.id); // Sắp xếp đánh giá theo id giảm dần
        const stats = calculateStats(filteredReviews);
        // console.log('stats', stats)
        // console.log('review', reviews)
        // console.log('reviewDate', filteredReviews)

        //setReviewData(sortedReviews)
        setReviewData(filteredReviews)
        setStats(stats);

        setShowReviewDate(true);
        setSelectedStar(null);
        setTextStar(`Tất cả đánh giá từ ngày ${formatDateForDatabase(startDate)} đến ngày ${formatDateForDatabase(endDate)}`)
    };

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
        // const fetchReviews = async () => {
        // //     try {
        // const sortedReviews = reviews.sort((a, b) => b.id - a.id); // Sắp xếp đánh giá theo id giảm dần
        // setReviewData(sortedReviews);
        //         const stats = calculateStats(sortedReviews);
        //         setStats(stats);
        //     } catch (error) {
        //         console.error('Error fetching reviews:', error);
        //     }

        // };

        // fetchReviews();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000); // Thời gian hiển thị thanh thời gian chạy: 3000ms
            return () => clearTimeout(timer);
        }
        // Không cần cleanup effect ở đây vì chúng ta không có sử dụng subscriptions hoặc timers
    }, [showProgressBar]);

    //thêm sắp xếp ngày gần nhất?? bị sai
    // useEffect(() => {
    //     const sortedReviews = reviews.sort((a, b) => new Date(b.datereview) - new Date(a.datereview));
    //     setReviewData(sortedReviews);
    // }, [reviews]);

    if (error) {
        return <div>{error}</div>;
    }

    const handleDeleteReview = async (reviewId) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/review/${reviewId}`);
            if (response.status === 200) {
                setMessage('Xóa đánh giá thành công!');
                setReviewData(reviews.filter(review => review.id !== reviewId));
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
        setTextStar(`Các đánh giá ${star} sao từ ngày ${formatDateForDatabase(startDate)} đến ngày ${formatDateForDatabase(endDate)}`);
        //định dạng này tính từ ngày đến ngày sai
        // const formattedStartDate = formatDateForDatabase(startDate);
        //const formattedEndDate = formatDateForDatabase(endDate);

        const filteredReviews = reviews.filter(review => {
            const reviewDate = formatDate(review.datereview)
            return reviewDate >= startDate && reviewDate <= endDate;
        });
        const filteredReviewsByStar = filteredReviews.filter(review => review.numberofstar === star);
        //const sortedReviews = filteredReviewsByStar.sort((a, b) => b.id - a.id); // Sắp xếp đánh giá theo id giảm dần

        setReviewData(filteredReviewsByStar);
        setShowReviewDate(true);
    };
    // Lọc đánh giá theo số sao
    //const filteredReviews = selectedStar ? reviews.filter(review => review.numberofstar === selectedStar) : reviews;

    return (
        <div className='review'>
            {message && (
                <div className="success-message">
                    {message}
                    {showProgressBar && <div className="progress-bar" />}
                </div>
            )}

            <div className='inline'>
                <ReviewStats reviews={reviews} />
                <div className='inline2'>
                    <h2>Thống kê số sao đánh giá trong khoảng thời gian</h2>
                    <div className="date-range">
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                            <label htmlFor="start-date">Từ ngày:</label>
                            <input
                                type="date"
                                id="start-date"
                                value={startDate}
                                onChange={e => setStartDate(e.target.value)}
                            />
                        </div>
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                            <label htmlFor="end-date">Đến ngày:</label>
                            <input
                                type="date"
                                id="end-date"
                                value={endDate}
                                onChange={e => setEndDate(e.target.value)}
                            />
                        </div>
                    </div>
                    <button onClick={handleSearch}>Tìm kiếm</button>
                </div>
            </div>
            {/* Hiển thị ReviewDate khi showReviewDate là true */}
            <div className='contentReview'>
                {stats.totalReviews > 0 && showReviewDate && (
                    <div className="stats-info">
                        <p>Số lượng đánh giá: {stats.totalReviews}</p>
                        <p>Số sao trung bình: {stats.averageStars.toFixed(1)}</p>
                        {stats && stats.starsCount && (
                            <div className="star-container">

                                {Object.entries(stats.starsCount).map(([star, count]) => (
                                    <div
                                        key={star}
                                        className="star-countDetail"
                                        onClick={() => handleStarClick(parseInt(star))}
                                    // style={{ cursor: 'pointer' }}
                                    >
                                        <p>{star} sao:</p>
                                        <p>({count})</p>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                )}
                {showReviewDate && (
                    <p className="textStar">{textStar}</p>
                )}
                {/* teredReviews.slice(0, visibleReviews).map((review, index) => ( */}
                {showReviewDate && Array.isArray(reviewData) && reviewData.slice(0, visibleReviews).map((review, index) => (
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
                        {index !== reviewData.length - 1 && <hr className="separator" />} {/* Gạch ngang phân cách */}
                    </div>
                ))}
                {visibleReviews < reviewData.length && showReviewDate && (
                    <div className="showMoreButton" onClick={handleShowMoreReviews}>Xem thêm</div>
                )}
            </div>
        </div>
    );

};

export default ReviewMonth;
