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
</head>
<body>
<%
    String userName = (String)session.getAttribute(Info.FUserNameKey);
    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
    }

    String basevuelink = Info.BaseVueLink;
%>
<jsp:include page="../headLayout.jsp"/>
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

<div>
    <br/>
    <h2 style="width: 200px;text-align:center">项目管理-></h2>
</div>
<hr/>

<div class="container" style="margin-top: 88px">
    <div  id="vue_company"  class="card">
        <div class="card-header">
            <div  class="form-inline" >
                <button type="button" class="btn btn-outline-primary" value="新增项目信息" onclick="location.href='Company_create.jsp'">新增项目信息</button>
                <%--<input type="text" class="form-control" id="seach" placeholder="Search" name="seach" v-model="search_name" style="width: 50%;margin: 10px">--%>
            </div>
        </div>
        <div class="card-body">
            <%--<table class="table">
                <thead>
                <tr>
                    <th>公司名称 {{size}}</th>
                    <th>APP版本号</th>
                    <th>当前用户数</th>
                    <th>公司地址</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="storage in companies">
                    <td>{{storage.CompanyName}}</td>
                    <td>{{storage.AppVersion}}</td>
                    <td>{{storage.has_user_num}}</td>
                    <td>{{storage.Address}}</td>
                    &lt;%&ndash;<td><button @click="toLink()">{{storage.AppID}}</button></td>&ndash;%&gt;
                    &lt;%&ndash;<router-link :to="{path:'testDemo',query:{setid:123456}}">&ndash;%&gt;
                        &lt;%&ndash;<button>点击跳转1</button>&ndash;%&gt;
                    &lt;%&ndash;</router-link>&ndash;%&gt;

                    &lt;%&ndash;<td><a href="<%=basevuelink%>{{storage.AppID}}">管理</a></td>&ndash;%&gt;
                    <td><a href="../company_find_4log?json={{storage.AppID}}">程序更新日志</a></td>
                </tr>
                </tbody>
            </table>--%>

            <table class="table">
                <thead>
                <%
                    CompanyDao aa = new CompanyDao();
                    StatisticalDao statisticalDao = new StatisticalDao();
                    String statisticalNum = statisticalDao.getStatisticalNum();
                    //    List list = (List) request.getAttribute("pl_list");
                    List list = aa.getCompany();
                    if (list==null){
                %><div class="alert alert-info"> 列表数据为空</div><%
                        return;
                    }
                %>
                <tr>
                    <th>公司名称(<%=list.size()%>)</th>
                    <th>APP版本号</th>
                    <th>当前用户数(<%=statisticalNum%>)</th>
                    <th>公司地址</th>
                    <%--<th>时间控制日期</th>--%>
                </tr>
                </thead>
                <tbody>
                <%

                    for (int i = 0; i < list.size(); i++) {
                        Company rs = (Company) list.get(i);
                %>

                <tr>
                    <td><%=rs.getCompanyName() %></td>
                    <td><%=rs.getAppVersion() %></td>
                    <td><a href="../ActiveUser_find?json=<%=rs.getAppID()%>"><%=statisticalDao.getStatisticalNum4Appid(rs.getAppID()) %></a></td>
                    <td><%=rs.getAddress() %></td>
                    <td><%=rs.getEndTime() %></td>
                    <%--<td style="height: 45px;width:80px"><%=rs.getLast_use_date() %></td>--%>
                        <td><a href="../company_find?json=<%=rs.getAppID()%>">管理</a></td>
                        <td><a href="../company_find_4log?json=<%=rs.getAppID()%>">程序更新日志</a></td>
                </tr>
                </tbody>
                <%} %>
            </table>
        </div>

    </div>
</div>

<script>
    var checkCompany = new Vue({
        el: '#vue_company',
        data :{
            setting_url: 'http://<%=basevuelink%>/company_find',
            loading_text: '',
            search_name: '',
            counter: 1,
            size: 0,
            bjson: '',
            urlset: 'http://<%=basevuelink%>/GetCompany4Web',
            info: null,
            companies:[]
        },

        mounted:function(){
            this.getStoreData();
        },
        // 在 `methods` 对象中定义方法
        methods: {
            getStoreData: function (event) {
                this.loading_text = "正在查找...";
                var getApp = this;
                axios.get(getApp.urlset, {
                    params: {
                        seach: getApp.search_name,
                    }
                }).then(function (response) {
                    getApp.loading_text = "";
                    //                console.log(response);
                    getApp.companies =response.data.companies
                    getApp.size =response.data.size
                }).catch(function (error) {
                    getApp.loading_text = "查找错误" + error;
                    console.log(error);
                    getApp.storeList = 'Error! Could not reach the API. ' + error
                })
            },
            toLink: function (event) {
                var getApp = this;
                axios.get(getApp.setting_url, {
                    params: {
                        json: getApp.loading_text,
                    }
                }).then(function (response) {
//                    getApp.loading_text = "";
                                    console.log(response);
//                    getApp.companies =response.data.companies
//                    getApp.size =response.data.size
                }).catch(function (error) {
//                    getApp.loading_text = "查找错误" + error;
                    console.log(error);
//                    getApp.storeList = 'Error! Could not reach the API. ' + error
                })
            }
        }

//        watch:{

//        },

    });
    checkCompany.$watch('search_name', function(nval, oval) {
        checkCompany.getStoreData();

////        alert('计数器值的变化 :' + oval + ' 变为 ' + nval + '!'+check.urlset);
//        axios.post(check.url2,check.post_data)
//        .then(function (response) {
//        console.log(response);
//            check.bjson =response.data.storages;
//        //                        vm.answer = _.capitalize(response.data.answer)
//        })
//        .catch(function (error) {
//        console.log(error);
//        check.bjson = 'Error! Could not reach the API. ' + error
//        })

    });

</script>

</body>


</html>
