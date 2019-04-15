import React from 'react';
import axios from 'axios';
import { Alert } from './Alert';
import { USER_URL } from '../services/constants';
import { Link } from 'react-router-dom';
import { ReCaptcha, loadReCaptcha } from 'react-recaptcha-google'

export class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.register = this.register.bind(this);
        this.onInputChange = this.onInputChange.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
    }

    componentDidMount() {
        loadReCaptcha();
    }

    onLoadRecaptcha() {
        if (this.captchaDemo) {
            this.captchaDemo.reset();
            this.captchaDemo.execute();
        }
    }

    verifyCallback(recaptchaToken) {
        this.setState({ ...this.state, recaptchaToken });
    }

    errorReponseHandler(e) {
        if (e.response) {
            let msg;
            switch (e.response.status) {
                case 404:
                    msg = 'Unable to connect to server';
                    break;
                default:
                    msg = 'Registration failed: ' + e.response.data.message;
            }
            this.setState({
                ...this.state, msg
            });
        } else if (e.request) {
            console.log('Request: ' + e.request);
        } else {
            console.log('Error: ' + e.message);
        }
    }

    register(e) {
        this.setState({ ...this.state, error: null });
        axios.post(USER_URL, this.state)
            .then(res => {
                this.setState({ ...this.state, created: true });
            }).catch(e => {
                this.errorReponseHandler(e);
            });
    }

    onInputChange(fieldName) {
        return (e) => this.setState({ ...this.state, [fieldName]: e.target.value });
    }

    render() {
        return <div className='container app-form'>
            <h3>Regiser User</h3>
            <Alert msg={this.state.msg} />
            {this.state.created && <div className='alert alert-success'>success: Click <Link to='/login'>here</Link> to login</div>}
            <input type='text' className='form-control' placeholder='Email id' onChange={this.onInputChange('emailId')} />
            <input type='password' className='form-control' placeholder='Password' onChange={this.onInputChange('password')} />
            <input type='text' className='form-control' placeholder='Name' onChange={this.onInputChange('name')} />
            <input type='text' className='form-control' placeholder='Phone number' onChange={this.onInputChange('phoneNumber')} />
            <ReCaptcha
                ref={(el) => { this.captchaDemo = el; }}
                size="normal"
                data-theme="dark"
                render="explicit"
                sitekey="6LehMJ4UAAAAAJC16E-xEP64cEEMNl9ya6nh4kL_"
                onloadCallback={this.onLoadRecaptcha}
                verifyCallback={this.verifyCallback}
            />
            <button className='form-control btn btn-primary' onClick={this.register}>Register</button>
        </div>;
    }
}
