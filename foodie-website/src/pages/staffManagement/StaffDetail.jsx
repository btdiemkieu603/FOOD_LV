import "../../css/mngtDetail.css";
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import { Link, useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from "axios";

import TableOrderBy from "../../components/table/TableOrderBy";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const StaffDetail = () => {
    const { staffId } = useParams();

    const [staff, setStaff] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const fetchData = async () => {
            try {
                // Thực hiện lấy dữ liệu sản phẩm dựa trên productId
                const response = await axios.get(`${backendUrl}/api/account/get/${staffId}`);
                if (response.status === 200) {
                    const contentType = response.headers['content-type'];
                    if (contentType && contentType.includes('application/json')) {
                        setStaff(response.data);
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

        fetchData();

    }, [staffId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (!staff) {
        return <div>No product found.</div>;
    }

    return (
        <div className="single home">
            <Sidebar />
            <div className="singleContainer homeContainer">
                <Navbar />
                <Link to={`/admin/staffManagement`} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="top">
                    <div className="left">
                        <Link to={`/admin/staffManagement/editStaff/${staffId}`} style={{ textDecoration: "none" }}>
                            <div className="editButton">Chỉnh sửa</div>
                        </Link>
                        <h1 className="title">Thông tin nhân viên</h1>
                        <div className="item">

                            <div className="details">
                                <div className="detailItem">
                                    <span className="itemKey">Họ:</span>
                                    <span className="itemValue">{staff.firstname}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Tên:</span>
                                    <span className="itemValue">{staff.lastname}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Số điện thoại:</span>
                                    <span className="itemValue">{staff.phonenumber}</span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Email:</span>
                                    <span className="itemValue">{staff.email}</span>
                                </div>
                                {/* <div className="detailItem">
                                    <span className="itemKey">Mật khẩu:</span>
                                    <span className="itemValue">{staff.password}</span>
                                </div> */}
                                <div className="detailItem">
                                    <span className="itemKey">Địa chỉ:</span>
                                    <span className="itemValue">
                                        {staff.address ? (
                                            <span>
                                                {/* {staff.address.street}, {staff.address.ward}, {staff.address.district}, {staff.address.province} */}
                                                {staff.address.street ? staff.address.street + ', ' : '_, '}
                                                {staff.address.ward ? staff.address.ward + ', ' : '_, '}
                                                {staff.address.district ? staff.address.district + ', ' : '_, '}
                                                {staff.address.province ? staff.address.province + '.' : '_'}
                                            </span>
                                        ) : (
                                            <span></span>
                                        )}
                                    </span>
                                </div>
                                <div className="detailItem">
                                    <span className="itemKey">Trạng thái:</span>
                                    <span className="itemValue">{staff.account_status.status === 'Active' ? 'Hoạt động' : 'Khóa'}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bottom">
                    <h1 className="title">Đơn hàng gần nhất</h1>
                    <TableOrderBy />
                </div>
            </div>
        </div>
    );
};

export default StaffDetail;
