
import React, { useState, useEffect } from 'react';
import axios from 'axios';
//import { TableContainer, Table, TableHead, TableBody, TableRow, TableCell, Paper, Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import "./tableOrderBy.css"
import { Link } from 'react-router-dom';

const backendUrl = process.env.REACT_APP_BACKEND_URL;
const TableCustomerStats = () => {
    const [customerReviews, setcustomerReviews] = useState([]);

    // const [currentPage, setCurrentPage] = useState(1); //c2
    // const [rowsPerPage, setRowsPerPage] = useState(5);
    // const [totalPages, setTotalPages] = useState(1);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/review/all`);
                const reviews = response.data;
                const customers = {};

                // Tính số lượng đánh giá và điểm trung bình của mỗi sản phẩm
                reviews.forEach(review => {
                    const customerId = review.account.id;
                    if (!customers[customerId]) {
                        customers[customerId] = {
                            id: review.account.id,
                            firstname: review.account.firstname,
                            lastname: review.account.lastname,
                            phonenumber: review.account.phonenumber,

                            totalReviews: 1,
                            totalStars: review.numberofstar
                        };
                    } else {
                        customers[customerId].totalReviews += 1;
                        customers[customerId].totalStars += review.numberofstar;
                    }
                });

                // Tạo mảng sản phẩm với thông tin số đánh giá và điểm trung bình
                const customerArray = Object.values(customers).map((customer, index) => {
                    return {
                        index: index + 1,
                        id: customer.id,
                        firstname: customer.firstname,
                        lastname: customer.lastname,
                        phonenumber: customer.phonenumber,

                        totalReviews: customer.totalReviews,
                        averageStars: (customer.totalStars / customer.totalReviews).toFixed(1)
                    };
                });

                setcustomerReviews(customerArray);
                // Giới hạn số lượng dòng hiển thị là 5     //chi hien thi 5 dong
                // setcustomerReviews(customerArray.slice(0, 5));

                // // Tính tổng số trang dựa trên số lượng dòng trên mỗi trang    //c2
                // setTotalPages(Math.ceil(customerArray.length / rowsPerPage));
                // // Lấy chỉ mục bắt đầu và kết thúc của dòng trên mỗi trang
                // const indexOfLastRow = currentPage * rowsPerPage;
                // const indexOfFirstRow = indexOfLastRow - rowsPerPage;
                // // Cắt mảng để lấy ra dòng trên trang hiện tại
                // const currentRows = customerArray.slice(indexOfFirstRow, indexOfLastRow);
                // setcustomerReviews(currentRows);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);
    // [currentPage, rowsPerPage]);  //c2

    // const handleNextPage = () => {
    //     if (currentPage < totalPages) {
    //         setCurrentPage(currentPage + 1);
    //     }
    // };

    // const handlePreviousPage = () => {
    //     if (currentPage > 1) {
    //         setCurrentPage(currentPage - 1);
    //     }
    // };

    const columns = [
        { field: 'index', headerName: 'STT', width: 80 },
        { field: 'id', headerName: 'ID KH', width: 150 },
        { field: 'firstname', headerName: 'Họ', width: 150 },
        { field: 'lastname', headerName: 'Tên', width: 150 },
        { field: 'phonenumber', headerName: 'SĐT', width: 200 },
        { field: 'totalReviews', headerName: 'Số đánh giá', width: 150 },
        { field: 'averageStars', headerName: 'Số sao trung bình', width: 150 },
        {
            field: 'action',
            headerName: '',
            sortable: false,
            width: 150,
            renderCell: (params) => (
                <Link to={`/admin/customerManagement/customerDetail/${params.row.id}`} style={{ textDecoration: "none" }}>
                    <div className="viewButton">Xem chi tiết</div>
                </Link>
            )
        }
    ];

    return (
        // <div>
        //     {/* <h2>Bảng thống kê đánh giá sản phẩm</h2> */}
        //     <TableContainer component={Paper} style={{ height: 400, width: '100%' }}>
        //         <Table aria-label="simple table">
        //             <TableHead>
        //                 <TableRow>
        //                     <TableCell>ID khách hàng</TableCell>
        //                     <TableCell>Họ</TableCell>
        //                     <TableCell >Tên</TableCell>
        //                     <TableCell >SĐT</TableCell>
        //                     <TableCell>Số lượng đánh giá</TableCell>
        //                     <TableCell>Điểm trung bình</TableCell>
        //                     <TableCell ></TableCell>
        //                 </TableRow>
        //             </TableHead>
        //             <TableBody>
        //                 {customerReviews.map((customer, index) => (
        //                     <TableRow key={index} className={customerReviews.length > 0 ? "tableRow" : "tableRowEmpty"}>
        //                         <TableCell>{customer.id}</TableCell>
        //                         <TableCell>{customer.firstname}</TableCell>
        //                         <TableCell>{customer.lastname}</TableCell>
        //                         <TableCell>{customer.phonenumber}</TableCell>
        //                         <TableCell>{customer.totalReviews}</TableCell>
        //                         <TableCell>{customer.averageStars.toFixed(1)}</TableCell>
        //                         <TableCell className="tableCell">

        //                             <Link to={`/admin/customerManagement/customerDetail/${customer.id}`} style={{ textDecoration: "none" }}>
        //                                 <div className="viewButton">Xem chi tiết</div>
        //                             </Link>

        //                         </TableCell>

        //                     </TableRow>
        //                 ))}
        //                 {/* Điền các hàng trống nếu số lượng dòng không đủ 5 */}
        //                 {customerReviews.length < 5 && (
        //                     Array(5 - customerReviews.length).fill(null).map((_, index) => (
        //                         <TableRow key={customerReviews.length + index}>
        //                             <TableCell colSpan={7}></TableCell>
        //                         </TableRow>
        //                     ))
        //                 )}
        //                 <TableRow>
        //                     <TableCell colSpan={7} align="center">
        //                         <Button onClick={handlePreviousPage} disabled={currentPage === 1}>Trang trước</Button>
        //                         <Button onClick={handleNextPage} disabled={currentPage === totalPages}>Trang tiếp</Button>
        //                     </TableCell>
        //                 </TableRow>
        //             </TableBody>
        //         </Table>

        //         {/* 
        //         <TableRow>
        //             <TableCell colSpan={7} align="center">
        //                 <Button onClick={handlePreviousPage} disabled={currentPage === 1}>Trang trước</Button>
        //                 <Button onClick={handleNextPage} disabled={currentPage === totalPages}>Trang tiếp</Button>
        //             </TableCell>
        //         </TableRow> */}


        //     </TableContainer>
        //     {/*    nam o ngoai bang
        //     <div style={{ display: 'flex', justifyContent: 'center', marginTop: '10px' }}>
        //         <Button onClick={handlePreviousPage} disabled={currentPage === 1}>Trang trước</Button>
        //         <Button onClick={handleNextPage} disabled={currentPage === totalPages}>Trang tiếp</Button>
        //     </div> */}
        // </div>


        <div style={{ height: 320, width: '100%' }}>
            <DataGrid className="datagridCustomerStats"
                rows={customerReviews}
                columns={columns}
                disableSelectionOnClick={false} // Đặt giá trị false để tắt chức năng chọn khi click
                checkboxSelection={false} // Đặt giá trị false để ẩn ô kiểm checkbox
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 4 },
                    },
                }}
                pageSizeOptions={[4]}

            />
        </div>
    );
};


export default TableCustomerStats;
