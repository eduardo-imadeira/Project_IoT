package core.funcionalidades;

public class Reminder {

	private String title;

	private String startTime;

	private String endTime;

	private int hours;

	private int minutes;

	public Reminder(String title, String startTime, String endTime, int hours, int minutes) {
		super();
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hours = hours;
		this.minutes = minutes;
	}

	public String getTitle() {
		return title;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public String[] toArray() {
		return new String[] { getTitle(), getStartTime(), getEndTime(),
				Integer.toString(getHours()), Integer.toString(getMinutes()) };
	}

	@Override
	public String toString() {
		return "Title " + title + " Starts " + startTime + " Ends " + endTime + " Repeats " + hours + "h" + minutes
				+ "min";
	}

}
