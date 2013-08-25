package util.DAO;
/**
 * 用于设置数据库连接信息
 * @author xby64
 *
 */
public class ConnectionInfo {
	public static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
	private static String url="";
	/**
	 * @return the url
	 */
	public static String getUrl() {
		return url;
	}
	/**通过传入要连接的数据库名来设置URL,虽然有点违背javabean原则,但这样视乎好点
	 * @param dbName
	 */
	public static void setUrl(String dbName) {
		url="jdbc:derby:"+dbName.trim()+";create=tru";
	}
		
}
