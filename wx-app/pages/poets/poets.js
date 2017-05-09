const util = require("../../utils/util.js")
const Poets = require("../../utils/poets.js")
const _ = require("../../utils/underscore.js")

Page({
  data: {
    textDefault: '一共有 0 位诗人',

    poets: [],
    inputShowed: false,
    inputVal: ""
  },
  onLoad: function (options) {
    // 生命周期函数--监听页面加载
    this.setData({
      textDefault: '一共有 ' + Poets.total + ' 位诗人',
    })
  },

  /**
   * 监听该页面用户下拉刷新事件
   * 该页面不需要刷新，下拉之后立刻停止当前页面下拉刷新
   * */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh()
  },

  showInput: function () {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
      inputVal: "",
      inputShowed: false,
      poets: []
    });
  },
  clearInput: function () {
    this.setData({
      inputVal: "",
      poets: []
    });
  },
  inputTyping: function (e) {
    this.setData({
      inputVal: e.detail.value,
      poets: _.filter(Poets.data, function (poet) {
        if (util.trim(e.detail.value).length > 0 && poet.name.indexOf(e.detail.value) >= 0) {
          return true;
        }
      })
    });
  },

  // 分享
  onShareAppMessage: function () {
    return {
      title: '诗人列表 - 搜索',
      path: '/pages/poets/poets',
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