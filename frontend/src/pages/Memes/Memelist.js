import React, { useEffect } from 'react';
import './Memelist.css';
import { useSelector, useDispatch } from 'react-redux';
import useFetch from '../../services/useFetch';
import Meme from '../../components/Meme';
import { getMemes } from '../../actions/memeActions';

const backendUrl = process.env.REACT_APP_API_URL;
const url = `${backendUrl}/meme`;

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
      <div className="memes">
        {error && <div>{error}</div>}
        {isPending && <div>Loading...</div>}
        {reducerMemeState.memes &&
          reducerMemeState.memes.map(meme => (
            <div className="memes" key={meme.meme_id}>
              <Meme meme={meme}/>
            </div>
          ))}
      </div>
    </div>
  );
};

export default Memelist;
