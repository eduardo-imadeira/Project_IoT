package  aspects.aparelhos_IoT;

import core.aparelhos_IoT.Sirene;

public aspect LuzesLed {
	before() : execution(* *.main(..)) {
		Sirene.setLuzesLed();
	}
}