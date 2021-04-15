import {
  SAVE_COMMENT,
  SET_REACTIONS
} from '../constants/actionTypes';

const INITIAL_STATE = {
  funny: 0,
  sad: 0,
  surprised: 0,
  angry: 0,
  erotic: 0,
  happy: 0,
  comments: [],
};

const reactionReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case SAVE_COMMENT:
      return {
        comments: action.payload,
      }
    case SET_REACTIONS:
      return {
        funny : action.payload.funny.reaction_list[5].count,
        sad : action.payload.sad.reaction_list[5].count,
        surprised: action.payload.surprised.reaction_list[5].count,
        angry : action.payload.angry.reaction_list[5].count,
        erotic: action.payload.reaction_list[5].count,
        happy : action.payload.reaction_list[5].count,
      }
    
    default:
      return state;
  }
};
export default reactionReducer;