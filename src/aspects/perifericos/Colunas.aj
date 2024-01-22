package aspects.perifericos;

public aspect Colunas {
	before() : execution(* *.main(..)) {
		core.perifericos.Colunas.setInstance();
	}	
}