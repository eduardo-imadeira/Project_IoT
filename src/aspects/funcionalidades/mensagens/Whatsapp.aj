package aspects.funcionalidades.mensagens;

import core.funcionalidades.mensagens.MessageModule;


public aspect Whatsapp {
	before() : execution(* *.main(..)) {
		
		MessageModule.setWpp();
	}
	
}