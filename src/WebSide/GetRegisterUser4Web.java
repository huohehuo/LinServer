package WebSide;

import Bean.RegisterCodeBean;
import Utils.Lg;
import WebSide.bean.CommonResponse4Web;
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
import java.util.ArrayList;

/**
 * 库存查询VUE版的接口
 */
@WebServlet(urlPatterns = "/GetRegisterUser4Web")
public class GetRegisterUser4Web extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        String json = "";
        PreparedStatement sta = null;
        ResultSet rs = null;
        Lg.e("进入查询GetRegisterUser4Web");
        response.setCharacterEncoding("UTF-8");
        String seach =request.getParameter("seach");

        StatisticalDao statisticalDao = new StatisticalDao();
        ArrayList<RegisterCodeBean> stsList = statisticalDao.getRegisterCodeByData(seach);
        CommonResponse4Web commonResponse4Web = new CommonResponse4Web(true,stsList.size());
        commonResponse4Web.registerCodeBeans = stsList;
        response.getWriter().write(new Gson().toJson(commonResponse4Web));

//        HttpSession session = request.getSession(true);
//        PrintWriter writer = response.getWriter();
//        String name =(String) session.getAttribute(Info.FUserNameKey);
//        String pwd =(String)session.getAttribute(Info.FUserPwdKey);
//        String ip = (String)session.getAttribute(Info.FServerIPKey);
//        String port = (String)session.getAttribute(Info.FServerPortKey);
//        String sname = (String)session.getAttribute(Info.FServerNameKey);
//        String spwd = (String)session.getAttribute(Info.FServerPwdKey);
//        String database = (String)session.getAttribute(Info.FDatabaseKey);
//        Lg.e("获得数据"+ip+port+sname+spwd+database);
//
//        String seach =request.getParameter("seach");
//        String storage_id =request.getParameter("storage_id");
//        if (null != storage_id && !"".equals(storage_id)){
//            json =json + " and t3.FName='"+storage_id+"'";
//        }
//        if (null != seach && !"".equals(seach)){
//            json =json + " and t2.FName LIKE  '%"+seach+"%'";
//        }
//        Lg.e("获得数据"+json);
////        if(json!=null&&!json.equals("")){
//            try {
//                conn = JDBCUtil.getConn(getDataBaseUrl.getUrl(ip, port, database), spwd, sname);
////                System.out.println(request.getParameter("sqlip")+" "
////                        +request.getParameter("sqlport")+" "+request.getParameter("sqlname")
////                        +" "+request.getParameter("sqlpass")+" "+request.getParameter("sqluser"));
//                String SQL = "select t2.FModel,t2.FNumber as 物料编码,t2.FName as 物料名称,convert(float,t1.FQty) as 基本单位库存,convert(float,t1.FSecQty) as 辅助单位库存,t6.FName as 辅助单位,t5.FName as 基本单位,t3.FName as 仓库,t4.FName as 仓位,t1.FBatchNo as 批次,t1.FKFDate as 生产日期,t1.FKFPeriod as 保质期 from ICInventory t1 left join t_ICItem t2 on t1.FItemID = t2.FItemID left join t_stock t3 on t1.FStockID = t3.FItemID left join t_stockPlace t4 on t1.FStockPlaceID = t4.FSPID left join t_Measureunit t5 on t2.FUnitID = t5.FItemID left join t_Measureunit t6 on t2.FSecUnitID = t6.FItemID where 1=1 "+json;
//                Lg.e("SQL："+SQL);
//                sta = conn.prepareStatement(SQL);
////                sta.setString(1,json);
//                rs = sta.executeQuery();
//                ArrayList<InStorageNumListBean.inStoreList> container = new ArrayList<>();
//                InStorageNumListBean iBean = new InStorageNumListBean();
//                while (rs.next()){
//                    InStorageNumListBean.inStoreList inBean = iBean.new inStoreList();
//                    inBean.FNumber = rs.getString("物料编码");
//                    inBean.FName = rs.getString("物料名称");
//                    inBean.FQty = rs.getString("基本单位库存");
//                    inBean.FSecQty = rs.getString("辅助单位库存");
//                    inBean.FUnit = rs.getString("基本单位");
//                    inBean.FStockID = rs.getString("仓库");
//                    inBean.FStockPlaceID = rs.getString("仓位");
//                    inBean.FBatchNo = rs.getString("批次");
//                    inBean.FKFDate = rs.getString("生产日期");
//                    inBean.FKFPeriod = rs.getString("保质期");
//                    container.add(inBean);
//                }
//                if(container.size()>0){
////                    iBean.list = container;
////                    Lg.e("返回数据"+container.size());
////                    request.setAttribute("list",container);
////                    request.getRequestDispatcher("/WebApp/StoreCheckShow.jsp").forward(request, response);
//
////                    response.sendRedirect(request.getContextPath()+"/WebApp/StoreCheck.jsp");
////                    writer.write(CommonJson.getCommonJson(true,new Gson().toJson(iBean)));
//                    Lg.e("库存数据返回",new CommonResponse4Web(true,container.size(),container));
//                    response.getWriter().write(new Gson().toJson(new CommonResponse4Web(true,container.size(),container)));
//                }else{
//                    request.setAttribute("feedback", "查询数据为空");
//                    request.getRequestDispatcher("/WebApp/errorHttp.jsp").forward(request, response);
//                }
//            } catch (SQLException e) {
//
//                request.setAttribute("feedback", "数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString());
//                request.getRequestDispatcher("/WebApp/errorHttp.jsp").forward(request, response);
////                writer.write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                request.setAttribute("feedback", "数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString());
//                request.getRequestDispatcher("/WebApp/errorHttp.jsp").forward(request, response);
//                e.printStackTrace();
//            }finally {
//                JDBCUtil.close(rs,sta,conn);
//            }

//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
