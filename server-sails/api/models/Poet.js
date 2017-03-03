/**
 * Poet.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  tableName: 'poets',
  attributes: {
    id: {
      type: 'integer',
      primaryKey: true,
      unique: true
    },
    name: { type: 'string' },
    created_at: { type: 'datetime' },
    updated_at: { type: 'datetime' },

    // Add a reference to Poetries
    poetries : {
      collection: 'Poetry',
      via: 'poet'
    }
  },
  autoCreatedAt: false,
  autoUpdatedAt: false,

};

