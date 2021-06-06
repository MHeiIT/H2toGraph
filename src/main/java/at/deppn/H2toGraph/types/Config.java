package at.deppn.H2toGraph.types;

import com.google.gson.annotations.SerializedName;

public class Config {
	@SerializedName("JDBC_DRIVER")
	private String JDBC_DRIVER;
	@SerializedName("DB_URL")
	private String DB_URL;
	@SerializedName("USER")
	private String USER;
	@SerializedName("PASS")
	private String PASS;
	
	public String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}
	public String getDB_URL() {
		return DB_URL;
	}
	public String getUSER() {
		return USER;
	}
	public String getPASS() {
		return PASS;
	}
}
