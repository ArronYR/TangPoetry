'use strict';

var React = require('react');
var ReactDOM = require('react-dom');
var ReactRouter = require('react-router');
var browserHistory = ReactRouter.browserHistory;
var Router = ReactRouter.Router;
var Route = ReactRouter.Route;
var IndexRoute = ReactRouter.IndexRoute;
var ReactCSSTransitionGroup = require('react-addons-css-transition-group');

var Main = require('./src/pages/main');
var About = require('./src/pages/about');
var Footer = require('./src/components/footer');
var Logo = require('./src/components/logo');
var NotFound = require('./src/components/404');
var Loader = require('./src/components/loader');

// require('./src/style/app.css');

// 应用入口
class App extends React.Component {
  render() {
    return (
      <div className="app">
        <Loader time={2000} />
        <div className="content">
          <Logo />
          {React.cloneElement(this.props.children, {
            key: this.props.location.pathname
          })}
        </div>
        <Footer />
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