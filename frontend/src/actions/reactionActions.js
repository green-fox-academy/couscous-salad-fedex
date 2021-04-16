import {
  SAVE_COMMENT,
  SET_REACTIONS,
  SET_MY_REACTIONS,
} from '../constants/actionTypes';

export function saveComment(payload) {
  return {
    type: SAVE_COMMENT,
    payload,
  }
}

export function setReactions(payload) {
  return {
    type: SET_REACTIONS,
    payload,
  }
}
export function setMyReactions(payload) {
  return {
    type: SET_MY_REACTIONS,
    payload,
  }
}