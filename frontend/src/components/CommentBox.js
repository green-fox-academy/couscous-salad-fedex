import React, { useState, useRef } from "react";
import cn from "classnames";
import "./CommentBox.css";
import { useDispatch } from 'react-redux';
import Cookies from 'universal-cookie';
import { saveComment } from '../actions/reactionActions';

const INITIAL_HEIGHT = 46;

const CommentBox = (props) => {
  const { meme } = props;
  const cookie = new Cookies();
  const token = cookie.get('accessToken');
  const backendUrl = process.env.REACT_APP_API_URL;
  const dispatch = useDispatch();
  const [isExpanded, setIsExpanded] = useState(false);
  const [commentValue, setCommentValue] = useState("");

  const outerHeight = useRef(INITIAL_HEIGHT);
  const textRef = useRef(null);
  const containerRef = useRef(null);

  const onExpand = () => {
		if (!isExpanded) {
      outerHeight.current = containerRef.current.scrollHeight;
      setIsExpanded(true);
    }
  }
  const onChange = (e) => {
    setCommentValue(e.target.value);
  }
  const onClose = () => {
    setCommentValue("");
    setIsExpanded(false);
  };
  const onSubmit = (e) => {
    e.preventDefault();

    const url = `${backendUrl}/comment/${meme.meme_id}`;

    fetch(url, {
      method: 'PUT',
      headers: {
        authorization: `Bearer ${token}`,
      },
      body: {
        comment: commentValue,
      },
    })
      .then(response => {
        if (response.status === 200) {
          return response;
        }
        throw new Error('Connection failed!');
      })
      .then(() => {
        dispatch(saveComment(commentValue)) 
      })
      .catch(err => {
        console.log(err);
      });
  };
  

  return (
	<form
    onSubmit={onSubmit}
    ref={containerRef}
    className={cn("comment-box", {
      expanded: isExpanded,
      collapsed: !isExpanded,
			modified: commentValue.length > 0,
    })}
    style={{
      minHeight: isExpanded ? outerHeight.current : INITIAL_HEIGHT
    }}
  >
     <div className="header">
      <div className="user">
        <span>{ token.username }</span>
      </div>
      </div>
      
	<label htmlFor="comment">Your comment</label>
    <textarea
      ref={textRef}
      onClick={onExpand}
      onFocus={onExpand}
      onChange={onChange}
      className="comment-field"
      placeholder="Your comment"
      value={commentValue}
      name="comment"
      id="comment"
      />
      <textarea />

		<div className="actions">
      <button type="button" className="cancel" onClick={onClose}>
        Cancel
      </button>
      <button type="submit" disabled={commentValue.length < 1}>
        Respond
      </button>
    </div>
	</form>
    
    
);
};

export default CommentBox;
