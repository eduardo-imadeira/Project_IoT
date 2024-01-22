package  aspects.i18n;

import core.i18n.I18N;

public aspect PT {
	before() : execution(* *.main(..)) {
		// male Brazilian Portuguese
		//Colunas.setVoice("kevin_pt");
		
		I18N.setInstance(new I18N("pt","PT"));
		System.err.println("Este produto fala portugues");
	}
}