
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
// import DriveFolderUploadOutlinedIcon from "@mui/icons-material/DriveFolderUploadOutlined";
import { useFormik } from "formik";
import * as Yup from "yup";
import { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "./changePassword.css";

import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const ChangePassword = ({ title }) => {
    //const [file, setFile] = useState("");

    //duy tri trang thai mat khau mac dinh
    const [showPassword, setShowPassword] = useState(false);
    const [showNewPassword, setShowNewPassword] = useState(false);
    const [showConfirmNewPassword, setShowConfirmNewPassword] = useState(false);

    const navigate = useNavigate();

    const [ID, setID] = useState("");
    const [message, setMessage] = useState('');
    const [messageErr, setMessageErr] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    useEffect(() => {
        const adminID = localStorage.getItem(`adminID`);
        const staffID = localStorage.getItem('staffID');
        if (role == "admin") {
            setID(adminID);
        } else {
            setID(staffID);
        }
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
            password: "",
            newPassword: "",
            confirmNewPassword: "",
        },
        validationSchema: Yup.object({
            password: Yup.string()
                .required("Mật khẩu không được bỏ trống.")
                .matches(
                    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{8,16}$/,
                    "Mật khẩu có 8 đến 16 ký tự, bao gồm ký tự thường, hoa, số và ký tự đặc biệt."
                ),

            newPassword: Yup.string()
                .required("Mật khẩu mới không được bỏ trống.")
                .matches(
                    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{8,16}$/,
                    "Mật khẩu mới phải có 8 đến 16 ký tự, bao gồm ký tự thường, hoa, số và ký tự đặc biệt."
                ),
            confirmNewPassword: Yup.string()
                .required("Nhập lại mật khẩu mới")
                .oneOf([Yup.ref("newPassword"), null], "Nhập lại mật khẩu sai"),
        }),
        onSubmit: async (values) => {
            //window.alert("Form submitted");
            console.log(values.password);
            setShowProgressBar(true);
            try {
                const response = await axios.put(`${backendUrl}/api/account/changepassword/${ID}`, {
                    password: values.password,
                    newpassword: values.newPassword,
                });
                if (response.status === 200) {
                    setMessage('Cập nhật mật khẩu thành công!');
                    setTimeout(() => {

                        // navigate('/admin');
                        if (role === "admin") {
                            navigate(`/admin`);
                        } else if (role === "staff") {
                            navigate(`/staff`);
                        }
                    }, 2000);
                } else {
                    setMessageErr('Mật khẩu không đúng.');
                }
            } catch (error) {
                console.error("Error logging in:", error);
                setMessageErr('Lỗi.');
            }
        },
    });

    const HomeLink = role === "admin"
        ? `/admin`
        : `/staff`


    return (
        <div className="new home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="newContainer homeContainer">
                <Navbar />
                <div className="title top">
                    <h1>{title}</h1>
                </div>
                <div className="bottom">
                    <section>
                        <form className="changePassword" onSubmit={formik.handleSubmit}>
                            <label> Mật khẩu hiện tại *</label>
                            <div className="pass">
                                <input
                                    type={showPassword ? "text" : "password"}
                                    id="password"
                                    name="password"
                                    value={formik.values.password}
                                    onChange={formik.handleChange}
                                    placeholder="Nhập mật khẩu hiện tại"
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
                            <label> Mật khẩu mới *</label>
                            <div className="pass">
                                <input
                                    type={showNewPassword ? "text" : "password"}
                                    id="newPassword"
                                    name="newPassword"
                                    value={formik.values.newPassword}
                                    onChange={formik.handleChange}
                                    placeholder="Nhập mật khẩu mới"
                                />

                                <div className="icon" onClick={() => {
                                    setShowNewPassword(!showNewPassword);
                                }}>
                                    {showNewPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
                                </div>
                            </div>

                            {formik.errors.newPassword && (
                                <p className="errorMsg"> {formik.errors.newPassword} </p>
                            )}

                            <label> Nhập lại mật khẩu mới*</label>
                            <div className="pass">
                                <input
                                    type={showConfirmNewPassword ? "text" : "password"}
                                    id="confirmNewPassword"
                                    name="confirmNewPassword"
                                    value={formik.values.confirmNewPassword}
                                    onChange={formik.handleChange}
                                    placeholder="Nhập lại mật khẩu mới"
                                />
                                <div className="icon" onClick={() => {
                                    setShowConfirmNewPassword(!showConfirmNewPassword);
                                }}>
                                    {showConfirmNewPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}

                                </div>
                            </div>
                            {formik.errors.confirmNewPassword && (
                                <p className="errorMsg"> {formik.errors.confirmNewPassword} </p>
                            )}
                            <div className="btnchangePass">
                                <Link to={HomeLink}>
                                    <button className="btnCancel" type="cancel"> Hủy </button>
                                </Link>
                                <button className="btnSubmit" type="submit"> Cập nhật </button>
                            </div>

                        </form>
                    </section>

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

    );
};

export default ChangePassword;
