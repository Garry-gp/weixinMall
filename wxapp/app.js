//app.js
App({
  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  getUserInfo:function(cb){
    var that = this
    if(this.globalData.userInfo){
      typeof cb == "function" && cb(this.globalData.userInfo)
    }else{
      //调用登录接口
      console.log('开始了------------！！！')
      wx.login({
        success: function (res) {
          var code = res.code
          wx.getUserInfo({
            success: function (res1) {
              var encryptedData = encodeURIComponent(res1.encryptedData)
              that.globalData.userInfo = res1.userInfo
              that.getOpenId(code, res1.userInfo) //传入code 获取openid
            }
          })
        }
      })
    }
  },
  //接收code获的用户openid
  getOpenId: function (code, userInfo) {
    var that = this;
    wx.request({
      url: "https://api.weixin.qq.com/sns/jscode2session?appid=wxebc98458ea4875ae&secret=6548b16c4d8992c9707533fe398fd552&js_code=" + code + "&grant_type=authorization_code",
      method: 'GET',
      success: function (res) {
        that.globalData.openId = res.data.openid
        that.Login(res.data.openid, userInfo)
      }
    })
  },
  //向本地服务器传送数据openid,userInfo 
  Login:function(openid,userInfo){
    console.log("我是openid = "+openid)
    wx.request({
      url: "http://192.168.10.99:8080/userInfo/getserInfo",
      data: {
       openId: openid,
       name: userInfo.nickName,
       city: userInfo.city,
       gender: userInfo.gender,
       avatarUrl: userInfo.avatarUrl,
       country: userInfo.country,
       province: userInfo.province
      },
      method: 'post',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
      }
    })
  },
  // 全局变量
  globalData:{
    userInfo:null,
    openId:'123456'
  },
  /* 微信支付 */
  wxpay: function (e) {
    var that = this
    var total = e
    //登陆获取code
    wx.login({
      success: function (res) {
        var code = res.code
      }
    });
  },
  /**生成商户订单 */
  generateOrder: function (openid,total) {
    var that = this
    //统一支付
    wx.request({
      url: 'http://192.168.10.99:8080/userInfo/',
      method: 'GET',
      data: {
        total_fee: total,
        body: '支付测试',
        attach: '这只是测试',
        openId:openid
      },
      success: function (res) {
        var pay = res.data
        //发起支付
        var timeStamp = pay[0].timeStamp;
        var packages = pay[0].package;
        var paySign = pay[0].paySign;
        var nonceStr = pay[0].nonceStr;
        var param = { "timeStamp": timeStamp, "package": packages, "paySign": paySign, "signType": "MD5", "nonceStr": nonceStr };
        that.pay(param)
      },
      fail:function(){

      },
      complete:function(){}
    })
  },

  /* 支付   */
  pay: function (param) {
    wx.requestPayment({
      timeStamp: param.timeStamp,
      nonceStr: param.nonceStr,
      package: param.package,
      signType: param.signType,
      paySign: param.paySign,
      success: function (res) {
        // success
        wx.navigateBack({
          delta: 1, // 回退前 delta(默认为1) 页面
          success: function (res) {
            wx.showToast({
              title: '支付成功',
              icon: 'success',
              duration: 2000
            })
          },
          fail: function () {
            // fail

          },
          complete: function () {
            // complete
          }
        })
      },
      fail: function (res) {
        // fail
      },
      complete: function () {
        // complete
      }
    })
  }

})