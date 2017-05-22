
public class Livre {

	private int numero;
	private String titre;
	private String nom_auteur;
	private String nom_illustrateur;
	private String nom_edition;
	private boolean estEmprunte;
	private boolean estDocumentaire;
	
	
	public Livre(int p_num, String p_titre){
		setNumero(p_num);
		setTitre(p_titre);
		
	}
	
	public Livre(int p_num, String p_titre, String p_auteur, String p_illustrateur, String p_edition,
			boolean p_emprunt, boolean p_documentaire) {
		
		setNumero(p_num);
		setTitre(p_titre);
		setNom_auteur(p_auteur);
		setNom_illustrateur(p_illustrateur);
		setNom_edition(p_edition); 
		setEstEmprunte(p_emprunt);
		setEstDocumentaire(p_documentaire);
		
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNom_auteur() {
		return nom_auteur;
	}

	public void setNom_auteur(String nom_auteur) {
		this.nom_auteur = nom_auteur;
	}

	public String getNom_illustrateur() {
		return nom_illustrateur;
	}

	public void setNom_illustrateur(String nom_illustrateur) {
		this.nom_illustrateur = nom_illustrateur;
	}

	public String getNom_edition() {
		return nom_edition;
	}

	public void setNom_edition(String nom_edition) {
		this.nom_edition = nom_edition;
	}

	public boolean isEstEmprunte() {
		return estEmprunte;
	}

	public void setEstEmprunte(boolean estEmprunte) {
		this.estEmprunte = estEmprunte;
	}

	public boolean isEstDocumentaire() {
		return estDocumentaire;
	}

	public void setEstDocumentaire(boolean estDocumentaire) {
		this.estDocumentaire = estDocumentaire;
	}
}
