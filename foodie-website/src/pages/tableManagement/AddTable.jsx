
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

const AddTable = () => {
    const navigate = useNavigate();

    const [tableNumber, setTableNumber] = useState('');
    const [seatingCapacity, setSeatingCapacity] = useState('');
    // State mới để lưu giá trị phần trăm dưới dạng số thập phân
    //const [percentValue, setPercentValue] = useState(0);
    const [status, setStatus] = useState('');
    //const [startdate, setStartdate] = useState('');
    //lỗi nhập định dạng %
    const [messageErr, setMessageErr] = useState('');
    //const [enddate, setEnddate] = useState('');
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [isExist, setIsExist] = useState('true');
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
        setTableNumber(event.target.value);
    };
    const handlePercentChange = (event) => {
        setSeatingCapacity(event.target.value);
    };
    // const handlePercentChange = (event) => {
    //     const percentInput = event.target.value;
    //     //phần trăm dưới dạng "20%"
    //     const percentRegex = /^(\d+)%$/; // Regex để kiểm tra định dạng phần trăm
    //     if (percentRegex.test(percentInput)) {
    //         // Nếu đúng định dạng, chuyển đổi thành phần trăm dạng số thập phân
    //         const percentValue = parseFloat(percentInput.match(/^(\d+)/)[0]) / 100;

    //         if (percentValue > 0 && percentValue <= 1) {
    //             setPercent(percentInput); // Hiển thị phần trăm trên giao diện
    //             setPercentValue((percentValue).toString()); // Lưu phần trăm dưới dạng số thập phân
    //             setMessageErr('');
    //         } else {
    //             setMessageErr('Vui lòng nhập giá trị phần trăm trong khoảng từ 1 đến 100.');

    //         }
    //     } else {
    //         setMessageErr('Vui lòng nhập đúng định dạng phần trăm (ví dụ: 20%)');
    //         setPercent(percentInput); // Nếu không, giữ nguyên giá trị nhập vào
    //     }
    // };
    // const handleIsExistChange = (event) => {
    //     setIsExist(event.target.value);
    // };
    // const handleStartdateChange = (event) => {
    //     setStartdate(event.target.value);
    // };
    // const handleEnddateChange = (event) => {
    //     setEnddate(event.target.value);
    // };

    const formatDate = (dateString) => {
        const [year, month, day] = dateString.split('-');
        return `${day}/${month}/${year}`;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        // if (messageErr) {
        // setShowProgressBar(true);
        // setMessage("Bạn chưa nhập đúng định dạng!! Cần nhập lại")
        // return;
        // }
        setShowProgressBar(true);
        try {
            // const formattedStartdate = formatDate(startdate);
            // const formattedEnddate = formatDate(enddate);
            const response = await axios.post(`${backendUrl}/api/tableInfo/add`, { tableNumber, seatingCapacity, status: "available", isExist });
            // percent: percent / 100
            if (response.status === 201) {
                setMessage('Tạo bàn thành công!');
                setTableNumber('');
                setSeatingCapacity('');
                // setStartdate('');
                // setEnddate('');
                // setMessageErr('');
                setTimeout(() => {
                    // navigate('/staff/tableManagement');
                    if (role === "admin") {
                        navigate(`/admin/tableManagement`);
                    } else if (role === "staff") {
                        navigate(`/staff/tableManagement`);
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

    const TableHomeLink = role === "admin"
        ? "/admin/tableManagement"
        : role === "staff"
            ? "/staff/tableManagement"
            : "/";
    return (
        <div className="new home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="newContainer homeContainer">
                <Navbar />
                <Link to={TableHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="bottom">
                    <div className="column2">
                        <div className="column">
                            <div className="title top">
                                <h1>Thêm bàn</h1>
                            </div>
                            <form onSubmit={handleSubmit}>

                                <div className="formInput">
                                    <label>Bàn:</label>
                                    <input type="text" value={tableNumber} placeholder="" onChange={handleCodeChange} required />
                                </div>
                                <div className="formInput">
                                    <label>Số chỗ:</label>
                                    <input type="text" value={seatingCapacity} placeholder="" onChange={handlePercentChange} required />
                                    {/* {messageErr && <div className="errorMsg">{messageErr}</div>} */}
                                </div>
                                {/* <div className="formInput">
                                    <label>Ngày bắt đầu:</label>
                                    <input type="date" value={startdate} onChange={handleStartdateChange} required />
                                </div>
                                <div className="formInput">
                                    <label>Ngày kết thúc:</label>
                                    <input type="date" value={enddate} onChange={handleEnddateChange} required />
                                </div> */}
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

export default AddTable;
