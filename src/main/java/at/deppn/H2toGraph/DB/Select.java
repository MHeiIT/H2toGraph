package at.deppn.H2toGraph.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.deppn.H2toGraph.types.GetPointsAll;
import at.deppn.H2toGraph.types.Config;
import at.deppn.H2toGraph.types.Injury;
import at.deppn.H2toGraph.types.Point;
import at.deppn.H2toGraph.types.PointsAvg;


public class Select {
	Config dbconfig;
	
	public Select(Config dbconfig) {
		super();
		this.dbconfig = dbconfig;
	}
	
	public List<Point> getPointList() {
		Connection conn = null;
		Statement stmt = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<Point> list = new ArrayList<Point>();
		
		try {
			Class.forName(dbconfig.getJDBC_DRIVER());
			conn = DriverManager.getConnection(dbconfig.getDB_URL(), dbconfig.getUSER(), dbconfig.getPASS());

			stmt = conn.createStatement();
			
			String sql = "SELECT USERNAME, START_DATE, POINTS FROM ACTIVITIES " + 
					"INNER JOIN CHALLENGEACTIVITIES ON CHALLENGEACTIVITIES.ACTIVITIES_ID = ACTIVITIES.ACTIVITIES_ID " + 
					"ORDER BY USERNAME;";
			ResultSet result = stmt.executeQuery(sql);
			
			while (result.next()) {
				Point p = new Point(result.getString(1),ZonedDateTime.of(LocalDateTime.parse(result.getString(2), formatter),ZoneOffset.UTC), result.getDouble(3));
				list.add(p);
			}
			
			sql = "SELECT USERNAME, START_DATE, END_DATE FROM INJURY " + 
					"INNER JOIN PARTICIPANTS ON PARTICIPANTS.INJURY_ID = INJURY.INJURY_ID;";
			
			result = stmt.executeQuery(sql);
			
			List<Injury> ilist = new ArrayList<Injury>();
			
			while (result.next()) {
				Injury i = new Injury(result.getString(1),ZonedDateTime.of(LocalDateTime.parse(result.getString(2), formatter),ZoneOffset.UTC),ZonedDateTime.of(LocalDateTime.parse(result.getString(3), formatter),ZoneOffset.UTC));
				ilist.add(i);
			}
			
			
			List<PointsAvg> lpa = getAvgPointsList();
			
			while (ilist.size() != 0) {
				Injury i = ilist.remove(0);
				for (int j = 0; j < lpa.size(); j++) {
					if (lpa.get(j).getUsername().equals(i.getUsername())) {
						
						for (int j2 = 0; j2 < i.getDays(); j2++) {
							Point p = new Point(i.getUsername(),i.getStart_date().plusDays(j2),lpa.get(j).getPoints());
							list.add(p);
						}
						break;
					}
				}
			}
			
			
			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		
		}
		return list;
	}
	
	public List<PointsAvg> getAvgPointsList() {
		Connection conn = null;
		Statement stmt = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<GetPointsAll> list = new ArrayList<GetPointsAll>();
		
		try {
			Class.forName(dbconfig.getJDBC_DRIVER());
			conn = DriverManager.getConnection(dbconfig.getDB_URL(), dbconfig.getUSER(), dbconfig.getPASS());

			stmt = conn.createStatement();
			
			String sql = "SELECT USERNAME, POINTS, START, END FROM PARTICIPANTS "
					+ "INNER JOIN CHALLENGES ON CHALLENGES.CHALLENGES_ID = PARTICIPANTS.CHALLENGES_ID "
					+ "WHERE PARTICIPANTS.CHALLENGES_ID = 1;";
			ResultSet result = stmt.executeQuery(sql);
			
			while (result.next()) {
				GetPointsAll gpa = new GetPointsAll(result.getString(1), result.getDouble(2),
						ZonedDateTime.of(LocalDateTime.parse(result.getString(3), formatter),ZoneOffset.UTC),
						ZonedDateTime.of(LocalDateTime.parse(result.getString(4), formatter),ZoneOffset.UTC));
				list.add(gpa);
			}
			
			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		
		}
		List<PointsAvg> ll = new ArrayList<PointsAvg>();

		while (list.size() != 0) {
			GetPointsAll gpa = list.remove(0);

			ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
			ZonedDateTime start = gpa.getStart();
			ZonedDateTime end = gpa.getEnd();

			if (start.until(now, ChronoUnit.DAYS) > start.until(end, ChronoUnit.DAYS)) {
				now = end;
			}

			long days = start.until(now, ChronoUnit.DAYS);

			double avgpoints = ((double) Math.round((gpa.getPoints() / days) * 100)) / 100;
			
			PointsAvg pa = new PointsAvg(gpa.getName(), avgpoints);
			
			ll.add(pa);
		}
		return ll;
	}
	
}
