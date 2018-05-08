import React from "react";

export default class PalindromeForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', savedMessage: ''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        fetch(this.props.apiServer+'/palindrome', {method: 'POST', body: this.state.value})
            .then(function (response) {
                return response.json();
            })
            .then((response) => {
                this.flashSavedMessage(response);
                this.props.fetchPalindromes();
            });

        event.preventDefault();
    }

    flashSavedMessage(response) {
        if (response.isPalindrome) {
            this.setSavedMessage("Palindrome Has been Saved")
        } else {
            this.setSavedMessage("Not a Palindrome")
        }

        setTimeout(() => this.setSavedMessage(""), 1000)
    }

    setSavedMessage(message) {
        const newState = this.state
        newState.savedMessage = message
        this.setState(newState)
    }

    render() {
        return (
            <div>
                <p>Please type a string and press enter to see if it is a <strong>Palindrome</strong></p>
                <form onSubmit={this.handleSubmit}>
                    <label>Palindrome:</label>&nbsp;
                    <input type="text" value={this.state.value} onChange={this.handleChange}/>&nbsp;
                    <input type="submit" value="Submit"/>&nbsp;
                    <span>{this.state.savedMessage}</span>
                </form>
            </div>
        );
    }
}