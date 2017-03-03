/**
 * PoetController
 *
 * @description :: Server-side logic for managing poets
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */
'use strict'

const Assert = require('../utils/assert.js');

module.exports = {

  findOne: function (req, res) {
    let poetId = parseInt(req.param('id'));
    if (isNaN(poetId)) {
      Assert(false, res, 400, 'Parameter error');
      return;
    };
    PoetService.findOne(poetId, function (poet) {
      res.ok({status: 200, msg: 'success', poet: poet});
    });
  },

  create: function (req, res) {
    res.notImplement();
  },

  update: function (req, res) {
    res.notImplement();
  },

  destroy: function (req, res) {
    res.notImplement();
  },

  count: function (req, res) {
    PoetService.count(function (count) {
      res.ok({status: 200, msg: 'success', count: count});
    });
  },

  rand: function (req, res) {
    PoetService.randOne(function (poet) {
      res.ok({status: 200, msg: 'success', poet: poet});
    });
  }

};

