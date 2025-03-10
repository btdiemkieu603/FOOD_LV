import React from 'react'
import "./navbar.css"
//import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
const Navbar = ({ title }) => {
    return (
        <div className="navbar">
            <div className="wrapper">
                <div className='titleText'>
                    <p>{title}</p>
                </div>
                {/* <div className="search">
                    <input type="text" placeholder="Search..." />
                    <div className='icon'>
                        <SearchOutlinedIcon />
                    </div>
                </div> */}
            </div>
        </div>
    )
}

export default Navbar