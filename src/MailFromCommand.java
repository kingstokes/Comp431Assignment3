public class MailFromCommand {

	private String reversePath;
		
	public void printMailFromCommand(String line) {
		reversePath = line.substring(6);
		System.out.println("MAIL FROM: " + reversePath);
	}

	

	

}
