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
<%@ page import="ServerVueWeb.Bean.NoteBean" %>
<%@ page import="Utils.CommonUtil" %>
<%@ page import="Utils.Lg" %>
<%@ page import="App4GoodsNotes.FileManager" %>
<%@ page import="App4GoodsNotes.BaseUtil" %>
<html>
<head>
    <title>文件列表</title>
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
    Lg.e("页面",getClass().getSimpleName());
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");
//    String userCode = (String)session.getAttribute(Info.FUser_Code);
    String baseUrl = request.getContextPath();
//    String userCode = "9a2c9e2e995c577b";
//    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
//        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
//    }
    List<String> list = FileManager.getXlsListByDir(BaseUtil.baseFileUrl);
    Lg.e("baseUrl",baseUrl);
    Lg.e("得到文件列表",list);


    //http://192.168.31.55:8085/Assist/App4GoodsNotes/App4GoodsNotesFile.jsp
%>

<hr/>
<header class="mui-bar mui-bar-nav">
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" ></a>--%>
    <h1 class="mui-title">文件列表</h1>
</header>

<div style="margin: 50px">
    <ul class="mui-table-view">

        <%

            for (int i = 0; i < list.size(); i++) {
//                NoteBean rs = (NoteBean) list.get(i);
                String Url = baseUrl+"/BackUpGoodsNotesDelete?json="+ CommonUtil.getURLEncoderString(list.get(i));
//                String Url = new String(Urlss.getBytes("UTF-8"),"UTF-8");
                String[] split = list.get(i).split("\\\\");
               String filename = split[split.length-1];

        %>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" href="<%=BaseUtil.baseDownUrl+"LinsServerFiles/AppExcel4GoodsNotes/"+filename%>"><%=filename%></a>
            <span class="mui-badge mui-badge-success"><a href="<%=Url%>">删除</a></span>
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
