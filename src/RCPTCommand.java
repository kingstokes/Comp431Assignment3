



public class RCPTCommand {

	private String forwardPath;
	
	public void printRCPTCommand(String line){
		forwardPath = line.substring(4);
		System.out.println("RCPT TO: " + forwardPath);
		
	}
}
