import { React } from 'react';
import UserButtons from './UserButtons';
import Cookies from 'universal-cookie';
import CommentBox from './CommentBox';

function Meme(props) {
  const { meme } = props;
  const cookie = new Cookies();
  const token = cookie.get('Meme-token');

  return (
    <div className="meme-info">
      <div className="meme-id">{meme.meme_id}</div>
      <div className="meme-url" img src={ meme.meme_path } alt={meme.meme_id}></div>
      {token ? (
        <div>
          <UserButtons meme={ meme } />
          <CommentBox meme={ meme } />
        </div>
      ) : ('')}
    </div>
  );
}

export default Meme;
