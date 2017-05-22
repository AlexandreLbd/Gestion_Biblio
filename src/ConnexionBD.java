import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionBD {
	private Connection connexion;
	
	public ConnexionBD() {
		try {
			connexion = DriverManager.getConnection("jdbc:hsqldb:file:BiblioBDD", "sa",  "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnexionBD() {
		//singleton
		try {
			if(connexion.isValid(1))
				return connexion;
			else
				connexion = DriverManager.getConnection("jdbc:hsqldb:mem:database", "sa",  "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connexion;
	}
}
