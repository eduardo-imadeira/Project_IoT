package  core.perifericos;

import java.util.LinkedList;
import java.util.Queue;



public class Ecra {
	
	public Queue<String> messageQueue = new LinkedList<>();
	private static Ecra INSTANCE= null;


	public static Ecra getInstance() {
		return INSTANCE;

	}

	public static void setInstance() {
		INSTANCE = new Ecra();
	}
	

    public Ecra() {
    }

    private   void displayMessages() throws InterruptedException {
        while (true) {
        	System.out.println(messageQueue.size());
            String message = messageQueue.poll();
            if (message != null) {
                System.out.println(message);
            }
            Thread.sleep(3000);
        }
    }
    
    public  void addMessageToQueue(String message) {
    	boolean cenas = messageQueue.add(message);
    	System.out.println("----" + message);
    	System.out.println("add " + cenas);
		
	}
    
    public static void main(String[] args) throws InterruptedException {
		getInstance().displayMessages();
	}

}
