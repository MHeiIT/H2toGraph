package at.deppn.H2toGraph.types;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Injury {
	private String username;
	private ZonedDateTime start_date;
	private ZonedDateTime end_date;
	
	public long getDays() {
		return Duration.between(start_date,end_date).toDays();
	}

	public Injury(String username, ZonedDateTime start_date, ZonedDateTime end_date) {
		this.username = username;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getUsername() {
		return username;
	}

	public ZonedDateTime getStart_date() {
		return start_date;
	}

	public ZonedDateTime getEnd_date() {
		return end_date;
	}
}
