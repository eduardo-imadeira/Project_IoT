package aspects.funcionalidades;

import core.userinterface.menu.*;
import core.i18n.*;

public aspect Calendario {
	after(Menu menu): initialization(Menu.new()) && target(menu) {
		Option op = new Option(I18N.getString(Messages.CALENDAR_SERVICE));
		menu.addMenuItem(op, new Action("Calendario"));
	}
	
	before() : execution(* *.main(..)) {
		// cria uma instancia da funcionalidade
		core.funcionalidades.Calendario.setInstance();
	}}