package ServerVueWeb;

import Utils.Lg;
import WebSide.Info;
import WebSide.UserDao;
import WebSide.Utils.FileControl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 用于程序访问页面跳转
 */
@WebServlet(urlPatterns = "/AppWebHomeIO")
public class AppWebHomeIO extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
//        String getbody = null;
        HttpSession session = request.getSession(true);
        String filename = request.getParameter("json");
        Lg.e("到达AppWebHomeIO",filename);
//        try {
//            parameter = ReadAsChars(request);//解密数据
//        } catch (Exception e) {
//            response.getWriter().write(gson.toJson(new WebResponse(false, "上传失败,请求体解析错误")));
//        }
//
//        WebResponse webResponse = gson.fromJson(parameter, WebResponse.class);
//        boolean isOK;
//        String filename="9a2c9e2e995c577b";
//        try {
//            //复制db文件并重命名为登录用户的所属db文件
//           filename = MD5.getMD5(webResponse.json);//避免中文时乱码，都一律转换成md5
//        } catch (Exception e) {
//            Lg.e("文件或数据处理出错....");
//        }

        session.setAttribute(Info.FUser_Code, filename);
        response.sendRedirect(request.getContextPath()+"/App/AppHome.jsp");
//        response.getWriter().write(new Gson().toJson(updataAppDataDao.getNoteDataForApp(filename)));
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
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