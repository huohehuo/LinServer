package Log;

import Bean.DownloadReturnBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
import com.google.gson.Gson;

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
@WebServlet(urlPatterns = "/GetSettingData")
public class GetSettingData extends HttpServlet {
    //文件路径
    private static final String PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\JsonFile\\setting.txt";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".txt";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        Gson gson = new Gson();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String json = request.getParameter("json");
        if(json!=null&&!json.equals("")){
            try {
                System.out.println("获得数据："+json);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date curDate = new Date();
                String str = format.format(curDate);
                DownloadReturnBean dBean = gson.fromJson(getString(PATH).replaceAll("null",""),DownloadReturnBean.class);
                System.out.println("txt数据获取："+gson.toJson(dBean));
                dBean.serverTime = str;
                response.getWriter().write(CommonJson.getCommonJson(true, gson.toJson(dBean)));
//                //截取规则：文件夹名字(项目名)^文件名(类名)^错误日记(activity+funtion+error)
//                if (json.contains("^")) {
//                    String[] split = json.split("\\^",3);
//                    System.out.println("解析数量："+split.length);
//                    if (split.length==3){
//                       writeToSDcard(split[0],split[1],split[2]);
//                    }
//                }
//                writeToSDcard("asdfasdf","bbbb");

            }catch (Exception e) {
                writer.write(CommonJson.getCommonJson(false,"服务器错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));
                e.printStackTrace();
            }finally {
                JDBCUtil.close(rs,sta,conn);
            }

        }
    }
    //读取本地下载好的txt数据包，解析
    public static String getString(String txtName) {
        String lineTxt = null;
        StringBuilder builder = new StringBuilder();
        try {

            File file = new File(txtName);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                while ((lineTxt = br.readLine()) != null) {
                    lineTxt+=br.readLine();
                    System.out.println("读取txt:"+lineTxt);
                    if (!"".equals(lineTxt)){
                        builder.append(lineTxt);
//                        Lg.e("读取txt2:"+builder.toString());
                    }
                }
                br.close();
                return builder.toString().substring(1,builder.toString().length()-4);
            } else {
                System.out.println("文件不存在!");
            }

//            File f = new File(txtName);
//            //以防有中文名路径，中文路径里面的空格会被"%20"代替
//            txtName = java.net.URLDecoder.decode(txtName, "utf-8");
//
//            FileInputStream redis = new FileInputStream(f);
////            br = new BufferedReader(new InputStreamReader(redis));
//
//            InputStream inputStream = App.getContext().getResources().getAssets().open(txtName);
//            byte[] arrayOfByte = new byte[inputStream.available()];
//            inputStream.read(arrayOfByte);
//            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineTxt;
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
