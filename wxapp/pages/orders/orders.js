// page/component/orders/orders.js
var app = getApp()
Page({
  data:{
    address:{},
    hasAddress: false,
    total:0,
    orders:[
      { id: 1, title: '小红帽故事书', image:'../../images/default_pic.png',num:4,price:0.01},
        { id: 2, title: 'java基数', image:'../../images/default_pic.png',num:1,price:0.03}
      ]
  },
  onLoad(e){
    var that = this
    console.log('status = ' + e.status)
    var status = e.status
    wx.request({
      url: 'http://192.168.10.99:8080/orderInfo/findByStatus',
      data:{
        status:status,
        openId: app.globalData.openId
      },
      success:function(res){
        var total = 0
        console.log(res.data)
        for(var i in res.data){
          if(res.data[i].status==1){
          total +=res.data[i].orderPrice
          }
        }
        that.setData({
          order:res.data,
          total:total
        })
      }
    })
  },
  onReady() {
    this.getTotalPrice();
  },
  
  onShow:function(){
    const self = this;
    wx.getStorage({
      key:'address',
      success(res) {
        self.setData({
          address: res.data,
          hasAddress: true
        })
      }
    })
  },

  /**
   * 计算总价
   */
  getTotalPrice() {
    let orders = this.data.orders;
    let total = 0;
    for(let i = 0; i < orders.length; i++) {
      total += orders[i].num * orders[i].price;
    }
    this.setData({
      //total: total
    })
  },

  toPay() {
    wx.showModal({
      title: '提示',
      content: '本系统只做演示，支付系统已屏蔽',
      complete() {
        wx.switchTab({
          url: '/page/user/user'
        })
      }
    })
  }
})