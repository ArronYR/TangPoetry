'use strict';

const React = require('react');
const $ = require('jquery');

class Poetry extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isReady: false,
      url: props.url,
      poetry: props.poetry || []
    }
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
      isReady: false,
      url: nextProps.url,
      poetry: []
    });
    this.loadDataFromServer();
  }

  componentDidMount() {
    this.loadDataFromServer();
  }

  loadDataFromServer() {
    $.ajax({
      url: this.state.url,
      type: 'GET',
      dataType: 'json',
      data: {},
      success: data => {
        this.setState({
          poetry: data.poetry,
          isReady: true
        });
      },
      error: (xhr, status, err) => {
        console.log(err.toString());
      }
    });
  }

  render() {
    if (!this.state.isReady) {
      return (
        <div className="poetry-loader-wrapper">
          <div className="poetry-loader"></div>
        </div>
      )
    }

    let poetryArr = this.state.poetry.content.split(/[\u3002]+/);
    let poetryContent = poetryArr.map(function (content, idx) {
      if (content.length > 1) {
        return (
          <li key={idx} className="poetry-line">{content}。</li>
        );
      };
    });
    return (
      <div className="poetry-container">
        <div className="poetry-title">{this.state.poetry.title}</div>
        <div className="poetry-author">{this.state.poetry.poet.name}</div>
        <ul className="poetry-content">{poetryContent}</ul>
      </div>
    );
  }
}

module.exports = Poetry;
