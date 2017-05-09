//user.js
var util = require('../../utils/util.js')

var app = getApp()
Page({
  data: {
    userInfo: {},
    collectsCount: 10
  },

  onLoad: function () {
    this.setData({
      userInfo: app.globalData.userInfo,
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
      collects: (wx.getStorageSync('collects') || []).map(function (collect) {
        collect.time = util.formatTime(new Date(collect.time))
        return collect
      }),
      collectsCount: (wx.getStorageSync('collects') || []).length
    })
    typeof cb == "function" && cb()
  },

  // 收藏按钮点击事件
  bindViewTapCollect: function () {
    wx.navigateTo({
      url: '../collects/collects'
    })
  },

  // 分享
  onShareAppMessage: function () {
    return {
      title: '个人中心',
      path: '/pages/user/user',
      success: function (res) {
        // 分享成功
        app.showToast("分享成功", 'success', 1000)
      },
      fail: function (res) {
        // 分享失败
      }
    }
  }
})
