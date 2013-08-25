package util.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 用于连接数据库
 * 
 * @author xby64
 * 
 */
public class DBConn {
	private String dbName;

	public DBConn(String dbName) {
		this.dbName = dbName;
	}

	public Connection getConn() {

		try {
			Class.forName(ConnectionInfo.DRIVER);
			ConnectionInfo.setUrl(dbName);
			Connection conn = DriverManager.getConnection(ConnectionInfo
					.getUrl());
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
