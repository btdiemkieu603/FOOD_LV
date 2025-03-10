
import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Link } from 'react-router-dom';

const TableProduct = ({ orders }) => {
    const [order, setOrder] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const orderArray = Object.values(orders).map((order, index) => {
                    return {
                        id: order.id,
                        index: index + 1,
                        name: order.name,
                        price: order.price,
                        description: order.description,
                        category: order.category.title,
                        // url_image_product: order.url_image_product,
                        value: order?.value ? order?.value : 0,
                    };
                });
                setOrder(orderArray);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, [orders]);

    const columns = [
        { field: 'index', headerName: 'STT', width: 90 },
        { field: 'id', headerName: 'ID Món ăn', width: 120 },
        { field: 'name', headerName: 'Tên', width: 200 },
        { field: 'price', headerName: 'Giá', width: 120 },
        { field: 'description', headerName: 'Mô tả', width: 200 },
        { field: 'category', headerName: 'Phân loại', width: 150 },
        // { field: 'url_image_product', headerName: 'ảnh', width: 150 },
        { field: 'value', headerName: 'Số suất đã bán', width: 150 },
        {
            field: 'action',
            headerName: '',
            sortable: false,
            width: 150,
            renderCell: (params) => (
                <Link to={`/admin/productManagement/productDetail/${params.row.id}`} style={{ textDecoration: "none" }}>
                    <div className="viewButton">Xem chi tiết</div>
                </Link>
            )
        }
    ];

    return (
        <div style={{ height: 370, width: '100%' }}>
            <DataGrid className="datagridCustomerStats"
                rows={order}
                columns={columns}
                disableSelectionOnClick={false}
                checkboxSelection={false}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 5 },
                    },
                }}
                pageSizeOptions={[5]}
            />
        </div>
    );
};

export default TableProduct;
