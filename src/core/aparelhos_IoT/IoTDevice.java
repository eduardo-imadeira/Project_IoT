package core.aparelhos_IoT;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class IoTDevice {
	
public static String getCurrentHour() {
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String current = formatter.format(calendar.getTime());
		
		return current;

	}

}
