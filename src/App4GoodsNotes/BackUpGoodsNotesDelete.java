package App4GoodsNotes;

import ServerVueWeb.Bean.BuyAtBean;
import Utils.CommonUtil;
import Utils.Lg;
import WebSide.WebResponse;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于备份公司信息表的数据到xls
 *
 *
 */
@WebServlet(urlPatterns = "/BackUpGoodsNotesDelete")
public class BackUpGoodsNotesDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String parameter = request.getParameter("json");
        Lg.e("到达---BackUpGoodsNotesDelete",parameter);
        //删除文件
        FileManager.deletePic(parameter);
        response.sendRedirect(request.getContextPath()+"/App4GoodsNotes/App4GoodsNotesFile.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}