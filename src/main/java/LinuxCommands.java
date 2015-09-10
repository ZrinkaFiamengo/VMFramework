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
		output = sendCommand("cat", " " + fileName+ " |awk '{print tolower($0)}' | grep 'error\\|warning'");
		if(output.contains("No such file or directory"))
		{
			System.out.println("You entered name of the file which does not exist in the current folder.");
			return;
		}
		System.out.println("LIST OF POTENTIAL PROBLEMS IN LOG: " + fileName+ "\n");
		System.out.println(output);
		Integer numberOfErrors = Integer.parseInt(sendCommand("cat", " " + fileName+ " |awk '{print tolower($0)}'  | grep 'error' | wc -l"));
		Integer numberOfWarnings = Integer.parseInt(sendCommand("cat", " " + fileName+ " |awk '{print tolower($0)}' | grep 'warning' | wc -l"));
		Integer totalNumber = numberOfErrors + numberOfWarnings;
		System.out.println("**********");
		System.out.println("Number of errors:" + numberOfErrors.toString());
		System.out.println("Number of warnings:" + numberOfWarnings.toString());
		System.out.println("TOTAL:" + totalNumber.toString());
	}
}