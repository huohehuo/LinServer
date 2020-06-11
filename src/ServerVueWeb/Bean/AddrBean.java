package ServerVueWeb.Bean;


/**
 * Created by NB on 2017/7/26.
 */
public class AddrBean {
    public Long id;
    public String FID;//名称
    public String FName;//名称
    public String FCreateData;//创建日期
    public String FIsCloud;//是否备份云端
    public String FUseNum;//使用次数
    public String FMapID;//地图经纬
    public AddrBean(){

    }
    public AddrBean(String name, String data){
        FName = name;
        FCreateData = data;
    }
    public AddrBean(String name, String data,String fid){
        FName = name;
        FCreateData = data;
        FID = fid;
    }


}
