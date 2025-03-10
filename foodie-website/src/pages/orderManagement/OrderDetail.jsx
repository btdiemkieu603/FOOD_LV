import "../../css/mngtDetail.css";
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import { Link, useLocation, useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from "axios";

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CheckIcon from '@mui/icons-material/Check';

import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const OrderDetail = () => {
    const { orderId } = useParams();
    const [order, setOrder] = useState(null);

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [currentStep, setCurrentStep] = useState(1);
    const [complete, setComplete] = useState(false);
    const [cancelled, setCancelled] = useState(false); // Thêm trạng thái cho việc hủy
    const [isWaitingConfirmation, setIsWaitingConfirmation] = useState(true); // Thêm trạng thái cho việc kiểm tra bước chờ xác nhận

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;
    const OrderHomeLink = role === "admin"
        ? "/admin/orderManagement"
        : role === "staff"
            ? "/staff/orderManagement"
            : "/";

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/order/all`);
                const getOrderId = response.data.find(order => order.id === parseInt(orderId));
                setOrder(getOrderId);

                setCurrentStep(getOrderId.orderStatus.id);
                if (getOrderId.orderStatus.status !== "watingConfirmation") {
                    setIsWaitingConfirmation(false);
                };
                if (getOrderId.orderStatus.status === "Completed") {
                    setComplete(true);
                };
                if (getOrderId.orderStatus.status === "Cancel") {
                    setCancelled(true);
                }
                // console.log(getOrderId)
                // console.log(getOrderId.orderStatus.id)

            } catch (error) {
                console.error('Error fetching product data:', error);

            }
        };

        fetchData();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000);
            return () => clearTimeout(timer);
        }

    }, [showProgressBar, orderId]);
    if (!order) {
        return <div>Không tìm thấy đơn hàng.</div>;
    }

    const steps = [
        // { label: 'Hủy', icon: '❌' },
        { label: 'Chờ xác nhận', icon: '⏳' },
        { label: 'Chờ lấy hàng', icon: '🚚', text: 'Xác nhận' },
        { label: 'Đang đến', icon: '📦', text: 'Lấy hàng' },
        { label: 'Đã giao', icon: '🎉', text: 'Đã giao' },


    ];

    const handleUpdateStatus = async (id, status) => {
        console.log('cập nhật')
        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/order/${id}/${status + 1}`)

            if (response.status === 200) {
                setMessage('Cập nhật trạng thái thành công!');
            } else {
                setMessage('Cập nhật trạng thái thất bại.');
            }
        } catch (error) {
            console.error('Error deleting product:', error);
            setMessage('Lỗi!');
        }
    };

    const handleCancelOrder = async (id) => {
        console.log("Hủy đơn hàng");
        setCancelled(true);
        setCurrentStep(steps.length + 1);
        // Chuyển sang bước cuối cùng khi hủy
        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/order/${id}/5`)
            if (response.status === 200) {
                setMessage('Hủy đơn hàng thành công!');
            } else {
                setMessage('Hủy đơn hàng thất bại.');
            }
        } catch (error) {
            console.error('Loi huy don hang:', error);
            setMessage('Lỗi!');
        }
    };
    const consolidateItems = (items) => {
        const consolidatedItems = {};
        items.forEach(item => {
            if (consolidatedItems[item.product.name]) {
                consolidatedItems[item.product.name].quantity += item.quantity;
            } else {
                consolidatedItems[item.product.name] = { ...item };
            }
        });
        return Object.values(consolidatedItems);
    };
    const calculateTotalPrice = () => {
        let totalPrice = 0;
        order.items.forEach(item => {
            // totalPrice += item.price * item.quantity;
            totalPrice += item.price;
        });
        return totalPrice;
    };
    const calculateDiscount = () => {
        if (order.discount && order.discount.isExist) {
            // return calculateTotalPrice() * order.discount.percent;
            return calculateTotalPrice() - order.totalprice
        }
        return 0;
    };

    return (
        <div className="single home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="singleContainer homeContainer">
                <Navbar />
                <Link to={OrderHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                {message && (
                    <div className="success-message">
                        {message}
                        {showProgressBar && <div className="progress-bar" />}
                    </div>
                )}
                <div className="top">
                    <div className="left">
                        <h1 className="title">Thông tin đơn hàng</h1>

                        <h3 className="itemTitle2 ">Đơn hàng: {order.id}</h3>
                        <div className="item2">

                            <div className="details2">

                                <div className="detailItem2" >
                                    <span className="itemKey2">Khách hàng:</span>
                                    <span className="itemValue">{order.account.firstname || ''} {order.account.lastname || ''}</span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Số điện thoại:</span>
                                    <span className="itemValue">{order.account.phonenumber}</span>
                                </div>

                                <div className="detailItem2">
                                    <span className="itemKey2">email:</span>
                                    <span className="itemValue">{order.account.email}</span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Địa chỉ:</span>
                                    <span className="itemValue">
                                        {order.account.address ? (
                                            <span>
                                                {order.account.address.street ? order.account.address.street + ', ' : '_, '}
                                                {order.account.address.ward ? order.account.address.ward + ', ' : '_, '}
                                                {order.account.address.district ? order.account.address.district + ', ' : '_, '}
                                                {order.account.address.province ? order.account.address.province + '.' : '_'}
                                            </span>
                                        ) : (
                                            <span></span>
                                        )}
                                    </span>
                                </div>

                                <div className="detailItem2">
                                    <span className="itemKey2">Ngày đặt hàng:</span>
                                    <span className="itemValue">{order.orderdate}</span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Khuyến mãi áp dụng</span>
                                    <span className="itemValue">
                                        {order.discount?.code ? (
                                            <span>
                                                {/* {order.discount?.code} giảm {order.discount?.percent * 100}% */}
                                                {order.discount?.code} giảm {calculateDiscount() * 100 / calculateTotalPrice()}%
                                            </span>
                                        ) : (
                                            <span></span>
                                        )}
                                    </span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Mã hóa đơn:</span>
                                    <span className="itemValue">
                                        {order.bill.id}
                                    </span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Ngày xuất hóa đơn:</span>
                                    <span className="itemValue">
                                        {order.bill.issuedate}
                                    </span>
                                </div>

                                {/* <div className="detailItem">
                                    <span className="itemKey">Ghi chú:</span>
                                    <span className="itemValue"></span>
                                </div> */}
                                <div className="detailItem2">
                                    <span className="itemKey2">Hình thức thanh toán:</span>
                                    <span className="itemValue">
                                        {order.checkout?.method === 'credit card' ? 'Ví ngân hàng'
                                            : order.checkout?.method === 'cash' ? 'Khi nhận hàng'
                                                : order.checkout?.method === 'momo' ? 'Ví Momo' : ''}
                                    </span>
                                </div>
                                <div className="detailItem2">
                                    <span className="itemKey2">Trạng thái:</span>
                                    <span className="itemValue">
                                        {order.orderStatus?.status === 'watingConfirmation' ? 'Chờ xác nhận'
                                            : order.orderStatus?.status === 'Processing' ? 'Chờ lấy hàng'
                                                : order.orderStatus?.status === 'beingTransported' ? 'Đang đến'
                                                    : order.orderStatus?.status === 'Completed' ? 'Đã giao'
                                                        : order.orderStatus?.status === 'Cancel' ? 'Hủy'
                                                            : ''}
                                    </span>
                                </div>

                            </div>
                        </div>

                        <div>
                            <TableContainer component={Paper} className="table">
                                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell className="tableCell">STT</TableCell>
                                            <TableCell className="tableCell">Tên món ăn</TableCell>
                                            <TableCell className="tableCell">Giá</TableCell>

                                            <TableCell className="tableCell">Số lượng</TableCell>
                                            <TableCell className="tableCell">Tổng tiền</TableCell>

                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {consolidateItems(order.items).map((item, index) => (
                                            <TableRow key={index}>
                                                <TableCell>{index + 1}</TableCell> {/* Số thứ tự tự động tăng */}
                                                <TableCell><img className="cellImg" src={item.product.url_image_product} alt="avatar" />{item.product.name}</TableCell>
                                                <TableCell>{item.price / item.quantity}</TableCell>

                                                <TableCell>{item.quantity}</TableCell>
                                                <TableCell>{item.price}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                                <div className="TotalPrice">
                                    <div className="TotalPrice_item">
                                        <p className="TotalPrice_item1" >Tổng tiền tất cả món ăn:</p>
                                        <p className="TotalPrice_item2" style={{ marginLeft: '20px' }}> {calculateTotalPrice()}<p className="VND">đ</p></p>
                                    </div>
                                    <div className="TotalPrice_item">
                                        <p className="TotalPrice_item1" >Khuyến mãi:</p>
                                        <p className="TotalPrice_item2" style={{ marginLeft: '15px' }}>- {calculateDiscount()}<p className="VND">đ</p></p>
                                    </div>
                                    <div className="TotalPrice_item">
                                        <p className="TotalPrice_item1" >Phí giao hàng:</p>
                                        <p className="TotalPrice_item2" style={{ marginLeft: '3px' }}>+ 10000<p className="VND">đ</p></p>
                                    </div>
                                    <div className="TotalPrice_item">
                                        <p className="TotalPrice_item1" >Thanh toán:</p>
                                        <p className="TotalPrice_item2" style={{ marginLeft: '17px' }}>{calculateTotalPrice() - calculateDiscount() + 10000}<p className="VND">đ</p></p>
                                    </div>

                                </div>
                            </TableContainer>

                        </div>
                    </div>

                </div>
                <div className="bottom">
                    <h1 className="title">Cập nhật trạng thái đơn hàng</h1>

                    <div className="updateStatus">
                        <div className="flex justify-between">
                            {steps?.map((step, i) => (
                                <div
                                    key={i}
                                    className={`step-item ${currentStep === i + 1 && "active"} ${i + 1 < currentStep || complete ? "complete" : ""} ${cancelled && i + 1 <= steps.length && "cancel"
                                        } `}
                                >
                                    <div className="step">
                                        {i + 1 < currentStep + 1 || complete ?
                                            <CheckIcon size={24} /> : i + 1}
                                    </div>

                                    {/* <p className="text-gray-500">{step}</p> */}
                                    <span className="step-label">{step.label}</span>
                                    <span className="step-icon">{step.icon}</span>
                                </div>
                            ))}
                        </div>

                        {!complete && (
                            <button
                                className={`btn ${cancelled && "hide"}`} // Thêm lớp 'cancel' khi bị hủy
                                onClick={() => {
                                    handleUpdateStatus(order.id, order.orderStatus.id);
                                    setIsWaitingConfirmation(false);
                                    currentStep === steps.length - 1 ? setComplete(true) : setCurrentStep((prev) => prev + 1);
                                }}
                                disabled={cancelled} // Vô hiệu hóa nút khi bị hủy
                            >
                                {/* {currentStep === steps.length ? "Finish" : steps[currentStep].text} */}
                                {currentStep === steps.length ? "Hoàn Thành" : "Cập nhật"}
                            </button>
                        )}

                        <button
                            className={`btn ${!isWaitingConfirmation && "hide"} ${cancelled && "hide"}`} // Thêm lớp 'hide' để ẩn nút khi không ở bước chờ xác nhận
                            onClick={() => {
                                handleCancelOrder(order.id);
                                setCancelled(true);
                            }}
                        >
                            Hủy
                        </button>

                        <div className="messageStatus messageStatus_cancel ">
                            {cancelled && <p>Đơn hàng đã bị hủy</p>}
                        </div>
                        <div className="messageStatus messageStatus_complete">
                            {complete && <p>Đơn hàng đã giao thành công</p>}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrderDetail;
