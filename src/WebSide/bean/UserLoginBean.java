package WebSide.bean;

public class UserLoginBean {
    public int lid;
    public String database;
    public String server_pwd;
    public String server_name;
    public String server_port;
    public String server_ip;
    public String user_pwd;
    public String user_name;
    public String login_time;
    public String login_log;

    public UserLoginBean() {

    }
    public UserLoginBean(String database, String server_pwd, String server_name, String server_port, String server_ip, String user_pwd, String user_name, String login_time, String login_log) {
        this.database = database;
        this.server_pwd = server_pwd;
        this.server_name = server_name;
        this.server_port = server_port;
        this.server_ip = server_ip;
        this.user_pwd = user_pwd;
        this.user_name = user_name;
        this.login_time = login_time;
        this.login_log = login_log;
    }
}
