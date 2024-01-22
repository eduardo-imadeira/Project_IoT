package  core.aparelhos_IoT;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import core.updateEvents.LampadaEventUpdate;
import core.updateEvents.SinaisDeLuzEventUpdate;


public class Lampada extends IoTDevice{
	private boolean state;
	
	private Bezirk bezirk;

	public Lampada() {

		state=false;
		BezirkMiddleware.initialize();
		 Bezirk bezirk = BezirkMiddleware.registerZirk("Lamp Assistant");

		final EventSet LampEvents = new EventSet(LampadaEventUpdate.class);

		LampEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				state = !state;

				System.out.println(state?"LIGADA":"DESLIGADA");


			}

		});
		bezirk.subscribe(LampEvents);

		final EventSet lightEvents = new EventSet(SinaisDeLuzEventUpdate.class);

		lightEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {

				System.out.println("lights on");
				System.out.println("lights off");
				System.out.println("lights on");
				System.out.println("lights off");
			}

		});

		bezirk.subscribe(lightEvents);

	}



	public static void main(String[] args) {
		new Lampada();
		System.err.println("This product have a Lamp");

	}   
}
