package  core.funcionalidades.mensagens;

import javax.inject.Inject;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import  core.i18n.I18N;
import  core.i18n.Messages;

public class SmsMessage implements Mensagem{
	public static final String ACCOUNT_SID = "AC4d7f3da5067a919026a4f73f7994f1a9";
	public static final String AUTH_TOKEN = "9ee883ccd4a160cfe990fe445f2a06d0";
	public static final String TWILIO_PHONE_NUMBER = "+12183929298";

	@Inject
	public SmsMessage() {
		//Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}

	@Override
	public void sendMsg(String conteudoMsg ) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		System.out.println("sms:" + conteudoMsg);
		
		// Send the message
        Message message = Message.creator(
            new PhoneNumber("+351926659697"),  // To number
            new PhoneNumber(TWILIO_PHONE_NUMBER),  // From number
            "sms: "+conteudoMsg  // Message
        ).create();
        
        System.out.println(I18N.getString(Messages.SEND_METHOD)+ " SMS!! Message SID: " + message.getSid());

		//System.out.println(I18N.getString(Messages.SEND_METHOD)+ " SMS:" + conteudoMsg );

	}
}
