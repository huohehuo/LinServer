package ServerVueWeb.Test;

import ServerVueWeb.Bean.AppLinkBean;
import Utils.Lg;
import Utils.MD5;
import Utils.MathUtil;
import WebSide.UserDao;
import WebSide.WebResponse;
import com.google.gson.Gson;
import sun.misc.LRUCache;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static WebSide.Utils.HttpRequestUtils.ReadAsChars;

/**
 * 用于更新时间控制表的当前时间
 */
@WebServlet(urlPatterns = "/TestIO")
public class TestIO extends HttpServlet {
    Gson gson;
    @Override
    public void init() throws ServletException {
        Lg.e("进入"+getClass().getSimpleName());
        gson = new Gson();
    }
    private static List<String> stringList = new ArrayList<>();
//    private static List<String> stringList = new Vector<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Lg.e("进入"+getClass().getSimpleName());
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        TestDao testDao = new TestDao();
        UserDao userDao = new UserDao();

        String getbody=null;
        String filename="";
        String parameter= null;
        try{
            parameter = ReadAsChars(request);//解密数据
        }catch (Exception e){
            response.getWriter().write(gson.toJson(new WebResponse(false,"上传失败,请求体解析错误")));
        }

        WebResponse pBean = gson.fromJson(parameter, WebResponse.class);
        Lg.e("得到："+pBean.json);
        if (stringList.contains(pBean.json)){
            Lg.e("请勿重复写入:"+pBean.json);
        }else{
            stringList.add(pBean.json);
            Lg.e(stringList.size()+"写入:"+stringList);
        }

        if (stringList.size()>60){
            Lg.e("超过 20 ");
            List<String> strings = new ArrayList<>();
            for(int i = 0;i <=20;i++) {
                strings.add(stringList.get(i));
            }
            stringList.removeAll(strings);
            Lg.e("剩余",stringList);
        }
//        Lg.e("写入"+reqCache);



//        testDao.saveData(pBean.json);
//
//
//        AppLinkBean loginBean = gson.fromJson(pBean.json, AppLinkBean.class);
//        loginBean.FToken = System.currentTimeMillis()+"";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date();
//        loginBean.FCreateTime = format.format(curDate);
//        boolean isOK;
//            try {
//                //复制db文件并重命名为登录用户的所属db文件
//                filename = MD5.getMD5(loginBean.FName);//避免中文时乱码，都一律转换成md5
//                loginBean.FName_code = filename;
////                session.setAttribute(Info.FUserDbName, filename);//记录md5处理过的用户名，用于区分db文件并获取数据
////                if (FileControl.hasFile(Info.copyDbFile(filename))){//存在文件，修改
//                    if (MathUtil.toD(userDao.checkHasUser(loginBean.FName,loginBean.FPwd))>0){//db中是否有数据
//                        Lg.e("存在用户数据");
//                        isOK = userDao.changeUser(loginBean,filename);//修改数据
//                    }else{
//                        Lg.e("不存在用户数据，新增");
//                        isOK = userDao.addUserDB(loginBean,filename);//添加数据到db文件
//                    }
////                }else{//不存在文件，创建
////                    FileControl.copyFile(Info.BaseDbFile,Info.copyDbFile(filename));
////                    isOK = userDao.addUserDB(loginBean,filename);//添加数据到db文件
////                }
//                WebResponse back = new WebResponse();
//                back.state = isOK;
//                back.backString="处理完毕";
//                back.json = filename;
//                back.FToken = System.currentTimeMillis()+"";
//                response.getWriter().write(gson.toJson(back));
//            } catch (Exception e) {
//                Lg.e("文件或数据处理出错....");
//                WebResponse back = new WebResponse();
//                back.state = false;
//                back.backString="文件或数据处理出错";
//                back.json = filename;
//                back.FToken = System.currentTimeMillis()+"";
//                response.getWriter().write(gson.toJson(back));
//            }

        // 如果不存在 session 会话，则创建一个 session 对象
//        HttpSession session = request.getSession(true);
//        String sssss = (String)session.getAttribute(Info.UserNameKey);
//        Lg.e("之前session"+sssss);
//        String userName = request.getParameter("user_name");
//        String pwd = request.getParameter("pwd");
//        String ip = request.getParameter("ip");
//        String port = request.getParameter("port");
//        String sname = request.getParameter("server_name");
//        String  spwd = request.getParameter("server_pwd");
//        String database = request.getParameter("database");
//        Lg.e(userName+pwd);
//        if ("admin".equals(userName) && "fangzuokeji601.".equals(pwd)){
//            session.setAttribute(Info.FUserNameKey, userName);
//            session.setAttribute(Info.FUserPwdKey, pwd);
//            session.setAttribute(Info.FServerIPKey, ip);
//            session.setAttribute(Info.FServerPortKey, port);
//            session.setAttribute(Info.FServerNameKey, sname);
//            session.setAttribute(Info.FServerPwdKey, spwd);
//            session.setAttribute(Info.FDatabaseKey, database);
//
//            response.sendRedirect(request.getContextPath()+"/MGM/index_mgm.jsp");

//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date curDate = new Date();
//            String create_time = format.format(curDate);
//            UserLoginBean loginBean = new UserLoginBean(database,spwd,sname,port,ip,pwd,userName,create_time,"");
//            boolean isOK = false;
//            try {
//                //复制db文件并重命名为登录用户的所属db文件
//                String filename = MD5.getMD5(userName);//避免中文时乱码，都一律转换成md5
//                session.setAttribute(Info.FUserDbName, filename);//记录md5处理过的用户名，用于区分db文件并获取数据
//                if (FileControl.hasFile(Info.copyDbFile(filename))){//存在文件，修改
//                    if (MathUtil.toD(userDao.checkHasUser(filename))>0){//db中是否有数据
//                        isOK = userDao.changeUser(loginBean,filename);//修改数据
//                    }else{
//                        isOK = userDao.addUserDB(loginBean,filename);//添加数据到db文件
//                    }
//                }else{//不存在文件，创建
//                    FileControl.copyFile(Info.BaseDbFile,Info.copyDbFile(filename));
//                    isOK = userDao.addUserDB(loginBean,filename);//添加数据到db文件
//                }
//            } catch (Exception e) {
//                Lg.e("文件或数据处理出错....");
//            }
//            if (isOK){
//                Lg.e("保存数据:",loginBean);
//                response.sendRedirect(request.getContextPath()+"/MGM/index_mgm.jsp");
//            }else{
//            request.setAttribute("feedback", "登录失败,生成个人数据出错");
//            request.getRequestDispatcher("/MGM/errorHttp.jsp").forward(request, response);
////                response.sendRedirect(request.getContextPath()+"/WebApp/errorHttp.jsp");
//            }
//        }else{
//            request.setAttribute("feedback", "登录失败,用户名和密码错误");
//            request.getRequestDispatcher("/MGM/errorHttp.jsp").forward(request, response);
////            response.sendRedirect(request.getContextPath()+"/WebApp/errorHttp.jsp");
//        }

        // 检查网页上是否有新的访问者
//        if (session.isNew()){
//        session.setAttribute(userIDKey, userID);
//        } else {
//            visitCount = (Integer)session.getAttribute(visitCountKey);
//            visitCount = visitCount + 1;
//            userID = (String)session.getAttribute(userIDKey);
//        }
//        session.setAttribute(visitCountKey,  visitCount);
        // 设置响应内容类型
//        response.setContentType("text/html;charset=UTF-8");


//        String sssss = (String)session.getAttribute(userIDKey);
//        Lg.e("进入--公司信息修改"+sssss);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date();
//        String create_time = format.format(curDate);
//        Company company = new Company(company_name,app_version,app_version2,app_version3,kd_version,app_id,img_logo_url,phone,address,remark,end_time,"0",create_time,user_max);
//        Lg.e("得到修改的公司",company);
//        CompanyDao webDao = new CompanyDao();
//        boolean ok = webDao.changeCompany(company);
//        if (ok) {
//            response.sendRedirect("MGM/CompanyList.jsp");
//        } else {
//            response.sendRedirect("errorHttp.jsp");
//        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
