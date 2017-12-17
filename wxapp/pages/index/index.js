//index.js
//获取应用实例
var app = getApp()
//var fileData = require('../../utils/data.js')
var arr = ['http://a.dangdang.com/api/data/cpx/img/39290001/1', 'http://a.dangdang.com/api/data/cpx/img/39320001/1', 'http://a.dangdang.com/api/data/cpx/img/39500001/1', 'http://a.dangdang.com/api/data/cpx/img/39410001/1']
Page({
  // 页面初始数据
  data: {
    imgUrls: arr,
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    circular: true,
    productData: [],
    proCat: [],
    page: 2,
    index: 2,
    brand: [],
    // 滑动
    imgUrl: [],
    kbs: [],
    lastcat: [],
    course: []
  },
  onLoad:function(){
  //  var userId = app.getUserInfo()
    wx.showLoading({
      title: '正在加载',
      icon:"loading",
      duration: 1000
    })
    var that = this
    app.getUserInfo(function (userInfo) {
      //更新数据
      that.setData({
        userInfo: userInfo
      })
    }) 
    //首页列表
    wx.request({
      url: 'http://192.168.10.99:8080/productInfo/findAll',
      success: function (res) {   
        var dd = res.data[0].image    
        that.setData({
          productData: res.data
        })
      }
    })
  },
  //点击书籍 跳转至详情页
  navigateDetail: function(e){
    var aid = e.currentTarget.dataset.aid
    wx.navigateTo({
      url: '../detail/detail?aid='+aid
    })
  },
  //点击购买 微信支付
  buy:function(e){
    //参数：价格 名称 
    app.wxpay(e)
  }
  
})
