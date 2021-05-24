// pages/historyreserve/historyreserve.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    info:[
    ],
    isDisabled:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  unreserveLatest:function(option){
    wx.showModal({
      title: '提示',
      content: '是否确认取消',
      success:function(res){
        if(res.confirm){
          wx.request({
            url: 'https://group21.cychenye.com/cancelReserveInfoServlet?stu_id='+app.globalData.stu_id+'&password=group21password',
            headers: {
              'Content-Type': 'application/json'
            },
            method: 'GET',
            success:function(res){
              if(res.data.status ==1){
                wx.showToast({
                  title: '取消成功',
                  image:'/image/success.png',
                  duration:2000
                })
              }
              else{
                wx.showToast({
                  title: '取消失败',
                  image:'/image/fail.png',
                  duration:2000
                })
              }
            }
          })
          wx.redirectTo({
            url: '../historyreserve/historyreserve',
          })
        }
      }
    })
  },
  onLoad: function (options) {
    const that =this
    wx.request({
      url: 'https://group21.cychenye.com/queryHistoryReserveInfoServlet?stu_id='+app.globalData.stu_id+'&password=group21password',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function(res){
        if(res.data.status == 1){
          that.setData({
            info:res.data.info
          })
        }
        if(that.data.info[0].flag ==0){
          that.setData({
            isDisabled:false
          })
        }
      }
    })
    // that.onLoad
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
    const that =this
    wx.request({
      url: 'https://group21.cychenye.com/queryHistoryReserveInfoServlet?stu_id='+app.globalData.stu_id+'&password=group21password',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function(res){
        if(res.data.status == 1){
          that.setData({
            info:res.data.info
          })
        }
        if(that.data.info[0].flag ==0){
          that.setData({
            isDisabled:false
          })
        }
      }
    })
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