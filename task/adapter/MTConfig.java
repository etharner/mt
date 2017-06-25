import java.util.Date;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class MTConfig {
	private final String mtTemplate = "config.template.ini";
	private final String mtConfig = "config.ini";

	private String mtSymbol;
	private String mtPeriod;
	private String mtFromDate;
	private String mtToDate;
	private String mtDeposit;
	private String mtLeverage;
	private String mtExpert;
	private String mtReport;

	public MTConfig(String test) throws Exception {
		Properties templateIni = this.readIni(this.mtTemplate);
		Properties testIni = this.readIni(test);
		
		this.mtExpert = templateIni.getProperty("Expert").substring("Advisors".length()) + ".mq5";
		this.mtReport = templateIni.getProperty("Report");

		this.mtSymbol = testIni.getProperty("Symbol");
		this.mtPeriod = testIni.getProperty("Period");
		this.mtFromDate = testIni.getProperty("FromDate");
		this.mtToDate = testIni.getProperty("ToDate");
		this.mtDeposit = testIni.getProperty("Deposit");
		this.mtLeverage = testIni.getProperty("Leverage");

		this.writeIni(this.mtTemplate, this.mtConfig);

		PrintWriter templateW = new PrintWriter(new FileWriter(this.mtConfig, true));
		templateW.write(
			"Symbol=" + this.mtSymbol + '\n' + 
			"Period=" + this.mtPeriod + '\n' + 
			"FromDate=" + this.mtFromDate + '\n' + 
			"ToDate=" + this.mtToDate + '\n' + 
			"Deposit=" + this.mtDeposit + '\n' + 
			"Leverage=" + this.mtLeverage + '\n'
		);
		templateW.flush();
		templateW.close();
	}

	private void writeIni(String ini1, String ini2) throws Exception {
		PrintWriter w = new PrintWriter(new File(ini2));
		w.print("");
		w.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ini1))));
		BufferedWriter out = new BufferedWriter(new FileWriter(ini2, true));
 
		String line = null;
		while ((line = in.readLine()) != null) {
			out.write(line);
			out.newLine();
		}
 
		in.close();
		out.close();
	}

	private Properties readIni(String ini) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(ini));
		} catch (Exception e) {
        	e.printStackTrace();
      	}
		
		return p;
	}

	public String getMtConfig() {
		return this.mtConfig;
	}

	public String getMtExpert() {
		return this.mtExpert;
	}

	public String getMtReport() {
		return this.mtReport;
	}
}