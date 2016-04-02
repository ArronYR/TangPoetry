'use strict';

const React = require('react');

let Poetry = require('../components/poetry');

class Main extends React.Component {
  constructor(props) {
    this.state = {
      poetry: props.poetry || []
    }
  }

  render() {
    return (
      <div className="about">
        <p>A small site about Tang poetry and create by react.js</p>
        <Poetry url='http://localhost:1337/poet/rand' />
        <ul className="actions vertical">
          <li><a href="javascript:void(0);" className="button fit">Change</a></li>
        </ul>
        <p className="note">( Click the Change button to get other poetry ~~)</p>
      </div>
    );
  }
}

module.exports = Main;
