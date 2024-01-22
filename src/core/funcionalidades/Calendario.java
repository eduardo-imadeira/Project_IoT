package core.funcionalidades;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import core.funcionalidades.avisos_internos.MsgInterna;
import core.funcionalidades.mensagens.ServicoMensagem;
import core.i18n.I18N;
import core.i18n.Messages;

public class Calendario {

	private static Calendario INSTANCE = null;

	private File events = new File("events/");

	private File csvFile = new File("events/events.csv");

	private static boolean memoVozSintetizada = false;

	public static void setMemoVoz() {
		memoVozSintetizada = true;

	}

	public Calendario() {
		// Check if a file exists, if it doesn't, create one
		createFileCSV();

		try {
			for (Reminder r : listReminders()) {
				createSchedule(r);
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}

	public static Calendario getInstance() {
		return INSTANCE;
	}

	public static void setInstance() {
		INSTANCE = new Calendario();
	}

	public void createFileCSV() {
		if (!events.exists()) {
			events.mkdirs();
		}

		if (!csvFile.exists()) {
			try {
				FileWriter outputFile = new FileWriter(csvFile);

				CSVWriter writer = new CSVWriter(outputFile);

				String[] header = { "Title", "Start", "End", "Hours", "Minutes" };
				writer.writeNext(header);

				writer.close();

			} catch (IOException e) {
				System.out.println(I18N.getString(Messages.FILE_CREATE_ERROR));
				e.printStackTrace();
			}
		}
	}

	public void writeReminderToCSV(Reminder reminder) {
		try {

			FileWriter outputFile = new FileWriter(csvFile, true);

			CSVWriter writer = new CSVWriter(outputFile);

			writer.writeNext(reminder.toArray());

			writer.close();

		} catch (IOException e) {
			// handle exception
		}

	}

	public List<Reminder> listReminders() throws IOException, CsvException {

		CSVReader reader = new CSVReader(new FileReader(csvFile));
		List<String[]> allElements = reader.readAll();
		List<Reminder> reminderList = new ArrayList<Reminder>();

		for (String[] e : allElements.subList(1, allElements.size())) {
			Reminder r = new Reminder(e[0], e[1], e[2], Integer.parseInt(e[3]), Integer.parseInt(e[4]));

			reminderList.add(r);
		}

		return reminderList;
	}

	public void deleteReminderFromCSV(int lineToDelete) throws IOException, CsvException {

		CSVReader reader = new CSVReader(new FileReader(csvFile));
		List<String[]> allElements = reader.readAll();

		if (lineToDelete <= 0 || lineToDelete > allElements.size()) {
			int size = allElements.size() - 1;
			throw new IllegalArgumentException(I18N.getString(Messages.INDEX_ERROR) + " " + size);
		}

		allElements.remove(lineToDelete);
		FileWriter sw = new FileWriter(csvFile);
		CSVWriter writer = new CSVWriter(sw);
		writer.writeAll(allElements);

		writer.close();
	}

	public void execSubMenu() {

		Calendario c = new Calendario();

		Scanner sc = new Scanner(System.in);

		int input;
		do {
			System.out.println(I18N.getString(Messages.SUB_MENU) + I18N.getString(Messages.CALENDAR_SERVICE));

			System.out.println("0 -" + I18N.getString(Messages.CREATE_EVENT) + "  1 -"
					+ I18N.getString(Messages.DELETE_EVENT) + "   2 -" + I18N.getString(Messages.DISPLAY_EVENTS)
					+ "   3 -" + I18N.getString(Messages.EXIT));

			input = sc.nextInt();

			switch (input) {
			case 0:
				SubMenuEventCreate(c);
				break;
			case 1:
				deleteReminderSubMenu(c);
				break;
			case 2:
				listEveryReminderSubMenu(c);
				break;
			case 3:
				return;
			default:
				System.out.println(I18N.getString(Messages.INVALID_OPTION));
				break;
			}

		} while (input != 3);

		sc.close();
	}

	// Submenu to create an event
	public void SubMenuEventCreate(Calendario c) {
		Scanner sc = new Scanner(System.in);

		System.out.println(I18N.getString(Messages.EVENT_NAME));

		String eventName = sc.nextLine();

		String dateTimeStart;

		LocalDateTime dateStart;

		// Repeat these steps if the event start time is invalid
		while (true) {
			System.out.println(I18N.getString(Messages.EVENT_START));

			dateTimeStart = sc.nextLine();

			try {
				dateStart = LocalDateTime.parse(dateTimeStart, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

				if (dateStart.isBefore(LocalDateTime.now())) {
					throw new Exception();
				}

				break;
			} catch (Exception e) {
				System.out.println(I18N.getString(Messages.EVENT_DATE_INVALID));
			}
		}

		String dateTimeEnd;

		LocalDateTime dateEnd;

		// Repeat these steps if the event end time is invalid
		while (true) {

			System.out.println(I18N.getString(Messages.EVENT_END));

			dateTimeEnd = sc.nextLine();

			try {
				dateEnd = LocalDateTime.parse(dateTimeEnd, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

				if (dateEnd.isBefore(dateStart)) {
					throw new Exception();
				}

				break;
			} catch (Exception e) {
				System.out.println(I18N.getString(Messages.EVENT_DATE_INVALID));
			}
		}

		String frequency;

		while (true) {

			System.out.println(I18N.getString(Messages.FREQUENCY_HOURS));

			frequency = sc.nextLine();

			try {
				LocalTime.parse(frequency, DateTimeFormatter.ofPattern("HH:mm"));
				break;
			} catch (DateTimeParseException e) {
				System.out.println(I18N.getString(Messages.FREQUENCY_ERROR));
			}

		}

		Reminder reminder = new Reminder(eventName, dateTimeStart, dateTimeEnd,
				Integer.parseInt(frequency.split(":")[0]), Integer.parseInt(frequency.split(":")[1]));

		c.writeReminderToCSV(reminder);

		createSchedule(reminder);

	}

	private void createSchedule(Reminder reminder) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		LocalDateTime start = LocalDateTime.parse(reminder.getStartTime(), formatter);

		final LocalDateTime end = LocalDateTime.parse(reminder.getEndTime(), formatter);

		int intervalHours = reminder.getHours();
		int intervalMinutes = reminder.getMinutes();
		
		if (LocalDateTime.now().isBefore(end)) {
			final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					LocalDateTime now = LocalDateTime.now();

					String memo = "MSG:Reminder: " + reminder.getTitle() + " at " + now.getHour() + ":" + now.getMinute()
							+ " on " + now.getDayOfWeek() + ", " + now.getMonth() + " " + now.getDayOfMonth();
					
					// O sistema faz um aviso para cada lembrete ativo. Uma mensagem com o lembrete
					// é também enviada para o exterior.
					sendMemo(memo);


					if (now.isAfter(end)) {
						executor.shutdown();
						return;
					}
				
				}

			}, getDelayInSeconds(start, intervalHours, intervalMinutes), intervalHours * 3600 + intervalMinutes * 60,
					TimeUnit.SECONDS);
		}

	}

	public static void sendMemo(String memo) {
		// mensagem exterior
		ServicoMensagem.sendMessage(memo);

		// aviso interno
		MsgInterna notificacao = new MsgInterna(memo);
		if (memoVozSintetizada)
			notificacao.setVozSintetizadaAviso();
		notificacao.sendWarning();
	}

	// Submenu to show the list of events
	public void listEveryReminderSubMenu(Calendario c) {

		try {
			List<Reminder> elements = c.listReminders();

			int i = 1;
			for (Reminder r : elements) {

				System.out.println(i + " - " + r.toString());

				i++;
			}

		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}

	}

	public void deleteReminderSubMenu(Calendario c) {

		Scanner sc = new Scanner(System.in);

		System.out.println(I18N.getString(Messages.DELETE_INDEX_EVENT));

		try {
			List<Reminder> elements = c.listReminders();
			int i = 1;

			if (elements.size() == 0) {
				System.out.println(I18N.getString(Messages.NO_EVENTS_FOUND));
				return;
			}

			int choice;

			do {
				for (Reminder r : elements) {

					System.out.println(i + " - " + r.toString());

					i++;
				}

				choice = sc.nextInt();

				if (choice > 1 && choice < elements.size()) {
					System.out.println(I18N.getString(Messages.NO_EVENTS_FOUND));
				}

			} while (choice < 1 && choice > elements.size());

			c.deleteReminderFromCSV(choice);

		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}

	}

	private static long getDelayInSeconds(LocalDateTime start, int intervalHours, int intervalMinutes){
		long delay;
		LocalDateTime now = LocalDateTime.now();

		if (now.isBefore(start)) {
			delay = start.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(start))
					- now.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(now));
		} else {
			// Calculate the delay in seconds between the current time and the next scheduled
			// execution time
			LocalDateTime nextExecutionTime = now.plusHours(intervalHours).plusMinutes(intervalMinutes);
			delay = nextExecutionTime.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(nextExecutionTime))
					- now.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(now));
		}

		return delay;
	}

}
