
import LoginForm from '../../components/login/LoginForm';
import './login.css';
import logo from "../../components/img/login.PNG"

function Login() {
  return (
    <div className="Login">
      <div className="leftLogin">
        <h1 className='titleLogin'>Foodie Admin</h1>
        <img className="logo" src={logo} alt="Logo" />
      </div>
      <div className="rightLogin">
        <h1 className='titleLogin' > Đăng nhập </h1>
        <LoginForm />
      </div>
    </div>
  );
}

export default Login;
