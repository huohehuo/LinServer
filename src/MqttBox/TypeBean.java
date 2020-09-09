package MqttBox;
/*cid	FName	FPwd	FName_code	FToken	FCreateTime	Img_Logo
358			9d8a121ce581499d			*/
public class TypeBean {
    public int uid;
    public String FName;
    public String FNumber;

    public TypeBean() {

    }
    public TypeBean(String code, String token) {
        this.FName = code;
        this.FNumber = token;
    }

}
