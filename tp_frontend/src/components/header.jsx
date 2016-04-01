'use strict';

const React = require('react');
let IndexLink = require('react-router').IndexLink;
let Link = require('react-router').Link;

const ACTIVE = { color: 'red' };

class Header extends React.Component {
  render () {
    return (
      <nav className="header">
        <IndexLink to="/" activeClassName={"active"} activeStyle={ACTIVE}>首页</IndexLink>
        <Link to="/about" activeClassName={"active"} activeStyle={ACTIVE}>关于</Link>
        <a href="http://www.helloarron.com" target="_blank">作者</a>
      </nav>
    );
  }
}

module.exports = Header;