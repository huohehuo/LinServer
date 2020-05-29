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
<%--<%@ page import="com.sun.org.apache.xpath.internal.operations.String" %>--%>
<%@ page import="WebSide.Info" %>
<%@ page import="Utils.Lg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>

    <!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>

    <!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/home_style.css">
    <script src="../js/jquery.js"></script>
    <script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
    <%--<script src="../js/vue.min.js"></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
</head>
<body class="col-sm-12" background="../img/login_bg.png">

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
<%
    //置空登录用户名
    session.setAttribute(Info.FUserNameKey,null);
    String ip = java.net.InetAddress.getLocalHost().getHostAddress();
    int port = request.getLocalPort();
//    String user = (String)session.getAttribute(Info.UserNameKey);
//    String userName = (String)session.getAttribute(Info.UserNameKey);
%>
<div class="row justify-content-center" style="margin-top: 88px">
    <div id="vue_data_list" class="card" style="padding: 50px">
        <form action="../LoginAppIO" method="post">
        <%--<form action="" name="form_login" method="post">--%>
            <div class="login">
                <h2 class="row justify-content-center" style="margin-bottom: 50px">方左科技数据中心</h2>
                <div class="login-top">
                        <div class="form-inline" style="margin-bottom: 10px">
                            <div class="form-group" style="width: 100%">
                                <a style="margin-right: 20px">用户名:</a>
                                <input type="text" class="form-control" id="user_name" placeholder="Enter name" name="user_name" value="user"
                                       style="width: 100%;margin-right: 10px">
                            </div>
                        </div>
                        <div class="form-inline" style="margin-bottom: 50px">
                            <div class="form-group" style="width: 100%">
                                <a  style="margin-right: 20px">密码:</a>
                                <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd" value=""
                                       style="width: 100%;margin-right: 10px">
                            </div>
                        </div>
                </div>

                <button type="submit" class="btn btn-primary"  style="width: 100%">登录</button>
            </div>

            <!-- 模态框 -->
            <div class="modal fade" id="myModal">
                <div class="modal-dialog  modal-lg">
                    <div class="modal-content">
                        <!-- 模态框头部 -->
                        <div class="modal-header">
                            <h4 class="modal-title">配置信息</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <!-- 模态框主体 -->
                        <div class="modal-body">
                            <div id="server_set">
                                <div class="login-top">
                                    <div class="form-inline" style="margin-bottom: 10px">
                                        <div class="form-group" style="width: 70%">
                                            <a style="margin-right: 20px">服务器IP:</a>
                                            <input type="text" class="form-control" id="ip" placeholder="请输入服务器IP" name="ip"
                                                   style="width: 100%;margin-right: 10px" value="192.168.0.15" v-model="ip">
                                        </div>
                                        <div class="form-group" style="width: 30%">
                                            <a style="margin-right: 20px">端口:</a>
                                            <input type="text" class="form-control" id="port" placeholder="请输入服务器端口" name="port"
                                                   style="width: 100%;margin-right: 10px" value="1433" v-model="port">
                                        </div>
                                    </div>
                                    <div class="form-inline" style="margin-bottom: 50px">
                                        <div class="form-group" style="width: 50%">
                                            <a  style="margin-right: 20px">服务器用户:</a>
                                            <input type="text" class="form-control" id="server_name" placeholder="请输入用户" name="server_name"
                                                   style="width: 100%;margin-right: 10px" value="sa" v-model="server_name">
                                        </div>
                                        <div class="form-group" style="width: 50%">
                                            <a  style="margin-right: 20px">密码:</a>
                                            <input type="text" class="form-control" id="server_pwd" placeholder="请输入密码" name="server_pwd"
                                                   style="width: 100%;margin-right: 10px" value="Abc123" v-model="server_pwd">
                                        </div>
                                    </div>

                                    <%--<button type="submit" class="btn btn-primary" style="width: 100%">查询</button>--%>

                                    <div class="form-inline" style="margin-bottom: 50px">
                                        <div class="form-group" style="width: 50%">
                                            <a  style="margin-right: 20px">账套ID:</a>
                                            <input type="text" class="form-control" id="database" placeholder="请输入用户" name="database"
                                                   style="width: 100%;margin-right: 10px" value="AIS20191120161845"  v-model="database">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="server_set_btn">
                                <%--<button v-on:click="getDataBase" class="btn btn-primary" style="width: 100%">获取账套信息</button>--%>
                                <div class="form-group" style="width: 100%">
                                    <h2 style="text-align: center">得到账套:{{size}}</h2>
                                    <a>{{database_obj.dataBaseName}}</a>
                                    <select  v-model="database_obj" style="width: 100%" >
                                        <option v-for="datas of dataList" v-bind:value=datas>{{datas.name}}</option>
                                        <%--<option  v-for="datas in dataList" value="datas.dataBaseID">{{datas.name}}</option>--%>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <!-- 模态框底部 -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
                        </div>

                    </div>
                </div>
            </div>
        </form>
            <%--<a id="set_tip"  v-on:click="getDataBase" style="text-align:right;float:right;margin-top: 5px"  data-toggle="modal" data-target="#myModal">配置信息</a>--%>


    </div>



    <%--<div id="app">--%>
        <%--<select v-model="one">--%>
            <%--<option v-for="x in shuju">{{x.name}}{{x.famname}}</option>--%>
        <%--</select><br>--%>
        <%--<a>获取数据：{{backjson}}</a>--%>
        <%--<div>--%>
            <%--<span>{{one}}</span><br>--%>
            <%--<span>{{oneF}}</span><br>--%>
            <%--<span>{{oneL}}</span>--%>
        <%--</div>--%>
    <%--</div>--%>

</div>
<script type="text/javascript">
    function login() {
        document.form_login.action="../LoginAppIO";
//        document.pay.action = "{:U('../LoginAppIO')}";
        document.form_login.submit();
    }


    /*<a onclick="showSetDiv()" id="set_tip" style="text-align:right;float:right;margin-top: 5px"  data-toggle="modal" data-target="#myModal">配置信息</a>*/
    function showSetDiv(){
        var  otherDiv=document.getElementById('server_set');//获取要显示的div对象
        var  otherBtnDiv=document.getElementById('server_set_btn');//获取要显示的div对象
        var x=document.getElementById('set_tip');//修改显示view后的文本信息
        if(x.innerHTML=="配置信息"){           //通过.innerHTML获取
            otherDiv.style.display="block";//显示
            otherBtnDiv.style.display="block";//显示
            x.innerHTML = "隐藏配置信息"
        }else{
            otherDiv.style.display="none";//显示
            otherBtnDiv.style.display="none";//显示
            x.innerHTML = "配置信息"
        }



    }
    function removeElement(id)
    {
        document.getElementById(id).style.display="none";
    }
    var getDatabase = new Vue({
        el: '#vue_data_list',
        data: {
            ip: '192.168.0.15',
            port: '1433',
            server_name: 'sa',
            server_pwd: 'Abc123',
            database: 'AIS20191120161845',
            database_obj: '',
            database_name: '',
            size: 0,
            bjson:'',
            url_con: 'http://<%=ip%>:<%=port%>/Assist/ConnectDataBaseWeb',
            url_seting: 'http://<%=ip%>:<%=port%>/Assist/SetDataBaseWeb',
            dataList:[]

        },
        // 在 `methods` 对象中定义方法
        methods: {
            getDataBase: function (event) {
                axios.get(getDatabase.url_con, {
                    params: {
                        ip: getDatabase.ip,
                        port:getDatabase.port,
                        server_name: getDatabase.server_name,
                        server_pwd: getDatabase.server_pwd
                    }
                })
                    .then(function (response) {
//                                        console.log(response);
                        //                getDatabase.bjson = _.capitalize(response.returnJson)
                        getDatabase.size = response.data.size
//                        getDatabase.bjson = response.data.dataBaseLists
                        getDatabase.dataList = response.data.dataBaseLists
                    })
                    .catch(function (error) {
                        console.log(error);
                        getDatabase.dataList = 'Error! Could not reach the API. ' + error
                    })
            },
            setDataBaseMsg: function (event) {
                axios.get(getDatabase.url_seting, {
                    params: {
                        ip: getDatabase.ip,
                        port:getDatabase.port,
                        server_name: getDatabase.server_name,
                        server_pwd: getDatabase.server_pwd,
                        database: getDatabase.database_obj.dataBase,
                        database_name: getDatabase.database_obj.name,
                        database_id: getDatabase.database_obj.dataBaseID
//                        database_id: "666s6ss6s"
                    }
                })
                    .then(function (response) {
//                                        console.log(response);
                        //                getDatabase.bjson = _.capitalize(response.returnJson)
//                        getDatabase.size = response.data.size
                        getDatabase.bjson = response.data

                        alert("设置成功:"+getDatabase.database_obj.name)
//                        getDatabase.dataList = response.data.dataBaseLists
                    })
                    .catch(function (error) {
                        console.log(error);
//                        getDatabase.dataList = 'Error! Could not reach the API. ' + error
                    })
            }
        },
        activated: function () {
            getDatabase.getDataBase();
        },
//        updated: function () {
//            getDatabase.getDataBase();
//        },
        created: function () {
            getDatabase.getDataBase();
        }
//        watch:{

//        },

    });
    //选择账套的时候，更新账套输入框的数据
    getDatabase.$watch('database_obj', function(nval, oval) {
        getDatabase.database = getDatabase.database_obj.dataBaseName;
    });
    getDatabase.$watch('ip', function(nval, oval) {
        getDatabase.getDataBase();
    });
    getDatabase.$watch('port', function(nval, oval) {
        getDatabase.getDataBase();
    });
    getDatabase.$watch('server_name', function(nval, oval) {
        getDatabase.getDataBase();
    });
    getDatabase.$watch('server_pwd', function(nval, oval) {
        getDatabase.getDataBase();
    });
</script>
<script>

    let rua=new Vue({
        el:"#app",
        data:{
            shuju:[
                {name:"孙",famname:"悟空"},
                {name:"猪",famname:"八戒"},
                {name:"沙",famname:"悟净"}
            ],
            one:"",
            backjson:""
        },

        computed:{
            oneF(){
                return this.one.charAt(0);
            },
            oneL(){
                return this.one.slice(1,3);
            }
        },
        mounted () {
            axios
                .get('http://192.168.0.106:8080/Assist/WebSideIO')
                .then(function (response) {
                    console.log(response);
                    rua.backjson =response.data
//                        vm.answer = _.capitalize(response.data.answer)
                })
                .catch(function (error) {
                    console.log(error);
//                        vm.answer = error
                    rua.backjson = 'Error! Could not reach the API. ' + error
                })
        },
        methods:{
            toRgist:function (str) {
                var theone = this.T_code + 'fzkj601';
                var addpwd = $.md5(theone);
                var cat = addpwd.substring(8, 24);
                var addpwd2 = $.md5(cat);
                var cat2 = addpwd2.substring(8, 24);
//                url = "http://" + ip2 + ":" + port2 + "/Assist/RegisterCode" + "?json=" + cat2;

                axios.get('http://'+this.T_ip+':'+this.T_port+'/Assist/RegisterCode?json='+cat2)
                    .then(function (response) {
                        console.log(response);
                        vm.testTxtC = _.capitalize(response)
//                        vm.answer = _.capitalize(response.data.answer)
                    })
                    .catch(function (error) {
                        console.log(error);
//                        vm.answer = error
                        vm.testTxtC = 'Error! Could not reach the API. ' + error
                    })

            }
        }
    });
</script>



</body>


</html>
