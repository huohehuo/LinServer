package App4GoodsNotes;

import ServerVueWeb.Bean.BuyAtBean;
import Utils.CommonUtil;
import Utils.ExcelExport;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于备份公司信息表的数据到xls
 */
@WebServlet(urlPatterns = "/BackUpGoodsNotes")
public class BackUpGoodsNotes extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String getbody=null;
        String parameter= null;
        Lg.e("到达");
        try{
            parameter = ReadAsChars(request);//解密数据
        }catch (Exception e){
            response.getWriter().write(gson.toJson(new WebResponse(false,"上传失败,请求体解析错误")));
        }

        WebResponse pBean = gson.fromJson(parameter, WebResponse.class);
        Lg.e("得到数据",pBean);
        String[] fields = {"日期","送货单编号","规格","材料","颜色","数量","单位","单价","金额"};
        ExcelExportUtil export = new ExcelExportUtil();
        int okFile =0;
        for (int i = 0; i < pBean.noteBeans.size(); i++) {
            List<BuyAtBean> list = new ArrayList<>();
            for (int j = 0; j < pBean.buyAtBeans.size(); j++) {
                if (pBean.buyAtBeans.get(j).FBuyName.equals(pBean.noteBeans.get(i).NBuyName)){
                    list.add(pBean.buyAtBeans.get(j));
                }
            }
            HSSFWorkbook wb = export.generateExcel();
            wb = export.generateExcelSheet(wb, CommonUtil.getTime(true)+"备份数据("+pBean.noteBeans.get(i).NBuyName+")", fields, list);
            boolean isOk =export.writeExcelToDisk(wb, CommonUtil.getTime(true)+"备份数据("+pBean.noteBeans.get(i).NBuyName+")"+".xlsx");
            if (isOk)okFile++;
        }
        response.getWriter().write(gson.toJson(new WebResponse(true,"数据执行完毕，更新Excel文件："+okFile+"\n请在电脑端浏览器网址进行下载")));
    }

    // 字符串读取post请求中的body数据
    private String ReadAsChars(HttpServletRequest request)
    {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}