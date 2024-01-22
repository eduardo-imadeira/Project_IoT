package   core.funcionalidades;

import java.util.Scanner;

import core.funcionalidades.mensagens.ServicoMensagem;
import  core.i18n.I18N;
import  core.i18n.Messages;

public class DetecaoVisitante {
	private String startHour ;
	private String endHour;
	private boolean visitanteForaDeHoras= false;
	
	
	private static DetecaoVisitante INSTANCE = null;
	
	protected DetecaoVisitante() {
		this.startHour= "";
		this.endHour= "";
	}

	public static DetecaoVisitante getInstance() {
		return INSTANCE;
	}
	
	public static void setInstance() {
		INSTANCE = new DetecaoVisitante();
	}

	public String getStartHour() {
		return startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	
	public void sendExternalAlert() {
		//Creates a message and send it 
		ServicoMensagem.sendMessage(I18N.getString(Messages.DOOR_OUT_OF_HOURS_ALLERT));
		
	}

	public void execSubMenu() {
		Scanner sc = new Scanner(System.in);
		int input;
		do {
			System.out.println(I18N.getString(Messages.SUB_MENU)+
					I18N.getString(Messages.DOOR_HOURS_SERVICE));
			
			System.out.println("0 -"+ I18N.getString(Messages.SET_START_HOUR)+"  1 -"+ I18N.getString(Messages.SET_END_HOUR)+
					"   2 -"+ I18N.getString(Messages.DISPLAY_START_AND_END_HOURS)+"   3 -"+ I18N.getString(Messages.EXIT));
			
			input= sc.nextInt();
			
			switch (input) {
			case 0:
				
				System.out.println(I18N.getString(Messages.SET_START_HOUR) + 
						I18N.getString(Messages.HOUR_FORMAT));		
				setStartHour(sc.next());

				break;
			case 1:
				System.out.println(I18N.getString(Messages.SET_END_HOUR) + 
						I18N.getString(Messages.HOUR_FORMAT));				
				setEndHour(sc.next());

				break;
			case 2:
				System.out.println(I18N.getString(Messages.START_HOUR)+"- " + startHour);			
				System.out.println(I18N.getString(Messages.END_HOUR)+"- " + endHour);	

				break;
			case 3:	
				sc.reset();
				break;
			default:
				System.out.println(I18N.getString(Messages.INVALID_OPTION));
				break;
			}

		} while (input!=3);
		
	}

	public void setVisitanteForaDeHoras() {
		visitanteForaDeHoras= true;
	}

	public boolean getVisitanteForaDeHoras() {
		return visitanteForaDeHoras;
	}

}
