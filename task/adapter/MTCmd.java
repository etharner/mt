import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class MTCmd {
	private MTConfig mtConfig;

	private String mt5;
	private String mtAdvisors;
	private String mtTerminal;
	private String mtCopy;
	private String mtCompile;

	public MTCmd(MTConfig mtConfig) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File inputFile = new File("MTCmd.xml");
		Document doc = builder.parse(inputFile);

		this.mtConfig = mtConfig;
		this.mt5 = doc.getElementsByTagName("MT5").item(0).getTextContent(); 
		this.mtAdvisors = doc.getElementsByTagName("MTAdvisors").item(0).getTextContent(); 
		this.mtTerminal = doc.getElementsByTagName("MTTerminal").item(0).getTextContent(); 
		this.mtCopy = doc.getElementsByTagName("MTCopy").item(0).getTextContent(); 
		this.mtCompile = doc.getElementsByTagName("MTCompile").item(0).getTextContent(); 
	}

	private void execCommand(String cmd) {
		System.out.println(cmd);
		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt.exec(cmd);
			p.waitFor();
		} catch (Exception e) {
        	e.printStackTrace();
      	}
	}

	private void mtCompile() {
		String cmd = this.mt5 + this.mtCompile + "\"" + this.mt5 + this.mtAdvisors + this.mtConfig.getMtExpert() + "\" ";
		this.execCommand(cmd);
	}

	public void mtSetExpert() {
		String cmd = this.mtCopy + " " + this.mtConfig.getMtExpert() + " \"" + this.mt5 + this.mtAdvisors + "\"";
		this.execCommand(cmd);
		this.mtCompile();
	}

	public void mtRunBacktest() {
		String cmd = this.mt5 + this.mtTerminal + " /config:\"" + this.mtConfig.getMtConfig() + "\"";
		this.execCommand(cmd);

		String[] files = {".htm", ".png", "-holding.png", "-hst.png", "-mfemae.png"};

		for (String s: files) {
			cmd = this.mtCopy + " \"" + this.mt5 + this.mtConfig.getMtReport() + s + "\" .";
			this.execCommand(cmd);
		}
	}
}