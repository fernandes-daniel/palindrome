import React from "react";
import ReactDOM from 'react-dom'
import PalindromeApp from './PalindromeApp'

ReactDOM.render(
    <PalindromeApp apiServer="http://localhost:8080"/>,
    document.getElementById('root')
);