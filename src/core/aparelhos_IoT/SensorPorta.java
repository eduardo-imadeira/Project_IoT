package  core.aparelhos_IoT;

import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import  core.updateEvents.AberturaDePortaEventUpdate;

public class SensorPorta extends IoTDevice{
	private static Bezirk bezirk;
	private final AberturaDePortaEventUpdate doorUpdate;

	public SensorPorta() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Open Door Zirk");

		// Create a single instance of the EventoAberturaUpdate class
		doorUpdate = new AberturaDePortaEventUpdate();


	}

	public void openDoor() {
		// Update the values in the EventoAberturaUpdate instance
		doorUpdate.setState(true);
		doorUpdate.setFlag("");

		// Send the updated instance as an event
		bezirk.sendEvent(doorUpdate);
	}
	
	public void closeDoor() {
		// Update the values in the EventoAberturaUpdate instance
		doorUpdate.setState(false);
		doorUpdate.setFlag("");

		// Send the updated instance as an event
		bezirk.sendEvent(doorUpdate);
	}



	public static void main(String args[]) throws InterruptedException {
		SensorPorta sens = new SensorPorta();
		System.err.println("This product have a Door Sensor");

		while(true) {
			Scanner myObj = new Scanner(System.in);  
			System.out.println("Choose: 'open' or 'close'");

			String cmd = myObj.nextLine();  

			if (cmd.equals("open")) {
				//bezirk.sendEvent(new AberturaDePortaEventUpdate(true, ""));
				sens.openDoor();
			} else if (cmd.equals("close")) {
				sens.closeDoor();
				//bezirk.sendEvent(new AberturaDePortaEventUpdate(false, ""));
			}
		}

	}

	public boolean isOpen() {
		return doorUpdate.getState() ? true : false;
	}
}

