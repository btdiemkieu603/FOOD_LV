
import "../../css/mngtHome.css"
import Navbar from '../../components/navbar/Navbar';
import Sidebar from '../../components/sidebar/Sidebar';

import TabContent from "../../components/tab-content/tab-content";
import AllTableOrderMngt from "../../components/tableOrderMngtHome/AllTableOrderMngt";
import ProcessingTableOrderMngt from "../../components/tableOrderMngtHome/ProcessingTableOrderMngt";
import ApprovedTableOrderMngt from "../../components/tableOrderMngtHome/ApprovedTableOrderMngt ";
import CompleteTableOrderMngt from "../../components/tableOrderMngtHome/CompleteTableOrderMngt";
import CancelTableOrderMngt from "../../components/tableOrderMngtHome/CancelTableOrderMngt";
import WatingConfirmationTableOrderMngt from "../../components/tableOrderMngtHome/WatingConfirmationTableOrderMngt";
import SidebarStaff from "../../components/sidebar/SidebarStaff";
import { useLocation } from "react-router-dom";

const OrderMngtHome = () => {
    const contents = [
        {
            title: "Tất cả đơn hàng",
            content: (
                <AllTableOrderMngt />
            ),
        },
        {
            title: "",

        },
        // {
        //     title: "Đơn hàng chờ xác nhận",
        //     content: (
        //         <WatingConfirmationTableOrderMngt />
        //     )
        // },
        // {
        //     title: "Đơn hàng chờ lấy hàng",
        //     content: (
        //         <ProcessingTableOrderMngt />
        //     )
        // },
        // {
        //     title: "Đơn hàng đang đến ",
        //     content: (
        //         <ApprovedTableOrderMngt />
        //     ),
        // },

        // {
        //     title: "Đơn hàng đã giao",
        //     content: (
        //         <CompleteTableOrderMngt />
        //     ),
        // },
        // {
        //     title: "Đơn hàng đã hủy",
        //     content: (
        //         <CancelTableOrderMngt />
        //     ),
        // },

    ];

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    return (
        <div className='list home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title="Quản lý đơn hàng" />
                <div className="headerMngt">
                </div>
                <TabContent input={contents} />
            </div>
        </div>
    )
}

export default OrderMngtHome