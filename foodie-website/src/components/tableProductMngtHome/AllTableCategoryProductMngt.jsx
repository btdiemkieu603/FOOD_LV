import "../../css/mngtTableHome.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';
import { Link, useLocation } from "react-router-dom";

import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';

const backendUrl = process.env.REACT_APP_BACKEND_URL;

function AllTableCategoryProductMngt() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [showProgressBar, setShowProgressBar] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');

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
            const response = await axios.get(`${backendUrl}/api/category/`);
            // if (response.status === 200) {
            setData(response.data);
            //console.log(response);
            setLoading(false);
            // }
        } catch (error) {
            console.error('Error fetching data:', error);
            setLoading(false);
        }
    };
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const filteredData = data.filter(category =>
        category.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (category.id === parseFloat(searchTerm))
    );
    const handleReset = async () => {
        setSearchTerm('');
        fetchData();
    };
    const columns = [
        // { field: 'id', headerName: 'ID', width: 150 },
        // { field: 'name', headerName: 'Name', width: 250 },
        // { field: 'description', headerName: 'Description', width: 350 },
        { field: "id", headerName: "ID", width: 100 },
        {
            field: "title",
            headerName: "Tên",
            width: 750,
            renderCell: (params) => {
                return (
                    <div className="cellWithImg">
                        <img className="cellImg" src={params.row.url_image_category} alt="avatar" />
                        {params.row.title}
                    </div>
                );
            },
        },
        {
            field: "isExist",
            headerName: "Tình trạng",
            width: 120,
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

    if (loading) {
        return <div>Loading...</div>;
    }
    const handleDelete = async (id) => {
        setShowProgressBar(true);
        try {
            const response = await axios.delete(`${backendUrl}/api/category/${id}`, {
                headers: {
                    'Content-Type': 'application/json', // Đảm bảo rằng yêu cầu sử dụng JSON
                }
            });
            if (response.status === 200) {
                setMessage('Xóa Danh mục món ăn thành công!');
                setData(data.filter((item) => item.id !== id));

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
            const response = await axios.put(`${backendUrl}/api/category/hidden/${id}`);

            if (response.status === 200) {
                console.log(`Cập nhật thành công.`);
                fetchData();
                setMessage(`Cập nhật thành công.`);
            }
        } catch (error) {
            console.error(`lỗi cập nhật status:`, error);
            setMessage(`Cập nhật thất bại.`);
        }
    };

    const actionColumn = [
        {
            field: "action",
            headerName: "",
            width: 300,
            renderCell: (params) => {
                const CategoryEditLink = role === "admin"
                    ? `/admin/productManagement/editCategory/${params.row.id}`
                    : `/staff/productManagement/editCategory/${params.row.id}`;

                const currentExist = params.row?.isExist;
                return (
                    <div className="cellAction">
                        <Link to={CategoryEditLink} style={{ textDecoration: "none" }}>
                            <div className="editButton"><EditIcon /></div>
                        </Link>

                        {/* if status:Hoạt động => button: Ẩn (Ẩn => Sử dụng) */}
                        {/* <div
                            className="blockButton"
                        //onClick={() => handleBlock(params.row.id)}
                        >
                            Ẩn
                        </div> */}
                        {/* <div
                            className="deleteButton"
                            onClick={() => handleDelete(params.row.id)}
                        >
                            < DeleteIcon />
                        </div> */}
                        <div className="viewButton" onClick={() => handleHidden(params.row.id)}>
                            {currentExist === true ? <LockIcon /> : <LockOpenIcon />}
                        </div>
                    </div>
                );
            },
        },
    ];
    const sortModel = [
        {
            field: 'id',
            sort: 'desc', // Sắp xếp giảm dần
        },
    ];

    return (
        <div className="datatable" >
            {message && (
                <div className="success-message">
                    {message}
                    {showProgressBar && <div className="progress-bar" />}
                </div>
            )}
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Tìm kiếm theo ID, tên"
                    value={searchTerm}
                    onChange={handleSearchChange}

                />
                <RefreshIcon
                    title="Tải lại dữ liệu"
                    onClick={handleReset}
                    style={{ cursor: 'pointer', marginLeft: '10px' }}
                />
            </div>
            <div style={{ height: 526, width: '100%' }}>
                <DataGrid className="datagrid"

                    rows={filteredData}
                    columns={columns.concat(actionColumn)}

                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 8 },
                        },
                    }}
                    pageSizeOptions={[8]}
                    checkboxSelection
                    sortModel={sortModel}
                />
            </div>
        </div>
    )
}
export default AllTableCategoryProductMngt