package WebSide;

import Utils.Lg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Servlet implementation class pl_find
 */
@WebServlet(urlPatterns = "/uploadFileTest")
public class uploadFileTest extends HttpServlet {
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
        //response.setContentType(）用来设置响应的报头信息。
//        response.setContentType("text/html; charset=GBK");
        //response.getWriter()获取响应的对象输出流
        PrintWriter out = response.getWriter();
        //request.setCharacterEncoding()对客户端请求进行重新编码
//        request.setCharacterEncoding("GBK");
        //request.getParameter()获取指定的请求参数值
//        String name = request.getParameter("namefile");
        String name ="test";
        Lg.e("文件名",name);
        //输出参数值
        Lg.e("普通的name参数为:"+name+"<br/>");
        //request.getPart()获取指定的文件上传域。还有getParts()来获取所有的文件上传域
        Part part = request.getPart("filewww");
        //part.getContentType()获取part上传文件的文件类型
        Lg.e("上传文件的类型为："+part.getContentType()+"<br/>");
        //part.getHeaderNames()获取文件上传域的Header Name
        Collection<String> headerNames = part.getHeaderNames();
        for(String headerName : headerNames){
            out.println(headerName+"--->"+part.getHeader(headerName)+"<br/>");
        }
        String fileNameInfo = part.getHeader("content-disposition");
        String fileName=fileNameInfo.substring(fileNameInfo.indexOf("filename=\"")+10,fileNameInfo.length() - 1);
        Lg.e("文件地址",fileName);
        Lg.e("文件地址",getServletContext().getRealPath("/uploadFiles")+"/"+fileName);
        part.write(getServletContext().getRealPath("/uploadFiles")+"/"+fileName);//要提前在项目目录下建一个文件夹。

    }

}
