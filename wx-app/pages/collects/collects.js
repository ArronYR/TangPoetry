const util = require("../../utils/util.js")
const _ = require("../../utils/underscore.js")

var app = getApp()
Page({
  data: {
    collects: [],
    collectsCount: 0
  },
  onLoad: function (options) {
    // 生命周期函数--监听页面加载
    this.setData({
      collects: _.groupBy((wx.getStorageSync('collects') || []).map(function (collect) {
        collect.time = util.formatTime(new Date(collect.time))
        return collect
      }), function (collect) {
        return collect.poet_id
      }),
      collectsCount: (wx.getStorageSync('collects') || []).length
    })
  },

  // 监听该页面用户下拉刷新事件
  onPullDownRefresh: function () {
    this.refreshData(function () {
      wx.stopPullDownRefresh()
    })
  },

  refreshData: function (cb) {
    this.setData({
      collects: _.groupBy((wx.getStorageSync('collects') || []).map(function (collect) {
        collect.time = util.formatTime(new Date(collect.time))
        return collect
      }), function (collect) {
        return collect.poet_id
      }),
      collectsCount: (wx.getStorageSync('collects') || []).length
    })
    typeof cb == "function" && cb()
  }
})