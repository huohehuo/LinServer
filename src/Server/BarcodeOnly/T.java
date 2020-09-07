package Server.BarcodeOnly;

import MqttBox.StartMqtt;
import Utils.Lg;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class T {

    public static String TOPIC = "XuTing";
    public static final String MQTT_Broker_URL = "tcp://129.211.59.124:1883";
    //    public static final String MQTT_Broker_URL = "tcp://192.168.31.55:1883";
    public static final String MQTT_ClientId = "assist";
    public static MqttClient mqttClient;
    private static boolean isOkMqtt;
    public static MqttTopic topic1;
    private static T instance;

    public static  T getInstance(){
        if(instance==null){
            instance =new T();
        }
        return instance;
    }
    public static void main(String[] args) {
        String s = "171 1100 107917112301477";
        s = s.replaceAll(" ","");
        System.out.println(s);
        System.out.println(s.substring(1,11));
        System.out.println(s.substring(11));
        Lg.e("启动Mqtt");
//        startMqtt();

    }

    private static void checkMqtt(){
        Lg.e("三秒后重连");
        Timer timer = new Timer();
        timer.schedule(new Task(), 30 * 1000);
    }
    public static class Task extends TimerTask {
        public void run(){
            if (!isOkMqtt){
                startMqtt();
            }
        }
    }



    public static void startMqtt(){
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
                    Lg.e("数据体",message);
                    Lg.e("收到数据"+topic+message.toString().length(),string);//中文会乱码(从Assist发布的话，这里正常中文，下面反而会乱码)
                    Lg.e("收到数据"+topic+message.toString().length(),new String(message.getPayload(),"UTF-8"));//由app，和服务器发送的数据，中文正常
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

}
