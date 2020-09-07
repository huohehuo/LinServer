package MqttBox;

import Server.BarcodeOnly.T;
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
@WebServlet(urlPatterns = "/StartMqttSend")
public class StartMqttSend extends HttpServlet {

//    private MqttClient mqttClient;
    private MqttMessage messageMqtt;

    public StartMqttSend(){
        Lg.e("创建StartMqttSend");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        String message = request.getParameter("json");
        Gson gson = new Gson();
//        String message = "Assist发送数据";
//        String message = new String("Assist发送数据".getBytes(),"UTF-8");
//        mqttClient = T.mqttClient;
//        Lg.e("MQTT对象",null == T.mqttClient);
        if (!T.mqttClient.isConnected()){
            Lg.e("未开启");
            return;
        }

        messageMqtt = new MqttMessage();
        messageMqtt.setQos(1);  //保证消息能到达一次
        messageMqtt.setRetained(true);
        messageMqtt.setPayload(message.getBytes("UTF-8"));
        try {
            MqttDeliveryToken token = T.topic1.publish(messageMqtt);
            token.waitForCompletion();
            Lg.e(messageMqtt.isRetained()+"=======retained状态");
        } catch (MqttException e) {
            Lg.e("发布失败");
        }


//        try {
//            mqttClient.publish(StartMqtt.TOPIC, mqttMsg);
//        } catch (MqttException e) {
//            Lg.e("发送失败");
//            e.printStackTrace();
//        }

    }

//    private void checkMqtt(){
//        Lg.e("三秒后重连");
//        Timer timer = new Timer();
//       timer.schedule(new Task(), 30 * 1000);
//    }
//    public class Task extends TimerTask {
//    public void run(){
//        if (!isOkMqtt){
//            startMqtt();
//        }
//    }
//}




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
