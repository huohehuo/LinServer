package ServerVueWeb.ForAppWeb;

import Utils.Lg;
import WebSide.Info;

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
@WebServlet(urlPatterns = "/AppWebHomeToDetailIO")
public class AppWebHomeToDetailIO extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UpdataAppDataDao updataAppDataDao = new UpdataAppDataDao();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Lg.e("进入"+getClass().getSimpleName());
//        String getbody = null;
        HttpSession session = request.getSession(true);
        String buyname = request.getParameter("json");

        session.setAttribute(Info.FUser_Home_To_Detail, buyname);
//                    request.getRequestDispatcher("/App/AppHomeToDetail.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath()+"/App/AppHomeToDetail.jsp");
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