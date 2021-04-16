import { GET_MEMES } from '../constants/actionTypes';

export function getMemes(payload) {
  return {
    type: GET_MEMES,
    payload,
  };
}

