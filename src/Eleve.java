import java.util.ArrayList;


public class Eleve {
	
	private int id_eleve_bdd; //id de l'Eleve dans la BDD
	
	private String nom;
	private String prenom;
	private String niveau;
			//Obtention de liste_emprunts par SELECT * from Livre WHERE id_emprunteur = id_eleve
	
	public Eleve(int bdd_id){
		
		String[] donnees = new String[2];
		donnees = main.gbd.getEleveParId(bdd_id);
		
		nom = donnees[0];
		prenom = donnees[1];
	}
	
	public Eleve(String p_nom, String p_prenom, String p_niveau, String p_nom_instit){
		
		nom = p_nom;
		prenom = p_prenom;
		niveau = p_niveau;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public String getNiveau() {
		return niveau;
	}
	
	
}
