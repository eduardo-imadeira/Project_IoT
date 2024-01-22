package  core.aparelhos_IoT;

import java.util.Arrays;
import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import  core.updateEvents.HelpEventUpdate;
import  core.updateEvents.LampadaEventUpdate;
import  core.updateEvents.FechaduraEventUpdate;
import core.updateEvents.SireneEventUpdate;


public class Comando extends IoTDevice{

	private Bezirk bezirk;

	public Comando() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Comando inteligente");

	}

	public void sendBotaoUpdate(int pressedButton) {
		switch (pressedButton) {
		case 1:
			
			//sends the event
			bezirk.sendEvent(new HelpEventUpdate(getCurrentHour()));

			break;
		case 2:
			// botão 2 serve para apagar as luzes;

			//sends the event
			bezirk.sendEvent(new LampadaEventUpdate());

			break;
		case 3:
			//nos produtos com sirene o botão 3 serve para ativar a sirene;

			//sends the event
			bezirk.sendEvent(new SireneEventUpdate ());

			break;
		case 4:
			// nos produtos com fechadura inteligente, o botão 4 serve para abrir a porta.

			//sends the event
			bezirk.sendEvent(new FechaduraEventUpdate());
			

			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		
		System.err.println("This product have a smart controller");
		
		Comando remote = new Comando();

		Scanner myObj = new Scanner(System.in); 
		while(true) {
			 
			System.out.println("Press 1, 2, 3 or 4:");

			int cmd = myObj.nextInt(); 
			
			if (Arrays.asList(1,2,3,4).contains(cmd)) {
				remote.sendBotaoUpdate(cmd);
			} else {
				System.err.println("incorrect input");
			}
			
		}

	}

}
