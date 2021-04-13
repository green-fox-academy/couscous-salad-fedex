import {
  LOAD_USER_TOKEN,
  LOAD_USER_TOKEN_PAYLOAD,
} from '../constans/actionTypes';

export function loadUserToken(payload) {
  return {
    type: LOAD_USER_TOKEN,
    payload,
  };
}

export function loadUserTokenPayload(payload) {
  return {
    type: LOAD_USER_TOKEN_PAYLOAD,
    payload,
  };
}
