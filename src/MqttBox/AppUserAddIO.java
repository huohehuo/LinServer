package MqttBox;

import Utils.Lg;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/AppUserAddIO")
public class AppUserAddIO extends HttpServlet {

    public AppUserAddIO(){
        Lg.e("创建StartMqtt");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Lg.e("获取值：",request.getParameter("name"));
        Lg.e("获取值：",request.getParameter("pwd"));
        Lg.e("获取值：",request.getParameter("name_c"));
        Lg.e("获取值：",request.getParameter("pwd_c"));
        Lg.e("获取值：",request.getParameter("state"));


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}

