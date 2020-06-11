package ServerVueWeb;

import Utils.Lg;
import Utils.MD5;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        UserDao userDao = new UserDao();
        UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String getbody = null;
        String parameter = null;
        Lg.e("到达");
        try {
            parameter = ReadAsChars(request);//解密数据
        } catch (Exception e) {
            response.getWriter().write(gson.toJson(new WebResponse(false, "上传失败,请求体解析错误")));
        }

        WebResponse webResponse = gson.fromJson(parameter, WebResponse.class);
        boolean isOK=false;
        String filename="";
        try {
            //复制db文件并重命名为登录用户的所属db文件
//           filename = MD5.getMD5(webResponse.json);//避免中文时乱码，都一律转换成md5
           filename = webResponse.json;//避免中文时乱码，都一律转换成md5
            if (FileControl.hasFile(Info.copyUserDataFile(filename))){
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