package  core.perifericos;

import java.awt.Toolkit;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.util.LinkedList;
import java.util.Queue;

public class Colunas {
	
	private static Voice voice;
	private static VoiceManager voiceManager;
	
	private Queue<String> voiceMessageQueue;
	private static Colunas INSTANCE= null;

	protected Colunas() {
		this.voiceMessageQueue=new LinkedList<>();
	}

	public static Colunas getInstance() {
		return INSTANCE;

	}

	public static void setInstance() {
		INSTANCE = new Colunas();
	}
	
	public static void setVoice(String voiceLang) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		voiceManager = VoiceManager.getInstance();
		voice= voiceManager.getVoice("kevin16");
		voice.allocate();
	}
	
	
	public void playMessages() {
        while (true) {
            String message = voiceMessageQueue.poll();
            if (message != null) {
                voice.speak(message);
            }
        }
    }
	

	public void addVoiceMessageToQueue(String message) {
    	this.voiceMessageQueue.add(message);
	} 
	
	public static void main(String[] args) {
		Colunas.getInstance().playMessages();
	}

	public void sendAvisoSonoro()  {
		// Generate a beep sound
		Toolkit.getDefaultToolkit().beep();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Toolkit.getDefaultToolkit().beep();
		System.err.println("beep beep");
	}


}
