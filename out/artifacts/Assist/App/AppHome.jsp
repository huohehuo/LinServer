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
<%@ page import="ServerVueWeb.UpdataAppDataDao" %>
<%@ page import="ServerVueWeb.Bean.NoteBean" %>
<html>
<head>
    <title>注册用户管理</title>
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
    String userCode = (String)session.getAttribute(Info.FUser_Code);
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
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" ></a>--%>
    <h1 class="mui-title">标签主页</h1>
</header>

<div style="margin: 50px">
    <ul class="mui-table-view">
        <%
            UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();
            //    List list = (List) request.getAttribute("pl_list");
            List list = updataAppDataDao.getNoteDataForApp(userCode);
//            if (list==null){
        %>
        <%

            for (int i = 0; i < list.size(); i++) {
                NoteBean rs = (NoteBean) list.get(i);
        %>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" href="../AppWebHomeToDetailIO?json=<%=rs.NBuyName%>"><%=rs.NBuyName%></a>
            <span class="mui-badge mui-badge-success"><a href="../AppWebHomeToDetailIO?json=<%=rs.NBuyName%>">详情</a></span>
            <div class="mui-media-body">

                <p class='mui-ellipsis'>条数:<%=updataAppDataDao.getBuyAtDataSize(userCode,rs.NBuyName) %><p class='mui-ellipsis'><%=rs.Ntime %></p></p>
            </div>
        </li>
        <%} %>
    </ul>

    <%--<div  id="vue_company"  class="card">--%>
        <%--<div class="card-body">--%>

            <%--<table class="table">--%>
                <%--<thead>--%>
                <%--<%--%>

                    <%--UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();--%>
                    <%--//    List list = (List) request.getAttribute("pl_list");--%>
                    <%--List list = updataAppDataDao.getNoteDataForApp(userCode);--%>
                    <%--if (list==null){--%>
                <%--%><div class="alert alert-info"> 列表数据为空</div><%--%>
                        <%--return;--%>
                    <%--}--%>
                <%--%>--%>
                <%--<tr>--%>
                    <%--<th class="display-4">标签(<%=list.size()%>)</th>--%>
                    <%--<th class="display-4">最近一次更新日期</th>--%>
                    <%--&lt;%&ndash;<th>时间控制日期</th>&ndash;%&gt;--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<%--%>

                    <%--for (int i = 0; i < list.size(); i++) {--%>
                        <%--NoteBean rs = (NoteBean) list.get(i);--%>
                <%--%>--%>

                <%--<tr>--%>
                    <%--<td class="display-4"><%=rs.NBuyName %></td>--%>
                    <%--<td class="display-4"><%=rs.Ntime %></td>--%>
                    <%--&lt;%&ndash;<td><a href="../ActiveUser_find?json=<%=rs.getAppID()%>"><%=statisticalDao.getStatisticalNum4Appid(rs.getAppID()) %></a></td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<td><%=rs.getAddress() %></td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<td><%=rs.getEndTime() %></td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<td style="height: 45px;width:80px"><%=rs.getLast_use_date() %></td>&ndash;%&gt;--%>
                        <%--<td class="display-4"><a href="../AppWebHomeToDetailIO?json=<%=rs.NBuyName%>">详情</a></td>--%>
                        <%--&lt;%&ndash;<td><a href="../company_find_4log?json=<%=rs.NBuyName%>">程序更新日志</a></td>&ndash;%&gt;--%>
                <%--</tr>--%>
                <%--</tbody>--%>
                <%--<%} %>--%>
            <%--</table>--%>
        <%--</div>--%>

    <%--</div>--%>
</div>

</body>


</html>
