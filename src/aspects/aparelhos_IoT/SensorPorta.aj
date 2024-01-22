package  aspects.aparelhos_IoT;



public aspect SensorPorta {
	before() : execution(* *.main(..)) {
		core.userinterface.ServiceAssistant.addSensor("Sensor Porta");
	}
}