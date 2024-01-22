package  aspects.aparelhos_IoT;

import core.userinterface.ServiceAssistant;

public aspect Lampada {
	before() : execution(* *.main(..)) {
		ServiceAssistant.addSensor("Lampada Inteligente");
	}
}