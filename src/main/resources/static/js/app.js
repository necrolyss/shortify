class ShortLinkForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {inputUrl: '', customPath: ''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        if (event.target.name == 'inputUrl') {
            this.setState({
                    inputUrl: event.target.value,
                    customPath: this.state.customPath
                }
            );
        } else {
            this.setState({
                    inputUrl: this.state.inputUrl,
                    customPath: event.target.value,
                }
            );
        }
    }

    handleSubmit(event) {
        console.log('A form was submitted, url = ' + this.state.inputUrl + ', custom path = ' + this.state.customPath);

        fetch('/shortLink', {
            method: "POST",
            body: JSON.stringify(this.state)
        }).then(function (response) {
            return response.text();
        }).then(function (whatever) {
            let shortLinkPreview = document.getElementById('shortLinkPreview');
            shortLinkPreview.innerHTML = whatever;
        }).catch(alert);

        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Destination URL *:
                    <input name="inputUrl" type="text" value={this.state.inputUrl} onChange={this.handleChange} />
                </label>
                <label>
                    Preferred ShortLink:
                    <input name="customPath" type="text" value={this.state.customPath} onChange={this.handleChange} />
                </label>
                <input type="submit" value="Generate!" />
                <div id="shortLinkPreview"/>
            </form>
        );
    }
}

ReactDOM.render(
    <ShortLinkForm />,
    document.getElementById('root')
);