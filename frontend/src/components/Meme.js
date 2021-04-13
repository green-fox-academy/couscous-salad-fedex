import { React, useEffect } from 'react';
import UserButtons from './UserButtons';
import Cookies from 'universal-cookie';
import { useDispatch } from 'react-redux';
import { setReactions } from '../actions/reactionActions'

function Meme(props) {
  const { meme } = props;
  const cookie = new Cookies();
  const token = cookie.get('accessToken');
  //const reactionState = useSelector(state => state.reactionState);
  const dispatch = useDispatch();
  
  useEffect(() => {
    dispatch(setReactions(meme));
  }, [meme]);

  return (
    <div className="meme-info">
      <div className="meme-id">{meme.id}</div>
      <div className="meme-name">{meme.name}</div>
      <div className="meme-description">{ meme.description }</div>
      <div className="meme-url" img src={ meme.url } alt={meme.title}></div>
      {token ? (
        <UserButtons meme={meme} />
      ) : ('')}
    </div>
  );
}

export default Meme;
