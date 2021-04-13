import {
  UPVOTE_HAPPY,
  UPVOTE_SAD,
  UPVOTE_FUNNY,
  UPVOTE_ANGRY,
  UPVOTE_SURPRISED,
  SET_REACTIONS,
} from '../constants/actionTypes';

export function setReactions(payload) {
  return {
    type: SET_REACTIONS,
    payload,
  }
}
export function upvoteHappy(payload) {
  return {
    type: UPVOTE_HAPPY,
    payload,
  };
}
export function upvoteSad(payload) {
  return {
    type: UPVOTE_SAD,
    payload,
  };
}
export function upvoteFunny(payload) {
  return {
    type: UPVOTE_FUNNY,
    payload,
  };
}
export function upvoteAngry(payload) {
  return {
    type: UPVOTE_ANGRY,
    payload,
  };
}
export function upvoteSurprised(payload) {
  return {
    type: UPVOTE_SURPRISED,
    payload,
  };
}