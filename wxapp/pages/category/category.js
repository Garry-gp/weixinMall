Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    //首页标签
    wx.request({
      url: 'http://192.168.10.99:8080/productInfo/findAllType',
      success: function (res) {
        that.setData({
          types: res.data,
          currType:0
        })
      }
    })
    wx.request({
      url: 'http://192.168.10.99:8080/productInfo/findAll',
      success: function (res) {
        var dd = res.data[0].image
        that.setData({
          typeTree: res.data
        })
      }
    })
  },
  tapType:function(e){
    let id = e.currentTarget.dataset.typeId
    var that = this
    this.setData({
      currType: id,
    })
    wx.request({
      url: 'http://192.168.10.99:8080/productInfo/findByType?typeId=' + id,
      data: {},
      success: function (res) {
        that.setData({
          typeTree: res.data
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})