
import "../../css/mngtHome.css"
import Navbar from '../../components/navbar/Navbar';
import Sidebar from '../../components/sidebar/Sidebar';
import TabContent from "../../components/tab-content/tab-content";
import { Link, useLocation } from "react-router-dom";

import AllTableCategoryProductMngt from "../../components/tableProductMngtHome/AllTableCategoryProductMngt";
import AllTableProductMngt from "../../components/tableProductMngtHome/AllTableProductMngt";
import SidebarStaff from "../../components/sidebar/SidebarStaff";


const ProductMngtHome = () => {
    const contents = [
        {
            title: "Tất cả Món ăn",
            content: (
                <AllTableProductMngt />
            ),
        },
        {
            title: "Tất cả danh mục",
            content: (
                <AllTableCategoryProductMngt />
            ),
        },

        // {
        //     title: "Món ăn đã ẩn",
        //     content: (
        //         <HiddenTableProductMngt />
        //     ),
        // },
    ];
    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    const addProductLink = role === "admin"
        ? `/admin/productManagement/addProduct`
        : role === "staff"
            ? `/staff/productManagement/addProduct`
            : `/`;
    return (
        <div className='list home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            {/* <Sidebar /> */}
            <div className="listContainer homeContainer">
                <Navbar title="Quản lý món ăn" />
                <div className="headerMngt">
                    <div className="linkAdd">
                        {/* <Link to="/admin/productManagement/addProduct" style={{ textDecoration: "none" }}> */}
                        <Link to={addProductLink} style={{ textDecoration: "none" }}>
                            <div className="textAdd">Thêm mới</div>
                        </Link>
                    </div>
                </div>
                <TabContent input={contents} />
            </div>
        </div>
    )
}

export default ProductMngtHome