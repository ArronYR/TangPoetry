'use strict'

const Co = require('co');
const Assert = require('../utils/assert.js');

module.exports = {
  findOne: function (poetId, cb) {
    Poet.findOne({id: poetId}).populate('poetries').then(function (poet){
      if (!poet) {poet = {}};
      cb(poet);
    }).catch(function (err){
      throw new Error(err.message);
    });
  },

  count: function (cb){
    Co(function* () {
      let count = yield Poet.count();
      cb(count);
    }).catch(err => {
      sails.log.error(err.message);
    });
  },

  randOne: function(cb){
    const sql = "SELECT * FROM `poets` AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM `poets`)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id ASC LIMIT 1";
    Poetry.query(sql, function (err, result){
      if (err) {
        throw new Error(err.message);
        return;
      }
      cb(result);
    });
  }
}