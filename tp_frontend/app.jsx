'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const ReactRouter = require('react-router');
const browserHistory = ReactRouter.browserHistory;
const Router = ReactRouter.Router;
const Route = ReactRouter.Route;
const IndexRoute = ReactRouter.IndexRoute;
const ReactCSSTransitionGroup = require('react-addons-css-transition-group');

const Main = require('./src/pages/main');
const About = require('./src/pages/about');
const Footer = require('./src/components/footer');
const Logo = require('./src/components/logo');
const NotFound = require('./src/components/404');
const Loader = require('./src/components/loader');

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