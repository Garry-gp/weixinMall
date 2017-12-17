//index.js
//获取应用实例
var app = getApp()
//var fileData = require('../../utils/data.js')

Page({
  // 页面初始数据
  data: {
    colors: ['red', 'orange', 'yellow', 'green', 'purple'],
   // navSectionItems: fileData.getIndexNavSectionData(),
    curNavId: 1,
    curIndex: 0,
    temp:true,
    goodsCount:5,
    cart:'',
    total:0,
    selectedAllStatus:2
  },
  onLoad: function () {
    var openId = app.globalData.openId
    var that = this
    //请求 获取 购物车 列表
    wx.request({
      url: 'http://192.168.10.99:8080/cart/findAll',
      data:{
        openId: openId
        },
      method:'post',
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },                                  
      success:function(res){
        console.log(res)
        let total=0
        let goodsCount=0
       for(var i in res.data){
         if(res.data[i].status==1){
           total += res.data[i].priceCount
           goodsCount+=res.data[i].productCount
         }
       }
        that.setData({
          cart:res.data,
          total:total.toFixed(2),
          goodsCount: goodsCount
        })
      }
    })
  },
  //跳转首页
  navigateIndex: function () {
    wx.switchTab({
      url: '../index/index',
      success: function () {
        var page = getCurrentPages().pop();
        if (page == undefined || page == null) return;
        page.onLoad();
      }
    })
  },
  // 跳转至详情页
  navigateDetail: function (e) {
    var that = this
    var id = e.currentTarget.dataset.id
    console.log('ididididididiididid='+id)
    wx.navigateTo({
      url: '../detail/detail?aid='+id,
    })
  },
  bindSelectAll:function(e){
    var selectAll = this.data.selectedAllStatus
    var that = this
    if (selectAll == 1) {
      selectAll = 2
    } else {
      selectAll = 1
    }
    var openId = app.globalData.openId
    console.log('status-----====  '+selectAll)
    wx.request({
      url: 'http://192.168.10.99:8080/cart/statusAll',
      data: {
        status: selectAll,
        openId: openId
      },
      success:function(){
        that.setData({ selectedAllStatus: selectAll })
        that.onLoad()
      }
    })
    
  },
  //点击事件
  checkboxChange:function(e){
    let status = 1
    var that = this
    var id = e.currentTarget.dataset.id
    if(e.detail.value.length==0){
      status = 2
    }
    wx.request({
      url: 'http://192.168.10.99:8080/cart/update',
      method:'post',
      data:{
         id:id,
         status:status
      },
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },
      success:function(res){
        that.onLoad();
      }
    })
  },
  formSubmit: function (e) {
  },
  formReset: function (e) {
    wx.showModal({
      title: '提示',
      content: '确定清除所选项',
      success: function (res) {
        if (res.confirm) {
          
        }
      }
    })
  },
  //减
  delCount:function(e){
    let id = e.currentTarget.dataset.id
    var count = e.currentTarget.dataset.count - 1
    this.req(id,count)
  },
  //加
  addCount:function(e){
    let id = e.currentTarget.dataset.id
    var count = e.currentTarget.dataset.count + 1
    this.req(id,count)
  },
  req:function(id,count){
    var that = this
    wx.request({
      url: 'http://192.168.10.99:8080/cart/update',
      data: {
        id: id,
        productCount: count
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success:function(res){
        that.onLoad();
      }
    })
  },
  //删除
  deleteShop:function(e){
    var that = this
    let aid = e.currentTarget.dataset.id;
    wx.request({
      url:'http://192.168.10.99:8080/cart/remove',
      data:{
        id:aid
      },
      method:'post',
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },
      success:function(res){
        that.onLoad();
      }
    })
  },
  //结算
  bindCheckout:function(e){
    var that = this
    var list = e.currentTarget.dataset.name;
    var productId="" 
    var count =""
    var id = ""
    let total = e.currentTarget.dataset.total
    for(var i in list){
      if(list[i].status==1){
       productId +=list[i].product_id+"-"
       count +=list[i].productCount+"-"
       id +=list[i].id+"-"
      }
    }
       wx.request({
         url: 'http://192.168.10.99:8080/orderInfo/save1',
         data:{
           productId:productId,
           count:count,
           openId:app.globalData.openId,
           total:total,
           id:id
         },
        success:function(res){
          if(res.data){
            console.log('下单成功')
          }else{
            console.log('下单失败')
          }
          that.onLoad()
        }
       })
    // wx.navigateTo({
    //   url: '../orders/orders',
    // })
  },
})

