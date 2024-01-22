package  core.updateEvents;

import com.bezirk.middleware.messages.Event;

public class AberturaDePortaEventUpdate extends Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean state = false;
	private String flag = "";
	private int tempoMax = 0;
	
	public AberturaDePortaEventUpdate(boolean state, String flag) {
		this.state = state;
		this.flag = flag;
	}
	
	public AberturaDePortaEventUpdate(int tempoMax) {
		this.tempoMax = tempoMax;
	}

	public AberturaDePortaEventUpdate() {
	}


	public boolean getState() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}

	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getTempoMax() {
		return tempoMax;
	}


}
