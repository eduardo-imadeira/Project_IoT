package  aspects.funcionalidades;

import core.i18n.*;
import core.userinterface.menu.*;

public aspect DetecaoVisitante {
	
	after(Menu menu): initialization(Menu.new()) && target(menu) {
		Option op = new Option(I18N.getString(Messages.DOOR_HOURS_SERVICE));
		menu.addMenuItem(op, new Action("Deteção de Visitante"));
	}
	
	before() : execution(* *.main(..)) {
		// cria uma instancia da funcionalidade 
		core.funcionalidades.DetecaoVisitante.setInstance();
	}

}