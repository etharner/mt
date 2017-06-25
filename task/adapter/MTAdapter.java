import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.IOException;

public class MTAdapter {
	public static void main(String args[]) throws Exception {
		String testName = "test";
		try {
			testName = args[0];
		} catch (Exception e) {
			System.out.println("Provide test name.");
			return;
		}

		MTConfig mtConfig = new MTConfig(testName);
		MTCmd mtCmd = new MTCmd(mtConfig);
		mtCmd.mtSetExpert();
		mtCmd.mtRunBacktest();
	}
}
