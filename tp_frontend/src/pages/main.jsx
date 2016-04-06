'use strict';

const React = require('react');

let Poetry = require('../components/poetry');

class Main extends React.Component {
  constructor(props) {
    this.state = {
      random: Math.random()
    }
  }

  handleRand(e) {
    e.preventDefault();
    this.setState({
      random: Math.random()
    });
  }

  render() {
    return (
      <div className="home">
        <Poetry random={this.state.random} />
        <ul className="actions vertical">
          <li><a href="javascript:void(0);" className="button fit" onClick={e => {this.handleRand(e)}}>Change</a></li>
        </ul>
        <p className="note">( Click the Change button to get next poetry ~~)</p>
      </div>
    );
  }
}

module.exports = Main;
