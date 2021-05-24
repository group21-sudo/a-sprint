Page({
  data:{
    rankList:[
      {stuId:'',signed_number:''}
    ]
  },
  onLoad: function (options) {
    const that = this
    wx.request({
      url: 'https://group21.cychenye.com/queryRankListServlet?number=10',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET',
      success: function (res) {
        that.setData({
          rankList : res.data.rankList
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
})