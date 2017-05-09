//app.js
const util = require("./utils/util.js")
const _ = require("./utils/underscore.js")

App({
  onLaunch: function () {
    if (!Object.assign) {
      Object.assign = require('./utils/object-assign')
    }
  },

  getUserInfo: function (cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
      //调用登录接口
      wx.login({
        success: function () {
          wx.getUserInfo({
            success: function (res) {
              that.globalData.userInfo = res.userInfo
              typeof cb == "function" && cb(that.globalData.userInfo)
            }
          })
        }
      })
    }
  },

  requestData: function (url, data, method, success, fail, complete) {
    var that = this
    wx.request({
      url: that.globalData.apiHost + url,
      data: typeof data == "object" && data ? data : {},
      method: typeof method == "string" && util.trim(method).length > 0 ? method : "GET",
      success: function (res) {
        typeof success == "function" && success(res)
      },
      fail: function (res) {
        typeof fail == "function" && fail(res)
      },
      complete: function (res) {
        typeof complete == "function" && complete(res)
      }
    })
  },

  checkLiked: function (id) {
    var collects = wx.getStorageSync('collects') || []
    var ret = _.where(collects, { id: id })
    if (ret.length > 0) {
      return true
    } else {
      return false
    }
  },

  globalData: {
    userInfo: null,
    apiHost: "https://tp-api.helloarron.com/api/"
  },

  showToast: function (msg, type, duration) {
    wx.showToast({
      title: msg,
      icon: type,
      duration: duration
    })
  }
})