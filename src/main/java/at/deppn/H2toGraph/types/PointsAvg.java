package at.deppn.H2toGraph.types;

public class PointsAvg {
	private String username;
	private Double points;
	
	public PointsAvg(String username, Double points) {
		this.username = username;
		this.points = points;
	}
	public String getUsername() {
		return username;
	}
	public Double getPoints() {
		return points;
	}
	
}
