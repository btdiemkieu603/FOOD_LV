
import "../../css/mngtHome.css";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import TabContent from "../../components/tab-content/tab-content";

import AllOrderTableMngtHome from "../../components/tableOrderTableStaff/AllOrderTableMngtHome";
import { useLocation } from "react-router-dom";
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const OrderTableMngtHome = () => {
    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    const contents = [
        {
            title: "Tất cả đơn tại bàn",
            content: <AllOrderTableMngtHome />,
        },
    ];


    return (
        <div className="list home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title={`Đơn đặt bàn`} />
                <div className="headerMngt"></div>
                <TabContent input={contents} />
            </div>
        </div>
    );
};

export default OrderTableMngtHome;
