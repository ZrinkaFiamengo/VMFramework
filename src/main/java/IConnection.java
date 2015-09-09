/**
 * @author ezrifia 08.09.2015.
 *
 *	Interface used for managing different types of connections
 */

public interface IConnection {

	/**
	 * Function used to connect to the host
	 * @return value is flag which indicates if connection was executed correctly.
	 * Function returns true if everything went as expected and false if an error occurred
	 */
	public boolean connect();
	
	/**
	 * Function used to disconnect from the host
	 */
	public void disconnect();

	/**
	 * Function used to send command to the host
	 * @param command that needs to be executed
	 * @return output value from executed command
	 * If any error occurs while executing function return value will be null
	 */
	public String sendCommand(String command);
	
	/**
	 * Function used to send argumented command to the host
	 * @param command that needs to be executed
	 * @param args arguments that need to be executed with command
	 * @return output value from executed command
	 * If any error occurs while executing function return value will be null
	 */
	public String sendCommand(String command, String args);


}
