<%--
  Created by IntelliJ IDEA.
  User: NB
  Date: 2017/8/7
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="MqttBox.MqttUserDao" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Utils.Lg" %>
<%@ page import="MqttBox.MqttUserBean" %>
<%@ page import="MqttBox.TypeBean" %>
<html>
<head>
    <title>用户管理</title>
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
//    String userName = (String)session.getAttribute(Info.FUserNameKey);
//    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
//        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
//    }
    String ip = java.net.InetAddress.getLocalHost().getHostAddress();
    String port = request.getLocalPort()+"";
    String basevuelink =ip+":"+port+ request.getContextPath();//当为ip+port时，jquery的请求会正常执行，
//                                                              当用户用的是域名时，需把这里改为相对应的域名，则jquery才能正常更改数据（跨域问题）
//    String basevuelink ="www.linspace.club"+ request.getContextPath();
        Lg.e("链接",basevuelink);
//    String basevuelink = Info.BaseVueLink;
    MqttUserDao aa = new MqttUserDao();
%>
<jsp:include page="../headLayout.jsp"/>

<div>
    <br/>
    <h2 style="width: 200px;text-align:center">用户管理-></h2>
</div>
<hr/>

<div  style="margin: 88px">
    <div  id="vue_company"  class="card">
        <div class="card-header">
                <%--<button type="button" class="btn btn-outline-primary" value="新增项目信息" onclick="location.href='Company_create.jsp'">新增项目信息</button>--%>
                <%--<input type="text" class="form-control" id="seach" placeholder="Search" name="seach" v-model="search_name" style="width: 50%;margin: 10px">--%>

                    <form action="../AppUserAddIO" method="post" style="padding: 8px">
                        <div class="form-inline" style="margin-bottom: 10px">
                            <div class="form-group" style="width: 50%">
                                <a style="margin-right: 20px">新增用户:</a><a style="color: red"  id="jqview">{{bjson}}</a>
                                <input type="text" class="form-control" id="name" placeholder="Enter your name"  v-model="search_name" name="name"
                                       style="width: 100%;margin-right: 10px">
                            </div>
                            <div class="form-group" style="width: 50%">
                                <a  style="margin-right: 20px">密码:</a>
                                <input type="text" class="form-control" id="pwd" placeholder="Enter password" name="pwd"
                                       style="width: 100%;margin-right: 10px">
                            </div>
                        </div>
                        <h4 >设置对应的关联 Cloud 帐号信息</h4>
                        <div class="form-inline" style="margin-bottom: 10px">
                            <div class="form-group" style="width: 50%">
                                <a style="margin-right: 20px">Cloud帐号:</a>
                                <input type="text" class="form-control" id="name_c" placeholder="Enter your name" name="name_c"
                                       style="width: 100%;margin-right: 10px">
                            </div>
                            <div class="form-group" style="width: 50%">
                                <a  style="margin-right: 20px">Cloud密码:</a>
                                <input type="text" class="form-control" id="pwd_c" placeholder="Enter password" name="pwd_c"
                                       style="width: 100%;margin-right: 10px">
                            </div>
                        </div>
                        <h4 >设置对应的账套信息</h4>
                        <a id="selectA">dddd</a>
                        <select id="selectType" style="width:150px" name="state" selectedIndex="$!{state}">
                            <%
                                //    List list = (List) request.getAttribute("pl_list");
                                List listType = aa.findAllType();
                                for (int i = 0; i < listType.size(); i++) {
                                    TypeBean rs = (TypeBean) listType.get(i);
                            %>
                            <option value="<%=rs.FNumber %>"><%=rs.FName %></option>
                            <%
                                }
                            %>
                        </select>


                        <button type="submit" class="btn btn-primary">确定添加</button>
                    </form>

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
                    //    List list = (List) request.getAttribute("pl_list");
                    List list = aa.findAll();
                    if (list==null){
                %><div class="alert alert-info"> 列表数据为空</div><%
                        return;
                    }
                %>
                <tr>
                    <th>用户名Code(<%=list.size()%>)</th>
                    <th>Token</th>
                    <%--<th>关联Cloud账户名</th>--%>
                    <%--<th>关联Cloud密码</th>--%>
                    <%--<th>时间控制日期</th>--%>
                </tr>
                </thead>
                <tbody>
                <%

                    for (int i = 0; i < list.size(); i++) {
                        MqttUserBean rs = (MqttUserBean) list.get(i);
                %>

                <tr>
                    <td><%=rs.FName_code %></td>
                    <td><%=rs.FToken %></td>
                    <%--<td><%=rs.user_name_link %></td>--%>
                    <%--<td><%=rs.user_pwd_link %></td>--%>
                    <%--<td><a href="../ActiveUser_find?json=<%=rs.getAppID()%>"><%=statisticalDao.getStatisticalNum4Appid(rs.getAppID()) %></a></td>--%>

                    <%--<td style="height: 45px;width:80px"><%=rs.getLast_use_date() %></td>--%>
                    <%--<td><a href="../AppUserDeleteIO?json=<%=rs.user_name%>">删除</a></td>--%>
                    <%--<td><a href="../company_find_4log?json=<%=rs.getAppID()%>">程序更新日志</a></td>--%>
                </tr>
                </tbody>
                <%} %>
            </table>
        </div>

    </div>
</div>

<script>
    $(function(){
        $('#name').bind('input propertychange', function() {
//            var ss = document.getElementsByName("name")[0].value
            var ss = $('input[name="name"]').val();
            $.get("http://<%=basevuelink%>/AppUserCheckIO?seach="+ss, function(data){
                $("#jqview").text(data);
//                alert("Data: " + data);
            });
            <%--$.ajax({--%>
                <%--type: "get",--%>
                <%--cache: false,--%>
                <%--data: {'_ajax': 1, facId: facId},--%>
                <%--url: 'http://<%=basevuelink%>/AppUserCheckIO?seach='+ss,--%>
                <%--dataType: "jsonp",--%>
                <%--success: function (data) {--%>
                    <%--if(data){--%>
                        <%--$("#jqview").text(data);--%>
<%--//                        $('#jqview').text('用户名是：');--%>
<%--//                        $("#jqview").val(data);--%>
                    <%--}--%>
                <%--}--%>
            <%--});--%>
//            $('#jqview').text('用户名是：'+$(this).val());
        });
        var  ops=document.getElementById("selectType");
        var index = ops.selectedIndex;
        ops.options[index].value();
        ops.options[index].text();
                $("#selectA").text(ops.options[index].value());
    });
    var checkCompany = new Vue({
        el: '#vue_company',
        data :{
            setting_url: 'http://<%=basevuelink%>/AppUserCheckIO',
            loading_text: '',
            search_name: '',
            counter: 1,
            size: 0,
            bjson: '',
            <%--urlset: 'http://<%=basevuelink%>/GetCompany4Web',--%>
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
                axios.get(getApp.setting_url, {
                    params: {
                        seach: getApp.search_name,
                    }
                }).then(function (response) {
                    getApp.loading_text = "";
                    //                console.log(response);
                    getApp.bjson =response.data;
//                    getApp.size =response.data.size
                }).catch(function (error) {
                    getApp.loading_text = "查找错误" + error;
                    console.log(error);
//                    getApp.storeList = 'Error! Could not reach the API. ' + error
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
