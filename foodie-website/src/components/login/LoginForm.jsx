import { useFormik } from "formik";
import * as Yup from "yup";
import "./loginForm.css";
import { useEffect, useState } from 'react';
import axios from "axios";
import { useNavigate } from "react-router-dom";

import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
//import { IconButton, InputAdornment } from '@mui/material';
const backendUrl = process.env.REACT_APP_BACKEND_URL;
const LoginForm = () => {
    const navigate = useNavigate();

    //duy tri trang thai mat khau mac dinh
    const [showPassword, setShowPassword] = useState(false);

    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    // const newuser = {
    //     phonenumber: "",
    //     password: ""
    // }

    // const add = () => {
    //     axios.post(`http://localhost:8080/api/login/`, newuser

    //     ).then(resp => {
    //         console.log(resp);
    //     })
    // }
    useEffect(() => {

        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 1500);
            return () => clearTimeout(timer);

        }
    }, [showProgressBar]);
    const formik = useFormik({
        initialValues: {
            phonenumber: "",
            password: "",
        },
        validationSchema: Yup.object({

            password: Yup.string()
                .required("Mật khẩu không được bỏ trống")
                .matches(
                    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{8,16}$/,
                    "Mật khẩu có 8 đến 16 ký tự, bao gồm ký tự thường, hoa, số và ký tự đặc biệt."
                ),

            phonenumber: Yup.string()
                .required("Số điện thoại không được bỏ trống")
                .matches(
                    /(84|0[3|5|7|8|9])+([0-9]{8})\b/,
                    "Số điện thoại phải  hợp lệ (có 10 số)."
                ),
        }),
        onSubmit: async (values) => {
            //window.alert("Form submitted");
            //newuser["phonenumber"] = values.phone;
            //newuser["password"] = values.password;
            // console.log(newuser);
            // axios.post(`http://localhost:8080/api/login/`, values

            // ).then(resp => {
            //     console.log(resp);
            // })
            console.log(`${backendUrl}/api/login/`);
            setShowProgressBar(true);

            try {
                const response = await axios.post(`${backendUrl}/api/login/`, values);
                const userData = response.data;
                console.log(userData);


                if (response.status === 200) {

                    if (userData.role.name === 'admin' && userData.account_status.status === 'Active') {// Lưu trạng thái đăng nhập vào localStorage hoặc sessionStorage
                        //const isLoggedIn = localStorage.setItem('isLoggedIn', true);
                        //localStorage.setItem('isLoggedIn', '1');
                        setMessage('Đăng nhập tài khoản Admin thành công!');
                        // Lưu tên admin vào localStorage
                        //localStorage.setItem('adminName', userData.firstname);
                        localStorage.setItem('adminName', `${userData.firstname} ${userData.lastname}`);
                        //console.log(localStorage.getItem('adminName'));
                        localStorage.setItem('adminID', userData.id);

                        // Chuyển hướng đến trang quản trị viên
                        // const isLoggedIn = localStorage.getItem('isLoggedIn') === true;

                        // console.log(isLoggedIn);
                        localStorage.setItem('isLoggedInAdmin', true);
                        const isLoggedInAdmin = localStorage.getItem('isLoggedInAdmin');
                        if (isLoggedInAdmin) {
                            setTimeout(() => {

                                navigate('/admin');
                            }, 1500);

                        }
                        console.log("admin");

                    } else if (userData.role.name === 'staff' && userData.account_status.status === 'Active') {
                        setMessage('Đăng nhập tài khoản Staff thành công!');
                        // Lưu trạng thái đăng nhập vào localStorage hoặc sessionStorage

                        localStorage.setItem('staffName', `${userData.firstname} ${userData.lastname}`);
                        localStorage.setItem('staffID', userData.id);
                        localStorage.setItem('isLoggedInStaff', true);
                        const isLoggedInStaff = localStorage.getItem('isLoggedInStaff');
                        // Chuyển hướng đến trang nhân viên
                        if (isLoggedInStaff) {
                            setTimeout(() => {
                                navigate('/staff/orderTable');
                            }, 1500);
                        }
                        console.log("staff");
                    } else if (userData.role.name === 'staff' && userData.account_status.status !== 'Active') {
                        setMessage('Tài khoản đã bị khóa!');
                    }
                    else {
                        setMessage('Người dùng không có vai trò phù hợp');
                        // Xử lý trường hợp khác, ví dụ: hiển thị thông báo lỗi
                        console.error('Người dùng không có vai trò hoặc trạng thái phù hợp');
                    }
                }
                else {
                    setMessage('Số điện thoại hoặc mật khẩu không đúng');
                }
            }
            catch (error) {
                console.error("Error logging in:", error);
                setMessage('Số điện thoại hoặc mật khẩu không đúng');
            }

        },
    });

    return (
        <div>
            {message && (
                <div className="success-message">
                    {message}
                    {showProgressBar && <div className="progress-bar" />}
                </div>
            )}
            <section>
                <form className="infoform" onSubmit={formik.handleSubmit}>
                    <label> Số điện thoại </label>
                    <input
                        type="text"
                        id="phonenumber"
                        name="phonenumber"
                        value={formik.values.phonenumber}
                        onChange={formik.handleChange}
                        placeholder="Nhập số điện thoại"
                    />
                    {formik.errors.phonenumber && (
                        <p className="errorMsg"> {formik.errors.phonenumber} </p>
                    )}

                    <label> Mật khẩu </label>
                    <div className="pass">
                        <input
                            type={showPassword ? "text" : "password"}
                            id="password"
                            name="password"
                            value={formik.values.password}
                            onChange={formik.handleChange}
                            placeholder="Nhập mật khẩu"
                        />

                        <div className="icon" onClick={() => {
                            setShowPassword(!showPassword);
                        }}>
                            {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}

                        </div>
                    </div>

                    {formik.errors.password && (
                        <p className="errorMsg"> {formik.errors.password} </p>
                    )}
                    <button type="submit" > Đăng nhập </button>
                </form>
            </section>
        </div>
    );
};

export default LoginForm;
