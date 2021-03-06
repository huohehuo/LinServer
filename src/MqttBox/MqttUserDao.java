package MqttBox;

import ServerVueWeb.Bean.AppLinkBean;
import Utils.JDBCUtil;
import Utils.Lg;
import Utils.MathUtil;
import WebSide.bean.UserLoginBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//处理公司信息表逻辑
public class MqttUserDao {
	public PreparedStatement prepStmt = null;
	Connection conn = null;
	PreparedStatement sta = null;
	ResultSet rs = null;

	//是否存在用户
	public boolean checkHasUserAndInsert(String code){
		boolean has=false;
		String num = "";
		try {
			conn = JDBCUtil.getMqttUserDbConn(code);
			String SQL = "SELECT COUNT(*) AS 数量 FROM Tb_User WHERE FName_code = ?";
			sta = conn.prepareStatement(SQL);
			sta.setString(1,code);
			rs = sta.executeQuery();
			while (rs.next()) {
				num = rs.getString("数量");
			}
			if (MathUtil.toD(num)>0){
				return true;
			}else{
				String insertSql = "INSERT INTO Tb_User (FName_code,FIsVip) VALUES (?,?)";
//				String insertSql = "INSERT INTO Tb_User (FName, FPwd,FName_code,FToken,FCreateTime,Img_Logo) VALUES (?)";
				sta = conn.prepareStatement(insertSql);
				sta.setString(1,code);
				sta.setString(2,"0");
				int i = sta.executeUpdate();
				if(i>0){
					has = true;
				}else{
					has = false;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs,sta,conn);
		}
		return has;
	}

	//
	public List<MqttUserBean> findAll(){
		List<MqttUserBean> pus = new ArrayList<>();
		try {
			conn = JDBCUtil.getMqttUserDbConn("");
			String SQL = "SELECT FName_code,FToken,FIsVip FROM Tb_User";
			sta = conn.prepareStatement(SQL);
			rs = sta.executeQuery();
			while (rs.next()) {
				MqttUserBean bean = new MqttUserBean(
						rs.getString("FName_code"),
						rs.getString("FToken"),
						rs.getString("FIsVip")
				);
				pus.add(bean);
			}
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}finally {
			JDBCUtil.close(rs,sta,conn);
		}
		return pus;
	}
    //删除公司项目相关数据
    public boolean deleteUser(String code){
        try {
            conn = JDBCUtil.getMqttUserDbConn("");
            String SQL = "DELETE FROM Tb_User WHERE FName_code = '"+code+"'";
            Lg.e("删除项目："+SQL);
            sta = conn.prepareStatement(SQL);
            boolean b = sta.execute();
            Lg.e("删除",b);
            return b;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,sta,conn);
        }
        return false;
    }


    //获取vip表数据
    public List<TypeBean> findAllType(){
        List<TypeBean> pus = new ArrayList<>();
        try {
            conn = JDBCUtil.getMqttUserDbConn("");
            String SQL = "SELECT FName,FNumber FROM Tb_Type";
            sta = conn.prepareStatement(SQL);
            rs = sta.executeQuery();
            while (rs.next()) {
                TypeBean bean = new TypeBean(
                        rs.getString("FName"),
                        rs.getString("FNumber")
                );
                pus.add(bean);
            }
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }finally {
            JDBCUtil.close(rs,sta,conn);
        }
        return pus;
    }



	//获取所有公司信息
	public List<UserLoginBean> getCompanyForUpgrade(){
		List<UserLoginBean> list = new ArrayList<>();
		try {
			conn = JDBCUtil.getMqttUserDbConn("");
			String SQL = "SELECT * FROM Tb_Company A LEFT  JOIN Tb_UpgradeBean B on A.AppID=B.AppID ORDER BY  ifnull(B.UpgradeTime,'99999999') DESC ";
			sta = conn.prepareStatement(SQL);
			rs = sta.executeQuery();
			while (rs.next()) {
				list.add(backBean(rs));
			}
			Lg.e("得到公司列表",list);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs,sta,conn);
		}
		return list;
	}
	//是否存在用户
	public String checkHasUser(String name,String pwd){
		String num="";
		try {
			conn = JDBCUtil.getMqttUserDbConn("");
			String SQL = "SELECT COUNT(*) AS 数量 FROM Tb_User WHERE FName = ? and FPwd = ?";
			sta = conn.prepareStatement(SQL);
			sta.setString(1,name);
			sta.setString(2,pwd);
			rs = sta.executeQuery();
			while (rs.next()) {
				num = rs.getString("数量");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs,sta,conn);
		}
		return num;
	}

	//获取所有公司信息
	public List<UserLoginBean> findUser(String name,String dbname){
		List<UserLoginBean> list = new ArrayList<>();
		try {
			conn = JDBCUtil.getMqttUserDbConn(dbname);
			String SQL = "SELECT * FROM Login_Base WHERE user_name='"+name+"' ORDER BY lid DESC ";
			sta = conn.prepareStatement(SQL);
			rs = sta.executeQuery();
			while (rs.next()) {
				list.add(backBean(rs));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs,sta,conn);
		}
		if (list.size()==0){
			list.add(new UserLoginBean("","","","","","","","",""));
		}
		Lg.e("找到用户数据",list);
		return list;
	}

	//添加公司信息
	public boolean addUserDB(AppLinkBean company,String dbname){
		try {
			conn = JDBCUtil.getMqttUserDbConn(dbname);
			String SQL = "INSERT INTO Tb_User (FName, FPwd,FName_code,FToken,FCreateTime,Img_Logo) VALUES (?,?,?,?,?,?)";
			sta = conn.prepareStatement(SQL);
			sta.setString(1,company.FName);
			sta.setString(2,company.FPwd);
			sta.setString(3,company.FName_code);
			sta.setString(4,company.FToken);
			sta.setString(5,company.FCreateTime);
			sta.setString(6,company.Img_Logo);
			int i = sta.executeUpdate();
			if(i>0){
				return true;
			}else{
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs,sta,conn);
		}
		return false;
	}
	//更新用户数据token
	public boolean changeUser(AppLinkBean company, String dbname){
		try {
			conn = JDBCUtil.getMqttUserDbConn(dbname);
			String SQL = "UPDATE Tb_User set FToken=?  WHERE FName_code='"+dbname+"'";
//			Lg.e("更新数据库语句"+SQL);
			sta = conn.prepareStatement(SQL);
			sta.setString(1,company.FToken);
			int i = sta.executeUpdate();
			if(i>0){
				return true;
			}else{
				JDBCUtil.close(rs,sta,conn);
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JDBCUtil.close(rs,sta,conn);
		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil.close(rs,sta,conn);
		}finally {
//			JDBCUtil.close(rs,sta,conn);
		}
		return false;
	}
//
//	//更新公司信息时，如果版本信息表存在该公司的app版本信息，则更新
//	public void changeUpgradeVersion(UserLoginBean company){
//		try {
//			conn = JDBCUtil.getMqttUserDbConn();
//			String findSQL ="select COUNT(*) as 数量 from Tb_UpgradeBean where AppID='"+company.getAppID()+"'";
//			sta = conn.prepareStatement(findSQL);
//			rs = sta.executeQuery();
//			String num="";
//			while (rs.next()) {
//				num = rs.getString("数量");
//			}
//			Lg.e("需要修改的版本信息："+num);
//			if (MathUtil.toD(num)>0){
//				String SQL = "UPDATE Tb_UpgradeBean set App_Version=?,App_Version2=?,App_Version3=?" +
//						" WHERE AppID='"+company.getAppID()+"'";
//				Lg.e("更新数据库语句"+SQL);
//				sta = conn.prepareStatement(SQL);
//				sta.setString(1,company.AppVersion);
//				sta.setString(2,company.AppVersion2);
//				sta.setString(3,company.AppVersion3);
//				int i = sta.executeUpdate();
////				if(i>0){
////					//更新公司信息表的app版本号
////					changeCompanyVersion(company.AppID,company.AppVersion);
////					return true;
////				}else{
////					return false;
////				}
//			}
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCUtil.close(rs,sta,conn);
//		}
//	}
//
//	//修改公司的Log
//	public boolean changeCompanyLog(UserLoginBean company){
//		Lg.e("修改的公司",company);
//		try {
//			conn = JDBCUtil.getMqttUserDbConn();
//			String findSQL ="select COUNT(*) as 数量 from Tb_Company where AppID='"+company.getAppID()+"'";
//			sta = conn.prepareStatement(findSQL);
//			rs = sta.executeQuery();
//			String num="";
//			while (rs.next()) {
//				num = rs.getString("数量");
//			}
//			Lg.e("存在需要修改的公司信息"+num);
//			if (MathUtil.toD(num)<=0){
//				return false;
//			}
//			String SQL = "UPDATE Tb_Company set Remark=? WHERE AppID='"+company.getAppID()+"'";
//			Lg.e("更新数据库语句"+SQL);
//			sta = conn.prepareStatement(SQL);
//			sta.setString(1,company.Remark);
//			int i = sta.executeUpdate();
//			if(i>0){
//				return true;
//			}else{
//				return false;
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCUtil.close(rs,sta,conn);
//		}
//		return false;
//	}

	public String getDeleteCode(){
		return "fangzuokeji12345789!@#$%";
	}


//	public List<FeedBackBean> getFeedBack(){
//		List<FeedBackBean> list = new ArrayList<>();
//		try {
//			conn = JDBCUtil.getSQLiteForFeedBack();
//			String SQL = "SELECT * FROM FeedBackOfWeb ORDER BY id DESC ";
//			sta = conn.prepareStatement(SQL);
//			rs = sta.executeQuery();
//			while (rs.next()) {
//				FeedBackBean bean = new FeedBackBean();
//				bean.id = rs.getString("id");
//				bean.name = rs.getString("name");
//				bean.phone = rs.getString("phone");
//				list.add(bean);
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCUtil.close(rs,sta,conn);
//		}
//		return list;
//	}
//	public void deleteFeedBack(String id){
//		try {
//			conn = JDBCUtil.getSQLiteForFeedBack();
//			String SQL = "DELETE id="+id+" FROM FeedBackOfWeb";
//			sta = conn.prepareStatement(SQL);
//			boolean b = sta.execute();
//			Lg.e("删除",b);
//			if (!b){
////                response.sendRedirect("error.jsp");
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCUtil.close(rs,sta,conn);
//		}
//	}

	//统一获取表数据
	private UserLoginBean backBean(ResultSet rs) throws SQLException{
		UserLoginBean bean = new UserLoginBean();
		bean.lid = rs.getInt("lid");
		bean.database = rs.getString("database");
		bean.server_pwd = rs.getString("server_pwd");
		bean.server_name = rs.getString("server_name");
		bean.server_port = rs.getString("server_port");
		bean.server_ip = rs.getString("server_ip");
		bean.user_pwd = rs.getString("user_pwd");
		bean.user_name = rs.getString("user_name");
		bean.login_time = rs.getString("login_time");
		bean.login_log = rs.getString("login_log");
		return bean;
	}

	/*
	admin  set
	*/
	// ����Ա��½
//	public boolean login(Admin user) throws Exception {
//		DbUtil lk = new DbUtil();
//		lk.connDb();
//		boolean i = false;
//		String sql = "select * from admin where name=? and pwd=?";
//		PreparedStatement ps = lk.getPstm(sql);
//		ps.setString(1, user.getName());
//		ps.setString(2, user.getPwd());
//		ResultSet rs1 = (ResultSet) ps.executeQuery();
//		if (rs1.next()) {
//			i = true;
//			rs1.close();
//			ps.close();
//		} else {
//			i = false;
//			rs1.close();
//			ps.close();
//		}
//		lk.closeDb();
//		return i;
//	}
//
//	// �����µ��û� ------------------------------------------------------
//	public PUser create(PUser use) {
//		try {
//			DbUtil dbu = new DbUtil();
//			dbu.connDb();
//			PreparedStatement prepStmt = dbu
//					.getPstm("insert into user(name,password,sex,age,clue,vip,mishi)"
//							+ "values(?,?,?,?,?,?,?)");
//
//			prepStmt.setString(1, use.getName());
//			prepStmt.setString(2, use.getPassword());
//			prepStmt.setString(3, use.getSex());
//			prepStmt.setInt(4, use.getAge());
//			prepStmt.setString(5, use.getClue());
//			prepStmt.setString(6, use.getVip());
//			prepStmt.setString(7, use.getMishi());
//			prepStmt.executeUpdate();
//			dbu.closeDb();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return use;
//	}
//
//	// �����û�����
//	public int update(PUser stu) {
//		// TODO Auto-generated method stub
//		int rowCount = 0;
//		DbUtil dbu = new DbUtil();
//		try {
//
//			dbu.connDb();
//			PreparedStatement prepStmt = dbu
//					.getPstm("update user set name=?,password=?,sex=?,age=?,clue=? where name=?");
//			prepStmt.setString(1, stu.getName());
//			prepStmt.setString(2, stu.getPassword());
//			prepStmt.setString(3, stu.getSex());
//			prepStmt.setInt(4, stu.getAge());
//			prepStmt.setString(5, stu.getName());
//			rowCount = prepStmt.executeUpdate();
//			if (rowCount == 0) {
//				throw new Exception("Update Error:Student Name:"
//						+ stu.getName());
//			}
//
//		} catch (Exception e) {
//		} finally {
//			dbu.closeDb();
//		}
//		return rowCount;
//	}
//
//	// ����ָ�����û�
//	public List<PUser> findupdate(String name) throws Exception {
//
//		DbUtil dbu = new DbUtil();
//		dbu.connDb();
//		List<PUser> puser = new ArrayList<PUser>();
//		PreparedStatement prepStmt = dbu
//				.getPstm("select * from user where name=?");
//		prepStmt.setString(1, name);
//		ResultSet rs = prepStmt.executeQuery();
//		while (rs.next()) {
//			PUser pu = new PUser();
//			pu.setRid(rs.getInt(1));
//			pu.setName(rs.getString(2));
//			pu.setPassword(rs.getString(3));
//			pu.setSex(rs.getString(4));
//			pu.setAge(rs.getInt(5));
//			pu.setClue(rs.getString(6));
//			pu.setVip(rs.getString(7));
//			pu.setMishi(rs.getString(8));
//			puser.add(pu);
//
//		}
//		dbu.closeDb();
//		return puser;
//	}
//

//
//	// ɾ���û�-------------------------web------------------------------
//	public void remove(PUser stu) throws Exception {
//		// TODO Auto-generated method stub
//		try {
//			DbUtil dbu = new DbUtil();
//			dbu.connDb();
//			PreparedStatement prepStmt = dbu
//					.getPstm("delete from user where name=?");
//			prepStmt.setString(1, stu.getName());
//			prepStmt.executeUpdate();
//			dbu.closeDb();
//		} catch (Exception e) {
//
//		} finally {
//		}
//	}
//
//	// �� ���� ����-----------------web------------------------------------
//	public List<PUser> finduserby(String name) throws Exception {
//		List<PUser> users = new ArrayList<PUser>();
//		DbUtil dbu = new DbUtil();
//		dbu.connDb();
//		PreparedStatement prepStmt = dbu
//				.getPstm("select rid,name,password,sex,age,clue,vip,mishi from user where name=?");
//		prepStmt.setString(1, name);
//		ResultSet rs = prepStmt.executeQuery();
//		try {
//			while (rs.next()) {
//				PUser u = new PUser();
//				u.setRid(rs.getInt("rid"));
//				u.setName(rs.getString("name"));
//				u.setPassword(rs.getString("password"));
//				u.setAge(rs.getInt("age"));
//				u.setSex(rs.getString("sex"));
//				u.setClue(rs.getString("clue"));
//				u.setVip(rs.getString("vip"));
//				u.setMishi(rs.getString("mishi"));
//				// u.setName(rs.getString("name"));
//				users.add(u);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			dbu.closeDb();
//		}
//		return users;
//	}
//
//	// ���������û�
//	public List<PUser> show_user() throws Exception {
//		DbUtil dbu = new DbUtil();
//		dbu.connDb();
//		List<PUser> pus = new ArrayList<PUser>();
//		PreparedStatement prepStmt = dbu.getPstm("select * from user");
//		ResultSet rs = prepStmt.executeQuery();
//		while (rs.next()) {
//			PUser pu = new PUser();
//			pu.setRid(rs.getInt(1));
//			pu.setName(rs.getString(2));
//			pu.setPassword(rs.getString(3));
//			pu.setSex(rs.getString(4));
//			pu.setAge(rs.getInt(5));
//			pu.setClue(rs.getString(6));
//			pu.setVip(rs.getString(7));
//			pu.setMishi(rs.getString(8));
//			pus.add(pu);
//		}
//		dbu.closeDb();
//		return pus;
//	}
//				/*
//					HuoDong
//					�
//					��ط���
//
//
//					*/
//
//	// �����µĻ ------------------------------------------------------
//	public HuoDong createhd(HuoDong use) {
//		try {
//			DbUtil dbu = new DbUtil();
//			dbu.connDb();
//			PreparedStatement prepStmt = dbu
//					.getPstm("insert into huodong(rid,hname,htitle,htime,hword) values(?,?,?,?,?)");
//			prepStmt.setInt(1, use.getRid());
//			prepStmt.setString(2, use.getHname());
//			prepStmt.setString(3, use.getHtitle());
//			prepStmt.setString(4, use.getHtime());
//			prepStmt.setString(5, use.getHword());
//
//			prepStmt.executeUpdate();
//			dbu.closeDb();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return use;
//	}
//
//	// ��ʾ���л---------------------------------------------------
//	public List<HuoDong> show_hd() throws Exception {
//		DbUtil dbu = new DbUtil();
//		dbu.connDb();
//		List<HuoDong> pus = new ArrayList<HuoDong>();
//		PreparedStatement prepStmt = dbu.getPstm("select * from huodong");
//		ResultSet rs = prepStmt.executeQuery();
//		while (rs.next()) {
//			HuoDong pu = new HuoDong();
//			pu.setHid(rs.getInt(1));
//			pu.setRid(rs.getInt(2));
//			pu.setHname(rs.getString(3));
//			pu.setHtitle(rs.getString(4));
//			pu.setHtime(rs.getString(5));
//			pu.setHword(rs.getString(6));
//			pus.add(pu);
//		}
//		dbu.closeDb();
//		return pus;
//	}
//
//	// ɾ��ָ���------------------delete-------------------------------
//	public void deletehd(HuoDong stu) throws Exception {
//		// TODO Auto-generated method stub
//		try {
//			DbUtil dbu = new DbUtil();
//			dbu.connDb();
//			PreparedStatement prepStmt = dbu
//					.getPstm("delete from huodong where hid=?");
//			prepStmt.setInt(1, stu.getHid());
//			prepStmt.executeUpdate();
//			dbu.closeDb();
//		} catch (Exception e) {
//
//		} finally {
//		}
//	}
//				/*
//				 *
//					PingLun
//					������ط���
//				*/
//	// �����µ����� ------------------------------------------------------
//	public PingLun createpl(PingLun use) {
//		try {
//			DbUtil dbu = new DbUtil();
//			dbu.connDb();
//			PreparedStatement prepStmt = dbu
//					.getPstm("insert into pinglun(hid,rid,pname,pword) values(?,?,?,?)");
//			prepStmt.setInt(1, use.getHid());
//			prepStmt.setInt(2, use.getRid());
//			prepStmt.setString(3, use.getPname());
//			prepStmt.setString(4, use.getPword());
//			prepStmt.executeUpdate();
//			dbu.closeDb();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return use;
//	}
//
//	// ��ʾ��������---------------------------------------------------
//	public List<PingLun> show_pl() throws Exception {
//		DbUtil dbu = new DbUtil();
//		dbu.connDb();
//		List<PingLun> pus = new ArrayList<PingLun>();
//		PreparedStatement prepStmt = dbu.getPstm("select * from pinglun");
//		ResultSet rs = prepStmt.executeQuery();
//		while (rs.next()) {
//			PingLun pu = new PingLun();
//			pu.setPid(rs.getInt(1));
//			pu.setHid(rs.getInt(2));
//			pu.setRid(rs.getInt(3));
//			pu.setPname(rs.getString(4));
//			pu.setPword(rs.getString(5));
//			pus.add(pu);
//		}
//		dbu.closeDb();
//		return pus;
//	}
//
//	// ����ָ��������
//	public List<PingLun> findpl(int hid) throws Exception {
//
//		DbUtil dbu = new DbUtil();
//		dbu.connDb();
//		List<PingLun> puser = new ArrayList<PingLun>();
//		PreparedStatement prepStmt = dbu
//				.getPstm("select * from pinglun where hid=?");
//		prepStmt.setInt(1, hid);
//		ResultSet rs = prepStmt.executeQuery();
//		while (rs.next()) {
//			PingLun pu = new PingLun();
//			pu.setPid(rs.getInt(1));
//			pu.setHid(rs.getInt(2));
//			pu.setRid(rs.getInt(3));
//			pu.setPname(rs.getString(4));
//			pu.setPword(rs.getString(5));
//			puser.add(pu);
//
//		}
//		dbu.closeDb();
//		return puser;
//	}
//	 //ɾ��ָ������-----------------------delete--------------------------
//	 public void deletepl(PingLun stu) throws Exception {
//			// TODO Auto-generated method stub
//			try{
//				DbUtil dbu=new DbUtil();
//				dbu.connDb();
//				PreparedStatement prepStmt=dbu.getPstm("delete from pinglun where pid=?");
//				prepStmt.setInt(1,stu.getPid());
//				prepStmt.executeUpdate();
//				dbu.closeDb();
//			}catch(Exception e){
//
//			}finally{
//			}
//		}
//
//	/*�糤����
//	 * forvip
//	 * ��ط���
//	 *
//	 */
//
//	// �����糤����
//		public int add_vip(PUser stu) {
//			// TODO Auto-generated method stub
//			int rowCount = 0;
//			DbUtil dbu = new DbUtil();
//			try {
//
//				dbu.connDb();
//				PreparedStatement prepStmt = dbu
//						.getPstm("update user set vip=? where rid=?");
//				prepStmt.setString(1,stu.getVip());
//				prepStmt.setInt(2, stu.getRid());
//				rowCount = prepStmt.executeUpdate();
//				if (rowCount == 0) {
//					throw new Exception("Update Error:Student Name:"
//							+ stu.getName());
//				}
//
//			} catch (Exception e) {
//			} finally {
//				dbu.closeDb();
//			}
//			return rowCount;
//		}
//		 //ɾ���Ѿ�ȷ��������-----------------------delete--------------------------
//		 public void del_forvip(Forvip stu) throws Exception {
//				// TODO Auto-generated method stub
//				try{
//					DbUtil dbu=new DbUtil();
//					dbu.connDb();
//					PreparedStatement prepStmt=dbu.getPstm("delete from forvip where rid=?");
//					prepStmt.setInt(1,stu.getRid());
//					prepStmt.executeUpdate();
//					dbu.closeDb();
//				}catch(Exception e){
//
//				}finally{
//				}
//			}
//		// ��ʾ��������---------------------------------------------------
//		public List<Forvip> show_forvip() throws Exception {
//			DbUtil dbu = new DbUtil();
//			dbu.connDb();
//			List<Forvip> pus = new ArrayList<Forvip>();
//			PreparedStatement prepStmt = dbu.getPstm("select * from forvip");
//			ResultSet rs = prepStmt.executeQuery();
//			while (rs.next()) {
//				Forvip pu = new Forvip();
//
//				pu.setRid(rs.getInt(1));
//				pu.setName(rs.getString(2));
//				pu.setReason(rs.getString(3));
//				pus.add(pu);
//			}
//			dbu.closeDb();
//			return pus;
//		}
//
//		//��ʾ����---------------------------------------------------
//		 public List<Gonggao> show_allgg() throws Exception {
//					DbUtil dbu=new DbUtil();
//					dbu.connDb();
//					List<Gonggao> pus =new ArrayList<Gonggao>();
//					PreparedStatement prepStmt=dbu.getPstm("select * from xgonggao");
//					ResultSet rs=prepStmt.executeQuery();
//						while(rs.next()){
//							Gonggao pu = new Gonggao();
//							pu.setGid(rs.getInt(1));
//							pu.setGname(rs.getString(2));
//							pu.setGclue(rs.getString(3));
//							pu.setGonggao(rs.getString(4));
//							pu.setOfschool(rs.getString(5));
//							pus.add(pu);
//						}
//						dbu.closeDb();
//					return pus;
//				}
//		// ɾ������------------------------------------------------------
//					public void removegg(String gname) throws Exception {
//						// TODO Auto-generated method stub
//						try {
//							DbUtil dbu = new DbUtil();
//							dbu.connDb();
//							PreparedStatement prepStmt = dbu
//									.getPstm("delete from xgonggao where gname=?");
//							prepStmt.setString(1, gname);
//							prepStmt.executeUpdate();
//							dbu.closeDb();
//						} catch (Exception e) {
//
//						} finally {
//						}
//					}
//
//					//��ʾ���е�������Ϣ---------------------------------------------------
//					 public List<Nmchat> show_nmchat() throws Exception {
//								DbUtil dbu=new DbUtil();
//								dbu.connDb();
//								List<Nmchat> pus =new ArrayList<Nmchat>();
//								PreparedStatement prepStmt=dbu.getPstm("select * from nmchat");
//								ResultSet rs=prepStmt.executeQuery();
//									while(rs.next()){
//										Nmchat pu = new Nmchat();
//										pu.setNid(rs.getInt(1));
//										pu.setName(rs.getString(2));
//										pu.setSex(rs.getString(3));
//										pu.setClue(rs.getString(4));
//										pu.setSchool(rs.getString(5));
//										pu.setSaytext(rs.getString(6));
//
//										pus.add(pu);
//									}
//									dbu.closeDb();
//								return pus;
//							}
//					// ɾ��ָ��������Ϣ------------------delete-------------------------------
//						public void deletenm(Nmchat stu) throws Exception {
//							// TODO Auto-generated method stub
//							try {
//								DbUtil dbu = new DbUtil();
//								dbu.connDb();
//								PreparedStatement prepStmt = dbu
//										.getPstm("delete from nmchat where nid=?");
//								prepStmt.setInt(1, stu.getNid());
//								prepStmt.executeUpdate();
//								dbu.closeDb();
//							} catch (Exception e) {
//
//							} finally {
//							}
//						}
}
