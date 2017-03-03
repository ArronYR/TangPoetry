/**
 * 201 (CREATED) Response
 *
 * Usage:
 * return res.created();
 * return res.created(data);
 * return res.created(data, 'auth/login');
 *
 * @param  {Object} data
 * @param  {String|Object} options
 *          - pass string to render specified view
 */
const LogRequest = require('../utils/logRequest.js');

module.exports = function created (data, options) {

  // Get access to `req`, `res`, & `sails`
  var req = this.req;
  var res = this.res;
  var sails = req._sails;

  sails.log.silly('res.created() :: Sending 201 ("CREATED") response');

  // Set status code
  res.status(201);

  // Log request
  LogRequest(req, res);

  return res.jsonx({status: 201, msg: 'Created.'});
};
