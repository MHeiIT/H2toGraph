package at.deppn.H2toGraph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import at.deppn.H2toGraph.DB.JsonReader;
import at.deppn.H2toGraph.DB.Select;
import at.deppn.H2toGraph.types.ChartPoint;
import at.deppn.H2toGraph.types.ChartPointSeries;
import at.deppn.H2toGraph.types.Config;
import at.deppn.H2toGraph.types.Point;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JsonReader jr = new JsonReader();
		Config dbconfig = jr.readConfig(Paths.get("./vendor/config.json"));
		Select sel = new Select(dbconfig);
		
		List<Point> list = sel.getPointList();
		Collections.sort(list);
		
		HashSet<Integer> hs = new HashSet<Integer>();
		for (Point p : list) {
			hs.add(p.getWeekOfYear());
		}
		
		HashSet<String> people = new HashSet<String>();
		for (Point p : list) {
			people.add(p.getUsername());
		}
		
		List<ChartPoint> chartPoints = new ArrayList<ChartPoint>();
		while (list.size() != 0) {
			Point p = list.remove(0);
			
			boolean exists = false;
			for (int i = 0; i < chartPoints.size(); i++) {
				if (chartPoints.get(i).getName().equals(p.getUsername()) && chartPoints.get(i).getWeekOfYear() == p.getWeekOfYear()) {
					chartPoints.get(i).addPoints(p.getPoints());
					exists = true;
					break;
				}
			}
			
			if (!exists) {
				chartPoints.add(new ChartPoint(p.getUsername(), p.getWeekOfYear(), p.getPoints()));
			}
		}
		//chartPoints.forEach(System.out::println);
		
		double[] xData = new double[hs.size()+1];
		xData[0] = 0;
		int count = 1;
		for (Integer i : hs) {
			xData[count] = i+2;
			count++;
		}
		List<ChartPointSeries> cps = new ArrayList<>();
		for (String name : people) {
			cps.add(new ChartPointSeries(name, hs, chartPoints));
		}

		// Create Chart
		XYChart c = new XYChart(new XYChartBuilder());
		c.setTitle("Challenge Chart");
		c.setXAxisTitle("Weeks");
		c.setYAxisTitle("Points");
		for (ChartPointSeries cps2 : cps) {
			c.addSeries(cps2.getName(), cps2.getyData());
		}
		
		File f = new File("./data");
		f.mkdir();
		
		try {
			BitmapEncoder.saveBitmap(c, "./data/Challenge_Chart", BitmapFormat.PNG);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
