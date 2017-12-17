var app = getApp()
var Tab = {
  _handleZanTabChange(e) {
    var dataset = e.currentTarget.dataset;
    var componentId = dataset.componentId;
    var selectedId = dataset.itemId;
    var data = { componentId, selectedId };

    console.info('[zan:tab:change]', data);
    if (this.handleZanTabChange) {
      this.handleZanTabChange(data);
    } else {
      console.warn('页面缺少 handleZanTabChange 回调函数');
    }
  }
};
module.exports = Tab;

Page(Object.assign({}, Tab, {
  data: {
    tab1: {
      list: [{
        id: 'all',
        title: '全部',
        userInfo: {},
        Industry:{}
      }, {
        id: 'topay',
        title: '待付款',
        userInfo: {},
        Industry:{}
      }, {
        id: 'tosend',
        title: '待发货'
      }, {
        id: 'send',
        title: '待收货'
      }, {
        id: 'sign',
        title: '已完成'
      }],
      selectedId: 'all',
      scroll: false
    },
  },

  handleZanTabChange(e) {
    var componentId = e.componentId;
    var selectedId = e.selectedId;

    this.setData({
      [`${componentId}.selectedId`]: selectedId
    });
  },
  //标签切换
  switchTab: function (e) {
    let id = e.currentTarget.dataset.id,
      index = parseInt(e.currentTarget.dataset.index)
    this.curIndex = parseInt(e.currentTarget.dataset.index)
    var that = this
    this.setData({
      curNavId: id,
      curIndex: index,
    })
    wx.request({
      url: 'http://192.168.10.99:8080/productInfo/findByType?typeId=' + id,
      data: {},
      success: function (res) {
        that.setData({
          user: res.data
        })
      }
    })
  },
  onLoad:function(e){
    var that = this;
    var self = this;
    wx.login({
      success: function (res) {
        var code = res.code
        wx.getUserInfo({
          success: function (res1) {
            var encryptedData = encodeURIComponent(res1.encryptedData)
          }
        })
      }
    });
    wx.getUserInfo({
      success: function (res) {
        console.log(res.userInfo.avatarUrl),
          self.setData({
            thumb: res.userInfo.avatarUrl,
            nickname: res.userInfo.nickName
          })
      }
    });
    //网络加载
    wx.showToast({
      title:'奋力加载',
      icon : 'loading'
    });
    wx.request({
      url: 'http://192.168.10.99:8080/orderInfo/findAll',//上线的话必须是https
     data: {
       openId: app.globalData.openId
     },
     method: 'GET',
     success: function(res){
       that.setData({
         order:res.data
       })
     }
   })
  },
  //取消订单
  cancelOrder:function(e){
    wx.request({
      url: '',
      method:'post',
      data:{
        id:''//订单id
      },
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },
      success:function(res){

      }
    })
  },
  //付款
  payment:function(e){
    //e参数：总费用 类别 订单号
    app.wxpay(e)
  },
  //点击订单号进入订单详情
}));
