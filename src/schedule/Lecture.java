package schedule;


public class Lecture {
	public enum STATE {SCHEDULED, PRESENTED, CANCELED};
	
	private Schedule schedule;
	private STATE state;
	private int number;
	
	public Lecture(Schedule schedule, int number) {
		this.schedule = schedule;
		this.number = number;
		this.state = STATE.SCHEDULED;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public STATE getState() {
		return state;
	}

	public void cancel() {
		this.state = STATE.CANCELED;
	}

	public void present() {
		this.state = STATE.PRESENTED;
	}

	public int getNumber() {
		return number;
	}
}
