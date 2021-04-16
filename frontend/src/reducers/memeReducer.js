import {
  GET_MEMES
} from '../constants/actionTypes';

const INITIAL_STATE = {
  memes: [],
};

const memeReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case GET_MEMES:
      return {
        memes: action.payload,
      };
    default:
      return state;
  }
};
export default memeReducer;
