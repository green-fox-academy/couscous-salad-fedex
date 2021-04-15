import React, { useEffect } from 'react';
import './Memelist.css';
import { useSelector, useDispatch } from 'react-redux';
import useFetch from '../services/useFetch';
import Meme from '../components/Meme';
import { getMemes } from '../actions/memeActions';

//const backendUrl = process.env.REACT_APP_BACKENDURL;
const url = `https://api.punkapi.com/v2/beers?page=5&per_page=10`;

const Memelist = () => {
  const reducerMemeState = useSelector(state => state.memeState);
  const dispatch = useDispatch();

  const { data: memes, error, isPending } = useFetch(url);
  
  useEffect(() => {
    if (memes && memes.length > 0) {
      dispatch(getMemes(memes));
    }
  },[memes]);

  return (
    <div className="memes-container">
      <h2>Welcome to couscous salad meme generator!</h2>
      <div className="memes">
        {error && <div>{error}</div>}
        {isPending && <div>Loading...</div>}
        {reducerMemeState.memes &&
          reducerMemeState.memes.map(meme => (
            <div className="memes" key={meme.id}>
              <Meme meme={meme}/>
            </div>
          ))}
      </div>
    </div>
  );
};

export default Memelist;
