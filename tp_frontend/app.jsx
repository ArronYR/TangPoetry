'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
let ReactRouter = require('react-router');
let browserHistory = ReactRouter.browserHistory;
let Router = ReactRouter.Router;
let Route = ReactRouter.Route;
let IndexRoute = ReactRouter.IndexRoute;
let StateMixin = ReactRouter.State;
let DefaultRoute = ReactRouter.DefaultRoute;

let Main = require('./src/pages/main');
let About = require('./src/pages/about');

let Header = require('./src/components/header');
let NoMatch = require('./src/components/404');

// 应用入口
class App extends React.Component {
  render() {
    return (
      <div className="app">
        <Header />
        <div className="content">
          {this.props.children}
        </div>
      </div>
    );
  }
}

ReactDOM.render(
  (
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={Main}/>
        <Route path="about" component={About}/>
        <Route path="*" component={NoMatch}/>
      </Route>
    </Router>
  ),
  document.getElementById('app')
)