package  core.userinterface.menu;

import core.funcionalidades.Calendario;
import core.funcionalidades.DetecaoTempoPortaAberta;
import core.funcionalidades.DetecaoVisitante;

public class Action {

	private String s;
	public Action (String s) {
		this.s = s;
	}
	
	
	public void execute() {
		switch (s) {
		case "Deteção de Visitante":
			DetecaoVisitante.getInstance().execSubMenu();

			break;
		case "Tempo Maximo Porta Aberta":
			DetecaoTempoPortaAberta.getInstance().execSubMenu();

			break;
		case "Calendario":
			Calendario.getInstance().execSubMenu();

			break;
		default:
			break;
		}
	}
}
