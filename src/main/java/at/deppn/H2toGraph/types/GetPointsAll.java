package at.deppn.H2toGraph.types;

import java.time.ZonedDateTime;

public class GetPointsAll implements Comparable<GetPointsAll> {
	private String name;
	private double points;
	private ZonedDateTime start;
	private ZonedDateTime end;
	
	public GetPointsAll(String name, double points, ZonedDateTime start, ZonedDateTime end) {
		this.name = name;
		this.points = points;
		this.start = start;
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public double getPoints() {
		return points;
	}
	public ZonedDateTime getStart() {
		return start;
	}
	public ZonedDateTime getEnd() {
		return end;
	}
	
	@Override
	public int compareTo(GetPointsAll arg0) {
		if (points < arg0.points) return -1;
        if (points > arg0.points) return 1;
		return 0;
	}
}
