'use strict';

const React = require('react');

let Poetry = require('../components/poetry');

class Main extends React.Component {
  constructor(props) {
    this.state = {
      originUrl: 'http://localhost:1337/poetry/rand',
      url: 'http://localhost:1337/poetry/rand?t=' + Math.random()
    }
  }

  handleRand(e) {
    e.preventDefault();
    this.setState({
      url: this.state.originUrl + '?t=' + Math.random()
    });
  }

  render() {
    return (
      <div className="home">
        <Poetry url={this.state.url} />
        <ul className="actions vertical">
          <li><a href="javascript:void(0);" className="button fit" onClick={e => {this.handleRand(e)}}>Change</a></li>
        </ul>
        <p className="note">( Click the Change button to get other poetry ~~)</p>
      </div>
    );
  }
}

module.exports = Main;
