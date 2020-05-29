package Utils;
import java.sql.*;

public class JDBCUtil {
	
	
	
	public static Connection getConn(String url,String password,String user) throws SQLException, ClassNotFoundException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, user, password);
		
	}
	
	public static Connection getSQLiteConn() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbsetfile.db");
		return conn;
	}

	public static Connection getSQLiteConn1() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbother.db");
		return conn;
	}
	public static Connection getSQLite4Company() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbCompany.db");
		return conn;
	}
	public static Connection getSQLite4Company4WC() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbWeChatTest.db");
		return conn;
	}
	public static Connection getSQLite4Statistical() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbStatistical.db");
		return conn;
	}
	public static Connection getSQLite4UserControl() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbUserControl.db");
		return conn;
	}

	//获取时间表的时间
	public static Connection getSQLiteForTime() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbManager.db");
		return conn;
	}

	public static Connection getSQLiteForFeedBack() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/dbWebFeedBack.db");
		return conn;
	}

	public static Connection getUserDbConn(String dbname) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite://c:/properties/web/dbWebAppBase"+dbname+".db");
		return conn;
	}


	public static void close(ResultSet rs,PreparedStatement sta,Connection connection){
		if(rs!=null){
			try {

				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(sta!=null){
			try {
				sta.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
