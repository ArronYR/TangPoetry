// pages/poetry/poetry.js

var app = getApp()
Page({
  data: {
    hdShowe: true,
    poet: {}
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    var that = this
    app.showToast("加载中", 'loading', 20000)
    app.requestData('poet/' + options.id, null, "GET", function (res) {
      that.setData({
        hdShowe: false,
        poet: res.data.result.poet
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

  // 分享
  onShareAppMessage: function () {
    var that = this
    return {
      title: '个人中心',
      path: '/pages/poet/poet?id=' + that.data.poet.id,
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