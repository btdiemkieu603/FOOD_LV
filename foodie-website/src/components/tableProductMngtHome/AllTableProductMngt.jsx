import "../../css/mngtTableHome.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { Link, useLocation } from 'react-router-dom';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

function AllTableProductMngt() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [searchStatus, setSearchStatus] = useState('');
    const [categories, setCategories] = useState([]);
    //const [originalData, setOriginalData] = useState([]);
    const location = useLocation();
    const pathname = location.pathname;
    const role = pathname.includes("/admin") ? "admin" : pathname.includes("/staff") ? "staff" : null;


    useEffect(() => {
        if (showProgressBar) {
            const timer = setTimeout(() => {
                setMessage('');
                setShowProgressBar(false);
            }, 2000);
            return () => clearTimeout(timer);
        }
        fetchData();
    }, [showProgressBar]);
    const fetchData = async () => {
        try {
            const response = await axios.get(`${backendUrl}/api/product/`);
            const updatedData = response.data.map(product => ({
                ...product,
                categoryTitle: product.category.title
            }));
            setData(updatedData);
            const getCategory = await axios.get(`${backendUrl}/api/category/`);
            setCategories(getCategory.data);
            // setOriginalData(updatedData);
            setLoading(false);
            // setFoodList(updatedData);
        } catch (error) {
            console.error('Error fetching data:', error);
            setLoading(false);
        }
    };

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };
    const handleSearchStatusChange = (event) => {
        setSearchStatus(event.target.value);
    };
    const filteredData = data.filter(product => {
        const isInSearchTerm = product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            product.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
            product.categoryTitle.toLowerCase().includes(searchTerm.toLowerCase()) ||
            (product.price === parseFloat(searchTerm)) ||
            (product.id === parseFloat(searchTerm))
        if (searchStatus !== '') {
            return isInSearchTerm && product.categoryTitle === searchStatus;
        } else {
            return isInSearchTerm;
        }
    });

    const handleReset = async () => {
        setSearchTerm('');
        setSearchStatus('');
        fetchData();
    };
    const handleDelete = async (id) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/product/${id}`, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            if (response.status === 200) {
                setMessage('Xóa Món ăn thành công!');
                setData(data.filter(item => item.id !== id));
            } else {
                setMessage('Xóa thất bại.');
            }
        } catch (error) {
            console.error('Error deleting product:', error);
            setMessage('Lỗi!');
        }
    };

    const handleHidden = async (id) => {

        setShowProgressBar(true);
        try {
            const response = await axios.put(`${backendUrl}/api/product/hidden/${id}`);

            if (response.status === 200) {
                console.log(`Cập nhật thành công.`);
                // fetchData();
                await fetchData();
                setMessage(`Cập nhật thành công.`);
            }
        } catch (error) {
            console.error(`lỗi cập nhật status:`, error);
            setMessage(`Cập nhật thất bại.`);
        }
    };


    const columns = [
        { field: "id", headerName: "ID", width: 80 },
        {
            field: "name",
            headerName: "Tên",
            width: 260,
            renderCell: (params) => {
                return (
                    <div className="cellWithImg">
                        <img className="cellImg" src={params.row.url_image_product} alt="avatar" />
                        {params.row.name}
                    </div>
                );
            },
        },
        { field: 'price', headerName: 'Giá', width: 100 },
        { field: "description", headerName: "Mô tả", width: 200 },
        { field: "categoryTitle", headerName: "Loại", width: 140 },
        {
            field: "isExist",
            headerName: "Tình trạng",
            width: 100,
            renderCell: (params) => {
                const isExist = params.row?.isExist;
                return (
                    <div className={`cellWithStatus ${isExist}`}>
                        {isExist === true ? 'Đang bán' : 'Ngừng bán'}
                        {/* {status} */}
                    </div>
                );
            },
        },
    ];

    const actionColumn = [
        {
            field: "action",
            headerName: "",
            width: 350,
            renderCell: (params) => {
                const ProductDetailLink = role === "admin"
                    ? `/admin/productManagement/productDetail/${params.row.id}`
                    : `/staff/productManagement/productDetail/${params.row.id}`;

                const ProductEditLink = role === "admin"
                    ? `/admin/productManagement/editProduct/${params.row.id}`
                    : `/staff/productManagement/editProduct/${params.row.id}`;
                const currentExist = params.row?.isExist;
                return (
                    <div className="cellAction">
                        <Link to={ProductDetailLink} style={{ textDecoration: "none" }}>
                            <div className="viewButton">Xem chi tiết</div>
                        </Link>
                        <Link to={ProductEditLink} style={{ textDecoration: "none" }}>
                            <div className="editButton"><EditIcon /></div>
                        </Link>
                        {/* <div className="deleteButton" onClick={() => handleDelete(params.row.id)}>
                            <DeleteIcon />
                        </div> */}
                        <div className="viewButton" onClick={() => handleHidden(params.row.id)}>
                            {currentExist === true ? <LockIcon /> : <LockOpenIcon />}
                        </div>
                    </div>
                );
            },
        },
    ];

    if (loading) {
        return <div>Loading...</div>;
    }

    const sortModel = [{ field: 'id', sort: 'desc' }];

    return (
        <div className="datatable">
            {message && (
                <div className="success-message">
                    {message}
                    {showProgressBar && <div className="progress-bar" />}
                </div>
            )}
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Tìm kiếm theo ID, tên, mô tả, loại hoặc giá"
                    value={searchTerm}
                    onChange={handleSearchChange}

                />
                <p className="textStatus"> Danh mục </p>
                <select className="selectStatus" value={searchStatus} onChange={handleSearchStatusChange}>
                    <option value="">Tất cả</option>
                    {categories.map((category) => (
                        <option key={category.id} value={category.title}>{category.title}</option>
                    ))}
                </select>
                <RefreshIcon
                    title="Tải lại dữ liệu"
                    onClick={handleReset}
                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                />
            </div>
            <div style={{ height: 526, width: '100%' }}>
                <DataGrid
                    className="datagrid"
                    rows={filteredData}
                    columns={columns.concat(actionColumn)}
                    initialState={{ pagination: { paginationModel: { page: 0, pageSize: 8 } } }}
                    pageSizeOptions={[8]}
                    checkboxSelection
                    sortModel={sortModel}
                />
            </div>
        </div>
    );
}

export default AllTableProductMngt;


//thêm chức năng xóa nhiều sản phẩm được chọn
