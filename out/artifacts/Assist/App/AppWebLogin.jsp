<%--
  Created by IntelliJ IDEA.
  User: NB
  Date: 2017/8/7
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" import="Bean.RegisterBean" import="WebSide.WebDao"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="WebSide.Info" %>
<%@ page import="ServerVueWeb.Dao.UpdataAppDataDao" %>
<%@ page import="Utils.Lg" %>
<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>

    <!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>

    <!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <%--<link rel="stylesheet" href="css/bootstrap.min.css">--%>
    <script src="../js/jquery.js"></script>
    <script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <script src="../js/mui.min.js"></script>
    <link href="../css/mui.min.css" rel="stylesheet"/>
    <script type="text/javascript" charset="utf-8">
        mui.init();

    </script>


</head>
<body>
<%
    Lg.e("登录jsp",getClass().getSimpleName());
    String userCode = (String) session.getAttribute(Info.FUser_Code);
    String buyName = (String) session.getAttribute(Info.FUser_Home_To_Detail);
    UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();
    String baseUrl = request.getContextPath();
//    String userCode = "9a2c9e2e995c577b";
//    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
//        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
//    }

%>

<hr/>
<header class="mui-bar mui-bar-nav">
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="../AppWebHomeIO?json=<%=userCode%>"></a>--%>
    <%--<a class="mui-icon mui-icon-left-nav mui-pull-left" href="../AppWebHomeIO?json=<%=userCode%>"></a>--%>
    <h1 class="mui-title">用户登录
    </h1>
</header>
<form style="margin-top: 50px" class="mui-input-group"  action="../AppWebLoginIO" method="post">
    <div class="mui-input-row">
        <label>用户名</label>
        <input type="text" class="mui-input-clear" placeholder="请输入用户名" name="name" id="name">
    </div>
    <div class="mui-input-row">
        <label>密码</label>
        <input type="password" class="mui-input-password" placeholder="请输入密码" name="pwd" id="pwd">
    </div>
    <div class="mui-button-row">
        <button type="submit" class="mui-btn mui-btn-primary" >注册/登录</button>
    </div>
</form>
    <div class="row">
        <div class="col-sm-12 col-lg-12" style="text-align: center"><h5>仅提供已在app端注册登录的用户进行网页操作</h5></div>
    </div>
<%--<button type="button" class="mui-btn mui-btn-primary" onclick="login()" >注册/登录</button>--%>
<%--<a id="test">标签</a>--%>





</body>

<script  type="text/javascript" charset="utf-8">
    function login() {
        var  sname=document.getElementById('name').value;
        var  spwd=document.getElementById('pwd').value;
        var  test=document.getElementById('test');
        test.innerText = sname;
        <%--mui.ajax('<%=baseUrl%>/AppWebLoginIO?name = '+sname+'&pwd='+spwd,{--%>
        mui.ajax('<%=baseUrl%>/AppWebLoginIO',{
            data:{
                name:sname,
                pwd:spwd
//                name:'111',
//                pwd:'111'
            },
            dataType:'json',//服务器返回json格式数据
            type:'get',//HTTP请求类型
            timeout:10000,//超时时间设置为10秒；
            headers:{'Content-Type':'application/x-www-form-urlencoded'},
            success:function(data){
                //服务器返回响应，根据响应结果，分析是否登录成功；
//                mui.toast("登录成功",{ duration:'long', type:'div' });
                mui.alert(data,"登录失败","确定");
            },
            error:function(xhr,type,errorThrown){
                //异常处理；
                console.log(type);
//                mui.toast("登录失败",{ duration:'long', type:'div' });

                mui.alert(xhr,"登录失败","确定");
            }
        });

    }

</script>


</html>
