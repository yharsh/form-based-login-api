import React, { Component } from 'react';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.css';
import { Register } from './components/Register';
import { Login } from './components/Login';
import { Welcome } from './components/Welcome';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

class App extends Component {
  render() {
    return <Router>
      <Switch>
        <Route exact path='/login' component={Login} />
        <Route exact path='/register' component={Register} />
        <Route path='/' component={Welcome} />
      </Switch>
    </Router>;
  }
}

export default App;