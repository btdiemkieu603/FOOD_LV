import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import "../../css/mngtForm.css";
import React, { useEffect, useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { Link, useNavigate, useParams } from "react-router-dom";

import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const EditStaff = () => {
    const { staffId } = useParams();
    const [userData, setUserData] = useState(null);
    const [error, setError] = useState(null);

    const [message, setMessage] = useState('');
    const [messageErr, setMessageErr] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);
    const [errorsShown, setErrorsShown] = useState({});

    const [dataLoaded, setDataLoaded] = useState(false); // Biến để kiểm tra xem dữ liệu đã được tải hay chưa
    const navigate = useNavigate();
    const [address, setAddress] = useState({
        street: '',
        ward: '',
        district: '',
        province: '',
    });
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/account/get/${staffId}`);
            setUserData(response.data);
            if (response.data.address) {
                setAddress(response.data.address)
            }
            setDataLoaded(true); // Đánh dấu rằng dữ liệu đã được tải
        } catch (error) {
            console.error('Error fetching data:', error);
            setError('Có lỗi xảy ra khi lấy dữ liệu');
        }
    };

    useEffect(() => {
        if (!dataLoaded) {
            fetchData();
        }

        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
                setMessageErr('')
            }, 2000); // Thời gian hiển thị thanh thời gian chạy: 2000ms
            return () => clearTimeout(timer);

        }

    }, [showProgressBar, dataLoaded, staffId]);

    const formik = useFormik({
        initialValues: {
            email: '',
            firstName: '',
            lastName: '',
            phoneNumber: '',
        },
        validationSchema: Yup.object({
            email: Yup.string()
                //.nullable()
                .required("Email không được bỏ trống.")
                .matches(
                    /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                    'Hãy nhập email đúng định dạng'
                ),
            firstName: Yup.string()
                .required('Họ không được bỏ trống.')
                .min(2, 'Họ phải có ít nhất 2 ký tự.'),
            lastName: Yup.string()
                .required('Tên không được bỏ trống.')
                .min(2, 'Tên phải có ít nhất 2 ký tự.'),
            phoneNumber: Yup.string()
                .required('Số điện thoại không được bỏ trống.')
                .matches(
                    /(84|0[3|5|7|8|9])+([0-9]{8})\b/,
                    'Số điện thoại phải hợp lệ (có 10 số).'
                ),
        }),
        onSubmit: async (values) => {
            setShowProgressBar(true);
            try {
                const response = await axios.put(`${backendUrl}/api/account/${staffId}`, {
                    email: values.email,
                    firstname: values.firstName,
                    lastname: values.lastName,
                    phonenumber: values.phoneNumber,
                });
                if (response.status === 200) {
                    if (response.data.id) {
                        setMessage('Cập nhật thông tin nhân viên thành công.');
                        setTimeout(() => {

                            navigate('/admin/staffManagement');
                        }, 2000);
                    } else {
                        setMessageErr('Số điện thoại và mật khẩu đã được sử dụng.');
                    }
                } else {
                    setMessageErr('Cập nhật thông tin nhân viên thất bại.');
                }
            } catch (error) {
                console.error('Đã xảy ra lỗi khi gửi yêu cầu:', error);
                setMessageErr('Đã xảy ra lỗi khi gửi yêu cầu.');
            }
        },
    });

    useEffect(() => {
        if (userData) {
            formik.setValues({
                email: userData.email || '',
                firstName: userData.firstname || '',
                lastName: userData.lastname || '',
                phoneNumber: userData.phonenumber || '',
            });
        }
    }, [userData]);

    if (!userData) return <p>Loading...</p>;


    const handleInputChange = (event) => {
        // Khi có sự kiện thay đổi trường input, kiểm tra lỗi và cập nhật trạng thái lỗi
        const fieldName = event.target.name;
        const fieldValue = event.target.value;
        const fieldError = formik.errors[fieldName];

        // Kiểm tra và cập nhật lỗi ngay khi có sự thay đổi
        setErrorsShown({
            ...errorsShown,
            [fieldName]: fieldError,
        });

        formik.handleChange(event);
    };

    const handleInputBlur = (fieldName) => {
        // Khi blur ra khỏi trường input, kiểm tra và hiển thị lỗi nếu có
        const fieldError = formik.errors[fieldName];
        setErrorsShown({
            ...errorsShown,
            [fieldName]: fieldError,
        });
    };

    // const [street, setStreet] = useState('');
    // const [ward, setWard] = useState('');
    // const [district, setDistrict] = useState('');
    // const [province, setProvince] = useState('');

    // const handleStreetChange = (event) => {
    //     setStreet(event.target.value);
    // };

    // const handleWardChange = (event) => {
    //     setWard(event.target.value);
    // };

    // const handleDistrictChange = (event) => {
    //     setDistrict(event.target.value);
    // };

    // const handleProvinceChange = (event) => {
    //     setProvince(event.target.value);
    // };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAddress({
            ...address,
            [name]: value
        });
    };
    const handleSubmitAddAddress = async (event) => {
        event.preventDefault();
        setShowProgressBar(true); // Hiển thị thanh tiến trình khi bắt đầu submit
        try {
            const response = await axios.put(`${backendUrl}/api/account/address/${staffId}`, address);

            if (response.status === 200) {
                setMessage('Cập nhật địa chỉ thành công!');
                // setStreet('');
                // setWard('');
                // setDistrict('');
                // setProvince('');

                setTimeout(() => {
                    navigate('/admin/staffManagement');
                }, 2000); // Hiển

            } else {
                setMessageErr('Cập nhật địa chỉ thất bại.');

            }
        } catch (error) {
            console.error('Error adding product:', error);
            setMessage('Cập nhật địa chỉ thành công!');
            setTimeout(() => {
                navigate('/admin/staffManagement');
            }, 2000);

        }
    };
    return (
        <div className="new home">
            <Sidebar />
            <div className="newContainer homeContainer">
                <Navbar title="Chỉnh sửa thông tin nhân viên" />
                <Link to={`/admin/staffManagement`} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />
                </Link>
                <div className="bottom">
                    <div className="column2">
                        <div className="column">
                            <form onSubmit={formik.handleSubmit}>
                                <div className="formInput">
                                    <label>Họ</label>
                                    <input
                                        id="firstName"
                                        name="firstName"
                                        type="text"
                                        onChange={handleInputChange}
                                        value={formik.values.firstName}
                                        onBlur={() => handleInputBlur('firstName')}
                                    />
                                    {formik.touched.firstName && formik.errors.firstName ? (
                                        <p className="errorMsg"> {formik.errors.firstName} </p>
                                    ) : errorsShown.firstName && (<p className="errorMsg">{errorsShown.firstName}</p>)}
                                </div>
                                <div className="formInput">
                                    <label>Tên</label>
                                    <input
                                        id="lastName"
                                        name="lastName"
                                        type="text"
                                        onChange={handleInputChange}
                                        value={formik.values.lastName}
                                        onBlur={() => handleInputBlur('lastName')}
                                    />
                                    {formik.touched.lastName && formik.errors.lastName ? (
                                        <p className="errorMsg"> {formik.errors.lastName} </p>
                                    ) : errorsShown.lastName && <p className="errorMsg">{errorsShown.lastName}</p>}
                                </div>
                                <div className="formInput">
                                    <label>Email *</label>
                                    <input
                                        id="email"
                                        name="email"
                                        type="text"
                                        value={formik.values.email}
                                        onChange={handleInputChange}
                                        onBlur={() => handleInputBlur('email')}
                                    />
                                    {formik.touched.email && formik.errors.email ? (
                                        <p className="errorMsg"> {formik.errors.email} </p>
                                    ) : errorsShown.email && <p className="errorMsg">{errorsShown.email}</p>}
                                </div>
                                <div className="formInput">
                                    <label>Số điện thoại</label>
                                    <input
                                        id="phoneNumber"
                                        name="phoneNumber"
                                        type="text"
                                        onChange={handleInputChange}
                                        value={formik.values.phoneNumber}
                                        onBlur={() => handleInputBlur('phoneNumber')}
                                    />
                                    {formik.touched.phoneNumber && formik.errors.phoneNumber ? (
                                        <p className="errorMsg"> {formik.errors.phoneNumber} </p>
                                    ) : errorsShown.phoneNumber && <p className="errorMsg">{errorsShown.phoneNumber}</p>}
                                </div>
                                <button type="submit">Cập nhật</button>
                            </form>
                        </div>

                        <div className="column">
                            <div className="title top">
                                <h1>Cập nhật địa chỉ</h1>
                            </div>
                            <form onSubmit={handleSubmitAddAddress}>
                                <div className="formInput">
                                    <label >Tên đường:</label>
                                    <input type="text" name="street" value={address.street} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Tên phường/xã:</label>
                                    <input type="text" name="ward" value={address.ward} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Tên quận/huyện:</label>
                                    <input type="text" name="district" value={address.district} onChange={handleChange} />
                                </div>
                                <div className="formInput">
                                    <label>Tên Thành phố/tỉnh:</label>
                                    <input type="text" name="province" value={address.province} onChange={handleChange} />
                                </div>
                                <button type="submit" >Cập nhật</button>
                            </form>
                        </div>


                        {message && (
                            <div className="success-message">
                                {message}
                                {showProgressBar && <div className="progress-bar" />}
                            </div>
                        )}
                        {messageErr && (
                            <div className="err-message">
                                {messageErr}
                                {showProgressBar && <div className="progress-bar-err" />}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EditStaff;
