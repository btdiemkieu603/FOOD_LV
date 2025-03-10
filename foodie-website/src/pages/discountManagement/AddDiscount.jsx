
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "../../css/mngtForm.css";
import React, { useState, useEffect } from 'react';
//import { discountInputs } from "../../data/dataFormDiscountMngt";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const AddDiscount = () => {
    const navigate = useNavigate();

    const [code, setCode] = useState('');
    const [percent, setPercent] = useState('');
    // State mới để lưu giá trị phần trăm dưới dạng số thập phân
    const [percentValue, setPercentValue] = useState(0);
    const [isExist, setIsExist] = useState('true');
    const [startdate, setStartdate] = useState('');
    //lỗi nhập định dạng %
    const [messageErr, setMessageErr] = useState('');
    const [enddate, setEnddate] = useState('');
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);
    useEffect(() => {
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 1500);
            return () => clearTimeout(timer);
        }
    }, [showProgressBar]);

    const handleCodeChange = (event) => {
        setCode(event.target.value);
    };
    const handlePercentChange = (event) => {
        const percentInput = event.target.value;
        //phần trăm dưới dạng "20%"
        const percentRegex = /^(\d+)%$/; // Regex để kiểm tra định dạng phần trăm
        if (percentRegex.test(percentInput)) {
            // Nếu đúng định dạng, chuyển đổi thành phần trăm dạng số thập phân
            const percentValue = parseFloat(percentInput.match(/^(\d+)/)[0]) / 100;

            if (percentValue > 0 && percentValue <= 1) {
                setPercent(percentInput); // Hiển thị phần trăm trên giao diện
                setPercentValue((percentValue).toString()); // Lưu phần trăm dưới dạng số thập phân
                setMessageErr('');
            } else {
                setMessageErr('Vui lòng nhập giá trị phần trăm trong khoảng từ 1 đến 100.');

            }
        } else {
            setMessageErr('Vui lòng nhập đúng định dạng phần trăm (ví dụ: 20%)');
            setPercent(percentInput); // Nếu không, giữ nguyên giá trị nhập vào
        }
    };
    const handleIsExistChange = (event) => {
        setIsExist(event.target.value);
    };
    const handleStartdateChange = (event) => {
        setStartdate(event.target.value);
    };
    const handleEnddateChange = (event) => {
        setEnddate(event.target.value);
    };

    const formatDate = (dateString) => {
        const [year, month, day] = dateString.split('-');
        return `${day}/${month}/${year}`;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (messageErr) {
            setShowProgressBar(true);
            setMessage("Bạn chưa nhập đúng định dạng!! Cần nhập lại")
            return;
        }
        setShowProgressBar(true);
        try {
            const formattedStartdate = formatDate(startdate);
            const formattedEnddate = formatDate(enddate);
            const response = await axios.post(`${backendUrl}/api/discount/add`, { code, percent: percentValue, isExist, startdate: formattedStartdate, enddate: formattedEnddate });
            // percent: percent / 100
            if (response.status === 201) {
                setMessage('Tạo khuyến mãi thành công!');
                setCode('');
                setPercent('');
                setStartdate('');
                setEnddate('');
                setMessageErr('');
                setTimeout(() => {
                    // navigate('/admin/discountManagement');
                    if (role === "admin") {
                        navigate("/admin/discountManagement");
                    } else if (role === "staff") {
                        navigate("/staff/discountManagement");
                    }
                }, 2000);
            } else {
                setMessage('Tạo thất bại.');
            }
        } catch (error) {
            console.error('Error adding category:', error);
            setMessage('Có lỗi xảy ra.');
        }
    };

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    const DiscountHomeLink = role === "admin"
        ? "/admin/discountManagement"
        : role === "staff"
            ? "/staff/discountManagement"
            : "/";

    return (
        <div className="new home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="newContainer homeContainer">
                <Navbar />
                <Link to={DiscountHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="bottom">
                    <div className="column2">
                        <div className="column">
                            <div className="title top">
                                <h1>Thêm khuyến mãi</h1>
                            </div>
                            <form onSubmit={handleSubmit}>

                                <div className="formInput">
                                    <label>Khuyến mãi:</label>
                                    <input type="text" value={code} placeholder="KM02" onChange={handleCodeChange} required />
                                </div>
                                <div className="formInput">
                                    <label>Giá trị (%):</label>
                                    <input type="text" value={percent} placeholder="20%" onChange={handlePercentChange} required />
                                    {messageErr && <div className="errorMsg">{messageErr}</div>}
                                </div>
                                <div className="formInput">
                                    <label>Ngày bắt đầu:</label>
                                    <input type="date" value={startdate} onChange={handleStartdateChange} required />
                                </div>
                                <div className="formInput">
                                    <label>Ngày kết thúc:</label>
                                    <input type="date" value={enddate} onChange={handleEnddateChange} required />
                                </div>
                                <button type="submit" >Thêm</button>
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

export default AddDiscount;
