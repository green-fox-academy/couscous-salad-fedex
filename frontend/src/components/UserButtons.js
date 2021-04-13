import React from 'react';
import { useDispatch } from 'react-redux';
import {
  upvoteHappy,
  upvoteSad,
  upvoteFunny,
  upvoteAngry,
  upvoteSurprised
} from '../actions/reactionActions';
import Cookies from 'universal-cookie'

function UserButtons(props) {
 
  const { meme } = props;
  const dispatch = useDispatch();
  const backendUrl = process.env.REACT_APP_BACKENDURL;
  const url = `${backendUrl}/reaction/${meme.meme_id}`;
  const cookie = new Cookies();
  const token = cookie.get('accessToken');

  const upvote = (event) => {
    const reaction = event.target.getAttribute('class');
    fetch(url, {
      method: 'PUT',
      headers: {
        authorization: `Bearer ${token}`,
      },
      body: {
        reaction_type: reaction,
      },
    })
      .then(response => {
        if (response.status === 200) {
          return response;
        }
        throw new Error('Connection failed!');
      })
      .then(() => {
        if (reaction === 'happy') {
        dispatch(upvoteHappy(meme.meme_id))
        } else if (reaction === 'sad') {
        dispatch(upvoteSad(meme.meme_id))
        } else if (reaction === 'funny') {
        dispatch(upvoteFunny(meme.meme_id))
        } else if (reaction === 'angry') {
        dispatch(upvoteAngry(meme.meme_id))
        } else if (reaction === 'surprised') {
        dispatch(upvoteSurprised(meme.meme_id))
        }
      })
      
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <div className="Reactions">
      <div className="happy">
        <span onClick={ upvote }>Happy { meme.happy }</span>
      </div>
      <div className="sad">
        <span onClick={ upvote }>Sad { meme.sad }</span>
      </div>
      <div className="funny">
        <span onClick={ upvote }>Funny { meme.funny }</span>
      </div>
      <div className="angry">
        <span onClick={ upvote }>Angry { meme.angry }</span>
      </div>
      <div className="surprised">
        <span onClick={ upvote }>Surprised { meme.surprised }</span>
      </div> 
    </div>
  );
}

export default UserButtons;
