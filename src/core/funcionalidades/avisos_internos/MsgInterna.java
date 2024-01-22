package core.funcionalidades.avisos_internos;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import core.i18n.I18N;
import core.i18n.Messages;

public class MsgInterna  {


	private boolean  vozSintetizada= false;

	public void setVozSintetizadaAviso(){
		this.vozSintetizada= true;

	}


	private String conteudoMsg;

	public MsgInterna(String conteudoMsg) {
		this.conteudoMsg= conteudoMsg;
	}

	public void sendWarning() {


		//Todos os avisos são mostrados no ecrã do computador.

		System.err.println(I18N.getString(Messages.WARNING_MSG )+ conteudoMsg);
		//Ecra.getInstance().addMessageToQueue(I18N.getString(Messages.WARNING_MSG )+ conteudoMsg);
		
		
		

		if (this.vozSintetizada) {

			// Nos produtos da linha que não são específicos para pessoas com problemas de 
			// audição é usada também voz sintetizada.
			// Colunas.getInstance().addVoiceMessageToQueue(I18N.getString(Messages.WARNING_MSG ) + conteudoMsg);
			
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			VoiceManager voiceManager = VoiceManager.getInstance();
			Voice voice= voiceManager.getVoice("kevin16");
			voice.allocate();
			
			voice.speak(I18N.getString(Messages.WARNING_MSG ) + conteudoMsg);
			
			voice.deallocate();
			
		} 
			
	}





}
