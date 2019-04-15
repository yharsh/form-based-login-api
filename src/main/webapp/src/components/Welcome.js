import { loggedIn } from '../services/login';
import { Redirect } from 'react-router-dom';
import React from 'react';
import axios from 'axios';
import { USER_URL } from '../services/constants';
import { Alert } from './Alert';

export class Welcome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }
    componentDidMount() {
        axios.get(USER_URL).then(res => {
            this.setState({
                ...res.data
            });
        }).catch(e => {
            this.setState({ msg: JSON.stringify(e) });
        });
    }

    render() {
        return loggedIn() ? <div className='app-form'>
            <h3>Your credentials:</h3>
            <Alert msg={this.state.msg} />
            <table className='table'>
                <tbody>
                    <tr>
                        <th scope="row">Email id</th>
                        <td>{this.state.emailId}</td>
                    </tr>
                    <tr>
                        <th scope="row">Password</th>
                        <td>{this.state.password}</td>
                    </tr>
                    <tr>
                        <th scope="row">Name</th>
                        <td>{this.state.name}</td>
                    </tr>
                    <tr>
                        <th scope="row">Phone number</th>
                        <td>{this.state.phoneNumber}</td>
                    </tr>
                </tbody>
            </table>
        </div> : <Redirect to='login' />;
    }
}
