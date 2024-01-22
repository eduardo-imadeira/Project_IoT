package aspects.perifericos;

public aspect Ecra {
	before() : execution(* *.main(..)) {
		core.perifericos.Ecra.setInstance();
	}	
}