package  core.funcionalidades;

import java.util.Scanner;

import core.funcionalidades.avisos_internos.MsgInterna;
import  core.i18n.I18N;
import  core.i18n.Messages;


public class DetecaoTempoPortaAberta {
	
	private int tempoMax;

	private static DetecaoTempoPortaAberta INSTANCE = null;

	protected DetecaoTempoPortaAberta() {
		this.tempoMax=0;
	}

	public static DetecaoTempoPortaAberta getInstance() {

		return INSTANCE;

	}

	public static void setInstance() {
		INSTANCE = new DetecaoTempoPortaAberta();
	}

	public int getTempoMax() {
		return tempoMax;
	}

	public void setTempoMax(int tempoMax) {
		this.tempoMax = tempoMax;
	}
	
	public void sendAlert(boolean vozSintetizada) {
		//Creates a message and send it 
		MsgInterna avisoInterna = new MsgInterna(I18N.getString(Messages.DOOR_TIME_ALERTA));
		if(vozSintetizada)avisoInterna.setVozSintetizadaAviso();
		avisoInterna.sendWarning();

	}

	public void execSubMenu() {
		Scanner sc = new Scanner(System.in);
		int input;
		do {
			System.out.println(I18N.getString(Messages.SUB_MENU)+ 
					I18N.getString(Messages.DOOR_TIMER_SERVICE));

			System.out.println("0 -"+ I18N.getString(Messages.SET_MAX_TIME)+"  1 -"+ I18N.getString(Messages.DISPLAY_MAX_TIME)+
					"   2 -"+ I18N.getString(Messages.EXIT));

			input= sc.nextInt();
			
			switch (input) {
			case 0:
				
				System.out.println(I18N.getString(Messages.SET_MAX_TIME));		
				setTempoMax(Integer.parseInt(sc.next()));

				break;
			case 1: 
				System.out.println(I18N.getString(Messages.DOOR_TIMEOUT)+"- " + tempoMax);			
			
				break;
			case 2:	
				sc.reset();
				break;
			default:
				System.out.println(I18N.getString(Messages.INVALID_OPTION));
				break;
			}

		} while (input!=2);

	}

	
}
