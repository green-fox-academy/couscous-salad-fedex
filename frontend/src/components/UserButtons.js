import React from 'react';
import { useDispatch } from 'react-redux';
import { upvoteAction } from '../actions/memeActions';

function UserButtons(props) {
  const { meme } = props;
  const dispatch = useDispatch();
  const backendUrl = process.env.REACT_APP_BACKENDURL;
  const url = `${backendUrl}/reaction/${meme.meme_id}`;

  const upvote = () => {
    fetch(url, {
      method: 'PUT',
      headers: {
        authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: {
        reaction_type: 'HAPPY'
      },
    })
      .then(response => {
        if (response.status === 200) {
          return response;
        }
        throw new Error('Connection failed!');
      })
      .then(() => dispatch(upvoteAction(meme.meme_id)))
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <div className="Reactions">
      <div className="happy function">
        <span onClick={ upvote }>Happy { meme.happy }</span>
      </div>
      <div className="sad function">
        <span onClick={ upvote }>Sad { meme.sad }</span>
      </div>
      <div className="funny function">
        <span onClick={ upvote }>Funny { meme.funny }</span>
      </div>
      <div className="angry function">
        <span onClick={ upvote }>Angry { meme.angry }</span>
      </div>
      <div className="surprised function">
        <span onClick={ upvote }>Surprised { meme.surprised }</span>
      </div> 
    </div>
  );
}

export default UserButtons;
