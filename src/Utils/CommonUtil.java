package Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static String getTime(boolean dia){
        SimpleDateFormat format = new SimpleDateFormat( dia ? "yyyy-MM-dd" : "yyyyMMdd");
        Date curDate = new Date();
        return format.format(curDate);
    }
    public static String getTimeLong(boolean dia){
        SimpleDateFormat format = new SimpleDateFormat( dia ? "yyyy-MM-dd HH:mm:ss" : "yyyyMMdd HH:mm:ss");
        Date curDate = new Date();
        return format.format(curDate);
    }
    public static String getTimeLongID(boolean dia){
        SimpleDateFormat format = new SimpleDateFormat( dia ? "yyyy-MM-dd HH:mm:ss" : "yyyyMMddHHmmss");
        Date curDate = new Date();
        return format.format(curDate);
    }
    private static final String PATH = "C:\\properties\\";
    //读取本地的txt文件
    public static String getString4pwd() {
        InputStreamReader inputStreamReader = null;
        String lineTxt = null;
        try {
            File file = new File(PATH+"/dowhatpwd.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                while ((lineTxt = br.readLine()) != null) {
                    Lg.e("读取txt:"+lineTxt);
                    return lineTxt;
                    //保存版本号
//                    Hawk.put(Config.Apk_Version,lineTxt);
//                    System.out.println(lineTxt);
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineTxt;
    }


    //解密加密的时间
    public static String dealTime(String timemd){
//        Lg.e("加密的日期："+timemd);
        StringBuffer buffer = new StringBuffer()
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(0)+"")+1))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(1)+"")+2))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(2)+"")+3))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(3)+"")+4))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(4)+"")+5))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(5)+"")+6))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(6)+"")+7))
                .append(timemd.charAt(Integer.parseInt(BaseData.Key.charAt(7)+"")+8));
        Lg.e("解析日期："+buffer.toString());
        return buffer.toString();
    }

    /*注意：java.net的url编码方法在对一些特殊符号编码时有个bug，如：+号decord时，+号会变成空格，需要把+号替换成%2B，str=str.replaceAll("\\+", "%2B");*/
    //在a标签中的url有中文时，转成URL编码进行链接跳转，不然会出现乱码无法跳转
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    //对URL编码的数据进行中文转码
    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
