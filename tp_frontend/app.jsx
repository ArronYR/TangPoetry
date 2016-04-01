'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
let ReactRouter = require('react-router');
let browserHistory = ReactRouter.browserHistory;
let Router = ReactRouter.Router;
let Route = ReactRouter.Route;
let IndexRoute = ReactRouter.IndexRoute;
let ReactCSSTransitionGroup = require('react-addons-css-transition-group');

let Main = require('./src/pages/main');
let About = require('./src/pages/about');
let Header = require('./src/components/header');
let NotFound = require('./src/components/404');
let Loader = require('./src/components/loader');

require('./src/style/app.css');

// 应用入口
class App extends React.Component {
  render() {
    return (
      <div className="app">
        <Loader time/>
        <Header />
        <ReactCSSTransitionGroup
          component="div"
          transitionName="page"
          transitionEnterTimeout={500}
          transitionLeaveTimeout={500}
        >
          {React.cloneElement(this.props.children, {
            key: this.props.location.pathname
          })}
        </ReactCSSTransitionGroup>
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
        <Route path="*" component={NotFound}/>
      </Route>
    </Router>
  ),
  document.getElementById('app')
)