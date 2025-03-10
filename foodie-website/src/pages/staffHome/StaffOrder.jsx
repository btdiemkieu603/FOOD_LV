
import "../../css/mngtHome.css";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import TabContent from "../../components/tab-content/tab-content";
import AllTableOrderTableDetail from "../../components/tableOrderTableStaff/AllTableOrderTableDetail";
import { Link, useLocation, useParams } from "react-router-dom";
// import StaffOrder from "./StaffOrder sai";

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import SidebarStaff from "../../components/sidebar/SidebarStaff";

const StaffOrderTable = () => {
    const { tableId } = useParams();
    // const contents = [
    //     {
    //         title: "Tất cả đơn tại bàn",
    //         content: <AllTableOrderTableDetail />,
    //     },
    // ];
    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    const OrderTableHomeLink = role === "admin"
        ? `/admin/orderTable`
        : role === "staff"
            ? `/staff/orderTable`
            : `/`;
    const TableMenuLink = role === "admin"
        ? `/admin/orderTable/menu/${tableId}`
        : role === "staff"
            ? `/staff/orderTable/menu/${tableId}`
            : `/`;
    return (
        <div className="list home">
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title={`Thông tin bàn: ${tableId}`} />
                <Link to={OrderTableHomeLink} className="arrow-back-icon" style={{ textDecoration: "none" }}>
                    <ArrowBackIcon />

                </Link>
                <div className="headerMngt">

                    <div className="linkAdd">
                        <Link to={TableMenuLink} style={{ textDecoration: "none" }}>
                            <div className="textAdd">Thêm mới</div>
                        </Link>
                    </div>

                </div>
                {/* <TabContent input={contents} /> */}
                <AllTableOrderTableDetail />
            </div>
        </div>
    );
};

export default StaffOrderTable;
