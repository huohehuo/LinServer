package MqttBox;

import Utils.Lg;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 用于app登录；
 */
@WebServlet(urlPatterns = "/AppUserDeleteIO")
public class AppUserDeleteIO extends HttpServlet {
    Gson gson;
    @Override
    public void init() throws ServletException {
        Lg.e("初始化"+getClass().getSimpleName());
        gson = new Gson();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Lg.e("进入"+getClass().getSimpleName());
        MqttUserDao userDao = new MqttUserDao();
        String getbody=null;
        String filename="";
        String name= request.getParameter("json");
        boolean isOK;
            try {
//                    if (MathUtil.toD(userDao.checkHasUser(name,pwd))>0){//db中是否有数据
//                        Lg.e("存在用户数据");
//                        isOK = userDao.changeUser(loginBean,filename);//修改数据
//                        request.setAttribute("feedback", "该用户名已存在，不能重复添加");
//                        request.getRequestDispatcher("/WebBox/errorHttp.jsp").forward(request, response);
//                    }else{
                        Lg.e("不存在用户数据，新增");
                        isOK = userDao.deleteUser(name);//添加数据到db文件
                        if (!isOK){
                            response.sendRedirect(request.getContextPath()+"/WebBox/AppUserSet.jsp");
                        }else{
                            request.setAttribute("feedback", "删除用户失败，请重试");
                            request.getRequestDispatcher("/WebBox/errorHttp.jsp").forward(request, response);
                        }
//                    }
//                }else{//不存在文件，创建
//                    FileControl.copyFile(Info.BaseDbFile,Info.copyDbFile(filename));
//                    isOK = userDao.addUserDB(loginBean,filename);//添加数据到db文件
//                }

            } catch (Exception e) {
                request.setAttribute("feedback", "处理用户数据失败，请检查文件是否完整");
                request.getRequestDispatcher("/WebBox/errorHttp.jsp").forward(request, response);
            }
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
