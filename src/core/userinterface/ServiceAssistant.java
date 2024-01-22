package  core.userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import core.funcionalidades.DetecaoTempoPortaAberta;
import core.funcionalidades.DetecaoVisitante;
import core.funcionalidades.PedidoAjuda;
import core.funcionalidades.avisos_internos.MsgInterna;
import core.i18n.I18N;
import core.i18n.Messages;
import core.updateEvents.CampainhaDePortaEventUpdate;
import core.updateEvents.HelpEventUpdate;
import core.updateEvents.SinaisDeLuzEventUpdate;
import core.updateEvents.AberturaDePortaEventUpdate;
import core.updateEvents.SireneEventUpdate;
import core.userinterface.menu.ExitAction;
import core.userinterface.menu.Menu;
import core.userinterface.menu.Option;


public class ServiceAssistant {


	private static boolean surdo = false;
	private static List<String> sensoresLigados = new ArrayList<>();
	private static boolean  vozSintetizada= false;

	public static void setVozSintetizadaAviso(){
		vozSintetizada= true;

	}
	public static void setSurdo(){
		surdo= true;
	}
	public static boolean getSurdo(){
		return surdo;
	}

	public static  List<String>  getSensores(){
		return sensoresLigados;
	}



	public static void addSensor(String sensor){
		sensoresLigados.add(sensor);
	}


	public ServiceAssistant() {
		BezirkMiddleware.initialize();

		final Bezirk bezirk = BezirkMiddleware.registerZirk("Service Assistant");

		//Funcionalidade :  Deteção Porta Aberta && abrir fora de horas
		//deteção porta aberta: permitem definir quanto tempo a porta aberta precisa de estar
		// aberta para ser despoletado um aviso e fazem esses avisos.

		final EventSet doorTimerEvents = new EventSet(AberturaDePortaEventUpdate.class);

		doorTimerEvents.setEventReceiver(new EventSet.EventReceiver() {

			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {

				if (event instanceof AberturaDePortaEventUpdate) {
					final AberturaDePortaEventUpdate update = (AberturaDePortaEventUpdate) event;

					if(DetecaoTempoPortaAberta.getInstance().getTempoMax()!=0){

						new Thread(()->{
							int timer= DetecaoTempoPortaAberta.getInstance().getTempoMax();

							while(update.getState()){
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									e.printStackTrace();
								}
								timer--;
								if(timer == 0){

									DetecaoTempoPortaAberta.getInstance().sendAlert(vozSintetizada);
									if(surdo) bezirk.sendEvent(new SinaisDeLuzEventUpdate());
									break;
								}
							}
						}).start();

					}else{
						MsgInterna notificacao = new MsgInterna(I18N.getString(Messages.MAX_TIME_UNDEFINED) +I18N.getString(Messages.DOOR_TIMER_SERVICE));
						if (vozSintetizada) notificacao.setVozSintetizadaAviso();
						notificacao.sendWarning();
						if(surdo) {

							bezirk.sendEvent(new SinaisDeLuzEventUpdate());
						}
					}

					System.err.println( DetecaoVisitante.getInstance().getVisitanteForaDeHoras());
					
					if( DetecaoVisitante.getInstance().getVisitanteForaDeHoras() && update.getState()){

						System.err.println("entrou");
						DetecaoVisitante.getInstance().sendExternalAlert();

					}
				}

			}
		});

		bezirk.subscribe(doorTimerEvents);


		// Funcionalidade : PEDIDO AJUDA
		final EventSet botaoEvents = new EventSet(HelpEventUpdate.class);
		botaoEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {

				if (event instanceof HelpEventUpdate) {
					final HelpEventUpdate botaoUpdate = (HelpEventUpdate) event;
					String currentUpdateHour  =botaoUpdate.getCurrentHour();

					PedidoAjuda.getInstance().sendAlert(currentUpdateHour, vozSintetizada);



					if(surdo) {

						bezirk.sendEvent(new SinaisDeLuzEventUpdate());

					}

				}


			}

		});

		bezirk.subscribe(botaoEvents);

		// Funcionalidade : Tocar ah campainha && Tocar ah campainha fora de horas
		final EventSet bellEvents = new EventSet(CampainhaDePortaEventUpdate.class);
		bellEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {

				if (event instanceof CampainhaDePortaEventUpdate) {
					final CampainhaDePortaEventUpdate campainhaUpdate = (CampainhaDePortaEventUpdate) event;

					//Os produtos com campainha têm a funcionalidade tocar à campainha: num sistema com sirene, faz
					//soar a sirene sempre que a campainha é pressionada; caso contrário, o sistema faz um aviso.

					if(surdo) bezirk.sendEvent(new SinaisDeLuzEventUpdate());

					if (sensoresLigados.contains("Sirene")) {

						bezirk.sendEvent(new SireneEventUpdate());


					}else {

						MsgInterna notificacao = new MsgInterna(I18N.getString(Messages.DOORBELL_RANG));
						if (vozSintetizada) notificacao.setVozSintetizadaAviso();
						notificacao.sendWarning();

					}

					String currentUpdateHour = campainhaUpdate.getCurrentHour();

					if (!DetecaoVisitante.getInstance().getStartHour().equals("") &&
							!DetecaoVisitante.getInstance().getEndHour().equals("") ) {

						if((currentUpdateHour.compareTo(DetecaoVisitante.getInstance().getStartHour()) < 0 
								|| currentUpdateHour.compareTo(DetecaoVisitante.getInstance().getEndHour()) > 0)){
							
							DetecaoVisitante.getInstance().setVisitanteForaDeHoras();
						}

					}else{

						new MsgInterna(I18N.getString(Messages.START_END_HOUR_UNDEFINED) +I18N.getString(Messages.DOOR_HOURS_SERVICE));
					}

				}
			}
		});

		bezirk.subscribe(bellEvents);

	}

	public static void main(String[] args) {

		if(surdo){
			System.out.println(I18N.getString(Messages.DEAF_PEOPLE));
		}

		new ServiceAssistant();
		System.out.println(I18N.getString(Messages.ALEXA));


		Scanner scanner  = new Scanner(System.in);
		Menu menu = new Menu();
		Option exit = new Option(I18N.getString(Messages.EXIT));
		menu.addMenuItem(exit, new ExitAction("Exit action"));
		int optionNum;
		Option option;


		do {
			System.out.println(menu);
			optionNum = readOption(menu, scanner);
			option = menu.getOption(optionNum);
			menu.execute(optionNum);
		} while(!option.equals(exit));

		System.exit(0);
	}

	private static int readOption(Menu menu, Scanner sc) {
		int value;
		do {
			System.out.println("Option? ");
			value = sc.nextInt();

			if (!menu.isValid(value))
				System.out.println("Value not valid.");
		} while(!menu.isValid(value));
		return value;
	}



}
