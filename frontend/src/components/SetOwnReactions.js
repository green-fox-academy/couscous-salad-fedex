import React from 'react';
import { useDispatch } from 'react-redux';
import useFetch from '../services/useFetch';
import { setMyReactions } from '../actions/reactionActions';
import Cookies from 'universal-cookie';

const SetOwnReactions = (props) => {
  
  const { meme } = props;
  const backendUrl = process.env.REACT_APP_API_URL;
  const url = `${backendUrl}/meme/${meme.meme_id}`;
  const dispatch = useDispatch();
  const cookie = new Cookies();
  const token = cookie.get('Meme-token');
  
  const { data: mymeme, error, isPending } = useFetch(url);

  const onClick = async (event) => {
    event.preventDefault();
    await fetch(url, {
      method: 'PUT',
      headers: {
        authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(mymeme.reaction_list)
    })
      .then(response => {
        if (response.status === 200) {
          return response;
        }
        throw new Error('Connection failed!');
      })
      .then(() => dispatch(setMyReactions(meme)))
      .catch(err => {
        console.log(err);
      });

  }
  return (
    <div className="reactions">
      {error && <div>{error}</div>}
      {isPending && <div>Loading...</div>}
      <div className="meme-url" img src={ mymeme.meme_path } alt={mymeme.meme_id}></div>
      <form className="reactions-form">
        <label for={ `${mymeme.reaction_list[0].reaction_type}` }>{ `${mymeme.reaction_list[0].reaction_type}` }</label>
        <input type="number" min="0" max="10" name={ `${mymeme.reaction_list[0].reaction_type}` }>{ `${mymeme.reaction_list[0].amount}` }</input>
        <label for={ `${mymeme.reaction_list[1].reaction_type}` }>{ `${mymeme.reaction_list[1].reaction_type}` }</label>
        <input type="number" min="0" max="10" name={ `${mymeme.reaction_list[1].reaction_type}` }>{ `${mymeme.reaction_list[1].amount}` }</input>
        <label for={ `${mymeme.reaction_list[2].reaction_type}` }>{ `${mymeme.reaction_list[2].reaction_type}` }</label>
        <input type="number" min="0" max="10" name={ `${mymeme.reaction_list[2].reaction_type}` }>{ `${mymeme.reaction_list[2].amount}` }</input>
        <label for={ `${mymeme.reaction_list[3].reaction_type}` }>{ `${mymeme.reaction_list[3].reaction_type}` }</label>
        <input type="number" min="0" max="10" name={ `${mymeme.reaction_list[3].reaction_type}` }>{ `${mymeme.reaction_list[3].amount}` }</input>
        <label for={ `${mymeme.reaction_list[4].reaction_type}` }>{ `${mymeme.reaction_list[4].reaction_type}` }</label>
        <input type="number" min="0" max="10" name={ `${mymeme.reaction_list[4].reaction_type}` }>{ `${mymeme.reaction_list[4].amount}` }</input>
        <label for={ `${mymeme.reaction_list[5].reaction_type}` }>{ `${mymeme.reaction_list[5].reaction_type}` }</label>
        <input type="number" min="0" max="10" name={ `${mymeme.reaction_list[5].reaction_type}` }>{ `${mymeme.reaction_list[5].amount}` }</input>
        <button onClick={onClick}>Submit changes</button>      
      </form>
    </div>
 
  );
}

export default SetOwnReactions;
  