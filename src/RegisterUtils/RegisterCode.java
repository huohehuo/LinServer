package RegisterUtils;

import Utils.CommonJson;
import Utils.JDBCUtil;
import Utils.Lg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/RegisterCode")
public class RegisterCode extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Lg.e("进入：RegisterCode");
        Connection conn = null;
        PreparedStatement sta = null;
        String paramter = request.getParameter("json");
        if(paramter!=null&&!paramter.equals("")){
            try {
                conn = JDBCUtil.getSQLiteConn1();
                String SQL = "INSERT INTO REGISTER (Register_code)VALUES (?)";
                sta = conn.prepareStatement(SQL);
                sta.setString(1,paramter);
                int i = sta.executeUpdate();
                if(i>0){
                    response.getWriter().write(CommonJson.getCommonJson(true,""));
                }else{
                    response.getWriter().write(CommonJson.getCommonJson(false,"注册失败"));
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.getWriter().write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
