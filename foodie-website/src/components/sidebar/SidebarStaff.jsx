import React, { useEffect, useState } from 'react'
import "./sidebar.css"
import HomeIcon from '@mui/icons-material/Home';
import { Link, useLocation } from "react-router-dom"
//tk
import SignalCellularAltIcon from '@mui/icons-material/SignalCellularAlt';
//import PollIcon from '@mui/icons-material/Poll';

//spbc
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';

//tkkh
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';

//tkdt
import PriceChangeIcon from '@mui/icons-material/PriceChange';
//import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';

//tkdg
//import CommentIcon from '@mui/icons-material/Comment';
import ForumIcon from '@mui/icons-material/Forum';

//qlkh
import PersonIcon from '@mui/icons-material/Person';
//import PortraitIcon from '@mui/icons-material/Portrait';

//qlnv
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';

//qldm
import PlaylistAddIcon from '@mui/icons-material/PlaylistAdd';

//qlkm
import DiscountIcon from '@mui/icons-material/Discount';
//import PercentIcon from '@mui/icons-material/Percent';

//qldh
import ReceiptLongIcon from '@mui/icons-material/ReceiptLong';

//qlsp
import StoreIcon from '@mui/icons-material/Store';
//import StorefrontIcon from '@mui/icons-material/Storefront';

//cd
import SettingsSuggestIcon from '@mui/icons-material/SettingsSuggest';

//mk
import MiscellaneousServicesIcon from '@mui/icons-material/MiscellaneousServices';

//night
//import NightlightIcon from '@mui/icons-material/Nightlight';


//logout
import ExitToAppIcon from '@mui/icons-material/ExitToApp';

//mắt
//import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';

//bật
//import ToggleOffIcon from '@mui/icons-material/ToggleOff';

//tắt tr th

import ReceiptIcon from '@mui/icons-material/Receipt';

import TableBarIcon from '@mui/icons-material/TableBar';

const SidebarStaff = () => {
    const [nameUserNV, setNameUserNV] = useState("");

    useEffect(() => {
        const staffName = localStorage.getItem(`staffName`);
        if (staffName) {
            setNameUserNV(staffName);
        }
    }, []);
    const handleLogout = () => {
        // Xóa các thông tin đăng nhập khỏi localStorage
        localStorage.removeItem('staffName');
        localStorage.removeItem('isLoggedInStaff');
        localStorage.removeItem('staffID');
        console.log('logout')
        // Chuyển hướng đến trang đăng nhập hoặc trang chính của ứng dụng
    };
    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    return (
        <div className='sidebar'>
            <div className="top">
                {nameUserNV && <span className="name">{nameUserNV}</span>}
            </div>
            <hr />
            <div className="center">
                <ul>
                    <Link to="/staff/orderTable" style={{ textDecoration: "none" }}>
                        <li className='titleHome'>
                            <HomeIcon className='iconTitle2' />
                            <span className='titleHomeText'>Đặt món</span>
                        </li>
                    </Link>


                    <p className="titleSidebar"><PlaylistAddIcon className='iconTitle' />Quản lý danh mục</p>

                    <Link to="/staff/tableManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <TableBarIcon className='icon' />
                            <span>Quản lý bàn</span>
                        </li>
                    </Link>

                    <Link to="/staff/productManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <StoreIcon className='icon' />
                            <span>Quản lý món ăn</span>
                        </li>
                    </Link>

                    <Link to="/staff/orderTableManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <ReceiptIcon className='icon' />
                            <span>Đơn đặt món</span>
                        </li>
                    </Link>

                    <Link to="/staff/orderManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <ReceiptLongIcon className='icon' />
                            <span>Quản lý đơn hàng</span>
                        </li>
                    </Link>

                    <Link to="/staff/discountManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <DiscountIcon className='icon' />
                            <span>Quản lý khuyến mãi</span>
                        </li>
                    </Link>



                    <Link to="/staff/customerManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <PersonIcon className='icon' />
                            <span>Quản lý khách hàng</span>
                        </li>
                    </Link>

                    <p className="titleSidebar"><SettingsSuggestIcon className='iconTitle' />Cài đặt</p>

                    <Link to="/staff/setting/changePassword" style={{ textDecoration: "none" }}>
                        <li>
                            <MiscellaneousServicesIcon className='icon' />
                            <span>Đổi mật khẩu</span>
                        </li>
                    </Link>

                    <Link to="/login" style={{ textDecoration: "none" }} onClick={handleLogout}>
                        <li>
                            <ExitToAppIcon className='icon' />
                            <span>Đăng xuất</span>
                        </li>
                    </Link>
                </ul>
            </div>

            {/* <div className="bottom">
                <div className="colorOption"></div>
                <div className="colorOption"></div>
            </div> */}
        </div>
    )
}

export default SidebarStaff