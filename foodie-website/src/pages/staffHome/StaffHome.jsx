
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "../../css/mngtHomeStaff.css"
import Navbar from '../../components/navbar/Navbar';
import Sidebar from '../../components/sidebar/Sidebar';
import { Link, useLocation, useNavigate } from "react-router-dom";

import TabContent from "../../components/tab-content/tab-content";
import AllTableStaffMngt from "../../components/tableStaffMngtHome/AllTableStaffMngt";
import ActiveTableStaffMngt from "../../components/tableStaffMngtHome/ActiveTableStaffMngt";
import BlockTableStaffMngt from "../../components/tableStaffMngtHome/BlockTableStaffMngt";
import SidebarStaff from "../../components/sidebar/SidebarStaff";
import FoodDisplay from "../../components/FoodDisplay/FoodDisplay";
import ExploreMenu from "../../components/ExploreMenu/ExploreMenu";

import TableBarIcon from '@mui/icons-material/TableBar';

const backendUrl = process.env.REACT_APP_BACKEND_URL;
const StaffHome = () => {
    const [tables, setTables] = useState([]);
    const [topSelling, setTopSelling] = useState([]);
    const [error, setError] = useState(null);
    const [dailySales, setDailySales] = useState(0);
    const [expectedRevenue, setexpRevenue] = useState(80);
    const [totalSales, setTotalSales] = useState(0);

    const [amountPro, setAmountPro] = useState(0);
    const navigate = useNavigate();

    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    useEffect(() => {
        const fetchTables = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/tableInfo/all`);
                if (response.status === 200) {
                    const ordersResponse = await axios.get(`${backendUrl}/api/orderTable/all`);
                    // Lọc danh sách các bàn có `isExist = true`
                    const existingTables = response.data.filter((table) => table.isExist);

                    const tablesWithOrderStatus = existingTables.map((table) => {
                        const hasOrdering = ordersResponse.data.some(
                            (order) => order.tableId === table.tableId && order.orderStatusId === 3 // Đặt món
                        );
                        // return {
                        //     ...table,
                        //     status: hasOrdering ? 'ordering' : table.status,
                        // };
                        // Nếu không có đơn đặt món, trả trạng thái mặc định (ví dụ: "available")
                        const updatedStatus = hasOrdering ? 'ordering' : 'available';

                        // Đồng bộ trạng thái nếu có sự khác biệt
                        if (table.status !== updatedStatus) {
                            updateTableStatus(table.tableId, updatedStatus);
                        }

                        return {
                            ...table,
                            status: updatedStatus,
                        };
                    });
                    setTables(tablesWithOrderStatus);
                } else {
                    throw new Error('Network response was not ok');
                }
            } catch (error) {
                console.error('Error fetching table data:', error);
                setError('Không thể tải danh sách bàn ăn. Vui lòng thử lại sau.');
            }
        };

        fetchTables();
    }, []);

    const getStatusStyle = (status) => {
        // if (hasOrderPlaced) {
        //     return { color: '#2196F3', label: 'Đặt món', icon: '🔵' }; // Màu xanh dương
        // }
        switch (status) {
            case 'available':
                return { color: '#4CAF50', label: 'Trống', icon: '🟢' };
            case 'occupied':
                return { color: '#FF5722', label: 'Đang sử dụng', icon: '🔴' };
            case 'reserved':
                return { color: '#FFC107', label: 'Đã đặt trước', icon: '🟡' };
            case 'ordering':
                return { color: '#0D47A1', label: 'Đặt món', icon: '🔵' };
            default:
                return { color: '#9E9E9E', label: 'Không rõ', icon: '⚪' };
        }
    };
    // Hàm cập nhật trạng thái bàn
    const updateTableStatus = async (tableId, status) => {
        try {
            const response = await axios.put(`${backendUrl}/api/tableInfo/updateStatus/${tableId}`, null, {
                params: { status },
            });
            if (response.status === 200) {
                console.log(`Updated status of table ${tableId} to ${status} successfully.`);
            }
        } catch (error) {
            console.error(`Error updating status of table ${tableId}:`, error);
        }
    };


    // Điều hướng đến trang orderTable khi nhấp vào bàn
    const handleTableClick = (tableId) => {
        // navigate(`/staff/orderTable/${tableId}`);
        if (role === "admin") {
            navigate(`/admin/orderTable/${tableId}`);
        } else if (role === "staff") {
            navigate(`/staff/orderTable/${tableId}`);
        }
    };

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className='list home'>
            <>
                {role === "admin" && <Sidebar />}
                {role === "staff" && <SidebarStaff />}
            </>
            <div className="listContainer homeContainer">
                <Navbar title="Danh sách bàn ăn" />
                <div className="headerStaff">
                    <div className="food-display" id="food-display">
                        {/* <h2>Danh sách bàn ăn</h2> */}
                        <div className="food-display-list">
                            {tables.length > 0 ? (
                                tables.map((table) => {
                                    const { color, label, icon } = getStatusStyle(table.status);
                                    return (
                                        <div className="table-card" key={table.tableId} style={{ borderColor: color }} onClick={() => handleTableClick(table.tableId)}>

                                            {/* <div className="table-info">
                                                <p>Số chỗ ngồi: {table.seatingCapacity}</p>
                                                <p className="seating-dots">
                                                    {Array.from({ length: table.seatingCapacity }).map((_, idx) => (
                                                        <span key={idx} className="seat-dot">•</span> // hoặc biểu tượng ghế
                                                    ))}
                                                </p>
                                                 */}

                                            {/* <div className="table-info">
                                                <p className="seating-info">
                                                    <TableBarIcon style={{ verticalAlign: 'middle', marginRight: '8px' }} />
                                                    {table.seatingCapacity} chỗ ngồi
                                                </p>
                                               */}


                                            <div className="table-container">
                                                {/* Bàn ở giữa */}
                                                <div className="table-center" style={{ backgroundColor: color, color: '#fff', padding: '10px', borderRadius: '50%' }}>
                                                    Bàn {table.tableNumber}
                                                </div>

                                                {/* Ghế xung quanh bàn */}
                                                <div className="seats">
                                                    {Array.from({ length: table.seatingCapacity }).map((_, idx) => (
                                                        <div key={idx}
                                                            className={`seat seat-${idx + 1}`}
                                                        ></div>
                                                    ))}
                                                </div>
                                            </div>

                                            {/* <p style={{ color }}>Trạng thái: {label}</p> */}
                                        </div>
                                        // </div>
                                    );
                                })

                                //})
                                // tables.map((table) => (
                                //     <div
                                //         className="table-card"
                                //         key={table.tableId}
                                //         style={{ backgroundColor: getStatusStyle(table.status) }}
                                //         onClick={() => handleTableClick(table.tableId)}
                                //     >
                                //         <div className="table-number">Bàn {table.tableNumber}</div>
                                //         <div className="seating-dots">
                                //             {Array.from({ length: table.seatingCapacity }).map((_, idx) => (
                                //                 <span key={idx}>•</span>
                                //             ))}
                                //         </div>
                                //     </div>
                                // ))
                            ) : (
                                <p>Không có bàn ăn nào.</p>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StaffHome