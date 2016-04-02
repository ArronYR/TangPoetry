'use strict';

const React = require('react');

class Main extends React.Component {
  render() {
    return (
      <div className="about">
        <h1>唐诗小站</h1>
        <p>A small site about Tang poetry and create by react.js</p>
        <ul className="actions vertical">
          <li><a href="javascript:void(0);" className="button fit">Change</a></li>
        </ul>
      </div>
    );
  }
}

module.exports = Main;
