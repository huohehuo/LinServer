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
<%@ page import="Bean.LiveDataBean" %>
<%@ page import="Utils.Lg" %>
<%@ page import="Utils.MathUtil" %>
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

    <script src="https://code.highcharts.com.cn/highcharts/highcharts.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/modules/exporting.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/modules/series-label.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/modules/oldie.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts-plugins/highcharts-zh_CN.js"></script>


</head>
<%
    String userName = (String)session.getAttribute(Info.FUserNameKey);
    if (null == userName || "".equals(userName)){//若本地session不存在登录用户的缓存数据，则跳到登录界面
        response.sendRedirect(request.getContextPath()+"/MGM/login.jsp");
    }

    String thistime = CommonUtil.getTime(false);
    String basevuelink = Info.BaseVueLink;
    StatisticalDao statisticalDao = new StatisticalDao();
    String thisMon= CommonUtil.getTime(true);
    List<LiveDataBean> liveData = statisticalDao.getStatisticalLiveData4User(thisMon.substring(0,thisMon.length()-2));
//    List<LiveDataBean> liveData4Num = statisticalDao.getStatisticalLiveData4Num(thisMon.substring(0,thisMon.length()-2));//无法统计每天活跃度
    List<String> dayList = new ArrayList<>();
//    List<String> dayList4Num = new ArrayList<>();
    for (int i = 0; i <= 31; i++) {
        dayList.add(i,"0");
//        dayList4Num.add(i,"0");
    }
//    Lg.e("得到daylist1111"+dayList.size(),dayList);

    /*活跃用户数*/
    for (int i = 0; i <= 32; i++) {
        for (int j = 0; j < liveData.size(); j++) {
            Lg.e("liveData"+i,MathUtil.toInt(liveData.get(j).LDay));
            if (MathUtil.toInt(liveData.get(j).LDay)==i){
                Lg.e("替换"+i+"--"+liveData.get(j).LNum);
                dayList.set(i-1,liveData.get(j).LNum);
            }
        }
    }
%>
<body>
<jsp:include page="../headLayout.jsp"/>
<%--

    String tips = (String) request.getAttribute("tips");
--%>

<div>
    <br/>
    <h2 style="width: 200px;text-align:center">往月数据->
        </h2>
</div>
    <div class="card" style="margin: 10px">
        <div style="margin: 20px;padding-left: 20px;padding-top: 10px">
            <h5 >当天数据</h5>
            <div  class="form-inline" >
                <a>时间过滤查询</a>
                <input type="text" class="form-control" id="seach" placeholder="Enter your name" name="seach" v-model="search_name"
                       style="width: 50%;margin: 10px">
            </div>

        </div>
        <hr/>
        <div id="container" style="width:100%;height:400px"></div>
        <%--<div class="card-body">
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
                    <td>{{storage.CompanyName}}</td>
                    <td>{{storage.AppID}}</td>
                    <td>{{storage.note}}</td>
                    <td>{{storage.register_code}}</td>
                    <td>{{storage.register_time}}</td>
                </tr>
                </tbody>
            </table>
        </div>--%>

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
            box_text: '活跃情况',
            loading_text: '',
            search_name: '<%=thistime%>',
            counter: 1,
            size: 0,
            bjson: '',
            urlset: 'http://<%=basevuelink%>/GetRegisterUser4Web',
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
                    getApp.registerCodeBeans =response.data.registerCodeBeans
                    getApp.size =response.data.size
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

    });

    // JS 代码
    var chart = Highcharts.chart('container', {
        title: {
            text: '{{box_text}}'
        },
//        subtitle: {
//            text: '数据来源：thesolarfoundation.com'
//        },
        yAxis: {
            title: {
                text: '用户数'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        plotOptions: {
            series: {
                label: {
                    connectorAllowed: false
                },
                pointStart: 1
            }
        },
        series: [{
            name: '活跃人数',
            data:<%=dayList%>
//            data:[5, 10, 20, 30, 30, 30, 2, 20, 30, 30, 30, 2, 20, 30, 30, 30, 2, 20, 30, 30, 30, 2, 20, 30, 30, 30, 2]
        }
// , {
//            name: '工人',
//            data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
//        }, {
//            name: '销售',
//            data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
//        }, {
//            name: '项目开发',
//            data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
//        }, {
//            name: '其他',
//            data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
//        }
        ],
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }
    });


var ss = new Vue({
    el:'rgstVue',
    data:{
        T_tip:'',
        T_ip:'',
        T_port:'',
        T_code:''
    },
    watch:{
        T_port:function (newQuestion, oldQuestion) {
            this.T_tip = 'bianhua'
//            this.T_tip = this.T_ip+':'+T_port+'/'+T_code,
//            if(this.Tip.length>4){
//                this.T_tip = '端口：不能超过四位'
//            }else{
//                this.T_tip = '端口：'
//            }
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
    }
});

    function toRegister() {
        var url;
        							console.log("111");
        var ip2 = document.getElementById("wrt_ip").innerText;
        							console.log("111");
        var port2 = document.getElementById("wrt_port").innerText;
        							console.log("111");
        var one = document.getElementById("wrt_register_code").innerText;
        var theone = one + "fzkj601";
        var addpwd = $.md5(theone);
        var cat = addpwd.substring(8, 24);
        var addpwd2 = $.md5(cat);
        var cat2 = addpwd2.substring(8, 24);
        url = "http://" + ip2 + ":" + port2 + "/Assist/RegisterCode" + "?json=" + cat2;
        							console.log(url);
        //							document.getElementById("changeurl").href = url;
        //				document.getElementById("theurl").innerHTML = url;
        //document.getElementById("tvcode").value = url;
        // window.location.href=url;
        window.open(url, top);
        //				mui.ajax(url,null);
        //											loadXMLDoc(url);

    }

</script>

<%--<%
    //    List list = (List) request.getAttribute("pl_list");
    CompanyDao aa = new CompanyDao();
    List list = aa.getCompany();
    List<TestB> testBS = new ArrayList<>();

//    String[] strings = new String[]{"{ text: 'One', value: 'A' }","{ text: 'One2', value: 'B' }","{ text: 'One3', value: 'C' }"};
//    strings[0]="{ text: 'One', value: 'A' }";
//    strings[1]="";
//    strings[3]="";
//    strings[4]="{ text: 'One4', value: 'D' }";
//    strings[5]="{ text: 'One5', value: 'E' }";
%>--%>
<%--<div id="example-5">--%>
    <%--<select v-model="selected">--%>
        <%--<option disabled value="">请选择</option>--%>
        <%--<%--%>
            <%--for (int i = 0; i < list.size(); i++) {--%>
                <%--Company rs = (Company) list.get(i);--%>
                <%--testBS.add(new TestB(rs.getCompanyName(), rs.getAppID()));--%>

        <%--%>--%>
        <%--<option><%=rs.getCompanyName()%>--%>
        <%--</option>--%>
        <%--<%--%>
            <%--}--%>
            <%--String res = new Gson().toJson(testBS);--%>
        <%--%>--%>
    <%--</select>--%>
    <%--<span id="getSelectData">Selected: {{ selected }}</span>--%>
<%--</div>--%>
<br/>

<%--<div id="select2">--%>
    <%--<select v-model="selected">--%>
        <%--<option v-for="option in options" v-bind:value="option.value">--%>
            <%--{{ option.text }}--%>
        <%--</option>--%>
    <%--</select>--%>
    <%--<span>Selected: {{ selected }}</span>--%>
<%--</div>--%>
<%--<br/>--%>


<%--<div id="app-5">--%>
    <%--<p>{{ message }}</p>--%>
    <%--<button v-on:click="reverseMessage">反转消息</button>--%>
    <%--<button v-on:click="toSet('')">修改消息</button>--%>
<%--</div>--%>


<%--<div id="watch-example">--%>
    <%--<p>--%>
        <%--Ask a yes/no question:--%>
        <%--<input v-model="question">--%>
    <%--</p>--%>
    <%--<p>{{ answer }}</p>--%>
<%--</div>--%>
<script>
    var watchExampleVM = new Vue({
        el: '#watch-example',
        data: {
            question: '',
            answer: 'I cannot give you an answer until you ask a question!'
        },
        watch: {
            // 如果 `question` 发生改变，这个函数就会运行
            question: function (newQuestion, oldQuestion) {
                this.answer = 'Waiting for you to stop typing...'
                this.debouncedGetAnswer()
            }
        },
        created: function () {
            // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
            // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
            // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
            // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
            // 请参考：https://lodash.com/docs#debounce
            this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
        },
        methods: {
            getAnswer: function () {
//                if (this.question.indexOf('?') === -1) {
//                    this.answer = 'Questions usually contain a question mark. ;-)'
//                    return
//                }
                this.answer = 'Thinking...'
                var vm = this
                axios.get('http://148.70.108.65:8080/LogAssist/UserIO')
                    .then(function (response) {
                        console.log(response);
                        vm.answer = _.capitalize(response)
//                        vm.answer = _.capitalize(response.data.answer)
                    })
                    .catch(function (error) {
                        console.log(error);
//                        vm.answer = error

                        vm.answer = 'Error! Could not reach the API. ' + error
                    })
            }
        }
    })
    var slel = new Vue({
        el: '#example-5',
        data: {
            selected: ''
        }
    })

    var test = new Vue({
        el: '#app-5',
        data: {
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {
                this.message = this.message.split('').reverse().join('')
            },
            toSet: function (str) {
                this.message = document.getElementById("getSelectData").innerText
            }
        }
    })

    var dle = new Vue({
        el: '#select2',
        data: {
            selected: 'A',
            <%--options: <%=res%>--%>
//            options: [{"text":"one","value":"A"},{"text":"one2","value":"B"},{"text":"one3","value":"C"}]
//            options: [
//                { text: 'One', value: 'A' },
//                { text: 'Two', value: 'B' },
//                { text: 'Three', value: 'C' }
//            ]
        }
    })

</script>

<hr/>


<%--<div class="container" style="margin-top: 88px">--%>
<%--<div  class="card">--%>
<%--<div class="card-body">--%>
<%--<table class="table">--%>
<%--<thead>--%>
<%--<%--%>
<%--CompanyDao aa = new CompanyDao();--%>
<%--UserControlDao userControlDao = new UserControlDao();--%>
<%--//                    List userControlList = userControlDao.getUserControlList();--%>
<%--//                    String statisticalNum = statisticalDao.getUserNum4Appid();--%>
<%--%>--%>
<%--<tr>--%>
<%--<th>公司名称</th>--%>
<%--<th>APP版本号</th>--%>
<%--<th>用户数</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--<%--%>

<%--//    List list = (List) request.getAttribute("pl_list");--%>
<%--List list = aa.getCompany();--%>
<%--if (list==null){--%>
<%--%><div class="alert alert-info"> 列表数据为空</div><%--%>
<%--return;--%>
<%--}--%>
<%--for (int i = 0; i < list.size(); i++) {--%>
<%--Company rs = (Company) list.get(i);--%>
<%--%>--%>

<%--<tr>--%>
<%--<td><%=rs.getCompanyName() %></td>--%>
<%--<td><%=rs.getAppVersion() %></td>--%>
<%--<td><%=userControlDao.getUserControlNum4Appid(rs.getAppID() )%></td>--%>
<%--&lt;%&ndash;<td><%=rs.getAppID() %></td>&ndash;%&gt;--%>
<%--&lt;%&ndash;<td style="height: 45px;width:80px"><%=rs.getLast_use_date() %></td>&ndash;%&gt;--%>
<%--<td><a href="../company_find_4upgrade?json=<%=rs.getAppID()%>">管理</a></td>--%>
<%--</tr>--%>
<%--</tbody>--%>
<%--<%} %>--%>
<%--</table>--%>
<%--</div>--%>

<%--</div>--%>
<%--</div>--%>


<%--<table border="0" bgcolor="ccceee" width="750" style="height: 161px;">
    <tr bgcolor="CCCCCC" align="center">
        <td style="height: 30px;width:80px ">用户码</td>
        <td style="height: 30px;width:180px ">手机IMIE码</td>
        <td style="height: 30px;width:80px ">版本号</td>
        <td style="height: 30px;width:80px ">操作</td>

    </tr>

    <tr align="center">
        <td style="height: 45px; width:80px"><%=rs.getRegister_code() %>
        </td>
        <td style="height: 45px; width:180px"><%=rs.getVal1() %>
        </td>
        <td style="height: 45px; width:80px"><%=rs.getVal2() %>
        </td>
        &lt;%&ndash;<td style="height: 45px;width:80px"><%=rs.getLast_use_date() %></td>&ndash;%&gt;
        <td width="80px" align="center"><a href="RegisterDelete?json=<%=rs.getRegister_code() %>">删除</a></td>
    </tr>
    <%} %>

</table>--%>
</body>


</html>
