'use strict';

var React = require('react');

class About extends React.Component {
  render() {
    return (
      <div className="about">
        <h1>唐诗小站</h1>
        <p>A small site about Tang poetry and create by react.js</p>
        <ul className="actions vertical">
          <li><a href="https://github.com/ArronYR/TangPoetry" className="button special fit">Github source</a></li>
          <li><a href="http://blog.helloarron.com" className="button fit">Personal site</a></li>
        </ul>
        <p className="note">( If you like this project, please give a star ~~)</p>
      </div>
    )
  }
}

module.exports = About;
