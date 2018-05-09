import React from "react";
import PalindromeForm from "./PalindromeForm";

export default class PalindromeApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            palindromePage: {
                palindromes: [],
                totalPalindromes: 0
            },
            currentPageNumber: 0
        };
    }

    componentDidMount(){
        this.fetchPalindromes();
    }

    fetchPalindromes() {
        fetch(this.props.apiServer+'/palindrome?pageNumber='+this.state.currentPageNumber+'&pageSize='+this.props.pageSize)
            .then(function (response) {
                return response.json();
            })
            .then((palindromePage) => {
                var newState = this.state
                newState.palindromePage = palindromePage
                this.setState(newState)
            });
    }

    getPageNumbers(){
        var pageNumbers = []
        const pageCount = Math.ceil(this.state.palindromePage.totalPalindromes / this.props.pageSize)
        for(var i=1 ; i <= pageCount ; i++){
            pageNumbers.push(i)
        }
        return pageNumbers
    }

    render() {
        return (
            <div>
                <PalindromeForm apiServer={this.props.apiServer} fetchPalindromes={()=>this.fetchPalindromes()}/>
                <p>List of valid palindromes</p>
                <ul>
                    {this.state.palindromePage.palindromes.map(palindrome => <li>{palindrome}</li>)}
                </ul>
                {this.getPageNumbers().map(pageNumber => <span><a href="#" onClick={()=>this.switchPage(pageNumber-1)}>{pageNumber}</a>&nbsp;|&nbsp;</span>)}
            </div>

        );
    }

    switchPage(pageNumber) {
        var newState = this.state
        newState.currentPageNumber = pageNumber
        this.setState(newState)
        this.fetchPalindromes()
    }
}