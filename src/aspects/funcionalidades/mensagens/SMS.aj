package aspects.funcionalidades.mensagens;

import core.funcionalidades.mensagens.MessageModule;

public aspect SMS {
before() : execution(* *.main(..)) {
		
		MessageModule.setSms();
	}
	
}

