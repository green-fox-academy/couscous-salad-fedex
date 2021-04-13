import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Link } from 'react-router-dom';
import Cookies from 'universal-cookie';
import './Header.css';
import deleteStoreAction from '../../actions/deleteStore';

const Header = () => {
  const cookie = new Cookies();
  const storeData = useSelector((state) => state);
  const token = useSelector((state) => state.login.token);
  const accessToken = new Cookies().get('accessToken');
  const userName = useSelector((state) => state.login.data.userName);
  const dispatch = useDispatch();
  console.log(storeData);

  const handleLogoutClick = () => {
    cookie.remove('accessToken');
    dispatch(deleteStoreAction());
  };

  const setHeaderToLogin = (
    <nav className="header">
      <h1>Meme Generator</h1>
      <div className="headerLinks">
        <Link to="/login">Login</Link>
        <Link to="/register">Register</Link>
      </div>
    </nav>
  );

  const setHeaderToMain = (
    <nav className="header">
      <Link to="/main">
        <h1>Hi {userName}!</h1>
      </Link>
      <div className="headerLinks">
        <Link>Create</Link>
        <Link to="/login" onClick={handleLogoutClick}>
          Logout
        </Link>
      </div>
    </nav>
  );
  return token || accessToken ? setHeaderToMain : setHeaderToLogin;
};

export default Header;