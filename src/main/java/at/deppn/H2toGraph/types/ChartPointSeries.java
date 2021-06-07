package at.deppn.H2toGraph.types;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

public class ChartPointSeries {

	private String name;
	private double[] yData;

	public ChartPointSeries(String name, HashSet<Integer> hs, List<ChartPoint> chartPoints) {
		this.name = name;
		
		double[] yData = new double[hs.size() + 1];
		if (name.equals("Mika")) {
			yData[0] = (calcTotalPoints(chartPoints)
					/ (ChronoUnit.DAYS.between(LocalDate.of(2020, 12, 25), LocalDate.now()) - DaysOff.MIKA_DAYS_OFF))
					* DaysOff.MIKA_DAYS_OFF;
		} else if (name.equals("Luki")) {
			yData[0] = (calcTotalPoints(chartPoints)
					/ (ChronoUnit.DAYS.between(LocalDate.of(2020, 12, 25), LocalDate.now()) - DaysOff.LUKI_DAYS_OFF))
					* DaysOff.LUKI_DAYS_OFF;
		} else {
			yData[0] = 0;
		}
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

	public double calcTotalPoints(List<ChartPoint> chartPoints) {
		double total = 0;
		for (ChartPoint cp : chartPoints) {
			if (cp.getName().equals(this.name)) {
				total += cp.getPoints();
			}
		}
		return total;
	}

	public String getName() {
		return name;
	}

	public double[] getyData() {
		return yData;
	}

}
