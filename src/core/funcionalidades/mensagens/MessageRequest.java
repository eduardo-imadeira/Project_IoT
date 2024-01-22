package  core.funcionalidades.mensagens;

import javax.inject.Inject;

public class MessageRequest {
	
	Mensagem m;
	
	@Inject 
	public MessageRequest(Mensagem m) {
		this.m= m;
	}
	
	public void makeRequest(String msg) {
		m.sendMsg(msg);
	}

}
