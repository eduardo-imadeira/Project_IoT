package  core.updateEvents;

import com.bezirk.middleware.messages.Event;

public class FechaduraEventUpdate extends Event{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String acceptedCode;
	
	public FechaduraEventUpdate(String acceptedCode) {
		this.acceptedCode = acceptedCode;
	}
	
	public FechaduraEventUpdate() {
	}

	boolean giveAcess(String guess) {
		
		if(this.getAcceptedCodes().contains(guess)) {
			return true;
		}
		return false;
	}
	
	public String getAcceptedCodes() {
		return acceptedCode;
	}
}
