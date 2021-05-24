// index.js
// 获取应用实例
var util = require('../../utils/util.js');

const app = getApp()

Page({
  data: {
    userInfo: {},
    tipflag: true,
    stu_id:'',
    name:'',
    loginstatus:false,
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName') // 如需尝试获取用户信息可改为false
  },
  // 事件处理函数
  unlogin:function(option){
    wx.showModal({
      title:'提示',
      content:'是否确定退出',
      success:function(res){
        if(res.confirm){
          app.globalData.stu_id='';
          app.globalData.name='';
          app.globalData.loginstatus=false;
          wx.redirectTo({
            url: '/pages/index/index',
          })
        }
      }
    })
  },

  reserveseat: function (options) {
    wx.navigateTo({
      url: '../reserveseat/reserveseat',
    })
  },
  watchRL: function (options) {
    wx.navigateTo({
      url: '../rankinglist/rankinglist',
    })
  },
  FormBindSubmit: function (e) {
    wx.redirectTo({
      url: '../login/login',
    })
  },
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onShow: function () {
    const that = this
    wx.request({
      url: 'https://group21.cychenye.com/querySeatsNumberServlet',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          num: res.data.num
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
  onLoad() {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
    var that = this //不要漏了这句，很重要
    wx.request({
      url: 'https://group21.cychenye.com/querySeatsNumberServlet',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          num: res.data.num,
          status: res.data.status,
          loginstatus  : getApp().globalData.loginstatus,
          name:getApp().globalData.name,
          stu_id:getApp().globalData.stu_id
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
    // loginstatus = getApp().globalData.loginstatus
    var time = util.formatTime(new Date());
    // 再通过setData更改Page()里面的data，动态更新页面的数据
    this.setData({
      time: time
    });
  },
  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log(res)
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    })
  },
  getUserInfo(e) {
    // 不推荐使用getUserInfo获取用户信息，预计自2021年4月13日起，getUserInfo将不再弹出弹窗，并直接返回匿名的用户个人信息
    console.log(e)
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})