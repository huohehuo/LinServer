<%--
  Created by IntelliJ IDEA.
  User: NB
  Date: 2017/8/7
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" import="Bean.RegisterBean" import="WebSide.WebDao"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="WebSide.Info" %>
<%@ page import="Utils.CommonUtil" %>
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

<div>
    <br/>
    <h2 style="width: 200px;text-align:center">用户控制->
        </h2>
</div>



    <div id="vue_list"   class="card" style="margin: 10px">
        <div style="margin: 20px;padding-left: 20px;padding-top: 10px">
            <h5 >当天请求注册的数据---{{size}}</h5>
            <div  class="form-inline" >
                <a>时间过滤查询</a>
                <input type="text" class="form-control" id="seach" placeholder="Enter your name" name="seach" v-model="search_name"
                       style="width: 50%;margin: 10px">
            </div>

        </div>
        <hr/>

        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th>公司名称</th>
                    <th>APPID</th>
                    <th>备注</th>
                    <th>注册码</th>
                    <th>请求时间</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="storage in registerCodeBeans">
                    <td>{{storage.NBuyName}}</td>
                    <td>{{storage.Ntime}}</td>
                    <%--<td>{{storage.note}}</td>--%>
                    <%--<td>{{storage.register_code}}</td>--%>
                    <%--<td>{{storage.register_time}}</td>--%>
                </tr>
                </tbody>
            </table>
        </div>

    </div>
    <%--<div class="registerhtml" style="width: 48%">--%>
        <%--<iFrame src=" http://148.70.108.65:8080/fangzuo/ui/Setting.html" width="48%" height="100%"></iFrame>--%>
    <%--</div>--%>

</div>

<%--当日请求注册的公司数据--%>




<script>
    var check = new Vue({
        el: '#vue_list',
        data :{
            loading_text: '',
            search_name: '<%=thistime%>',
            counter: 1,
            size: 0,
            bjson: '',
            urlset: 'http://<%=basevuelink%>/AppGetNoteDataIO',
//            urlset: 'http://148.70.108.65:8080/LogAssist/GetRegisterUser4Web',
            info: null,
            registerCodeBeans:[]
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
                    getApp.registerCodeBeans =response.data
//                    getApp.size =response.data.size
                    }).catch(function (error) {
                    getApp.loading_text = "查找错误" + error;
                        console.log(error);
                    getApp.storeList = 'Error! Could not reach the API. ' + error
                    })
            }
        }

//        watch:{

//        },

    });
    check.$watch('search_name', function(nval, oval) {
        check.getStoreData();

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
<br/>


</body>


</html>
