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
@WebServlet(urlPatterns = "/AppUserSetVipIO")
public class AppUserSetVipIO extends HttpServlet {
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
        String code= request.getParameter("json");
        String slt= request.getParameter(code+"_slt");

        Lg.e("获取值：",code);
        Lg.e("获取值：",slt);
//        boolean isOK;
//            try {
////                    if (MathUtil.toD(userDao.checkHasUser(name,pwd))>0){//db中是否有数据
////                        Lg.e("存在用户数据");
////                        isOK = userDao.changeUser(loginBean,filename);//修改数据
////                        request.setAttribute("feedback", "该用户名已存在，不能重复添加");
////                        request.getRequestDispatcher("/WebBox/errorHttp.jsp").forward(request, response);
////                    }else{
//                        Lg.e("不存在用户数据，新增");
//                        isOK = userDao.deleteUser(name);//添加数据到db文件
//                        if (!isOK){
//                            response.sendRedirect(request.getContextPath()+"/WebBox/AppUserSet.jsp");
//                        }else{
//                            request.setAttribute("feedback", "删除用户失败，请重试");
//                            request.getRequestDispatcher("/WebBox/errorHttp.jsp").forward(request, response);
//                        }
////                    }
////                }else{//不存在文件，创建
////                    FileControl.copyFile(Info.BaseDbFile,Info.copyDbFile(filename));
////                    isOK = userDao.addUserDB(loginBean,filename);//添加数据到db文件
////                }
//
//            } catch (Exception e) {
//                request.setAttribute("feedback", "处理用户数据失败，请检查文件是否完整");
//                request.getRequestDispatcher("/WebBox/errorHttp.jsp").forward(request, response);
//            }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
