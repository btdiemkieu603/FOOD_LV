
import React, { useState, useEffect } from 'react';
import axios from 'axios';
//import { TableContainer, Table, TableHead, TableBody, TableRow, TableCell, Paper } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import "./tableOrderBy.css"
import { Link } from 'react-router-dom';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

const TableProductStats = () => {
    const [productReviews, setProductReviews] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${backendUrl}/api/review/all`);
                const reviews = response.data;
                const products = {};

                // Tính số lượng đánh giá và điểm trung bình của mỗi sản phẩm
                reviews.forEach(review => {
                    const productId = review.product.id;
                    if (!products[productId]) {
                        products[productId] = {
                            id: review.product.id,
                            name: review.product.name,
                            price: review.product.price,

                            totalReviews: 1,
                            totalStars: review.numberofstar
                        };
                    } else {
                        products[productId].totalReviews += 1;
                        products[productId].totalStars += review.numberofstar;
                    }
                });

                // Tạo mảng sản phẩm với thông tin số đánh giá và điểm trung bình
                const productArray = Object.values(products).map((product, index) => {
                    return {
                        id: product.id,
                        index: index + 1,
                        name: product.name,
                        price: product.price,

                        totalReviews: product.totalReviews,
                        averageStars: (product.totalStars / product.totalReviews).toFixed(1)
                    };
                });

                setProductReviews(productArray);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);
    const columns = [
        { field: 'index', headerName: 'STT', width: 90, tooltip: 'STT' },
        { field: 'id', headerName: 'ID Món ăn', width: 150, tooltip: 'ID món ăn' },
        { field: 'name', headerName: 'Tên', width: 200, tooltip: 'Tên món ăn' },
        { field: 'price', headerName: 'Giá', width: 150, tooltip: 'Giá món ăn' },
        { field: 'totalReviews', headerName: 'Số đánh giá', width: 200, tooltip: 'Số đánh giá của món ăn' },
        { field: 'averageStars', headerName: 'Số sao trung bình', width: 200, tooltip: 'Số sao trung bình ' },
        {
            field: 'action',
            headerName: '',
            sortable: false,
            width: 150,
            tooltip: 'Các thao tác',
            renderCell: (params) => (
                <Link to={`/admin/productManagement/productDetail/${params.row.id}`} style={{ textDecoration: "none" }}>
                    <div className="viewButton">Xem chi tiết</div>
                </Link>
            ),
        },
    ];

    return (
        <div style={{ height: 320, width: '100%' }}>
            <DataGrid className="datagridCustomerStats"
                rows={productReviews}
                columns={columns}
                // pageSize={5}
                // rowsPerPageOptions={[5]}
                // disableSelectionOnClick
                disableSelectionOnClick={false} // Đặt giá trị false để tắt chức năng chọn khi click
                checkboxSelection={false} // Đặt giá trị false để ẩn ô kiểm checkbox
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 4 },
                    },
                }}
                pageSizeOptions={[4]}
            //checkboxSelection
            />
        </div>
    );
};

export default TableProductStats;
