package aspects.incapacidades;

import core.userinterface.ServiceAssistant;

public aspect Audicao {

	before() : execution(* *.main(..)){
		ServiceAssistant.setSurdo();
	}
	}