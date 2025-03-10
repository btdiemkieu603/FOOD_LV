import React, { useEffect, useState } from 'react'
import "./sidebar.css"
import HomeIcon from '@mui/icons-material/Home';
import { Link } from "react-router-dom"
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

import PostAddIcon from '@mui/icons-material/PostAdd';
const Sidebar = () => {
    const [nameUser, setNameUser] = useState("");

    useEffect(() => {
        const adminName = localStorage.getItem(`adminName`);
        if (adminName) {
            setNameUser(adminName);
        }
    }, []);
    const handleLogout = () => {
        // Xóa các thông tin đăng nhập khỏi localStorage
        localStorage.removeItem('adminName');
        localStorage.removeItem('isLoggedInAdmin');
        localStorage.removeItem('adminID');
        console.log('logout')
        // Chuyển hướng đến trang đăng nhập hoặc trang chính của ứng dụng
    };
    return (
        <div className='sidebar'>
            <div className="top">
                {nameUser && <span className="name">{nameUser}</span>}
            </div>
            <hr />
            <div className="center">
                <ul>
                    <Link to="/admin" style={{ textDecoration: "none" }}>
                        <li className='titleHome'>
                            <HomeIcon className='iconTitle' />
                            <span className='titleHomeText'>Trang chủ</span>
                        </li>
                    </Link>

                    <Link to="/admin/orderTable" style={{ textDecoration: "none" }}>
                        <li className='titleHome'>
                            <PostAddIcon className='iconTitle2' />
                            <span className='titleHomeText'>Đặt món</span>
                        </li>
                    </Link>

                    <p className="titleSidebar">
                        <SignalCellularAltIcon className='iconTitle' />Thống kê</p>

                    {/* <Link to="/admin/table2" style={{ textDecoration: "none" }}> */}
                    <Link to="/admin/statistical/topSelling" style={{ textDecoration: "none" }}>
                        <li>
                            <KeyboardDoubleArrowUpIcon className='icon' />
                            <span>Món ăn bán chạy</span>
                        </li>
                    </Link>

                    <Link to="/admin/statistical/revenue" style={{ textDecoration: "none" }}>
                        <li>
                            <PriceChangeIcon className='icon' />
                            <span>Doanh thu</span>
                        </li>
                    </Link>

                    <Link to="/admin/statistical/review" style={{ textDecoration: "none" }}>
                        <li>
                            <ForumIcon className='icon' />
                            <span>Đánh giá</span>
                        </li>
                    </Link>

                    <Link to="/admin/statistical/customerStat" style={{ textDecoration: "none" }}>
                        <li>
                            <PeopleAltIcon className='icon' />
                            <span>Khách hàng</span>
                        </li>
                    </Link>

                    <p className="titleSidebar"><PlaylistAddIcon className='iconTitle' />Quản lý danh mục</p>

                    <Link to="/admin/tableManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <TableBarIcon className='icon' />
                            <span>Quản lý bàn</span>
                        </li>
                    </Link>

                    <Link to="/admin/productManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <StoreIcon className='icon' />
                            <span>Quản lý món ăn</span>
                        </li>
                    </Link>

                    <Link to="/admin/orderTableManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <ReceiptIcon className='icon' />
                            <span>Đơn đặt món</span>
                        </li>
                    </Link>

                    <Link to="/admin/orderManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <ReceiptLongIcon className='icon' />
                            <span>Quản lý đơn hàng</span>
                        </li>
                    </Link>

                    <Link to="/admin/discountManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <DiscountIcon className='icon' />
                            <span>Quản lý khuyến mãi</span>
                        </li>
                    </Link>

                    <Link to="/admin/customerManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <PersonIcon className='icon' />
                            <span>Quản lý khách hàng</span>
                        </li>
                    </Link>

                    <Link to="/admin/staffManagement" style={{ textDecoration: "none" }}>
                        <li>
                            <ManageAccountsIcon className='icon' />
                            <span>Quản lý nhân viên</span>
                        </li>
                    </Link>
                    <p className="titleSidebar"><SettingsSuggestIcon className='iconTitle' />Cài đặt</p>

                    <Link to="/admin/setting/changePassword" style={{ textDecoration: "none" }}>
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

export default Sidebar