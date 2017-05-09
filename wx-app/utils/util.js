function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function trim(str) {
  return str.replace(/(^\s*)|(\s*$)/g, "")
}

function contentToArr(content) {
  // 先按照“。”分开 \u3002
  var arr = content.split(/[\u3002]+/)
  for (var i = 0; i < arr.length; i++) {
    arr[i] += "。"
    // 先按照“？”分开 \uff1f
    if (arr[i].match(/[\uff1f]+/)) {
      arr[i] = arr[i].split(/[\uff1f]+/)
      for (var j = 0; j < arr[i].length - 1; j++) {
        arr[i][j] += "？"
      }
    }
  }
  // 合成一个数组
  var results = new Array()
  var n = 0
  for (var k = 0; k < arr.length; k++) {
    if (arr[k] instanceof Array) { 
      for (var m = 0; m < arr[k].length; m++) {
        results[n + m] = arr[k][m]
        n++
      }
    } else {
      results[n] = arr[k]
    }
    n++
  }
  return results.slice(0, results.length-1)
}

/**
 * 合并 Page 对象所有的方法及事件
 * 子对象不能使用 data 属性，请在 onLoad 中使用 setData 方法设置
 */
function mergePage(dest, ...src) {
  let args = arguments;
  let eventsStack = {
    onLoad: [],
    onReady: [],
    onShow: [],
    onHide: [],
    onUnload: [],
    onPullDownRefresh: [],
    onReachBottom: [],
  };
  // 保存所有的事件，最后一个参数的事件会最先调用。
  for (let name in eventsStack) {
    for (let i = args.length - 1; i >= 0; i--) {
      args[i][name] && eventsStack[name].push(args[i][name])
    }
  }
  // Object.assign(...args);
  // Object.assign 需要添加 polyfill 兼容 Android（不支持数组参数展开）
  Object.assign.apply(null, args);
  for (let name in eventsStack) {
    dest[name] = function () {
      for (let i = 0; i < eventsStack[name].length; i++) {
        eventsStack[name][i].apply(this, arguments);
      }
    }
  }
  return dest;
}

module.exports = {
  formatTime: formatTime,
  mergePage: mergePage,
  trim: trim,
  contentToArr: contentToArr
}
