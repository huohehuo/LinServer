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
<%@ page import="Bean.Company" %>
<%@ page import="Utils.BaseData" %>
<%@ page import="Bean.TestB" %>
<%@ page import="WebSide.StatisticalDao" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="Utils.CommonUtil" %>
<%@ page import="Bean.RegisterCodeBean" %>
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
    <%--<link rel="stylesheet" href="../js/vue.min.js">--%>
    <%--<link rel="stylesheet" href="../js/vue.min.js" />--%>
    <script src="../js/jquery.js"></script>
    <script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/vant@2.8/lib/index.css"
    />
    <script src="https://cdn.jsdelivr.net/npm/vant@2.8/lib/vant.min.js"></script>
</head>
<%
//    String userName = (String)session.getAttribute(Info.FUserNameKey);
//    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
//        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
//    }

    String thistime = CommonUtil.getTime(false);
    String basevuelink = Info.BaseVueLink;
%>
<body>
<%--<jsp:include page="../headLayout.jsp"/>--%>
<%--

    String tips = (String) request.getAttribute("tips");
--%>

<div style="margin-left: 12px;">

    <h1 class="display-3">用户使用说明</h1>


    <br/>
    <h2 class="display-4">
        本软件为开发交流使用
    </h2><br/>
    <h2 class="display-4">
        主要用于记录平时汇总的记账信息
    </h2><br/>
    <h2 class="display-4">
        仅提供简单的数据备份，请勿记录涉及隐私权限过高的东西以防丢失
    </h2><br/>
    <h2 class="display-4">
        本软件服务器较为简单，网络因素备份失败请多试几次
    </h2><br/>
    <h2 class="display-4">
        网络备份仅限必要时使用，不保证数据有效性
    </h2>
    <hr/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <h2>
        反馈邮箱:753392431@qq.com
    </h2><br/>
    <h2>
        vx:Ijustcalltosay
    </h2><br/>
</div>



<hr/>

</body>


</html>
