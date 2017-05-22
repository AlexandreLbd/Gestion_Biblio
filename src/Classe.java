import java.sql.SQLException;
import java.util.ArrayList;


public class Classe {
	/**
	 * Represente une classe d'enfants
	 */
	
	private String nom_instit;
	private String niveau;
	private int id;
	
	public Classe(int bdd_id) {
		
		id = bdd_id;
		
		String[] str = main.gbd.getClasseParId(bdd_id);
		
		niveau = str[0];
		nom_instit = str[1];
		
	}
	
	public int getId() { return id; }
	
	public String getNiveau() { return niveau; }
	
	public String getInstit() { return nom_instit; }
	
}
