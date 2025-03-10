
import "../../css/mngtHome.css"
import Navbar from '../../components/navbar/Navbar';
import Sidebar from '../../components/sidebar/Sidebar';
import { Link } from "react-router-dom";

import TabContent from "../../components/tab-content/tab-content";
import AllTableStaffMngt from "../../components/tableStaffMngtHome/AllTableStaffMngt";
//import ActiveTableStaffMngt from "../../components/tableStaffMngtHome/ActiveTableStaffMngt";
//import BlockTableStaffMngt from "../../components/tableStaffMngtHome/BlockTableStaffMngt";
const StaffMngHome = () => {
    const contents = [
        {
            title: "Tất cả nhân viên",
            content: (
                <AllTableStaffMngt />//Data={fetchData}
            ),
        },
        {
            title: "",

        },
        // {
        //     title: "Tài khoản hoạt động",
        //     content: (
        //         <ActiveTableStaffMngt />
        //     ),
        // },
        // {
        //     title: "Tài khoản đã khóa",
        //     content: (
        //         <BlockTableStaffMngt />
        //     ),
        // },

    ];

    return (
        <div className='list home'>
            <Sidebar />
            <div className="listContainer homeContainer">
                <Navbar title="Quản lý nhân viên" />
                <div className="headerMngt">
                    {/* <div className="search">
                        <input type="text" placeholder="Search..." />
                        <div className='icon'>
                            <SearchOutlinedIcon />
                        </div>
                    </div> */}
                    <div className="linkAdd">
                        <Link to="/admin/staffManagement/addStaff" style={{ textDecoration: "none" }}>
                            <div className="textAdd">Thêm nhân viên</div>
                        </Link>
                    </div>
                </div>
                <TabContent input={contents} />
            </div>
        </div>
    )
}

export default StaffMngHome