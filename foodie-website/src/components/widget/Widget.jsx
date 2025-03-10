
import "./widget.css";
import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
//icon 
//import ExpandLessIcon from '@mui/icons-material/ExpandLess';

//user
import PersonIcon from '@mui/icons-material/Person';
import StoreIcon from '@mui/icons-material/Store';
import ReceiptLongIcon from '@mui/icons-material/ReceiptLong';
import ForumIcon from '@mui/icons-material/Forum';
import DiscountIcon from '@mui/icons-material/Discount';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const Widget = ({ type }) => {
    let data;
    const [amount, setAmount] = useState(0);
    useEffect(() => {
        const fetchData = async () => {
            try {
                let apiUrl = '';
                switch (type) {
                    case "product":
                        apiUrl = `${backendUrl}/api/product/`; // Địa chỉ API để lấy tất cả sản phẩm
                        break;

                    case "order":
                        apiUrl = `${backendUrl}/api/order/all`; // Địa chỉ API để lấy số lượng đơn hàng

                        break;

                    case "discount":
                        apiUrl = `${backendUrl}/api/discount/all`; // Địa chỉ API để lấy số lượng khuyến mãi
                        break;

                    case "user":
                        apiUrl = `${backendUrl}/api/account/1`; // Địa chỉ API để lấy số lượng khách hàng
                        break;
                    case "review":
                        apiUrl = `${backendUrl}/api/review/all`; // Địa chỉ API để lấy số lượng đánh giá
                        break;
                    default:
                        break;
                }

                const response = await axios.get(apiUrl);
                // setAmount(response.data.length); // Đếm số lượng từ dữ liệu nhận được

                // Nếu type là "order" và response không rỗng
                if (type === "order" && Array.isArray(response.data)) {
                    // Lọc và chỉ lấy các đơn hàng đã hoàn thành
                    const completedOrders = response.data.filter(order =>
                        order.orderStatus && order.orderStatus.status === "Completed"
                    );
                    setAmount(completedOrders.length); // Đặt số lượng đơn hàng đã hoàn thành
                } else {
                    setAmount(response.data.length); // Đặt số lượng từ dữ liệu nhận được
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, [type]);

    //temporary
    //const amount = 100
    //const diff = 20

    switch (type) {
        case "product":
            data = {
                title: "MÓN ĂN",
                isMoney: false,
                text: "Xem tất cả món ăn",
                link: "/admin/productManagement",
                icon: <StoreIcon className="icon" />,
            };
            break;

        case "order":
            data = {
                title: "ĐƠN HÀNG ĐÃ GIAO",
                isMoney: false,
                text: "Xem tất cả đơn hàng",
                link: "/admin/orderManagement",
                icon: <ReceiptLongIcon className="icon" />,
            };
            break;

        case "discount":
            data = {
                title: "KHUYẾN MÃI",
                isMoney: false,
                text: "Xem tất cả khuyến mãi",
                link: "/admin/discountManagement",
                icon: <DiscountIcon className="icon" />,
            };
            break;

        case "user":
            data = {
                title: "KHÁCH HÀNG",
                isMoney: false,
                text: "Xem tất cả khách hàng",
                link: "/admin/customerManagement",
                icon: <PersonIcon className="icon" />,
            };
            break;
        case "review":
            data = {
                title: "ĐÁNH GIÁ",
                isMoney: false,
                text: "Xem tất cả đánh giá",
                link: "/admin/statistical/review",
                icon: <ForumIcon className="icon" />,
            };
            break;
        default:
            break;
    }

    return (
        <div className="widget">
            <div className="left">
                <span className="titlehome">{data.title}</span>
                <span className="counter">{data.isMoney && "$"} {amount}</span>
                <Link to={data.link} className="link">{data.text}</Link>
            </div>
            <div className="right">
                {/* <div className="percentage positive"><ExpandLessIcon />{diff}%</div> */}
                <div className="percentage positive"></div>
                {data.icon}
            </div>
        </div>
    )
}

export default Widget