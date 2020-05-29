package OutIO;

import Bean.DownloadReturnBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
import Utils.Lg;
import Utils.getDataBaseUrl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/UserIO")
public class UserIO extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
//        String parameter = request.getParameter("json");
//        String version = request.getParameter("version");
        String SQL = "";
        Gson gson = new Gson();
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        ArrayList<DownloadReturnBean.User> container = new ArrayList<>();
//        System.out.println(parameter);
//        if (parameter != null) {
//        List<TestB> testBS = new ArrayList<>();
//        testBS.add(new TestB("one","A"));
//        testBS.add(new TestB("one2","B"));
//        testBS.add(new TestB("one3","C"));
//        Lg.e("列表",testBS);
            try {
                conn = JDBCUtil.getConn(getDataBaseUrl.getUrl("192.168.0.131", "1433", "AIS20140120174606"), "Abc123", "sa");
                SQL = "select FUserID,FName,'' as FPassWord,FEmpID,FPrimaryGroup as " +
                        "FGroupName,'' as FPermit from t_User where abs(FUserID) between " +
                        "16384 and 2147483647 Order By FUserID";
                sta = conn.prepareStatement(SQL);
                System.out.println("SQL:"+SQL);
                rs = sta.executeQuery();
                DownloadReturnBean downloadReturnBean = new DownloadReturnBean();
                if(rs!=null){
                    int i = rs.getRow();
                    while (rs.next()) {
                        DownloadReturnBean.User bean = downloadReturnBean.new User();
                        bean.FUserID = rs.getString("FUserID");
                        bean.FName = rs.getString("FName");
                        bean.FPassWord = rs.getString("FPassWord");
                        bean.FGroupName = rs.getString("FGroupName");
                        bean.FPermit = rs.getString("FPermit");
                        container.add(bean);
                    }
                    downloadReturnBean.User = container;
                    Lg.e("返回数据：",downloadReturnBean);
//                    response.getWriter().write(CommonJson.getCommonJson(true,"{answer:123}"));
                    response.getWriter().write("{\"answer\":\"one\",\"value\":\"A\"}");
                }else{
                    response.getWriter().write(CommonJson.getCommonJson(false,"未查询到数据"));
                }


            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                response.getWriter().write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));

            }

//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
