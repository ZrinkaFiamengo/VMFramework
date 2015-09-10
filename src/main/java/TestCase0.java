/**
 * @author ezrifia 08.09.2015.
 *
 */

public class TestCase0 {

	public static void main(String[] args) {

		LinuxCommands communicate = new LinuxCommands();
		//SshConnection communicate2 = new SshConnection("localhost", "", "");
		
		if(!communicate.connect())
		{
			return;
		}
			
		//final String output= communicate.sendCommand("ls", "|wc -l");
		//final String output= communicate.sendCommand("cat", "a.txt | grep 'Error\\|Warning'");
		//String output = communicate.chkconfig("ufw");
		
	    //communicate.countErrorWarning("a.txt");
		
		communicate.getIpAdress(); 
		
		/*if (output!=null)
		{
			System.out.println("output: " + output);
		}
		else
		{
			System.out.println("An error occured during command execution.");
		}
		*/
		
		//int number_of_folders= communicate.countFiles(true);
		//System.out.println(number_of_folders);
               
		communicate.disconnect();   
		} 
}
