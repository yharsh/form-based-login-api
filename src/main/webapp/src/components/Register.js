import React from 'react';
import axios from 'axios';
import { Alert } from './Alert';
import { USER_URL } from '../services/constants';
import { Link } from 'react-router-dom';
import { ReCaptcha, loadReCaptcha } from 'react-recaptcha-google'

export class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            validationResult: {
                valid: true
            }
        };
        this.register = this.register.bind(this);
        this.onInputChange = this.onInputChange.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
        this.validate = this.validate.bind(this);
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
        const validationResult = this.validate();
        console.log(validationResult);
        if (validationResult.valid) {
            this.setState({ ...this.state, error: null, validationResult });
            axios.post(USER_URL, this.state)
                .then(res => {
                    this.setState({ ...this.state, created: true });
                }).catch(e => {
                    this.errorReponseHandler(e);
                });
        } else {
            this.setState({ ...this.state, validationResult });
        }
    }

    onInputChange(fieldName) {
        return (e) => this.setState({ ...this.state, [fieldName]: e.target.value });
    }

    validate() {
        const validationResult = {
            valid: true
        };
        //Validate email id
        const emailPattern = /^\w+([\\.-]?\w+)*@\w+([\\.-]?\w+)*(\.\w{2,3})+$/;
        console.log(this.state);
        if (!this.state.emailId || !this.state.emailId.match(emailPattern)) {
            validationResult.valid = false;
            validationResult.emailId = 'Invalid email id';
        }
        const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20}$/;
        if (!this.state.password || !this.state.password.match(passwordPattern)) {
            validationResult.valid = false;
            validationResult.passsword = 'Password should contain atleast one upppercase, one lowercase, one special(@, #, $, %), one numeric character and should be of length 6 to 20 characters';
        }
        //Indian format
        const phoneNumberPattern = /^[0-9]{10}$/;
        if (this.state.phoneNumber && this.state.phoneNumber.length > 0 && !this.state.phoneNumber.match(phoneNumberPattern)) {
            validationResult.valid = false;
            validationResult.phoneNumber = 'Invalid phone number';
        }
        const recaptchaPattern = /^[A-Za-z0-9_-]+$/;
        if (!this.state.recaptchaToken || !this.state.recaptchaToken.match(recaptchaPattern)) {
            validationResult.valid = false;
            validationResult.recaptchaToken = 'Invalid captcha';
        }
        return validationResult;
    }

    render() {
        return <div className='container app-form'>
            <h3>Regiser User</h3>
            <Alert msg={this.state.msg} />
            {this.state.created && <div className='alert alert-success'>Success: click <Link to='/login'>here</Link> to login</div>}
            <input type='text' className='form-control' placeholder='Email id' onChange={this.onInputChange('emailId')} />
            <Alert msg={this.state.validationResult.emailId} />
            <input type='password' className='form-control' placeholder='Password' onChange={this.onInputChange('password')} />
            <Alert msg={this.state.validationResult.passsword} />
            <input type='text' className='form-control' placeholder='Name' onChange={this.onInputChange('name')} />
            <input type='text' className='form-control' placeholder='Phone number' onChange={this.onInputChange('phoneNumber')} />
            <Alert msg={this.state.validationResult.phoneNumber} />
            <ReCaptcha
                ref={(el) => { this.captchaDemo = el; }}
                size="normal"
                data-theme="dark"
                render="explicit"
                sitekey="6LehMJ4UAAAAAJC16E-xEP64cEEMNl9ya6nh4kL_"
                onloadCallback={this.onLoadRecaptcha}
                verifyCallback={this.verifyCallback}
            />
            <Alert msg={this.state.validationResult.recaptchaToken} />
            <button className='form-control btn btn-primary' onClick={this.register}>Register</button>
        </div>;
    }
}
