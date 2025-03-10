
import "../../css/mngtHome.css"
import Navbar from '../../components/navbar/Navbar';
import Sidebar from '../../components/sidebar/Sidebar';
import TabContent from "../../components/tab-content/tab-content";

import ActiveTableCustomerMngt from "../../components/tableCustomerMngtHome/ActiveTableCustomerMngt";
import BlockTableCustomerMngt from "../../components/tableCustomerMngtHome/BlockTableCustomerMngt";
import AllTableCustomerMngt from "../../components/tableCustomerMngtHome/AllTableCustomerMngt";
import SidebarStaff from "../../components/sidebar/SidebarStaff";
import { useLocation } from "react-router-dom";

const CustomerMngtHome = () => {

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;


    const contents = [
        {
            title: "Tất cả khách hàng",
            content: (
                <AllTableCustomerMngt />
            ),
        },
        {
            title: "",

        },
        // {
        //     title: "Tài khoản đã khóa",
        //     content: (
        //         <BlockTableCustomerMngt />
        //     ),
        // },

    ];
    return (

        <div className='list home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title="Quản lý Khách hàng" />
                <div className="headerMngt">
                </div>
                <TabContent input={contents} />
            </div>
        </div>
    )
}

export default CustomerMngtHome