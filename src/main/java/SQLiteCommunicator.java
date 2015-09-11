//import org.sqlite.SQLiteConfig;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;



public class SQLiteCommunicator {
	private static SQLiteCommunicator instance = null;
	//private static SQLiteConfig configuration = new SQLiteConfig();
	private static int timeout = 30;
	
	Connection connection = null;
	
	public static SQLiteCommunicator getInstance()
	{
		if(instance == null)
			instance = new SQLiteCommunicator();
		return instance;
	}
	
	public void connect()
	{
		try{
			String url = "jdbc:sqlite:C:\\Users\\ezrifia\\Desktop\\Dokumenti\\database.db";
	        connection = DriverManager.getConnection (url);
		}
		 catch (SQLException e) {
				e.printStackTrace();
		}
	}
	
	public void dissconnect() {
		try {
	        if(!connection.equals(null)) {
	          connection.close();
	          connection = null;
	        }
	    }
	    catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	public boolean checkConnection() {
		boolean passed = true;
		try {
			if(connection.equals(null) && !connection.isValid(timeout)) {
				passed = false;
			}
		} catch (SQLException e) {
			passed = false;
			e.printStackTrace();
		}
		return passed;
	}
	
	public Integer executeUpdate(String update) {
		if(checkConnection()) {
			Integer result = null;
			Statement statement = null;
			try {
				statement = connection.createStatement();
				result = statement.executeUpdate(update);
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	public ResultSet executeQuery(String query) {
		if(checkConnection()) {
			try {
				ResultSet rs = null;
				Statement statement = null;
				statement = connection.createStatement();
				rs = statement.executeQuery(query);
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
}
