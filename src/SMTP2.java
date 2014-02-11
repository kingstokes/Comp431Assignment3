
/*
 * Comp 431 - Internet Services and Protocols
 * UNC-Chapel Hill
 * Spring 2014
 * Client-Side Processing of SMTP server
 * Program will generate commands to send file from local mail server to destination server
 * 
 * By Robert Stokes
 * 
 */




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SMTP2 {

	public static void main(String[] args) throws IOException {
		// read in a file from the command line
		// if no file is specified then terminate after printing QUIT command to console.
		BufferedReader file = null;
		if (args.length > 0) {
			file = new BufferedReader(new FileReader(args[0]));

		} else {
			System.out.println("QUIT");
			System.exit(4);
		}

		
		// create objects to write MAIL FROM COMMANDS and RCPT TO COMMANDS
		MailFromCommand mfc = new MailFromCommand();
		RCPTCommand rc = new RCPTCommand();
		
		// setup a loop to read each line from the file
		// loop will continue to issue commands and wait for responses
		// responses are read in using a separate BufferedReader
		// keep reading until end of file or an error response is received.
		// all responses will be echoed to stderr
		// all commands will be written to stdout.

		BufferedReader serverResponse = null;
		serverResponse = new BufferedReader(new InputStreamReader(System.in));

		String lineFromFile = file.readLine();
		String lineFromResponse = null;
		while (true) {
			
			mfc.printMailFromCommand(lineFromFile); // prints mail-fm-cmd
			lineFromResponse = serverResponse.readLine(); // await response
			
			//handle any awkward responses which may be less than 3 characters
			if (lineFromResponse.length() < 3){
				System.out.println("QUIT");
				System.err.println(lineFromResponse);
				System.exit(4);
			}
			// echo response to stderr
			System.err.println(lineFromResponse);
						
			if (lineFromResponse.substring(0, 3).equals("250")) {				
				// now we're ready to send a RCPT TO command
				
				lineFromFile = file.readLine();
				rc.printRCPTCommand(lineFromFile);
				lineFromResponse = serverResponse.readLine(); // await response
				
			} else {

				System.out.println("QUIT");
				System.exit(4);
			}
			
			//look at response to RCPT COMMAND
			if (lineFromResponse.length() < 3){
				System.out.println("QUIT");
				System.err.println(lineFromResponse);
				System.exit(4);
			}
			
			// echo response to stderr
			System.err.println(lineFromResponse);
			
			if (lineFromResponse.substring(0, 3).equals("250")) {
				
				// now we're ready to send a DATA command
				System.out.println("DATA");
				// await response
				lineFromResponse = serverResponse.readLine();
				
			} else {

				System.out.println("QUIT");
				System.exit(4);
			}
			
			//look at the response to DATA command
			if (lineFromResponse.length() < 3){
				System.out.println("QUIT");
				System.err.println(lineFromResponse);
				System.exit(4);
			}
			
			// echo response to stderr
			System.err.println(lineFromResponse);
			if (lineFromResponse.substring(0, 3).equals("354")) {				
				// now we're ready to print message contents
				
				// print out contents of message until we see "From:"
				while (true) {
					lineFromFile = file.readLine();
					// System.out.println("lineFromFile.length(): "+
					// lineFromFile.length());
					if (lineFromFile == null) {
						// end of file
						System.out.println(".");
						//await 250 
						lineFromResponse = serverResponse.readLine();
						
						if (lineFromResponse.length() < 3){
							System.out.println("QUIT");
							System.err.println(lineFromResponse);
							System.exit(4);
						}
						
						//echo response to stderr
						System.err.println(lineFromResponse);
						if (lineFromResponse.substring(0, 3).equals("250")){
							System.out.println("QUIT");
							System.exit(0);
						} else {
						System.out.println("QUIT");
						System.exit(4);
						}
					} else if (lineFromFile.length() >= 6
							&& lineFromFile.substring(0, 6).trim().equals("From:")) {
						// end of message
						System.out.println("."); //signals end of message
						//await 250 OK
						lineFromResponse = serverResponse.readLine();
						
						if (lineFromResponse.length() < 3){
							System.out.println("QUIT");
							System.err.println(lineFromResponse);
							System.exit(4);
						}
						
						// echo response to stderr
						System.err.println(lineFromResponse);
						if (lineFromResponse.substring(0, 3).equals("250")){
							break;
						} else {
							System.out.println("QUIT");
							System.exit(4);
						}
						

					} else {
						// print out line to standard out.
						System.out.println(lineFromFile);
					}
				}// end inner while
				
			} else {

				System.out.println("QUIT");
				System.exit(4);
			}		

			// lineFromFile now has line from new message. Loop around and print
			

			
		}// end outer while


	}// end main

}// end class
