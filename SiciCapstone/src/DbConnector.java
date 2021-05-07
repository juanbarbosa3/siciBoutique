import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbConnector {
	
	/*
	 * Creates connection with database 
	 * JGBT
	 */

	private String url;
	private String uname;
	private String password;
	private Connection con;
	
	public Connection getConnection() {
		return con;
	}
	
	//Each class that extends the connector, must call this method
	//JGBT
	public void setUpDB() {
		this.url = "jdbc:postgresql://localhost:5432/Boutique"; //replace w/ jdbc:postgresql://host:port/database name
		this.uname = "postgres"; //replace w/ custom username
		this.password = "replace w/ custom password"; //replace w/ custom password 
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			this.con = DriverManager.getConnection(url, uname, password);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("succesful connection");
	}
	
}
