'use strict'

const Co = require('co');
const Assert = require('../utils/assert.js');

module.exports = {
  findOne: function (poetId, cb) {
    Poetry.findOne({id: poetId}).populate('poet_id').exec(function (err, poetry){
      if (err) {
        throw new Error(err.message);
        return;
      }
      if (!poetry) {poetry = {}};
      cb(poetry);
    })
  },

  count: function (cb){
    Co(function* () {
      let count = yield Poetry.count();
      cb(count);
    }).catch(err => {
      sails.log.error(err.message);
    });
  },

  randOne: function(cb){
    Poetry.query("SELECT * FROM `poetries` AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM `poetries`)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id ASC LIMIT 1", function (err, result){
      if (err) {
        throw new Error(err.message);
        return;
      }
      cb(result);
    });
  }
}