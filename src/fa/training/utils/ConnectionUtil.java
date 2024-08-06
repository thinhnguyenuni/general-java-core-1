package fa.training.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//C:\Users\Van Thinh\Downloads\sqljdbc_12.2.0.0_enu\sqljdbc_12.2\enu



public class ConnectionUtil {

	private static final String URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;";
	private static final String DATABSE = "databaseName=BTJP;";
	private static final String USER_NAME = "sa";
	private static final String PASSWORD = "123";
	
//	private static final String URL = "jdbc:sqlserver://quanlithitienganh.database.windows.net:1433;encrypt=true;trustServerCertificate=true;";
//	private static final String DATABSE = "databaseName=QuanLiThiTiengAnh;";
//	private static final String USER_NAME = "QuanLiProJect";
//	private static final String PASSWORD = "Project@12345";

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(URL + DATABSE, USER_NAME, PASSWORD);
			// localhost:1433 - <IP may co DB, truong hop cai tai may ca nhan thi la
			// localhost>:<port - mac dinh luc cai SQL server ko thay doi gi thi la 1433>
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Connect that bai");
		}

		return con;
	}
	public static void main(String[] args) {
		Connection con = ConnectionUtil.getConnection();
		if (con != null)
			System.out.println("Connect thanh cong");
		else
			System.out.println("Connect that bai");
	}
}
