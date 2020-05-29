package WebSide.ApkApi;

import Bean.Company;
import Utils.Lg;
import WebSide.CompanyDao;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class pl_find
 */
@WebServlet(urlPatterns = "/getCompanyData")
public class getCompanyData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {

            String appid = request.getParameter("json");
            Lg.e("得到公司id", appid);
            if (null == appid || "".equals(appid)) {
                response.getWriter().write(new Gson().toJson(new Company()));
                return;
            }
            CompanyDao run = new CompanyDao();
//	          	stu.setHid(hid);
            List<Company> list2 = run.findCompany(appid);
            if (list2.size() > 0) {
                response.getWriter().write(new Gson().toJson(list2.get(0)));
            } else {
                response.getWriter().write(new Gson().toJson(new Company()));
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
