
import { DataGrid } from "@mui/x-data-grid";
import "./chart.css";
import React, { useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

// Hàm chuyển đổi định dạng ngày từ yyyy-mm-dd sang dd/mm/yyyy
// const formatDateForDatabase = (dateString) => {
//     const [year, month, day] = dateString.split('-');
//     return `${day}/${month}/${year}`;
// };

const CustomerStatDate = ({ data }) => {

    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [showChart, setShowChart] = useState(false);

    const [startMonth, setStartMonth] = useState('');
    const [endMonth, setEndMonth] = useState('');
    const [chartType, setChartType] = useState(''); // daily or monthly

    const [startYear, setStartYear] = useState('');
    const [endYear, setEndYear] = useState('');
    const formatDate = (dateString) => {
        const [day, month, year] = dateString.split('/');
        return `${year}-${month}-${day}`;
    };

    const formatDate2 = (dateString) => {
        const [year, month, day] = dateString.split('-');
        return `${day}/${month}/${year}`;
    };
    const formatMonthOut = (input) => {
        const [year, month] = input.split('-');
        return `${month}/${year}`;
    };
    // const formatMonth = (input) => {
    //     const [month, year] = input.split('/');
    //     return `${year}-${month}`;
    // };
    const handleSearch = () => {
        setShowChart(true);
    };
    const handleChartTypeChange = (type) => {
        setShowChart(false);
        setChartType(type);
        setStartDate('');
        setEndDate('');
        setStartMonth('');
        setEndMonth('');
        setStartYear('');
        setEndYear('');

    };
    const filterDataByDateRange = (startDate, endDate) => {
        console.log("Start date:", startDate);
        console.log("End date:", endDate);
        const filteredData = data.filter(item => {
            //xem thêm (biểu đồ revenue) 
            const [datePart, timePart] = item.bill.issuedate.split(' '); // Tách phần ngày và phần giờ
            const date = formatDate(datePart);
            return date >= startDate && date <= endDate;
        });
        return filteredData;
    };
    const filterDataByMonthRange = (startMonth, endMonth) => {
        console.log("Start month:", startMonth);
        console.log("End month:", endMonth);
        const filteredData = data.filter(item => {
            //xem thêm (biểu đồ revenue) 
            const [datePart, timePart] = item.bill.issuedate.split(' '); // Tách phần ngày và phần giờ
            const month = formatDate(datePart);
            return month >= startMonth && month < endMonth + 1;
        });
        return filteredData;
    };
    const filterDataByYearRange = (startYear, endYear) => {
        console.log("Start y:", startYear);
        console.log("End y:", endYear);
        const filteredData = data.filter(item => {
            //xem thêm (biểu đồ revenue) 
            const [datePart, timePart] = item.bill.issuedate.split(' '); // Tách phần ngày và phần giờ
            const year = datePart.substring(6, 10);
            return year >= startYear && year <= endYear;
        });
        return filteredData;
    };

    //tính tổng số đơn trong 1 ngày
    // const prepareData = () => {
    //     //const filteredData = filterDataByDateRange(startDate, endDate);
    //     let filteredData;
    //     if (chartType === 'daily') {
    //         filteredData = filterDataByDateRange(startDate, endDate);
    //     } else if (chartType === 'monthly') {
    //         filteredData = filterDataByMonthRange(startMonth, endMonth);
    //     }
    //     else {
    //         filteredData = filterDataByYearRange(startYear, endYear);
    //     }

    //     //const dailyRevenue = {};
    //     const dailyCustomerCount = {};
    //     filteredData.forEach(item => {
    //         //xem thêm (biểu đồ revenue) 
    //         const [datePart, timePart] = item.bill.issuedate.split(' '); // Tách phần ngày và phần giờ
    //         const date = formatDate(datePart);
    //         if (chartType === 'daily') {
    //             if (dailyCustomerCount[date]) {
    //                 dailyCustomerCount[date]++;
    //             } else {
    //                 dailyCustomerCount[date] = 1;
    //             }
    //         } else if (chartType === 'monthly') {
    //             const month = date.substring(0, 7); // Extract year and month
    //             console.log('filtermonth', month);
    //             if (dailyCustomerCount[month]) {
    //                 dailyCustomerCount[month]++;
    //             } else {
    //                 dailyCustomerCount[month] = 1;
    //             }
    //         }
    //         else {
    //             const year = date.substring(0, 4); // Extract year and month
    //             console.log('filteryear', year);
    //             if (dailyCustomerCount[year]) {
    //                 dailyCustomerCount[year]++;
    //             } else {
    //                 dailyCustomerCount[year] = 1;
    //             }
    //         }
    //     });
    //     // Sắp xếp lại các ngày để đảm bảo hiển thị từ quá khứ đến hiện tại
    //     //const sortedDates = Object.keys(dailyRevenue).sort((a, b) => new Date(a) - new Date(b));
    //     const sortedDates = Object.keys(dailyCustomerCount).sort((a, b) => new Date(a) - new Date(b));

    //     //const chartData = Object.keys(dailyRevenue).map(date => ({
    //     const chartData = sortedDates.map(date => ({
    //         date: chartType === 'daily' ? formatDate2(date) : (chartType === 'monthly' ? formatMonthOut(date) : date),
    //         customers: dailyCustomerCount[date],
    //     }));

    //     return chartData;
    // };


    const prepareData = () => {
        let filteredData;

        let totalCustomers = 0;

        if (chartType === 'daily') {
            filteredData = filterDataByDateRange(startDate, endDate);
        } else if (chartType === 'monthly') {
            filteredData = filterDataByMonthRange(startMonth, endMonth);
        } else {
            filteredData = filterDataByYearRange(startYear, endYear);
        }

        const dailyCustomerCount = {};
        const dailyOrderCount = {};

        filteredData.forEach(item => {
            const customerId = item.account.id;
            const [datePart, timePart] = item.bill.issuedate.split(' ');
            let date;
            //const date = formatDate(datePart);
            if (chartType === 'daily') {
                date = formatDate(datePart);
                if (dailyOrderCount[date]) {
                    dailyOrderCount[date]++;
                } else {
                    dailyOrderCount[date] = 1;
                }
            } else if (chartType === 'monthly') {
                date = formatDate(datePart);
                date = date.substring(0, 7);
                if (dailyOrderCount[date]) {
                    dailyOrderCount[date]++;
                } else {
                    dailyOrderCount[date] = 1;
                }
            } else {
                date = formatDate(datePart);

                date = date.substring(0, 4);
                if (dailyOrderCount[date]) {
                    dailyOrderCount[date]++;
                } else {
                    dailyOrderCount[date] = 1;
                }
            }

            // Tạo một tập hợp để lưu trữ các khách hàng duy nhất
            if (!dailyCustomerCount[date]) {
                dailyCustomerCount[date] = new Set();
            }

            // Thêm khách hàng vào tập hợp nếu chưa tồn tại
            dailyCustomerCount[date].add(customerId);
        });

        // Tính số lượng khách hàng duy nhất cho mỗi ngày, tháng hoặc năm
        const customerCountData = {};
        Object.keys(dailyCustomerCount).forEach(date => {
            customerCountData[date] = dailyCustomerCount[date].size;
        });

        //  Sắp xếp lại các ngày để đảm bảo hiển thị từ quá khứ đến hiện tại
        //const sortedDates = Object.keys(dailyRevenue).sort((a, b) => new Date(a) - new Date(b));
        const sortedDates = Object.keys(customerCountData).sort((a, b) => new Date(a) - new Date(b));

        // Calculate total number of customers
        Object.keys(dailyCustomerCount).forEach(date => {
            totalCustomers += dailyCustomerCount[date].size;
        });
        // Chuyển đổi dữ liệu thành mảng để chuẩn bị cho việc hiển thị trên biểu đồ
        // const chartData = sortedDates.map(date => ({
        //     date: chartType === 'daily' ? formatDate2(date) : (chartType === 'monthly' ? formatMonthOut(date) : date),
        //     customers: customerCountData[date],

        // }));

        const chartData = sortedDates.map(date => {
            const customers = customerCountData[date] || 0; // Số lượng khách hàng. Nếu không có dữ liệu, sẽ được đặt là 0.
            const orders = dailyOrderCount[date] || 0; // Số lượng đơn hàng. Tương tự như trên.

            return {
                date: chartType === 'daily' ? formatDate2(date) : (chartType === 'monthly' ? formatMonthOut(date) : date),
                customers: customers,
                orders: orders
            };
        });
        // Tính tổng số khách hàng từ mảng chartData
        // const totalCustomer = chartData.reduce((total, data) => total + data.customers, 0);

        console.log("totalCus", totalCustomers)
        console.log("cus", dailyCustomerCount)
        console.log("chartData", chartData)
        return { chartData, totalCustomers };
    };

    const { chartData, totalCustomers } = prepareData();
    // const calculateTotalRevenue = (startDate, endDate) => {
    //     const filteredData = filterDataByDateRange(startDate, endDate);
    //     let totalRevenue = 0;
    //     filteredData.forEach(item => {
    //         totalRevenue += item.totalprice;
    //     });
    //     return totalRevenue;
    // };
    // const totalRevenue = calculateTotalRevenue(startDate, endDate);
    const calculateTotalOrders = (filteredData) => {
        //console.log('customaertotal', filteredData)
        let totalOrders = 0;
        filteredData.forEach(item => {
            //totalRevenue += item.bill.totalprice;
            totalOrders++;
        });
        return totalOrders;
    };
    const totalDailyOrders = calculateTotalOrders(filterDataByDateRange(startDate, endDate));
    const totalMonthlyOrders = calculateTotalOrders(filterDataByMonthRange(startMonth, endMonth));
    const totalYearlyOrders = calculateTotalOrders(filterDataByYearRange(startYear, endYear));


    //bảng 
    // Hàm tạo id duy nhất cho mỗi hàng
    // const createRowId = (index) => {
    //     return index; // Sử dụng chỉ số của hàng làm id
    // };
    return (
        <div>
            <h2>Biểu đồ thống kê số khách hàng {chartType === 'daily' ? 'theo ngày' : (chartType === 'monthly' ? 'theo tháng' : (chartType === 'yearly' ? 'theo năm' : ''))}</h2>

            {chartType === 'daily' && (
                <>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <label >Từ ngày: </label>
                        <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <label >Đến ngày: </label>
                        <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
                        {/* <button onClick={handleSearch}>Search</button> */}
                    </div>
                </>
            )}
            {chartType === 'monthly' && (
                <>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <label>Từ tháng:</label>
                        <input type="month" value={startMonth} onChange={(e) => setStartMonth(e.target.value)} />
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <label>Đến tháng:</label>
                        <input type="month" value={endMonth} onChange={(e) => setEndMonth(e.target.value)} />
                    </div>
                </>
            )}
            {chartType === 'yearly' && (
                <>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <label>Từ năm:</label>
                        {/* <input type="year" value={startYear} onChange={(e) => setStartYear(e.target.value)} /> */}
                        <select value={startYear} onChange={(e) => setStartYear(e.target.value)}>
                            {/*
        Thay 'startYear' bằng danh sách các năm bạn muốn hiển thị, chẳng hạn từ 2000 đến 2100.
        Bạn có thể tạo một hàm hoặc một mảng năm để tạo ra các tùy chọn này.
    */}
                            {Array.from({ length: 101 }, (_, index) => {
                                const year = 2020 + index;
                                return <option key={year} value={year}>{year}</option>;
                            })}
                        </select>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <label>Đến năm:</label>
                        {/* <input type="year" value={endYear} onChange={(e) => setEndYear(e.target.value)} /> */}
                        <select value={endYear} onChange={(e) => setEndYear(e.target.value)}>
                            {/*
        Thay 'startYear' bằng danh sách các năm bạn muốn hiển thị, chẳng hạn từ 2000 đến 2100.
        Bạn có thể tạo một hàm hoặc một mảng năm để tạo ra các tùy chọn này.
    */}
                            {Array.from({ length: 101 }, (_, index) => {
                                const year = 2020 + index;
                                return <option key={year} value={year}>{year}</option>;
                            })}
                        </select>
                    </div>
                </>
            )}
            <div>
                <button className='revenue' style={{ marginLeft: '17%' }} onClick={() => handleChartTypeChange('daily')}>Theo ngày</button>
                <button className='revenue' style={{ marginLeft: '17%' }} onClick={() => handleChartTypeChange('monthly')}>Theo tháng</button>
                <button className='revenue' style={{ marginLeft: '17%' }} onClick={() => handleChartTypeChange('yearly')}>Theo năm</button>
                <button className='revenue' style={{ marginLeft: '17%', background: '#191b97', marginBottom: '40px' }} onClick={handleSearch}>Thống kê</button>
            </div>
            {chartData.length > 0 && showChart && (
                <div>
                    {chartType === 'daily' && (
                        <div className="textResult">
                            Từ {formatDate2(startDate)} đến {formatDate2(endDate)} có:  {totalCustomers} khách hàng và {totalDailyOrders} đơn hàng
                        </div>
                    )}
                    {chartType === 'monthly' && (
                        <div className="textResult">
                            Từ tháng {formatMonthOut(startMonth)} đến tháng {formatMonthOut(endMonth)} có:  {totalCustomers} khách hàng và {totalMonthlyOrders} đơn hàng
                        </div>
                    )}
                    {chartType === 'yearly' && (
                        <div className="textResult">
                            Từ năm {startYear} đến năm {endYear} có:  {totalCustomers} khách hàng và {totalYearlyOrders} đơn hàng
                        </div>
                    )}
                    <ResponsiveContainer width="100%" height={400}>
                        <BarChart
                            data={chartData} //prepareData() //thêm totalCustomer thì lỗi prepareData()
                            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                        >
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="date" />
                            {/* <XAxis dataKey="date" tick={{ fontSize: 15 }} domain={['dataMin', 'dataMax']} /> */}
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            {/* #5a5ccc  #0d660d*/}
                            <Bar dataKey="customers" name="Khách hàng" fill="#8884d8" barSize={30} />
                            <Bar dataKey="orders" name="Đơn hàng" fill="#82ca9d" barSize={30} />
                        </BarChart>
                    </ResponsiveContainer>

                    {/* Hiển thị DataGrid  không hiển thị đc id, phoneNumber
                    <div style={{ marginTop: '20px' }}>
                        <DataGrid
                            rows={prepareData().map((row, index) => ({ ...row, id: createRowId(index) }))}
                            columns={[
                                { field: 'date', headerName: 'Ngày', width: 150 },
                                { field: 'customers', headerName: 'Số Khách hàng', width: 150 },
                                { field: 'id', headerName: 'Họ và tên', width: 200 },
                                { field: 'phoneNumber', headerName: 'Số điện thoại', width: 200 },

                            ]}
                            pageSize={5} // Số hàng trên mỗi trang
                            autoHeight // Tự động điều chỉnh chiều cao
                        />
                    </div> */}
                </div>
            )}
        </div >
    );
};






export default CustomerStatDate;
