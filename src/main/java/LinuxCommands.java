/**
 * Class used for maintaining Linux commands.
 * Extends SshConnection class which allows it to connect via SSH protocol.
 * @author ezrifia 09.09.2015. - GIT TEST NO.1
 *
 */
public class LinuxCommands extends SshConnection{
	
	/**
	 * 
	 * Function counts how many files current directory has
	 * @param allFiles boolean value which needs to be set as true if user wants to count all files (including hidden ones)
	 * @return function returns number of files in the current directory 
	 * 
	 */
	public int countFiles(boolean allFiles)
	{
		final String output;
		if(allFiles)
			output = sendCommand("ls", "-a|wc -l");
		else
			output = sendCommand("ls", "|wc -l");
		
		return Integer.parseInt(output);
	}
	
	/**
	 * FUnction executes command chkconfig 
	 * @param args argument to be send with function chkconfig
	 * Function covers situation where given command is not installed by installing it before execution
	 * @return
	 */
	public String chkconfig(String args)
	{
		String output;
		output = sendCommand("chkconfig", args);
		if(output.contains("The program 'chkconfig' is currently not installed.")||output.contains("/sbin/chkconfig: No such file or directory"))
		{
			System.out.println("Command not installed. System will now install it.");
			System.out.println("Ignore next messages:");
			sendCommand("sudo", "apt-get install chkconfig");
			cli.send(password);
			System.out.println("***********************************");
			output = sendCommand("chkconfig", args);
		}
		return output;
	}
	

	
	public void countErrorWarning(String fileName)
	{
		String output;
		output = sendCommand("cat", fileName + "| tr 'A-Z' 'a-z' |grep 'error\\|warning'");
		if(output.contains("No such file or directory"))
		{
			System.out.println("You entered name of the file which does not exist in the current folder.");
			return;
		}
		System.out.println("LIST OF POTENTIAL PROBLEMS IN LOG: " + fileName);
		System.out.println(output);
		Integer numberOfErrors = Integer.parseInt(sendCommand("cat", fileName+ "| tr 'A-Z' 'a-z' |grep error | wc -l"));
		Integer numberOfWarnings = Integer.parseInt(sendCommand("cat", fileName+ "| tr 'A-Z' 'a-z' |grep warning | wc -l"));
		Integer totalNumber = numberOfErrors + numberOfWarnings;
		System.out.println("**********");
		System.out.println("Number of errors:" + numberOfErrors.toString());
		System.out.println("Number of warnings:" + numberOfWarnings.toString());
		System.out.println("TOTAL:" + totalNumber.toString());
	}
	
	/** Function will print your IP address from ifconfig command output
	 *  "eth2" and "inet addr:1" are strings sequences whose index numbers are needed to locate ip address in output
	 *  IP_adr is substring that contains ip address
	 *  result1 and result2 are variables where indexes are stored  
	 */
	public void getIpAdress()
	{
		String output;
		String eth = "eth1";
		String inet_addr = "inet addr:1";
		String IP_adr = "";
		int result1;
		int result2;
		output = sendCommand("ifconfig");
		
		result1 = output.indexOf(eth);
		result2 = output.indexOf(inet_addr, result1);
		IP_adr = output.substring(result2 + 10, result2 + 22);
		System.out.println(IP_adr);	
	}
}