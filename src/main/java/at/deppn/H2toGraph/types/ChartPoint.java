package at.deppn.H2toGraph.types;

public class ChartPoint {
	
	private String name;
	private Integer weekOfYear;
	private double points;
	
	public ChartPoint(String name, Integer weekOfYear, double points) {
		this.name = name;
		this.weekOfYear = weekOfYear;
		this.points = points;
	}
	
	public String getName() {
		return name;
	}

	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public double getPoints() {
		return points;
	}
	
	public void addPoints(double points) {
		this.points += points;
	}

	@Override
	public String toString() {
		return "ChartPoint [name=" + name + ", weekOfYear=" + weekOfYear + ", points=" + points + "]";
	}
	
}
