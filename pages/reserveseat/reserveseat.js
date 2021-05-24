// pages/reserveseat/reserveseat.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    usableSeats: 0
  },
  reserveSeat: function () {
    const that = this
    wx.showModal({
      title: '提示',
      content: '请在30分钟内到达自习室，否则取消预约',
      
      success: function (res) {
        if (res.confirm) {
          
            wx.request({
              url: 'https://group21.cychenye.com/applyReserveInfoServlet?stu_id='+app.globalData.stu_id+'&password=group21password',
              headers: {
                'Content-Type': 'application/json'
              },
              method: 'GET',
              success:function(res){
                if(res.data.status == 1){
                  wx.showToast({
                    title: '预约成功',
                    image:'/image/success.png',
                    duration: 2000
                  })
                } else {
                  wx.showToast({
                    title: '预约失败',
                    image:'/image/fail.png',
                    duration: 2000
                  })
                }
              },
              fail:function(res){
                wx.showToast({
                  title: '网络连接失败',
                  image:'../../image/fail.png',
                  duration:2000
                })
              }
            })
            wx.redirectTo({
              url: '../reserveseat/reserveseat',
            })
        } else {
          wx.showToast({
            title: '取消预约申请',
            image:'/image/success.png',
            duration: 2000
          })
        }
      }
    })
  },
  watchHr: function(){
    wx.navigateTo({
      url: '../historyreserve/historyreserve',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const that = this
    wx.request({
      url: 'https://group21.cychenye.com/queryReserveNumberServlet',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          usableSeats: res.data.num
        })
      },
      fail:function(res){
        wx.showToast({
          title: '网络连接失败',
          image:'../../image/fail.png',
          duration:2000
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
    const that = this
    wx.request({
      url: 'https://group21.cychenye.com/queryReserveNumberServlet',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          usableSeats: res.data.num
        })
      },
      fail:function(res){
        wx.showToast({
          title: '网络连接失败',
          image:'../../image/fail.png',
          duration:2000
        })
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