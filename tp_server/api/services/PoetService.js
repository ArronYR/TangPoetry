'use strict'

const Co = require('co');
const Assert = require('../utils/assert.js');

module.exports = {
  findOne: function (poetId, cb) {
    Poet.findOne({id: poetId}).populate('poetries').exec(function (err, poet){
      if (err) {
        throw new Error(err.message);
        return;
      }
      if (!poet) {poet = {}};
      cb(poet);
    })
  },

  count: function (cb){
    Co(function* () {
      let count = yield Poet.count();
      cb(count);
    }).catch(err => {
      sails.log.error(err.message);
    });
  }
}