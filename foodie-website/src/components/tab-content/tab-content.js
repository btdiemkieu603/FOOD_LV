import React from "react";
import "./tab-content.css";

export default class TabContent extends React.Component {
  constructor(props) {
    super(props);

    // Tạo ra một mảng để lưu ref của các tab content
    this.refTabContents = [];
    this.props.input.forEach((_) => {
      this.refTabContents.push(React.createRef());
    });

    // Mặc định sẽ hiển thị tab đầu tiên, nên this.state.tabIndex = 0
    this.state = {
      tabIndex: 0,
    };

    this.changeTabIndex = this.changeTabIndex.bind(this);
    this.updateTabContent = this.updateTabContent.bind(this);
  }

  /**
   * Thay đổi tabIndex khi người dùng thay đổi tab
   */
  changeTabIndex(index) {
    this.setState({
      tabIndex: index,
    });
  }

  /**
   * Cập nhật tab content
   *
   * Hàm này sẽ duyệt từng tab content,
   *  + Nếu this.state.tabIndex trùng với index của tab => tab đó được active
   *    => gán maxHeight cho nó bằng với giá trị scrollHeight - độ cao cần thiết
   *    => và gán opacity = 1 để hiển thị nó
   *  + Ngược lại, khi nó không được active
   *    => gán maxHeight cho nó bằng 0.
   *    => mà trong file css mình đã set overflow = hidden nên nó sẽ bị ẩn
   *    => tuy nhiên nó vẫn có padding và border
   *    => nên cần set opacity = 0 để ẩn nốt border đi
   */
  updateTabContent() {
    this.refTabContents.forEach((refTab, index) => {
      const elmTab = refTab.current;

      if (this.state.tabIndex === index) {
        elmTab.style.maxHeight = 630 + "px";
        elmTab.style.opacity = "1";
      } else {
        elmTab.style.maxHeight = null;
        elmTab.style.opacity = "0";
      }
    });
  }

  /**
   * Hàm này được gọi khi TabContent được render xong.
   * Khi đó mình sẽ cập nhật TabContent - updateTabContent lần đầu tiên.
   * Sau đó, đăng ký sự kiện khi thay đổi kích thước màn hình,
   * sẽ cập nhật lại TabContent,
   * (thực chất là mình chỉ cập nhật lại maxHeight)
   */
  componentDidMount() {
    this.updateTabContent();
    window.addEventListener("resize", this.updateTabContent);
  }

  /**
   * Hàm này được gọi khi TabContent bị huỷ.
   * Lúc này cần huỷ đăng ký sự kiện resize lúc trước để tránh leak memory
   */
  componentWillUnmount() {
    window.removeEventListener("resize", this.updateTabContent);
  }

  /**
   * Hàm này được gọi mỗi khi this.state.tabIndex thay đổi.
   * Tức là mình cần phải cập nhật lại nội dung của Tab content
   * ứng với từng tab
   */
  componentDidUpdate() {
    this.updateTabContent();
  }

  render() {
    return (
      <div className="lp-tab-content">
        <div className="tab">
          {this.props.input.map((tabContent, index) => {
            return (
              <button
                key={index}
                className={`tab-link ${this.state.tabIndex === index ? "active" : ""
                  }`}
                onClick={() => this.changeTabIndex(index)}
              >
                {tabContent.title}
              </button>
            );
          })}
        </div>

        <div className="tab-content-wrapper">
          {this.props.input.map((tabContent, index) => {
            return (
              <div
                ref={this.refTabContents[index]}
                key={index}
                className={`tab-content`}
              >
                {tabContent.content}
              </div>
            );
          })}
        </div>
      </div>
    );
  }
}
// import React, { useState } from "react";
// import "./tab-content.css";

// const TabContent = ({ input, onTabChange }) => {
//   const [activeTab, setActiveTab] = useState(0);

//   const handleTabClick = (index) => {
//     setActiveTab(index);
//     onTabChange(index);
//   };

//   return (
//     <div className="lp-tab-content">
//       <div className="tab">
//         {input.map((tabContent, index) => (
//           <button
//             key={index}
//             className={`tab-link ${activeTab === index ? "active" : ""}`}
//             onClick={() => handleTabClick(index)}
//           >
//             {tabContent.title}
//           </button>
//         ))}
//       </div>

//       <div className="tab-content-wrapper">
//         {input.map((tabContent, index) => (
//           <div
//             key={index}
//             className={`tab-content ${activeTab === index ? "active-tab-content" : ""
//               }`}
//           >
//             {tabContent.content}
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// };

// export default TabContent;

