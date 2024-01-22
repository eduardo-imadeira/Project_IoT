package core.funcionalidades.mensagens;

import javax.inject.Inject;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import core.i18n.I18N;
import core.i18n.Messages;

public class WhatsAppMessage implements Mensagem{
	
	public static final String ACCOUNT_SID = "AC4d7f3da5067a919026a4f73f7994f1a9";
	public static final String AUTH_TOKEN = "9ee883ccd4a160cfe990fe445f2a06d0";
	public static final String TWILIO_PHONE_NUMBER = "+14155238886";

	
	
	@Inject
	public WhatsAppMessage() {
	}

	@Override
	public void sendMsg(String conteudoMsg) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		System.out.println("whatsapp:" + conteudoMsg);
		// Send the message
        Message message = Message.creator(
            new PhoneNumber("whatsapp:+351926659697"),  // To number
            new PhoneNumber("whatsapp:"+TWILIO_PHONE_NUMBER),  // From number
            "whatsapp: "+conteudoMsg  // Message
        ).create();
        
        System.out.println(I18N.getString(Messages.SEND_METHOD)+ " WhatsApp! Message SID: " + message.getSid());

	}

}
