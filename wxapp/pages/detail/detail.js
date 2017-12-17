var app = getApp()
Page({
  firstIndex: -1,
  data: {
    bannerApp: true,
    winWidth: 0,
    winHeight: 0,
    currentTab: 0, //tab切换  
    productId: 0,
    itemData: {},
    bannerItem: [],
    buynum: 1,
    // 产品图片轮播
    indicatorDots: false,
    autoplay: false,
    interval: 5000,
    duration: 1000,
    // 属性选择
    firstIndex: -1,
    //准备数据
    //数据结构：以一组一组来进行设定
    commodityAttr: [],
    attrValueList: [],
    isCollect:0
  },
  onLoad: function (options) {
    var that = this
    wx.request({
      url: 'http://192.168.10.99:8080/productInfo/findAllById?id=' + options.productId,
      //把id传到后台
      success: function (res) {
        that.setData({
          book: res.data
        })
      }
    })
  },
  swichNav: function (e) {
    var that = this;
    if (that.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  //进入购物车页面
  shopPage: function () {
    wx.switchTab({
      url: '../cart/cart',
      success: function (e) {
        var page = getCurrentPages().pop();
        if (page == undefined || page == null) return;
        page.onLoad();
      }
    })
  },
  // 弹窗
  setModalStatus: function (e) {
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })

    this.animation = animation
    animation.translateY(300).step();

    this.setData({
      animationData: animation.export()
    })

    if (e.currentTarget.dataset.status == 1) {

      this.setData(
        {
          showModalStatus: true
        }
      );
    }
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        this.setData(
          {
            showModalStatus: false
          }
        );
      }
    }.bind(this), 200)
  },
  addFavorites:function(e){
    let c = e.currentTarget.dataset.bbb
    console.log(c)
    if(c==0){
      this.setData({isCollect:1})
    }else{
      this.setData({isCollect:0})
    }
  },
  //加入购物车
  joinShop: function (e) {
    var openId = app.globalData.openId
    let id = e.currentTarget.dataset.aid
    console.log(id)
    let name = e.currentTarget.dataset.name    //获取用户id
    wx.request({
      url: 'http://192.168.10.99:8080/cart/add',
      method: 'post',
      data: {
        "product.id": id,
        openId: openId
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var title = '加入购物车成功'
        if (res == false) {
          title = '加入购物车失败'
        }
        wx.showToast({
          title: title,
          icon: 'success',
          duration: 2000
        })
      },
      fail: function (err) {
        wx.showToast({
          title: '加入购物车失败',
          icon: 'loading',
          duration: 2000
        })
      }
    })
  },  
})