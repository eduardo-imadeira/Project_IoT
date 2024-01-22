package  core.funcionalidades.mensagens;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ServicoMensagem {
	
	public static void sendMessage(String counteudoMsg) {
		
		Injector injector = Guice.createInjector(new MessageModule());
		MessageRequest request = injector.getInstance(MessageRequest.class);
		request.makeRequest(counteudoMsg);

	}

}
