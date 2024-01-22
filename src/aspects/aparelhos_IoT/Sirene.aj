package  aspects.aparelhos_IoT;

public aspect Sirene {
	before() : execution(* *.main(..)) {
		core.userinterface.ServiceAssistant.addSensor("Sirene");
	}
}