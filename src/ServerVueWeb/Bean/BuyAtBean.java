package ServerVueWeb.Bean;

/**
 * Created by NB on 2017/7/26.
 */
public class BuyAtBean {
    public Long id;
    public String FID;//名称
    public String FName;//数量
    public String FNum;//数量
    public String FAddrID;
    public String FAddrName;
    public String FBuyID;
    public String FBuyName;


    public String FSum;//数量
    public String FPrice;//数量
    public String FColorName;
    public String FModelName;
    public String FStuffName;
    public String FUnitName;



    public String FCreateData;//创建日期
    public String FIsCloud;//是否备份云端
    public String FUseNum;//使用次数
    public String FMapID;//地图经纬

    public void setBuyBean(BuyBean bean){
        if (null == bean)return;
        FBuyID = bean.FID;
        FBuyName = bean.FName;
    }
    public void setAddrBean(AddrBean bean){
        if (null == bean)return;
        FAddrID = bean.FID;
        FAddrName = bean.FName;
    }



}
