package Log;

import Utils.CommonJson;
import Utils.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/GetLogMessage")
public class GetLogMessage extends HttpServlet {
    //文件路径
    private static final String PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\PdaLog\\";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".txt";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String json = request.getParameter("json");
        if(json!=null&&!json.equals("")){
            try {
                System.out.println("获得数据："+json);
                //截取规则：文件夹名字(项目名)^文件名(类名)^错误日记(activity+funtion+error)
                if (json.contains("^")) {
                    String[] split = json.split("\\^",3);
                    System.out.println("解析数量："+split.length);
                    if (split.length==3){
                       writeToSDcard(split[0],split[1],split[2]);
                    }
                }
//                writeToSDcard("asdfasdf","bbbb");

            }catch (Exception e) {
                writer.write(CommonJson.getCommonJson(false,"服务器错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));
                e.printStackTrace();
            }finally {
                JDBCUtil.close(rs,sta,conn);
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    //将异常写入文件
    private void writeToSDcard(String fileName,String txtName,String error) throws IOException{
        File filedir = new File(PATH+fileName);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-ddHHmmss").format(new Date(currenttime));
        File exfile = new File(PATH+fileName +File.separator+txtName+"-"+time + ".txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        pw.println(time);
        pw.println(error);
        pw.close();

    }
}
