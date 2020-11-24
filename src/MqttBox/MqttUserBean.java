package MqttBox;
/*cid	FName	FPwd	FName_code	FToken	FCreateTime	Img_Logo
358			9d8a121ce581499d			*/
public class MqttUserBean {
    public int cid;
    public String FName;
    public String FPwd;
    public String FName_code;
    public String FToken;
    public String FCreateTime;
    public String Img_Logo;
    public String FIsVip;

    public MqttUserBean() {

    }
    public MqttUserBean(String code,String token,String vip) {
        this.FName_code = code;
        this.FToken = token;
        this.FIsVip = vip;
    }

}
