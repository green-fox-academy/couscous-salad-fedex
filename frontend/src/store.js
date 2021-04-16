import { createStore, applyMiddleware, combineReducers } from 'redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
import loginReducer from './reducers/loginReducer';
import memeReducer from './reducers/memeReducer';
import reactionReducer from './reducers/reactionReducer'

const rootReducer = combineReducers({
  login: loginReducer,
  memeState: memeReducer,
  reactionState: reactionReducer
});

const store = createStore(
  rootReducer,
  composeWithDevTools(applyMiddleware(thunk))
);

export default store;
