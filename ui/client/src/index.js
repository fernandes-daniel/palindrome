import React from "react";
import ReactDOM from 'react-dom'
import PalindromeApp from './PalindromeApp'

ReactDOM.render(
    <PalindromeApp apiServer="http://localhost:8080" pageSize={2}/>,
    document.getElementById('root')
);