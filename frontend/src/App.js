import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './pages/Login/Login';
import Register from './pages/Register/Register';
import NotFound from './pages/NotFound/NotFound';
import './App.css';
import Header from './parts/Header/Header';
import RootRedirect from './parts/RootRedirect/RootRedirect';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Route path="/">
          <RootRedirect />
        </Route>
        <Switch>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/register">
            <Register />
          </Route>
          <Route path="*">
            <NotFound />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
