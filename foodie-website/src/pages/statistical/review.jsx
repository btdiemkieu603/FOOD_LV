import React, { useEffect, useState } from 'react';
import "./statistical.css"
import Sidebar from '../../components/sidebar/Sidebar';
import Navbar from '../../components/navbar/Navbar';
import axios from 'axios';

import ReviewMonth from '../../components/chart/ReviewMonth';
import TableProductStats from '../../components/table/TableProductStats';
import TableCustomerStats from '../../components/table/TableCustomerStats';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const Review = () => {
    const [review, setReview] = useState([]);
    const [error, setError] = useState(null);
    useEffect(() => {
        const fetchReviewData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/review/all`);
                setReview(response.data);
            } catch (error) {
                setError('Error fetching orders data. Please try again later.');
            }
        };
        fetchReviewData();
    }, []);
    if (error) {
        return <div>{error}</div>;
    }
    return (
        <div className='home'>
            <Sidebar />
            <div className="homeContainer">
                <Navbar title="Thống kê đánh giá" />
                <div className="charts2">
                    <ReviewMonth reviews={review} />
                </div>

                <div className="listContainer">
                    <div className="listTitle">Bảng thống kê các đánh giá theo món ăn</div>
                    <TableProductStats />
                    <div className="listTitle">Bảng thống kê các đánh giá theo khách hàng</div>
                    <TableCustomerStats />
                </div>
            </div>
        </div>
    )
}

export default Review