'use strict';

var React = require('react');
var IndexLink = require('react-router').IndexLink;
var Link = require('react-router').Link;

class Footer extends React.Component {
  render() {
    return (
      <nav className="footer">
        <ul className="nav-bar">
          <li><IndexLink to="/" activeClassName={"active"}>首页</IndexLink></li>
          <li><Link to="/about" activeClassName={"active"}>关于</Link></li>
          <li><a href="http://www.helloarron.com" target="_blank">作者</a></li>
        </ul>
      </nav>
    )
  }
}

module.exports = Footer;
