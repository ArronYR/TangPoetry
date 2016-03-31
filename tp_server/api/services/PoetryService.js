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
  }
}