package util.DAO.fileOperation;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import util.DAO.DBConn;

public class FileSave {
	private SimpleDateFormat format=new SimpleDateFormat("yy-MM-dd-hh:mm");
	private String index;
	private String dbName="";
	private String spl="";
	private  FileInputStream in;
	private DBConn dbconn=new DBConn("");
	private Connection conn;
	private Statement statm;
}
