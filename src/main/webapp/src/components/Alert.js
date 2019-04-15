import React from 'react';

export const Alert = ({ msg, type = 'danger' }) => msg ? <div className={'alert alert-' + type}>{msg}</div> : <div />;
