import {
  DELETE_STORE,
  LOAD_USER_TOKEN,
  LOAD_USER_TOKEN_PAYLOAD,
} from '../constants/actionTypes';

const initialState = {
  token: '',
  data: {},
};

function loginReducer(state = initialState, action) {
  if (action.type === LOAD_USER_TOKEN) {
    return {
      ...state,
      token: action.payload,
    };
  }
  if (action.type === LOAD_USER_TOKEN_PAYLOAD) {
    return {
      ...state,
      data: action.payload,
    };
  }
  if (action.type === DELETE_STORE) {
    return initialState;
  }
  return state;
}
export default loginReducer;
