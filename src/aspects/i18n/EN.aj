package  aspects.i18n;

import core.i18n.I18N;
import core.perifericos.Colunas;

public aspect EN {
	before() : execution(* *.main(..)) {
		// male American English
		Colunas.setVoice("kevin16");

		I18N.setInstance(new I18N("en","US"));
		System.err.println("This product speaks english.");
	}
}