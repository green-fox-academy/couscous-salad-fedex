import React, { useState } from 'react';
import Cookies from 'universal-cookie';
import jwt from 'jsonwebtoken';
import './Login.css';
import { useHistory } from 'react-router';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loadUserToken, loadUserTokenPayload } from '../../actions/loginAction';

const Login = () => {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [inputStatus, setInputStatus] = useState('login-input-OK');
  const [error, setError] = useState(null);
  const history = useHistory();

  const dispatch = useDispatch();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userData = { userName, password };
    const URL = process.env.REACT_APP_API_URL;

    try {
      const response = await fetch(`${URL}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData),
      });
      const responseBody = await response.json();
      if (response.status !== 200) {
        throw Error(responseBody.error);
      }
      const decodedJWTToken = jwt.decode(responseBody.token, {
        complete: true,
      });
      dispatch(loadUserToken(responseBody.token));
      dispatch(loadUserTokenPayload(decodedJWTToken.payload));
      const cookie = new Cookies();
      cookie.set('accessToken', responseBody.token, {
        path: '/',
      });
      history.push('/main');
    } catch (error) {
      console.log(error.message);
      setError(error.message);
      setInputStatus('login-input-ERROR');
    }
  };

  return (
    <div className="login-page">
      <div className="login">
        <h1 className="login-h1">Meme Generator</h1>
        <form className="login-form" onSubmit={handleSubmit}>
          <input
            className={inputStatus}
            type="text"
            required
            placeholder="Username"
            minLength="3"
            value={userName}
            onChange={(e) => {
              setUserName(e.target.value);
              setInputStatus('login-input-OK');
              setError(null);
            }}
          />
          <br />
          <input
            className={inputStatus}
            type="password"
            required
            placeholder="Password"
            minLength="8"
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setInputStatus('login-input-OK');
              setError(null);
            }}
          />
          <div className="errorBox">
            {error && (
              <div className="input-error-message">
                {error}
                <i className="material-icons">warning</i>
              </div>
            )}
          </div>
          <Link className="login-a" to="/">
            Forgot your password?
          </Link>
          <button className="login-btn" type="submit">
            LOGIN
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
