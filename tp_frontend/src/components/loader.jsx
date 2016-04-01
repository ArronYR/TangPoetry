'use strict';

const React = require('react');
const $ = require('jquery');

class Loader extends React.Component {
  constructor(props) {
    this.state = {
      time: parseInt(props.time) || 2000,
      display: false
    }
    console.log(this.state);
  }

  componentDidMount() {
    setTimeout(() => {
      console.log(this.state.time);
      $('#app').addClass('loaded');
    }, this.state.time)
  }

  render() {
    return (
      <div className="loader-wrapper">
        <div className="loader"></div>
        <div className='loader-left-wrapper'></div>
        <div className='loader-right-wrapper'></div>
      </div>
    );
  }
}

module.exports = Loader;