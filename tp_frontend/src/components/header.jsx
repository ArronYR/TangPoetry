'use strict';

const React = require('react');
let Link = require('react-router').Link;

class Header extends React.Component {
  render () {
    return (
      <nav className="header">
        <Link to="/">首页</Link>
        <Link to="/about">关于</Link>
        <a href="http://www.helloarron.com" target="_blank">作者</a>
      </nav>
    );
  }
}

module.exports = Header;