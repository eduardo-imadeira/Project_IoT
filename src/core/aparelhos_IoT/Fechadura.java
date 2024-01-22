package  core.aparelhos_IoT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import  core.i18n.I18N;
import  core.i18n.Messages;
import  core.updateEvents.FechaduraEventUpdate;

public class Fechadura {
	
	//File acceptedCodes = new File("acceptedCodesForLock.txt");
	
	public Fechadura() {
		
		BezirkMiddleware.initialize();
        final Bezirk bezirk = BezirkMiddleware.registerZirk("Lock Zirk");
        
        final EventSet LockEvents = new EventSet(FechaduraEventUpdate.class);
        
        LockEvents.setEventReceiver(new EventSet.EventReceiver() {
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if (event instanceof FechaduraEventUpdate) {
					openDoor();
				}

			}
		});
        bezirk.subscribe(LockEvents);
	}

	
	public boolean verifyCode(String guess) {
		
		BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader("acceptedCodesForLock.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(guess)) {
                    return true;           
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
	}
	
	public static void main(String args[]) {
        Fechadura lock = new Fechadura();
        System.err.println("This product have a Smart Lock");
        Scanner cmd = new Scanner(System.in);
        while(true) {
        
        	
        	System.out.print("Type code: ");
        
        	String guess = cmd.nextLine();
        	boolean codeaccepted= lock.verifyCode(guess);
        
        	String response = codeaccepted ? I18N.getString(Messages.CODEACCEPTED) : I18N.getString(Messages.CODENOTACCEPTED);
        	System.out.print(response);
        	if (codeaccepted) openDoor();
        
        }
    } 
	
	private static void openDoor() {
		System.out.println("\nDoor Open");
	}
}
