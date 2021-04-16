import { React, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { setReactions } from '../actions/reactionActions';
import { useHistory } from 'react-router';

function UserButtons(props) {

  const { meme } = props;
  const dispatch = useDispatch();
  const history = useHistory();

  const handleClick = (meme) => {
    history.push(`/setreactions/:${meme.meme_id}`)
  }

  useEffect(() => {
    dispatch(setReactions(meme));
  }, [meme]);

  return (
    <div className="reactions">
      <div className="meme-funny">{meme.reaction_list[0].reaction_type} : {meme.reaction_list[0].amount}</div>
      <div className="meme-sad">{meme.reaction_list[1].reaction_type} : {meme.reaction_list[1].amount}</div>
      <div className="meme-surprised">{meme.reaction_list[2].reaction_type} : {meme.reaction_list[2].amount}</div>
      <div className="meme-angry">{meme.reaction_list[3].reaction_type} : {meme.reaction_list[3].amount}</div>
      <div className="meme-erotic">{meme.reaction_list[4].reaction_type} : {meme.reaction_list[4].amount}</div>
      <div className="meme-happy">{meme.reaction_list[5].reaction_type} : { meme.reaction_list[5].amount }</div>
      <div className="modify function">
        <button onClick={handleClick}>Set reactions</button>
      </div>
    </div>
  );
}

export default UserButtons;
