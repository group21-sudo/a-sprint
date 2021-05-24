//index.js
//获取应用实例
const app = getApp()
 
Page({
  data: {
    stu_id: '',
    name: '',
    loginstatus:false
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  bindStuid:function(e){
    if (e.detail.value.stu_id == '') {
      wx.showToast({
        title: '请输入学号',
        duration: 2000,
        icon: 'none'
      })
    }
      else {
        this.setData({
          stu_id:e.detail.value,
        })
        getApp().globalData.stu_id = this.data.stu_id
      }
  },
  bindName:function(e){
    if (e.detail.value.name == '') {
      wx.showToast({
        title: '请输入姓名',
        duration: 2000,
        icon: 'none'
      })
    }
    else {
      this.setData({
        name: e.detail.value,
      })
      getApp().globalData.name = this.data.name
    }
  },
  
  submitStuidName:function(){
    getApp().globalData.stu_id = this.data.stu_id
      getApp().globalData.name = this.data.name
      var that = this
      wx.request({
        url: 'https://group21.cychenye.com/loginServlet?stu_id='+app.globalData.stu_id+'&name='+app.globalData.name,
        headers: {
          'Content-Type': 'application/json'
        },
        method: 'GET',
        success: function (res) {
          if(res.data.status==1){
            that.setData({
              loginstatus: true,
            })
            getApp().globalData.loginstatus = that.data.loginstatus
            wx.redirectTo({
              url: '../index/index',
            })
          } else{
              wx.showToast({
                title: '学号或姓名错误，请重新输入！',
                icon:'none'
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
  },
 
 
  FormBindSubmit: function (e) {
    if (e.detail.value.stu_id == '') {
      wx.showToast({
        title: '请输入学号',
        duration: 2000,
        icon: 'none'
      })
    } else if (e.detail.value.name == '') {
      wx.showToast({
        title: '请输入姓名',
        duration: 2000,
        icon: 'none'
      })
    } else {
      this.setData({
        stu_id:e.detail.value.stu_id,
        name: e.detail.value.name,
      })
      getApp().globalData.stu_id = this.data.stu_id
      getApp().globalData.name = this.data.name
      var that = this
      wx.request({
        url: 'https://group21.cychenye.com/loginServlet?stu_id='+app.globalData.stu_id+'&name='+app.globalData.name,
        headers: {
          'Content-Type': 'application/json'
        },
        method: 'GET',
        success: function (res) {
          if(res.data.status==1){
            that.setData({
              loginstatus: true,
            })
            getApp().globalData.loginstatus = that.data.loginstatus
            wx.redirectTo({
              url: '../index/index',
            })
          } else{
              wx.showToast({
                title: '学号或姓名错误，请重新输入！',
                icon:'none'
              })
          }
        }
      })
    }
  }
})
 