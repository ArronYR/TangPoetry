/**
 * Poetry.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  tableName: 'poetries',
  attributes: {
    id: {
      type: 'integer',
      primaryKey: true,
      unique: true
    },
    poet_id: {
      type: 'integer'
    },
    // Add a reference to User
    poet: {
      columnName: 'poet_id',
      model: 'poet'
    },

    title: { type: 'string' },
    content: { type: 'text' },
    created_at: { type: 'datetime' },
    updated_at: { type: 'datetime' },
  },

  autoCreatedAt: false,
  autoUpdatedAt: false,
};

