//index.js
const util = require("../../utils/util.js")
const _ = require("../../utils/underscore.js")

//获取应用实例
var loaderTimer;
var app = getApp()
Page({
  data: {
    userInfo: {},
    poetry: {},
    loaderShow: true,
    liked: false
  },
  onLoad: function (options) {
    var that = this
    //调用API从本地缓存中获取数据
    this.setData({
      collects: wx.getStorageSync('collects') || []
    })
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function (userInfo) {
      //更新数据
      that.setData({
        userInfo: userInfo
      })
    })
    that.getRandPoetry(that, function () {
      that.showLoader(2000)
    })
  },
  // 监听该页面用户下拉刷新事件
  onPullDownRefresh: function () {
    var that = this
    that.getRandPoetry(that, function () {
      that.showLoader(2000)
      wx.stopPullDownRefresh()
    })
  },

  getRandPoetry: function (tt, cb) {
    app.requestData('poetry/rand', null, "GET", function (res) {
      var contentArr = util.contentToArr(res.data.result.poetry.content);
      tt.setData({
        poetry: res.data.result.poetry,
        contentArr: contentArr,
        liked: app.checkLiked(res.data.result.poetry.id)
      })
    }, function (res) {
      app.showToast(res.errMsg, 'success', 2000)
    }, function (res) {
      typeof cb == "function" && cb()
    })
  },

  showLoader: function (time, cb) {
    var that = this
    clearTimeout(loaderTimer);
    that.setData({
      loaderShow: true
    });
    loaderTimer = setTimeout(() => {
      that.setData({
        loaderShow: false
      });
      cb && cb();
    }, time);
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
    return {
      title: '唐诗小站',
      path: '/pages/index/index',
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