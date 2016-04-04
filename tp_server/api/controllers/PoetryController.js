/**
 * PoetryController
 *
 * @description :: Server-side logic for managing Poetries
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */
'use strict'

const Assert = require('../utils/assert.js');

module.exports = {
  findOne: function (req, res) {
    let poetryId = parseInt(req.param('id'));
    if (isNaN(poetryId)) {
      Assert(false, res, 400, 'Parameter error');
      return;
    };
    PoetryService.findOne(poetryId, function (poetry) {
      res.ok({status: 200, msg: 'success', poetry: poetry});
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
    PoetryService.count(function (count) {
      res.ok({status: 200, msg: 'success', count: count});
    });
  },

  rand: function (req, res) {
    PoetryService.randOne(function (poetry) {
      res.ok({status: 200, msg: 'success', poetry: poetry});
    });
  }
};

