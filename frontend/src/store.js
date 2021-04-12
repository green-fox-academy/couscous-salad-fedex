import { createStore, applyMiddleware, combineReducers } from 'redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
import loginReducer from './reducers/loginReducer';
import buildingReducer from './reducers/buildingReducer';
import leaderboardReducer from './reducers/leaderboardReducer';

const rootReducer = combineReducers({
  login: loginReducer,
  buildings: buildingReducer,
  leaderboard: leaderboardReducer,
});

const store = createStore(
  rootReducer,
  composeWithDevTools(applyMiddleware(thunk))
);

export default store;
