package at.deppn.H2toGraph.types;

import java.time.ZonedDateTime;

public class Point {
	private String username;
	private ZonedDateTime start_date;
	private double points;
	
	public Point(String username, ZonedDateTime start_date, double points) {
		super();
		this.username = username;
		this.start_date = start_date;
		this.points = points;
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
	
}
