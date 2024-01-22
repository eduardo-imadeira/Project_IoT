package  core.funcionalidades.mensagens;


import com.google.inject.AbstractModule;

public class MessageModule extends AbstractModule{
	
	private static boolean wpp= false;

	public static void setWpp() {
		wpp=true;
	}
	public static void setSms() {
		wpp=false;
	}
	
	@Override
	protected void configure() {
		if(wpp) {
			bind(Mensagem.class).to(WhatsAppMessage.class);
		}else {
			bind(Mensagem.class).to(SmsMessage.class);
		}
		
	}

	




}
