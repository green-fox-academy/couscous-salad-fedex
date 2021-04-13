import React from 'react';
import './NotFound.css';
import { useHistory } from 'react-router';

const NotFound = () => {
  const history = useHistory();
  const handleClick = () => {
    history.push('/main');
  };
  return (
    <div className="not-found-page">
      <div className="not-found">
        <h2>Sorry</h2>
        <p className="not-found-p">Not implemented yet!</p>
        <button className="not-found-btn" onClick={handleClick}>
          GO BACK
        </button>
      </div>
    </div>
  );
};

export default NotFound;
