
import "./chart.css";
import React, { useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';


const RevenueDateChart = ({ data }) => {
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
            //định dạng ngày: 18/04/2024
            //const date = formatDate(item.issuedate);
            //return date >= startDate && date <= endDate;

            //định dạng ngày: 18/04/2024 18:16:20
            const [datePart, timePart] = item.issuedate.split(' '); // Tách phần ngày và phần giờ
            const date = formatDate(datePart);
            return date >= startDate && date <= endDate;
        });
        return filteredData;
    };
    const filterDataByMonthRange = (startMonth, endMonth) => {
        console.log("Start month:", startMonth);
        console.log("End month:", endMonth);
        const filteredData = data.filter(item => {
            //sai const month = item.issuedate.substring(2, 10); // Extract year and month  substring(0, 10)

            //18/04/2024
            //const month = formatDate(item.issuedate);

            //18/04/2024 18:26:20
            const [datePart, timePart] = item.issuedate.split(' '); // Tách phần ngày và phần giờ
            const month = formatDate(datePart);
            console.log('m', month);
            return month >= startMonth && month < endMonth + 1;
        });
        return filteredData;
    };
    const filterDataByYearRange = (startYear, endYear) => {
        console.log("Start y:", startYear);
        console.log("End y:", endYear);
        const filteredData = data.filter(item => {
            //sai const month = item.issuedate.substring(2, 10); // Extract year and month  substring(0, 10)

            //18/04/2024
            //const year = item.issuedate.substring(6, 10);

            //18/04/2024 18:27:20
            const [datePart, timePart] = item.issuedate.split(' '); // Tách phần ngày và phần giờ
            const year = datePart.substring(6, 10);
            console.log('y', year);

            return year >= startYear && year <= endYear;
        });
        return filteredData;
    };
    const prepareData = () => {
        //const filteredData = filterDataByDateRange(startDate, endDate);
        let filteredData;
        if (chartType === 'daily') {
            filteredData = filterDataByDateRange(startDate, endDate);
        } else if (chartType === 'monthly') {
            filteredData = filterDataByMonthRange(startMonth, endMonth);
        }
        else {
            filteredData = filterDataByYearRange(startYear, endYear);
        }

        const dailyRevenue = {};

        filteredData.forEach(item => {
            //định dạng ngày: 18/04/2024
            //const date = formatDate(item.issuedate);

            //định dạng ngày: 18/04/2024 18:24:20
            const [datePart, timePart] = item.issuedate.split(' '); // Tách phần ngày và phần giờ
            const date = formatDate(datePart);
            console.log('filterdate', date);
            const revenue = item.totalprice;
            if (chartType === 'daily') {
                if (dailyRevenue[date]) {
                    dailyRevenue[date] += revenue;
                } else {
                    dailyRevenue[date] = revenue;
                }
            } else if (chartType === 'monthly') {
                const month = date.substring(0, 7); // Extract year and month
                console.log('filtermonth', month);
                if (dailyRevenue[month]) {
                    dailyRevenue[month] += revenue;
                } else {
                    dailyRevenue[month] = revenue;
                }
            }
            else {
                const year = date.substring(0, 4); // Extract year and month
                console.log('filteryear', year);
                if (dailyRevenue[year]) {
                    dailyRevenue[year] += revenue;
                } else {
                    dailyRevenue[year] = revenue;
                }
            }
        });
        // Sắp xếp lại các ngày để đảm bảo hiển thị từ quá khứ đến hiện tại
        const sortedDates = Object.keys(dailyRevenue).sort((a, b) => new Date(a) - new Date(b));

        //const chartData = Object.keys(dailyRevenue).map(date => ({
        const chartData = sortedDates.map(date => ({
            date: chartType === 'daily' ? formatDate2(date) : (chartType === 'monthly' ? formatMonthOut(date) : date),
            revenue: dailyRevenue[date],
        }));

        return chartData;
    };

    // const calculateTotalRevenue = (startDate, endDate) => {
    //     const filteredData = filterDataByDateRange(startDate, endDate);
    //     let totalRevenue = 0;
    //     filteredData.forEach(item => {
    //         totalRevenue += item.totalprice;
    //     });
    //     return totalRevenue;
    // };
    // const totalRevenue = calculateTotalRevenue(startDate, endDate);
    const calculateTotalRevenue = (filteredData) => {
        let totalRevenue = 0;
        filteredData.forEach(item => {
            totalRevenue += item.totalprice;
        });
        return totalRevenue;
    };
    const totalDailyRevenue = calculateTotalRevenue(filterDataByDateRange(startDate, endDate));
    const totalMonthlyRevenue = calculateTotalRevenue(filterDataByMonthRange(startMonth, endMonth));
    const totalYearlyRevenue = calculateTotalRevenue(filterDataByYearRange(startYear, endYear));

    return (
        <div>
            <h2>Biểu đồ doanh thu {chartType === 'daily' ? 'theo ngày' : (chartType === 'monthly' ? 'theo tháng' : (chartType === 'yearly' ? 'theo năm' : ''))}</h2>

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
            {showChart && (
                <div>
                    {chartType === 'daily' && (
                        <div className="textResult">
                            Tổng doanh thu từ {formatDate2(startDate)} đến {formatDate2(endDate)}: {totalDailyRevenue}đ
                        </div>
                    )}
                    {chartType === 'monthly' && (
                        <div className="textResult">
                            Tổng doanh thu từ tháng {formatMonthOut(startMonth)} đến tháng {formatMonthOut(endMonth)}: {totalMonthlyRevenue}đ
                        </div>
                    )}
                    {chartType === 'yearly' && (
                        <div className="textResult">
                            Tổng doanh thu từ năm {startYear} đến năm {endYear}: {totalYearlyRevenue}đ
                        </div>
                    )}
                    <ResponsiveContainer width="100%" height={400}>
                        <BarChart
                            data={prepareData()}
                            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                        >
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="date" />
                            {/* <XAxis dataKey="date" tick={{ fontSize: 15 }} domain={['dataMin', 'dataMax']} /> */}
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            <Bar dataKey="revenue" name="Doanh thu" fill="#5a5ccc" barSize={30} />
                        </BarChart>
                    </ResponsiveContainer>
                </div>
            )}
        </div >
    );
};

export default RevenueDateChart;
