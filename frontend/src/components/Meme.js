import React from 'react';
import UserButtons from './UserButtons'

function Meme(props) {
  const { meme } = props;
  const token = localStorage.getItem('token');
  
  return (
    <div className="meme-info">
      <div className="meme-id">{meme.id}</div>
      <div className="meme-name">{meme.name}</div>
      <div className="meme-description">{ meme.description }</div>
      {token ? (
        <UserButtons meme={meme} />
      ) : ('')}
    </div>
  );
}

export default Meme;
