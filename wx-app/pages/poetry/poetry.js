// pages/poetry/poetry.js
const util = require("../../utils/util.js")
const _ = require("../../utils/underscore.js")

var app = getApp()
Page({
  data: {
    poetry: {},
    liked: false
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    var that = this
    //调用API从本地缓存中获取数据
    this.setData({
      collects: wx.getStorageSync('collects') || []
    })
    app.showToast("加载中", 'loading', 20000)
    app.requestData('poetry/' + options.id, null, "GET", function (res) {
      var contentArr = util.contentToArr(res.data.result.poetry.content);
      that.setData({
        poetry: res.data.result.poetry,
        contentArr: contentArr,
        liked: app.checkLiked(res.data.result.poetry.id)
      })
    }, function (res) {
      app.showToast(res.errMsg, 'success', 2000)
    }, function (res) {
      wx.hideToast()
    })
  },

  /**
   * 监听该页面用户下拉刷新事件
   * 该页面不需要刷新，下拉之后立刻停止当前页面下拉刷新
   * */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh()
  },

  bindImgTap: function () {
    var that = this
    var collects = this.data.collects
    var poetry = this.data.poetry
    var ret = _.where(collects, { id: poetry.id })
    if (ret.length > 0) {
      collects = _.reject(collects, function (collect) {
        return collect.id == that.data.poetry.id
      })
      this.setData({
        liked: false
      })
    } else {
      poetry.time = Date.now()
      collects.push(poetry)
      this.setData({
        liked: true
      })
    }
    this.setData({
      collects: collects
    })
    wx.setStorageSync('collects', collects)
  },

  // 分享
  onShareAppMessage: function () {
    var that = this
    return {
      title: '个人中心',
      path: '/pages/poetry/poetry?id=' + that.data.poetry.id,
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