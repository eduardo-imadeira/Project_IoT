package  aspects.funcionalidades;

import core.userinterface.menu.*;
import core.i18n.*;

public aspect DetecaoPortaAberta {
	after(Menu menu): initialization(Menu.new()) && target(menu) {
		Option op = new Option(I18N.getString(Messages.DOOR_TIMER_SERVICE));
		menu.addMenuItem(op, new Action("Tempo Maximo Porta Aberta"));
	}
	
	before() : execution(* *.main(..)) {
		// cria uma instancia da funcionalidade 
		core.funcionalidades.DetecaoTempoPortaAberta.setInstance();
	}
}