import "./tableOrderBy.css"
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Link, useLocation } from "react-router-dom";

const TableOrderBy = ({ orders }) => {

    //data
    // const rows = [
    //     {
    //         id: 1143155,
    //         //product: "Acer Nitro 5",
    //         // img: "https://m.media-amazon.com/images/I/81bc8mA3nKL._AC_UY327_FMwebp_QL65_.jpg",
    //         firtName: "Nguyen",
    //         lastName: "John Smith",
    //         date: "1 March",
    //         amount: 785,
    //         method: "Cash on Delivery",
    //         status: "Đang giao",
    //     },
    //     {
    //         id: 2235235,
    //         // product: "Playstation 5",
    //         //img: "https://m.media-amazon.com/images/I/31JaiPXYI8L._AC_UY327_FMwebp_QL65_.jpg",
    //         firtName: "Nguyen",
    //         lastName: "Michael Doe",
    //         date: "1 March",
    //         amount: 900,
    //         method: "Online Payment",
    //         status: "Chờ duyệt",
    //     },
    // ];

    const location = useLocation();
    const pathname = location.pathname;

    // Xác định vai trò từ URL
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;

    return (
        <TableContainer component={Paper} className="table">
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell className="tableCell">ID Đơn hàng</TableCell>
                        <TableCell className="tableCell">Khách hàng</TableCell>
                        <TableCell className="tableCell">Ngày đặt hàng</TableCell>
                        <TableCell className="tableCell">Khuyến mãi áp dụng</TableCell>
                        <TableCell className="tableCell">Tổng tiền</TableCell>
                        <TableCell className="tableCell">Phương thức thanh toán</TableCell>
                        <TableCell className="tableCell">Trạng thái</TableCell>
                        <TableCell className="tableCell"></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {Array.isArray(orders) && orders.map((order) => {
                        const OrderDetailLink = role === "admin"
                            ? `/admin/orderManagement/orderDetail/${order.id}`
                            : `/staff/orderManagement/orderDetail/${order.id}`;
                        return (
                            <TableRow key={order.id}>
                                <TableCell className="tableCell">{order.id}</TableCell>
                                <TableCell className="tableCell">
                                    <div className="cellWrapper">
                                        <div className="firtName">{order.account.firstname} {order.account.lastname}</div>
                                    </div>
                                </TableCell>
                                <TableCell className="tableCell">{order.orderdate}</TableCell>
                                <TableCell className="tableCell">{order.discount?.code}</TableCell>
                                <TableCell className="tableCell">{order.bill.totalprice}</TableCell>
                                <TableCell className="tableCell"> {order.checkout?.method === 'credit card' ? 'Ví ngân hàng'
                                    : order.checkout?.method === 'cash' ? 'Khi nhận hàng' : order.checkout?.method === 'momo' ? 'Ví Momo' : ''}</TableCell>
                                <TableCell className="tableCell">
                                    <span className={`status ${order.orderStatus?.status}`}>{order.orderStatus?.status === 'watingConfirmation' ? 'Chờ xác nhận'
                                        : order.orderStatus?.status === 'Processing' ? 'Chờ lấy hàng'
                                            : order.orderStatus?.status === 'beingTransported' ? 'Đang đến'
                                                : order.orderStatus?.status === 'Completed' ? 'Đã giao'
                                                    : order.orderStatus?.status === 'Cancel' ? 'Hủy'
                                                        : ''}</span>
                                </TableCell>
                                <TableCell className="tableCell">

                                    <Link to={OrderDetailLink} style={{ textDecoration: "none" }}>
                                        <div className="viewButton">Xem chi tiết</div>
                                    </Link>

                                </TableCell>
                            </TableRow>
                        )
                    })}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default TableOrderBy