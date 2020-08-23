package MqttBox;

import Bean.UseTimeBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/StartMqtt")
public class StartMqtt extends HttpServlet {
    public static String TOPIC = "pdatest";
    public static final String MQTT_Broker_URL = "tcp://129.211.59.124:1883";
    //    public static final String MQTT_Broker_URL = "tcp://192.168.31.55:1883";
    public static final String MQTT_ClientId = "assist";
    public static MqttClient mqttClient;
    private boolean isOkMqtt;
    public static MqttTopic topic1;

    public StartMqtt(){
        Lg.e("创建StartMqtt");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        String paramter = request.getParameter("json");
        Gson gson = new Gson();
        Lg.e("启动Mqtt");
        startMqtt();


    }

    private void checkMqtt(){
        Lg.e("三秒后重连");
        Timer timer = new Timer();
       timer.schedule(new Task(), 30 * 1000);
    }
    public class Task extends TimerTask {
    public void run(){
        if (!isOkMqtt){
            startMqtt();
        }
    }
}



    private void startMqtt(){
        try {
            mqttClient = new MqttClient(MQTT_Broker_URL, MQTT_ClientId, new MemoryPersistence());
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Lg.e("连接丢失connectionLost");

//                            Lg.e("连接丢失connectionLost"+cause.getMessage().toString());
                    isOkMqtt = false;
                    checkMqtt();
//                    App.getInstance().startCheckMqtt();
//                            disConnectCallBack();
                }

                @Override
                public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
                    String string = message.toString();
                    Lg.e("收到数据"+topic+message.toString().length(),string);//中文会乱码
                    Lg.e("收到数据"+topic+message.toString().length(),new String(message.getPayload(),"UTF-8"));
                }

                @Override
                public void deliveryComplete(MqttDeliveryToken token) {

                }
            });
            MqttConnectOptions options = new MqttConnectOptions();
            // 清除缓存
            options.setCleanSession(true);
            // 设置超时时间，单位：秒
            options.setConnectionTimeout(15);
            // 心跳包发送间隔，单位：秒
            options.setKeepAliveInterval(15);
            // 用户名
            options.setUserName("admin");
            // 密码
            options.setPassword("admin".toCharArray());
            mqttClient.connect(options);

            //Subscribe to all subtopics of homeautomation
            mqttClient.subscribe(TOPIC);
            topic1 = mqttClient.getTopic(TOPIC);
            isOkMqtt = true;



        } catch (MqttException e) {
            isOkMqtt = false;
            Lg.e("启动失败",e.getMessage());
            checkMqtt();
        }
    }

    public static boolean isConnected(){
        if (mqttClient.isConnected()){
            return true;
        }
        return false;
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
