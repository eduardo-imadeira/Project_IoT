package  core.aparelhos_IoT;

import java.awt.Toolkit;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import core.updateEvents.SinaisDeLuzEventUpdate;
import core.updateEvents.SireneEventUpdate;


public class Sirene extends IoTDevice{

	private static boolean luzesLed= false;

	private Bezirk bezirk;

	public Sirene(){
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Sirene inteligente");

		final EventSet sireneEvents = new EventSet(SireneEventUpdate.class);

		sireneEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {

				Toolkit.getDefaultToolkit().beep();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Toolkit.getDefaultToolkit().beep();
				System.err.println("beep beep");


			}

		});
		bezirk.subscribe(sireneEvents);

		final EventSet lightEvents = new EventSet(SinaisDeLuzEventUpdate.class);

		lightEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {

				if(luzesLed) {

					try {
						System.out.println("leds on");
						Thread.sleep(500);
						System.out.println("leds off");
						Thread.sleep(500);
						System.out.println("leds on");
						Thread.sleep(500);
						System.out.println("leds off");
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


			}

		});

		bezirk.subscribe(lightEvents);
	}



	public static void main(String[] args) {
		Sirene sirene = new Sirene();

		System.err.println("This product have a Siren");


	}

	public static void setLuzesLed() {
		luzesLed= true;
	}

	public static boolean getLuzesLed() {
		return luzesLed;
	}
}
