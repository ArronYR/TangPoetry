'use strict';

const React = require('react');
const $ = require('jquery');

class Poetry extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      url: props.url,
      poetry: props.poetry || []
    }
  }

  componentDidMount() {
    this.loadDataFromServer();
  }

  loadDataFromServer() {
    $.ajax({
      url: 'http://localhost:1337/poetry/1',
      type: 'GET',
      dataType: 'json',
      data: {},
      success: data => {
          this.setState({poetry: data.poetry});
      },
      error: (xhr, status, err) => {
          console.log(err.toString());
      }
    });
  }

  render() {
    console.log(this.state.poetry);
    var poetryNode = '';
    if (!this.state.poetry) {
      return (
        <div>{this.state.poetry.title}</div>
      );
    }else{
      return (
        <div>Error</div>
      )
    }
  }
}

module.exports = Poetry;
