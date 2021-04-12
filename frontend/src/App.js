import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Header from './parts/Header/Header';
import Login from './pages/Login/Login';
import MemeGenerator from './pages/MemeGenerator/MemeGenerator';
import NotFound from './pages/NotFound/NotFound';
import Register from './pages/Register/Register';
import SetOwnReactions from './components/SetOwnReactions';
import './App.css';

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
          <Route path="/memegenerator">
            <MemeGenerator />
          </Route>
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
            <Route exact path="/" className="memelist" component={Memelist} />
          </Switch>

      </div>
    </Router>
  );
}

export default App;
