package MqttBox;

import ServerVueWeb.Bean.AppLinkBean;
import Utils.Lg;
import Utils.MD5;
import Utils.MathUtil;
import WebSide.UserDao;
import WebSide.WebResponse;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static WebSide.Utils.HttpRequestUtils.ReadAsChars;

/**
 * 用于app登录；
 */
@WebServlet(urlPatterns = "/UploadMqttUserIO")
public class UploadMqttUserIO extends HttpServlet {
    Gson gson;
    MqttUserDao userDao;
    @Override
    public void init() throws ServletException {
        Lg.e("初始化"+getClass().getSimpleName());
        gson = new Gson();
        userDao = new MqttUserDao();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        synchronized (this){
            Lg.e("进入"+getClass().getSimpleName());
            String getbody=null;
            String filename="";
            String parameter = null;
            try{
                parameter = ReadAsChars(request);//解密数据
            }catch (Exception e){
                response.getWriter().write(gson.toJson(new WebResponse(false,"上传失败,请求体解析错误")));
            }

            WebResponse pBean = gson.fromJson(parameter, WebResponse.class);

            WebResponse back = new WebResponse();
            back.state = userDao.checkHasUserAndInsert(pBean.json);
            back.backString="处理完毕";
            back.json = filename;
            back.FToken = System.currentTimeMillis()+"";
            response.getWriter().write(gson.toJson(back));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
