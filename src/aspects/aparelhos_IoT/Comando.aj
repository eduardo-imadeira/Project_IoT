package  aspects.aparelhos_IoT;

public aspect Comando {
	before() : execution(* *.main(..)) {
		core.userinterface.ServiceAssistant.addSensor("Comando");
	}
}