import { DELETE_STORE } from '../constans/actionTypes';

function deleteStoreAction(payload) {
  return {
    type: DELETE_STORE,
    payload,
  };
}

export default deleteStoreAction;
