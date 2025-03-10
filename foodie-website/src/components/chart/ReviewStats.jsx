
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './reviewStats.css';

const backendUrl = process.env.REACT_APP_BACKEND_URL;
const ReviewStats = () => {
    const [totalReviews, setTotalReviews] = useState(0);
    const [averageStars, setAverageStars] = useState(0);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/review/all`);
                const reviews = response.data;
                if (reviews.length > 0) {
                    const totalStars = reviews.reduce((acc, review) => acc + review.numberofstar, 0);
                    const average = totalStars / reviews.length;
                    setTotalReviews(reviews.length);
                    setAverageStars(average);
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData(); // Gọi hàm để lấy dữ liệu từ API khi component được render
    }, []); // Sử dụng mảng rỗng để chỉ gọi một lần sau khi component được render

    return (
        <div className="inline3">
            <h2>Thống kê đánh giá</h2>
            <p>Tổng số đánh giá: {totalReviews}</p>
            <p>Số sao trung bình: {averageStars.toFixed(1)}</p>
        </div>
    );
};

export default ReviewStats;
