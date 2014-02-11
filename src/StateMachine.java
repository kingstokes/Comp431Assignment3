
/*
 * 
 * This is a simple state machine to determine which command to output for a given line 
 * in  a file. Also, state machine determines which response from stdin will be considered
 * valid.
 * 
 */


public class StateMachine {

	private boolean awaiting250 = false;
	private boolean awaiting354 = false;
	private int state;
	
	public StateMachine(){
		state = 1;
	}
	
	public void setState(int x){
		//state may be any integer from 1 to 3.
		state = x;
		if (x == 1){
			awaiting250 = true;
			awaiting354 = false;
		} else if (x == 2){
			awaiting250 = true;
			awaiting354 = false;
		} else if (x == 3){
			awaiting354 = true;
			awaiting250 = false;
		}
	}
	
	public int checkState(){
		return state;
	}
	
	public boolean isAwaiting250(){
		return awaiting250;
	}
	
	public boolean isAwaiting354(){
		return awaiting354;
	}
}
