
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "../../css/mngtForm.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate, useParams } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const EditDiscount = () => {
    //const [inputs] = useState(discountInputs);
    const { discountId } = useParams();
    // State mới để lưu giá trị phần trăm dưới dạng số thập phân
    const [percentValue, setPercentValue] = useState(0);
    const navigate = useNavigate();
    const [message, setMessage] = useState('');
    //lỗi nhập định dạng %
    const [messageErr, setMessageErr] = useState('');
    // const [error, setError] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [discount, setDiscount] = useState({
        code: '',
        percent: '',
        startdate: '',
        enddate: '',
        isExist: '',
    });

    const getFormatDate = (dateString) => {
        const [day, month, year] = dateString.split('/');
        return `${year}-${month}-${day}`;
    };
    const putFormatDate = (dateString) => {
        const [year, month, day] = dateString.split('-');
        return `${day}/${month}/${year}`;
    };
    useEffect(() => {
        fetchData();
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000);
            return () => clearTimeout(timer);
        }
    }, [discountId, showProgressBar]);

    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/discount/all`);
            const foundDiscount = response.data.find((discount) => discount.id === parseInt(discountId));
            const getFormattedDiscount = {
                ...foundDiscount,
                startdate: getFormatDate(foundDiscount.startdate),
                enddate: getFormatDate(foundDiscount.enddate),
                percent: foundDiscount.percent * 100 + '%'
            };
            setDiscount(getFormattedDiscount);
            //console.log(foundDiscount)
        } catch (error) {
            console.error('Error fetching product data:', error);
            setMessage('Có lỗi xảy ra khi lấy dữ liệu');
        }
    };
    const handleChange = (e) => {
        const { name, value } = e.target;
        let processedValue = value;

        // Kiểm tra nếu trường là percent thì xử lý giá trị
        if (name === "percent") {
            const percentInput = value;
            const percentRegex = /^(\d+)%$/; // Regex để kiểm tra định dạng phần trăm

            if (percentRegex.test(percentInput)) {
                // Nếu đúng định dạng, chuyển đổi thành phần trăm dạng số thập phân
                const percentValue = parseFloat(percentInput.match(/^(\d+(\.\d{0,2})?)%/)[0]) / 100;
                if (percentValue > 0 && percentValue <= 1) {
                    setPercentValue(percentValue); // Lưu phần trăm dưới dạng số thập phân
                    setMessageErr('');
                } else {
                    setMessageErr('Vui lòng nhập giá trị phần trăm trong khoảng từ 0 đến 100.');
                }
            } else {
                setMessageErr('Vui lòng nhập đúng định dạng phần trăm (ví dụ: 20%)');
            }
            processedValue = percentInput;
        }
        setDiscount({
            ...discount,
            [name]: processedValue
            // [name]: value
        });
    };
    const handleSubmitEditDiscount = async (event) => {
        event.preventDefault();
        if (messageErr) {
            setShowProgressBar(true);
            setMessage("Bạn chưa nhập đúng định dạng!! Cần nhập lại")
            return;
        }
        // if (message ) return; // Không thực hiện submit nếu có lỗi
        setShowProgressBar(true);
        try {
            const putFormattedDiscount = {
                ...discount,
                startdate: putFormatDate(discount.startdate),
                enddate: putFormatDate(discount.enddate),
                percent: percentValue
            };

            const response = await axios.put(`${backendUrl}/api/discount/${discountId}`, putFormattedDiscount);
            if (response.status === 200) {
                setMessage('Cập nhật khuyến mãi thành công.');
                setDiscount(prevDiscount => ({ ...prevDiscount, ...discount }));
                setTimeout(() => {
                    navigate('/admin/discountManagement');
                }, 1500);
            } else {
                setMessage('Cập nhật khuyến mãi thất bại.');
            }
        } catch (error) {
            console.error('Error updating product:', error);
            setMessage('Có lỗi xảy ra khi lấy dữ liệu.');
        }
    };
    if (!discount) {
        return null;
    }
    return (
        <div className="new home">
            <Sidebar />
            <div className="newContainer homeContainer">
                <Navbar title={"Chỉnh sửa thông tin khuyến mãi"} />
                <Link to={`/admin/discountManagement`} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="bottom">
                    <div className="column2">
                        <div className="column">
                            <form onSubmit={handleSubmitEditDiscount}>
                                <div className="formInput">
                                    <label>Tên khuyến mãi:</label>
                                    <input type="text" name="code" value={discount.code} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Giá trị (%):</label>
                                    <input type="text" name="percent" value={discount.percent} onChange={handleChange} />
                                    {messageErr && <div className="errorMsg">{messageErr}</div>}
                                </div>
                                <div className="formInput">
                                    <label>Ngày bắt đầu:</label>
                                    <input type="date" name="startdate" value={discount.startdate} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Ngày kết thúc:</label>
                                    <input type="date" name="enddate" value={discount.enddate} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Trạng thái:</label>
                                    {/* <input type="text" name="url_image_category" value={category.url_image_category} onChange={handleChange} /> */}
                                    <select name="isExist" value={discount.isExist} onChange={handleChange}>
                                        <option>Chọn 1 trong 2 trạng thái</option> {/* Mục mặc định */}
                                        <option value={true}>true</option>
                                        <option value={false}>false</option>
                                    </select>
                                </div>

                                {/* {inputs.map((input) => (
                                <div className="formInput" key={input.id}>
                                    <label>{input.label}</label>
                                    <input type={input.type} value={input.value} />
                                </div>
                            ))} */}
                                <button type="submit" >Cập nhật</button>
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

export default EditDiscount;
