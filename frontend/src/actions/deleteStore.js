import { DELETE_STORE } from '../constants/actionTypes';

function deleteStoreAction(payload) {
  return {
    type: DELETE_STORE,
    payload,
  };
}

export default deleteStoreAction;
