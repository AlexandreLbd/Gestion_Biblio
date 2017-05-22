import java.util.Date;


public class Emprunt {

	private int numero_livre;
	private String nom_eleve;
	private String prenom_eleve;
	private String date_emprunt;
	private String date_rendu;
	
	public Emprunt(int p_livre, String p_nom, String p_prenom) {
		
		numero_livre = p_livre;
		nom_eleve = p_nom;
		prenom_eleve = p_prenom;
		
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( "dd/mm/yyyy" ); 
		java.util.Date date_sys = new java.util.Date(); 
		date_emprunt = formater.format(date_sys);
	}
}
