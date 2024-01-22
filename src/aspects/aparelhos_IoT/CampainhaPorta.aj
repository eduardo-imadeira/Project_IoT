package  aspects.aparelhos_IoT;

import  core.userinterface.ServiceAssistant;

public aspect CampainhaPorta {
	
	before() : execution(* *.main(..)) {
		ServiceAssistant.addSensor("Campainha Inteligente");
	}
	
}