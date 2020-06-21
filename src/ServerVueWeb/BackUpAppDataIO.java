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
 * app数据备份
 */
@WebServlet(urlPatterns = "/BackUpAppDataIO")
public class BackUpAppDataIO extends HttpServlet {
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
        Lg.e("到达");
        try {
            parameter = ReadAsChars(request);//解密数据
        } catch (Exception e) {
            response.getWriter().write(gson.toJson(new WebResponse(false, "上传失败,请求体解析错误")));
        }

        WebResponse webResponse = gson.fromJson(parameter, WebResponse.class);
        boolean isOK;
        String filename="";
        try {
           filename = webResponse.json;//此为经过md5转换过的用户名code
            if (FileControl.hasFile(Info.copyUserDataFile(filename))) {//存在该用户相应的数据文件
                Lg.e("存在用户数据文件");
                isOK = updataAppDataDao.saveDataForApp(webResponse,filename);
            } else {//不存在文件，创建
                Lg.e("不存在用户数据文件---新建");
                FileControl.copyFile(Info.BaseUserDataFile, Info.copyUserDataFile(filename)); //复制db文件并重命名为登录用户的所属db文件
                isOK = updataAppDataDao.saveDataForApp(webResponse,filename);
            }
        } catch (Exception e) {
            Lg.e("文件或数据处理出错....");
            isOK = false;
        }
        WebResponse back = new WebResponse();
        back.state = isOK;
        back.backString=isOK?"处理完毕":"数据备份失败";
        back.size = MathUtil.toInt(updataAppDataDao.getDataCountForApp(filename));
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