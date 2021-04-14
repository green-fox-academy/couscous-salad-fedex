import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './pages/Login/Login';
import Register from './pages/Register/Register';
import NotFound from './pages/NotFound/NotFound';
import './App.css';
import Header from './parts/Header/Header';
import SetOwnReactions from './components/SetOwnReactions';

import Memelist from './pages/Memes/Memelist';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Route exact path="/"></Route>
        <Switch>
          <Route exact path="/" className="memelist" component={Memelist}/>
          <Route exact path="/setreaction/:id" className="memereaction" component={SetOwnReactions}/>
          <Route exact path="/login">
            <Login />
          </Route>
          <Route exact path="/register">
            <Register />
          </Route>
          <Route exact path="*">
            <NotFound />
          </Route>
        </Switch>
          <Switch>
            <Route exact path="/" className="memelist" component={Memelist} />
          </Switch>
       
      </div>
    </Router>
  );
}

export default App;
