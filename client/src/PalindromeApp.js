import React from "react";
import PalindromeForm from "./PalindromeForm";

export default class PalindromeApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {palindromes: []};
    }

    componentDidMount(){
        this.fetchPalindromes();
    }

    fetchPalindromes() {
        fetch(this.props.apiServer+'/palindrome?pageNumber=0')
            .then(function (response) {
                return response.json();
            })
            .then((palindromes) => {
                this.setState({palindromes: palindromes})
            });
    }

    render() {
        return (
            <div>
                <PalindromeForm apiServer={this.props.apiServer} fetchPalindromes={()=>this.fetchPalindromes()}/>
                <p>List of valid palindromes</p>
                <ul>
                    {this.state.palindromes.map(palindrome => <li>{palindrome}</li>)}
                </ul>
            </div>

        );
    }
}