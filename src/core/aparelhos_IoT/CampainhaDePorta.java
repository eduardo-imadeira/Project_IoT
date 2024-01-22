package  core.aparelhos_IoT;

import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import  core.updateEvents.CampainhaDePortaEventUpdate;


public class CampainhaDePorta extends IoTDevice{

	private Bezirk bezirk;

	public CampainhaDePorta(){
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Campainha inteligente");
	}

	public void sendBellUpdate() {
		CampainhaDePortaEventUpdate BellEventUpdate = null;

		BellEventUpdate = new CampainhaDePortaEventUpdate(getCurrentHour());

		//sends the event
		bezirk.sendEvent(BellEventUpdate);
	}

	public static void main(String[] args) {
		CampainhaDePorta campainha = new CampainhaDePorta();

		System.err.println("This product have a Door Bell");

		while (true) {
			System.out.println("Ring the doorbell by pressing K");

			Scanner myObj = new Scanner(System.in);
			String cmd = myObj.nextLine();

			if (cmd.equals("K")) {
				campainha.sendBellUpdate();
			}

		}
	}
}
