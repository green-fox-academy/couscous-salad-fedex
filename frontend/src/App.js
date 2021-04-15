import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './pages/Login/Login';
import Register from './pages/Register/Register';
import NotFound from './pages/NotFound/NotFound';
import './App.css';
import Header from './parts/Header/Header';

import Memelist from './pages/Memelist'

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Route exact path="/"></Route>
        <Switch>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/register">
            <Register />
          </Route>
          <Route exact path="*">
            <NotFound />
          </Route>
        </Switch>
          <Switch>
            <Route exact path="/" component={Memelist} />
          </Switch>
       
      </div>
    </Router>
  );
}

export default App;
