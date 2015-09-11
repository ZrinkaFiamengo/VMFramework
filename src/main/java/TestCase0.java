import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ezrifia 08.09.2015.
 *
 */

public class TestCase0 {

	public static void main(String[] args) {

		/*
		LinuxCommands communicate = new LinuxCommands();
		
		//SshConnection communicate2 = new SshConnection("localhost", "", "");
		
		if(!communicate.connect())
		{
			return;
		}
		
		final String output= communicate.sendCommand("ls", "|wc -l");
		//final String output= communicate.sendCommand("cat", " log.txt | grep 'error' ");
		//final String output= communicate.sendCommand("ls", "|wc -l");
		//final String output= communicate.sendCommand("cat", "a.txt | grep 'Error\\|Warning'");
		//String output = communicate.chkconfig("ufw");

		//if (output!=null)
	    //communicate.countErrorWarning("a.txt");
			//communicate.getIpAdress(); 
		
		if (output!=null)
		{
			System.out.println("output: " + output);
		}
		else
		{
			System.out.println("An error occured during command execution.");
		}


		//communicate.countErrorWarning("log.txt");
		
		//int number_of_folders= communicate.countFiles(true);
		//System.out.println(number_of_folders);
               
		communicate.disconnect();
		*/
		
		SQLiteCommunicator sqlConnect = new SQLiteCommunicator();
		sqlConnect.connect();
		sqlConnect.executeUpdate("drop table if exists data");
		sqlConnect.executeUpdate("create table data (id integer primary key autoincrement, data1 text, data2 integer)");
		sqlConnect.executeUpdate("insert into data(data1,data2) values('Neki tamo tekst1', 1234)");
		sqlConnect.executeUpdate("insert into data(data1,data2) values('Neki tamo tekst2', 345)");
		sqlConnect.executeUpdate("insert into data(data1,data2) values('Neki tamo tekst3', 3444)");
		ResultSet rs= sqlConnect.executeQuery("select * from data");
		try {
			while(rs.next())
			{
				System.out.println(rs.getString("id")+ "\t"+ rs.getString("data1") + "\t" + rs.getString("data2"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} 
}
