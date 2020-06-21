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
<%@ page import="WebSide.CompanyDao" %>
<%@ page import="WebSide.Info" %>
<%@ page import="Bean.Company" %>
<%@ page import="Utils.BaseData" %>
<%@ page import="Utils.ExcelExport" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="WebSide.StatisticalDao" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="ServerVueWeb.Dao.UpdataAppDataDao" %>
<%@ page import="Utils.Lg" %>
<html>
<head>
    <title>数据添加</title>
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
//    String userCode = "9a2c9e2e995c577b";
//    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
//        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
//    }

%>
<%--<jsp:include page="../headLayout.jsp"/>--%>
<%--
<%
    WebDao aa = new WebDao();
//    List list = (List) request.getAttribute("pl_list");
    List list = aa.getRegister();


    for (int i = 0; i < list.size(); i++) {
        RegisterBean rs = (RegisterBean) list.get(i);
%>--%>
<%--<%
    String tips = (String) request.getAttribute("tips");
%>
<h5 ><%=tips%></h5>--%>

<hr/>
<header class="mui-bar mui-bar-nav">
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="../AppWebHomeIO?json=<%=userCode%>"></a>--%>
    <a class="mui-icon mui-icon-left-nav mui-pull-left" href="../AppWebHomeIO?json=<%=userCode%>"></a>
    <h1 class="mui-title"><%=buyName%>
    </h1>
</header>
<form style="margin-top: 50px" class="mui-input-group"  action="../AppWebAddDetailIO" method="post">
    <div class="mui-input-row">
        <label>说明</label>
        <input type="text" class="mui-input-clear" placeholder="请输入说明" name="note" id="note">
    </div>
    <div class="mui-input-row">
        <label>数量</label>
        <input type="number" class="mui-input-clear" placeholder="请输入数量" name="num">
    </div>
    <div class="mui-button-row">
        <button type="submit" class="mui-btn mui-btn-primary" >确认添加</button>
    </div>
</form>



</body>


</html>
