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
import java.util.ArrayList;
import java.util.List;

import at.deppn.H2toGraph.types.Config;
import at.deppn.H2toGraph.types.Point;


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
			
			String sql = "SELECT USERNAME, POINTS, START, END FROM PARTICIPANTS "
					+ "INNER JOIN CHALLENGES ON CHALLENGES.CHALLENGES_ID = PARTICIPANTS.CHALLENGES_ID "
					+ "WHERE PARTICIPANTS.CHALLENGES_ID = 1;";
			ResultSet result = stmt.executeQuery(sql);
			
			while (result.next()) {
				Point p = new Point(result.getString(1),ZonedDateTime.of(LocalDateTime.parse(result.getString(2), formatter),ZoneOffset.UTC), result.getDouble(3));
				list.add(p);
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
}
