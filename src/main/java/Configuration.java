import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ericsson.commonlibrary.remotecli.CliFactory;


public class Configuration {
	
	private String host;
	private String username;
	private String password;
	
	public Configuration()
	{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("src/main/java/config.xml");
			
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName("Configuration");
			Node node = nodeList.item(0);
			
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element elem =(Element)node;
				this.host = elem.getElementsByTagName("host").item(0).getTextContent();
				this.username = elem.getElementsByTagName("username").item(0).getTextContent();;         
				this.password = elem.getElementsByTagName("password").item(0).getTextContent();;
			}
		}
		catch (Exception e)
		{
			System.out.println("Erorr");
			e.printStackTrace();
		}
	}
	
	public String getHost()
	{
		return this.host;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}

}
