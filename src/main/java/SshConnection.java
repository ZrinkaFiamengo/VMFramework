import com.ericsson.commonlibrary.remotecli.Cli;
import com.ericsson.commonlibrary.remotecli.CliFactory;
import com.ericsson.commonlibrary.remotecli.exceptions.AuthenticationException;
import com.ericsson.commonlibrary.remotecli.exceptions.ConnectException;
import com.ericsson.commonlibrary.remotecli.exceptions.ConnectionToServerException;
import com.ericsson.commonlibrary.remotecli.exceptions.LoginException;
import com.ericsson.commonlibrary.remotecli.exceptions.ReadException;
import com.ericsson.commonlibrary.remotecli.exceptions.ReadTimeoutException;
import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.commonlibrary.remotecli.exceptions.UnableToOpenSessionException;
import com.ericsson.commonlibrary.remotecli.exceptions.WriteException;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Class used for maintaining SSH connections.
 * Implements IConnection interface
 * @author ezrifia 08.09.2015.
 *
 */
public class SshConnection implements IConnection{


	protected String host;
	protected String username;
	protected String password;
	final protected Cli cli;
	
	

	/**
	 * Constructor of the class SshConnection.
	 * Without parameters system connects to the default host
	 */
	public SshConnection()
	{
		Configuration config = new Configuration();
		this.host = config.getHost();
		this.username = config.getUsername();
		this.password = config.getPassword();
		cli = CliFactory.newSsh(host, username, password);
	}
	
	/**
	 * 
	 * Constructor of the class SshConnection which allows user to connect to chosen host. 
	 * @param newHost
	 * @param newUsername
	 * @param newPassword
	 */
	public SshConnection(String newHost, String newUsername, String newPassword)
	{
		this.host = newHost;
		this.username = newUsername;
		this.password = newPassword;
		cli = CliFactory.newSsh(host, username, password);
	}


	public boolean connect()
	{
		boolean success = false;
		try {
            cli.connect();
            success = true;
        } catch (final ReadTimeoutException e) {
            System.out.println("Took to long to find prompt");
            //e.printStackTrace();
        } catch (final ReadException e) {
            System.out.println("Failed to find prompt");
            //e.printStackTrace();
        } catch (final ConnectionToServerException e) {
            System.out.println("Offine host or invalid host");
            //e.printStackTrace();
        } catch (final AuthenticationException e) {
            System.out.println("Invalid Authentication process");
            //e.printStackTrace();
        } catch (final LoginException e) {
            System.out.println("Username/password probably wrong");
            //e.printStackTrace();
        } catch (final UnableToOpenSessionException e) {
            System.out.println("Failed to open session");
            //e.printStackTrace();
        } catch (final ConnectException e) {
            System.out.println("Some connection problem");
            //e.printStackTrace();
        } catch (final RemoteCliException e) {
            System.out.println("Other unknown error");
            //e.printStackTrace();
        } 
		return success;
	}


	public void disconnect()
	{
		cli.disconnect(); 
	}
	
	
	public String sendCommand(String command, String args)
	{
		if(checkIfStringIsCommand(command) == false)
			return null;
		command = isCommandLowerCase(command);
		
		String argumentedCommand = command + " " + args;
		
		return ExecuteCommand(argumentedCommand);
	}
	
	
	public String sendCommand(String command)
	{
		if(checkIfStringIsCommand(command) == false)
			return null;
		command = isCommandLowerCase(command);
		return ExecuteCommand(command);	
	}
	
	/**
	 * Function checks if string is allowed command
	 * @param command user sent
	 * @returns true if command is allowed, and if not then returns false 
	 */
	private boolean checkIfStringIsCommand(String command)
	{
		try{

			Command.valueOf(command);
		}
		catch(Exception e)
		{
			System.out.println("Command you asked for is not allowed command!");
			return false;
		}
		return true;
	}
	
	/**
	 * Function which checks if command is written in lower case, as it is supouse to be in Linux
	 * If not, command is changed to lower case and warning is send
	 * @param command raw command
	 * @return command in lower case
	 */
	private String isCommandLowerCase(String command)
	{
		if(command!=null && !command.equals(command.toLowerCase()))
		{
			System.out.println("Warning: All linux commands should to be written in lowercase!");
			command = command.toLowerCase();
		}	
		return command;
	}
	
	
	/**
	 * Function executes valid command
	 * @param FullCommand command that passed security checks
	 * @return output value of the command
	 */
	private String ExecuteCommand(String FullCommand)
	{
		String output=null;
		
		try {
			if(FullCommand!=null)
				output = cli.send(FullCommand);
        } catch (final ReadTimeoutException e) {
            System.out.println("Took to long to find prompt");
            //e.printStackTrace();
        } catch (final ReadException e) {
            System.out.println("Failed to find prompt");
            //e.printStackTrace();
        } catch (final WriteException e) {
            System.out.println("Failed to send");
            //e.printStackTrace();
        } catch (final RemoteCliException e) {
            System.out.println("Other unknown error");
            //e.printStackTrace();
        } 
		return output;
	}

}
