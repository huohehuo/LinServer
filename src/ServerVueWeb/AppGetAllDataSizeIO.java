package ServerVueWeb;

import ServerVueWeb.Dao.UpdataAppDataDao;
import Utils.Lg;
import Utils.MathUtil;
import WebSide.UserDao;
import WebSide.Utils.FileControl;
import WebSide.Utils.Info;
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
 * 用于备份公司信息表的数据到xls
 */
@WebServlet(urlPatterns = "/AppGetAllDataSizeIO")
public class AppGetAllDataSizeIO extends HttpServlet {
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
        Lg.e("进入"+getClass().getSimpleName());
        try {
            parameter = ReadAsChars(request);//解密数据
        } catch (Exception e) {
            response.getWriter().write(gson.toJson(new WebResponse(false, "上传失败,请求体解析错误")));
        }

        WebResponse webResponse = gson.fromJson(parameter, WebResponse.class);
        boolean isOK=false;
        String filename="";
        try {
           filename = webResponse.json;//此为经过md5转换过的用户名code
            if (FileControl.hasFile(Info.copyUserDataFile(filename))){//根据用户名code，查找相关db文件是否存在
                isOK = true;
            }
        } catch (Exception e) {
            Lg.e("文件或数据处理出错....");
        }

        WebResponse back = new WebResponse();
        back.state = isOK;
        back.backString="处理完毕";
        back.size = isOK?MathUtil.toInt(updataAppDataDao.getDataCountForApp(filename)):0;
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