package  aspects.aparelhos_IoT;

import core.userinterface.ServiceAssistant;

public aspect Fechadura {
	before() : execution(* *.main(..)) {
		ServiceAssistant.addSensor("Fechadura Inteligente");
	}
}