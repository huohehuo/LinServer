package ServerVueWeb;

import ServerVueWeb.Dao.UpdataAppDataDao;
import Utils.Lg;
import Utils.MathUtil;
import WebSide.UserDao;
import WebSide.WebResponse;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * app获取已备份的所有数据
 */
@WebServlet(urlPatterns = "/AppGetAllDataIO")
public class AppGetAllDataIO extends HttpServlet {
    Gson gson;
    @Override
    public void init() throws ServletException {
        Lg.e("初始化"+getClass().getSimpleName());
        gson = new Gson();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String getbody = null;
        String parameter = null;
        Lg.e("到达"+getClass().getSimpleName());
        try {
            parameter = ReadAsChars(request);//解密数据
        } catch (Exception e) {
            response.getWriter().write(gson.toJson(new WebResponse(false, "上传失败,请求体解析错误")));
        }

        WebResponse webResponse = gson.fromJson(parameter, WebResponse.class);
        boolean isOK;
        String filename="";
        try {
           filename =webResponse.json;//此为经过md5转换过的用户名code
        } catch (Exception e) {
            Lg.e("文件或数据处理出错....");
            return;
        }
        WebResponse back = new WebResponse();
        if (updataAppDataDao.getAllDataForApp(back,filename)){//获取相关的所有数据
            back.state = true;
            back.backString = "获取成功";
        }else{
            back.state = false;
            back.backString = "获取失败";
        }
        back.size = MathUtil.toInt(updataAppDataDao.getDataCountForApp(filename));//获取所有数据的size
        back.FToken = System.currentTimeMillis()+"";
        response.getWriter().write(gson.toJson(back));
    }

    // 字符串读取post请求中的body数据
    private String ReadAsChars(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}