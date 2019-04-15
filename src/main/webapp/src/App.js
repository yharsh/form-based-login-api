import React, { Component } from 'react';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.css';
import { Register } from './components/Register';
import { Login } from './components/Login';
import { Welcome } from './components/Welcome';
import { HashRouter as Router, Route, Switch } from 'react-router-dom';

class App extends Component {
  render() {
    return <Router>
      <Switch><Route path='/login' component={Login} />
        <Route path='/register' component={Register} />
        <Route exact path='/' component={Welcome} />
      </Switch>
    </Router>;
  }
}

export default App;
