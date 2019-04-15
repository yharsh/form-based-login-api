import React from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import { LOGIN_URL } from '../services/constants';
import { Alert } from './Alert';
import { setLoggedIn } from '../services/login';

export class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.login = this.login.bind(this);
        this.onInputChange = this.onInputChange.bind(this);
    }

    errorReponseHandler(e) {
        if (e.response) {
            let error;
            switch (e.response.status) {
                case 404:
                    error = 'Unable to connect to server';
                    break;
                default:
                    error = e.response.data.message;
            }
            this.setState({
                ...this.state, error
            });
        } else if (e.request) {
            console.log('Request: ' + e.request);
        } else {
            console.log('Error: ' + e.message);
        }
    }

    login(e) {
        this.setState({ ...this.state, error: null });
        const formData = new FormData();
        formData.set('username', this.state.username);
        formData.set('password', this.state.password);
        axios.post(LOGIN_URL, new URLSearchParams(formData))
            .then(res => {
                setLoggedIn(true);
                this.props.history.push('/');
            }).catch(e => {
                this.errorReponseHandler(e);
            });
    }

    onInputChange(fieldName) {
        return (e) => this.setState({ ...this.state, [fieldName]: e.target.value });
    }

    render() {
        return <div className='container app-form'>
            <h3>Login</h3>
            <Alert msg={this.state.error} />
            <input type='text' className='form-control' placeholder='Email id' onChange={this.onInputChange('username')} />
            <input type='password' className='form-control' placeholder='Password' onChange={this.onInputChange('password')} />
            <button className='form-control btn btn-primary' onClick={this.login}>Login</button>
            Not registered yet? Click <Link to='/register'>here</Link> to register.
        </div>;
    }
}
