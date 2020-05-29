package WebSide.Utils;

public class Info {
	public static String BaseDbFile="C:\\properties\\web\\dbWebAppBase.db";

	public static String copyDbFile(String name){
		return "C:\\properties\\web\\dbWebAppBase"+name+".db";
	}
	public static String FUserNameKey="UserNameKey";
	public static String FUserPwdKey="UserPwdKey";
	public static String FServerIPKey="ServerIPKey";
	public static String FServerPortKey="ServerPortKey";
	public static String FServerNameKey="ServerNameKey";
	public static String FServerPwdKey="ServerPwdKey";
	public static String FDatabaseKey="DatabaseKey";
	public static String FUserDbName="UserDbName";
}
