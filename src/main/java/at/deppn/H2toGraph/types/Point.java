package at.deppn.H2toGraph.types;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Point implements Comparable<Point> {
	private String username;
	private ZonedDateTime start_date;
	private double points;
	private Integer weekOfYear;
	
	public Point(String username, ZonedDateTime start_date, double points) {
		super();
		this.username = username;
		this.start_date = start_date;
		this.points = points;
		
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		if (this.start_date.get(weekFields.weekOfWeekBasedYear()) == 52 && this.start_date.getYear() == 2020) {
			this.weekOfYear = -1;
		} else if (this.start_date.get(weekFields.weekOfWeekBasedYear()) == 53 && (this.start_date.getYear() == 2020 || (this.start_date.getYear() == 2021 && this.start_date.getMonth() == Month.JANUARY))) {
			this.weekOfYear = 0;
		} else {
			this.weekOfYear = this.start_date.get(weekFields.weekOfWeekBasedYear());
		}
	}
	
	public String getUsername() {
		return username;
	}
	public ZonedDateTime getStart_date() {
		return start_date;
	}
	public double getPoints() {
		return points;
	}
	public int getWeekOfYear() {
		return weekOfYear;
	}

	public int compareTo(Point o) {
		return this.weekOfYear.compareTo(o.weekOfYear);
	}
	
	public void addPoints(double points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Point [username=" + username + ", start_date=" + start_date + ", points=" + points + ", weekOfYear="
				+ weekOfYear + "]";
	}
	
}
