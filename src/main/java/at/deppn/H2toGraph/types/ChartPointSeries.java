package at.deppn.H2toGraph.types;

import java.util.HashSet;
import java.util.List;

public class ChartPointSeries {

	private String name;
	private double[] yData;

	public ChartPointSeries(String name, HashSet<Integer> hs, List<ChartPoint> chartPoints) {
		this.name = name;
		
		double[] yData = new double[hs.size() + 1];
		yData[0] = 0;
		double before = yData[0];
		for (int i = 1; i < yData.length; i++) {
			boolean set = false;
			for (int j = 0; j < chartPoints.size(); j++) {
				if (chartPoints.get(j).getWeekOfYear() == i - 2 && chartPoints.get(j).getName().equals(name)) {
					yData[i] = chartPoints.get(j).getPoints() + before;
					before += chartPoints.get(j).getPoints();
					set = true;
					break;
				}
			}
			if (!set) {
				yData[i] = before;
			}
		}

		this.yData = yData;
	}

	public String getName() {
		return name;
	}

	public double[] getyData() {
		return yData;
	}

}
