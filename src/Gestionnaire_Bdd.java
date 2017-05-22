import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;


public class Gestionnaire_Bdd {
	private Connection connexion;
	private Statement requete;
	private ListeModel model_tableau;
	private String requete_model; //Permet de rafraichir le modele.
	private String requete_tmp; // valeur qui change souvent pour gestion erreurs

	
	public Gestionnaire_Bdd(){
		try {
			//Chargement driver hsqldb
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			
			connexion = DriverManager.getConnection("jdbc:hsqldb:file:BiblioBDD", "sa",  "");
			requete = connexion.createStatement();
			creerTables();
			
			selection(new String[]{"niveau", "professeur"}, "classes");
			rafraichir_tableau();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void extinction() {
		
		try {
			requete.execute("SHUTDOWN");
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void executerRequeteBrute(String s, boolean afficher){
		ResultSet rs = null;
		
		try {
			if(requete.execute(s) && afficher){
				rs = requete.getResultSet();
				while (rs.next()){
					for(int i = 1; i < rs.getMetaData().getColumnCount(); i++)
						System.out.print(rs.getObject(i) + " : ");
					System.out.println();
				}
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*****\n" +s + "*****\n");
			e.getMessage();
			e.printStackTrace();
		}
	}
		
	
	public void creerTables(){
		
		try {	
				requete.execute("CREATE TABLE eleves ("	
				+ "ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
				+ "nom VARCHAR(30),"
				+ "prenom VARCHAR(30),"
				+ "id_classe INTEGER NOT NULL"
				+ ");");
		
			
			requete.execute("CREATE TABLE classes ("	
					//+ "ID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,"
					+ "ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
					+ "niveau VARCHAR(20),"
					+ "professeur VARCHAR(50)"
						+ ");");
			


				
			requete.execute("CREATE TABLE livres ("
					+ "ID INTEGER PRIMARY KEY,"
					+ "titre VARCHAR(100) NOT NULL,"
					+ "auteur VARCHAR(50),"
					+ "illustrateur VARCHAR(50),"
					+ "edition VARCHAR(50),"
					+ "est_emprunte BOOLEAN,"
					+ "est_documentaire BOOLEAN,"
					+ ");");

			requete.execute("CREATE TABLE emprunts ("
					+ "ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
					+ "id_livre INTEGER NOT NULL,"
					+ "id_eleve INTEGER NOT NULL,"
					+ "titre_livre VARCHAR(100),"
					+ "date_emprunt VARCHAR(10),"
					+ "date_rendu VARCHAR(10)"
					+ ");");
					
		} catch (SQLException e) {
			//VIDE
			if(e.getErrorCode() != -5504)
				e.printStackTrace();
		}
		
		try {
			requete.execute("COMMIT");
			connexion.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/**
 * 
 * @param p_requete
 * @param colonnes Contient les Valeurs des colonnes que l'on veut en SQL (départ : 1 et non 0)
 * @return
 *//*
	public Object[][] resultats_tableau(String p_requete, int[] colonnes){
		
		
		
		ResultSet rs;
		Object[][] retourne = null;
		int i = 0;
		int j;
		Object[] tab_tmp;
		ArrayList<Object[]> lignes = new ArrayList<Object[]>();
		
		try {
			if(requete.execute(p_requete)) {
				rs = requete.getResultSet();
				
				tab_tmp = null;
				
				while(rs.next()){
					
					tab_tmp = new Object[colonnes.length];
					for (i = 0; i < tab_tmp.length; i++){
						tab_tmp[i] = rs.getObject(colonnes[i]);
					}
					lignes.add(tab_tmp);
				}
				
				
				retourne = new Object[lignes.size()][colonnes.length];
				System.out.println("i = " + lignes.size());
				for(i = 0; i < lignes.size(); i++)
					for(j = 0; j < retourne[0].length; j++){
						System.out.println("imoui : " + i + " j : " + j +  " " + (lignes.get(i))[j]);
						retourne[i][j] = lignes.get(i)[j];
					}
			}
				
				
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retourne;
	}
		*/
	
	public Object[][] resultats_tableau(String p_requete) {
		// TODO Auto-generated method stub
		
		ResultSet rs;
		Object[][] retourne = null;
		int i = 0;
		int j = 0;
		Object[] tab_tmp;
		ArrayList<Object[]> lignes = new ArrayList<Object[]>();
		
		try {
			if(requete.execute(p_requete)) {
				rs = requete.getResultSet();
				//rs.last();
				
				tab_tmp = null;
				
				
				
				while (rs.next()){
					tab_tmp = new Object[rs.getMetaData().getColumnCount()];
					for(i = 1; i <= tab_tmp.length; i++){
						j = i -1;
						tab_tmp[j] = rs.getObject(i);
						//System.out.println(" RS lol: " + tab_tmp[j]);
					}
					//System.out.println("tab" + tab_tmp[1]);
					lignes.add(tab_tmp);
				}
				
				i = 0;
				while(i < lignes.size()){
					//System.out.println(" lignes : " + i + " " + lignes.get(i)[1]);
					i++;
				}
				
				
				retourne = new Object[lignes.size()][rs.getMetaData().getColumnCount()];
				//System.out.println("i = " + lignes.size());
				for(i = 0; i < lignes.size(); i++)
					for(j = 0; j < retourne[0].length; j++){
						retourne[i][j] = lignes.get(i)[j];
					}
			}
				
				
				
				/*while(rs.next()){
					for(j = 0; j < retourne.length; j++)
						retourne[i][j] = rs.getObject(j);
					i++;
				}*/
				
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("REQUETE DEFFECTUEUSE : " + p_requete);
			e.printStackTrace();
		}
		
		return retourne;
	}

	public void ajouter(String[] valeurs, String[] champs, String nom_table){
		String s_requete = "INSERT INTO " + nom_table + "(";
		int i = 0;
		boolean b = true;
		
		while(b) {
			s_requete += champs[i];
			
			i++;
			
			b = i < champs.length;
			
			if(b)
				s_requete += ",";
			else
				s_requete += ")";
		}
		
		s_requete += "VALUES ('";
		doublequote(valeurs);
		b = true;
		i = 0;
		
		while(b) {
			s_requete += valeurs[i];
			
			i++;
			b = i < valeurs.length;
			
			if(b)
				s_requete += "','";
			else
				s_requete +="')";
			
		}
		
		try {
			requete.execute(s_requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("REQUETE ERRONEE : " + s_requete);
			e.printStackTrace();
		}
		rafraichir_tableau();
	}
	
	
	/**
	 * Gere les apostrophes pour eviter erreurs SQL.
	 * Le tableau est directement modifie !
	 * @param valeurs
	 */
	private void doublequote( String[] valeurs) {
		// TODO Auto-generated method stub
		int i = -1;
		try{
			for(i = 0; i < valeurs.length; i++)
				valeurs[i] = valeurs[i].replaceAll("'", "''");
		}catch(Exception e){
			System.out.println("IMPOSSIBLE D'ACCEDER A VALEURS " + i);
		}
	}

	public void supprimer(String[] valeurs, String[] champs, String titre) {
		// TODO Auto-generated method stub		
		String s_requete = "DELETE FROM " + titre + " WHERE ";
		
		doublequote(valeurs);
		
		for(int i = 0; i < valeurs.length; i++){
			champs[i] = champs[i].toLowerCase();
			s_requete += "" + champs[i] + " = '" + valeurs[i] + "'";
			if(i < valeurs.length - 1)
				s_requete += " AND ";
		}
		System.out.println(s_requete);
		try {
			this.requete.execute(s_requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("REQUETE DEFFECTUEUSE : \n" + s_requete);
			e.printStackTrace();
		}
		rafraichir_tableau();
	}
	
	public ListeModel getListeModel() {
		return model_tableau;
	}
	
	public void selection_conditionnelle(String[] champs, String nom_table, String champ_cond, String valeur_cond) {
		selection(champs, nom_table);
		
		if(champ_cond != null) {
			requete_model += " WHERE " + champ_cond + " = " + valeur_cond;
		}
		
		rafraichir_tableau();
		
	}
	
	private String selection(String[] champs, String nom_table) {
		requete_model = "SELECT ";
		
		for(int i = 0; i < champs.length; i++){
			requete_model += champs[i];
			if(i < champs.length - 1)
				requete_model += ",";
		}
		
		requete_model += " FROM " + nom_table;
		
		model_tableau = new ListeModel(champs, nom_table);
		
		return requete_model;
	}
	
	public void selectionner(String[] champs, String nom_table) {
		selection(champs, nom_table);
		
		rafraichir_tableau();
	}
	
	public void selectionner_contenant(String[] champs, String nom_table, String champ_conteneur, String valeur_contenue) {
		selection(champs, nom_table);
		
		requete_model += " WHERE " + champ_conteneur + " LIKE '"  + valeur_contenue + "%'";
		
		rafraichir_tableau();
	}
	
	public void selectionner_conditionnel(String[] champs, String nom_table, String champ_cond, String valeur_cond){
		selection(champs, nom_table);
		
		if(champ_cond != "" && champ_cond != null)
			requete_model += " WHERE " + champ_cond + " = " + valeur_cond;
		
		rafraichir_tableau();
		
	}
	
	public void selectionner_conditionnel(String[] champs, String nom_table, String champ_cond, int valeur_cond){
		selection(champs, nom_table);
		
		if(champ_cond != "" && champ_cond != null)
			requete_model += " WHERE " + champ_cond + " = " + valeur_cond;
		
		rafraichir_tableau();
		
	}

	private void rafraichir_tableau() {
		model_tableau.setRowData(resultats_tableau(requete_model));
		model_tableau.fireTableDataChanged();
	}

	public int recup_id_eleve(Object valueAt, Object valueAt2) {
		String requete_s = "SELECT ID from ELEVES where nom = '" + valueAt.toString() + "' AND prenom = '" + valueAt2 + "'";
		int i = -1;
		
		try{
			requete.execute(requete_s);
			
			ResultSet rs = requete.getResultSet();
			
			rs.next();
			i = ((Integer) (rs.getObject("ID")));
		} catch (SQLException e){
			System.out.println("REQUETE DEFFECTUEUSE : " + requete_s);
			e.printStackTrace();
		}
		
		return i;
	}
	/**
	 * Renvoie vrai si le livre dont l'id est en parametre est deja emprunte et non rendu!
	 * @param valueAt
	 * @return
	 */
	public String[] livreDejaEmprunte(int valueAt) {
		// TODO Auto-generated method stub
		String requete_s = "SELECT * from EMPRUNTS where id_livre = " + valueAt + " AND date_rendu IS NULL";
		String[] donnees_eleve = null;
		
		try{
			requete.execute(requete_s);
			
			ResultSet rs = requete.getResultSet();
			if(rs.next()) {
				int id_eleve = ((Integer) rs.getObject("id_eleve"));
				donnees_eleve = new String[2];
				requete_s = "SELECT * from ELEVES where ID = " + id_eleve;
				requete.execute(requete_s);
				rs = requete.getResultSet();
				rs.next();
				donnees_eleve[0] = rs.getObject("prenom").toString();
				donnees_eleve[1] = rs.getObject("nom").toString();
			}
		} catch (SQLException e) {
			System.out.println("Erreur dans EMPRUNTS : " + requete_s);
			e.printStackTrace();
		}
		return donnees_eleve;
	}
	
	public void emprunterLivre(Object valueAt,String titre_livre, int id_eleve) {
		
		// on crée l'objet en passant en paramétre une chaîne representant le format
		SimpleDateFormat formatter = new SimpleDateFormat ("dd.MM.yyyy");
		//récupération de la date courante
		Date currentTime_1 = new Date();
		//on crée la chaîne à partir de la date  
		String dateString = formatter.format(currentTime_1);
		
		System.out.println("date : " + dateString);
		String requete_s = "INSERT INTO EMPRUNTS(id_livre,id_eleve,titre_livre,date_emprunt,date_rendu) VALUES('" + ((Integer) valueAt) + "','"  + id_eleve + "','"+ titre_livre + "','" + dateString + "',NULL)";
		String requete_u = "UPDATE Livres SET est_emprunte = TRUE "
				+ "WHERE id = " + ((Integer) valueAt);
		
		try{
			requete.execute(requete_u);
			requete.execute(requete_s);			
			System.out.println(requete_s);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void terminerEmprunt(int indice_eleve, String id_livre, String date_emprunt) {
		
		String requete_u = "SELECT date_rendu from EMPRUNTS"
				+ " WHERE id_eleve = " + indice_eleve
				+ " AND id_livre = " + id_livre
				+ " AND date_emprunt = '" + date_emprunt + "'";
		
		try{
			requete.execute(requete_u);
			ResultSet rs = requete.getResultSet();
			rs.next();
			
			if(rs.getString("date_rendu") == null) {
		
				// on crée l'objet en passant en paramétre une chaîne representant le format
				SimpleDateFormat formatter = new SimpleDateFormat ("dd.MM.yyyy");
				//récupération de la date courante
				Date currentTime_1 = new Date();
				//on crée la chaîne à partir de la date  
				String dateString = formatter.format(currentTime_1);					
				
				requete_u = "UPDATE EMPRUNTS SET date_rendu = '" + dateString + "'" 
						+ " WHERE id_eleve = " + indice_eleve + ""
						+ " AND id_livre  = " + id_livre + ""
						+ " AND date_emprunt = '" + date_emprunt + "'";
				
				requete.execute(requete_u);
				rafraichir_tableau();
			}else { System.out.println("Deja rendu");}
		} catch (SQLException e) {
			System.out.println("impossible de rendre emprunt " + requete_u);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param indice_eleve
	 * @return tableau string [id, titre]
	 */
	public String[] getEmpruntNonRendu(int indice_eleve) {
		// TODO Auto-generated method stub
		String[] rest = null;
		String requete_s = "SELECT * from EMPRUNTS where id_eleve = " + indice_eleve + " AND date_rendu IS NULL";
		
		try{
			requete.execute(requete_s);
			
			ResultSet rs = requete.getResultSet();
			if(rs.next()){
				int id_livre = ((Integer) rs.getObject("id_livre"));
				rest = new String[2];
				requete_s = "SELECT * from LIVRES where ID = " + id_livre;
				requete.execute(requete_s);
				rs = requete.getResultSet();
				rs.next();
				rest[0] = rs.getObject("id").toString();
				rest[1] = rs.getObject("titre").toString();
			}
		} catch (SQLException e) {
			System.out.println("Erreur dans EMPRUNTS : " + requete_s);
			e.printStackTrace();
		}
		return rest;
	}

	public String getPremierNumeroLibre() {
		// TODO Auto-generated method stub
		String requete_s = "SELECT id from LIVRES";
		int i = 0;
		boolean egalite = true;
		
		try{
			requete.execute(requete_s);
			
			ResultSet rs = requete.getResultSet();
			
			do{
				i++;
				rs.next();
				egalite = (i ==((Integer) rs.getObject(1)));
			}while(egalite);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String s = "" + i;
		
		return s;
	}

	public boolean numeroLivreDejaEnregistre(int numero_livre) {
		// TODO Auto-generated method stub
		
		String requete_s = "SELECT id from LIVRES where id = " + numero_livre;
		
		try{
			requete.execute(requete_s);
			
			ResultSet rs = requete.getResultSet();
			
			if(rs.next())
				return (numero_livre == ((Integer) rs.getObject("id")));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

	public void selectionner_conditionnel_ordre(String[] strings,
			String string, String string2, int indice_eleve, String string3) {
		
		selectionner_conditionnel(strings, string, string2, indice_eleve);
		
		requete_model += "ORDER BY " + string3 + " DESC";
		
		rafraichir_tableau();
		
	}

	/**
	 * 
	 * @param ligne : indice de classe
	 * @return tableau contenant prénom et nom de chaque enfant qui n'a pas rendu son livre
	 */
	public ArrayList<String[]> getElevesIrreguliers(int ligne) {
		// TODO Auto-generated method stub
		
		ArrayList<String[]> res = new ArrayList<String[]>();
		String requete_s = "SELECT id, prenom, nom FROM eleves WHERE id_classe = "  + ligne;
		
		try{
			requete.execute(requete_s);
			ResultSet rs_eleves = requete.getResultSet();
			
			while(rs_eleves.next()){
				if(getEmpruntNonRendu((Integer) (rs_eleves.getObject("id"))) != null) {
					String[] tmp_eleve = new String[2];
					tmp_eleve[0] = ((String) rs_eleves.getObject("prenom"));
					tmp_eleve[1] = ((String) rs_eleves.getObject("nom"));
					res.add(tmp_eleve);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(res.size() > 0)
			return res;
		else
			return null;
	}

	public void supprimerEleve(int ligne) {
		System.out.println("JE SUPPRIME UN ELEVE");
		
		supprimerEmpruntsParEleve(ligne);
		
		try{
			if(getEmpruntNonRendu(ligne) == null) {
				requete.execute("DELETE FROM eleves WHERE id = " + ligne);
				rafraichir_tableau();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void supprimerClasse(int ligne) {
		// TODO Auto-generated method stub
		System.out.println("JE SUPPRIME UNE CLASSE " + ligne);
		try{
			
			requete.execute("SELECT id FROM eleves where id_classe = " + ligne);
			
			ResultSet rs = requete.getResultSet();
			
			while(rs.next()) {
				supprimerEmpruntsParEleve(rs.getInt("ID"));
			}
			
			requete.execute("DELETE FROM eleves WHERE id_classe =" + ligne);
			requete.execute("DELETE FROM classes WHERE ID = " + ligne);
			rafraichir_tableau();
		} catch(Exception e ){
			e.printStackTrace();
		}
		
	}

	/**
	 * Supprime les emprunts via id_eleve
	 * @param indice_eleve
	 */
	public void supprimerEmpruntsParEleve(int indice_eleve) {
		// TODO Auto-generated method stub
		try {
			requete.execute("DELETE FROM emprunts WHERE id_eleve = " + indice_eleve + " AND date_rendu is not null");
			rafraichir_tableau();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @param nom_eleve
	 * @param prenom_eleve
	 * @return NIVEAU classe, nom du professeur
	 */
	public String[] getClasse(String nom_eleve, String prenom_eleve) {
		// TODO Auto-generated method stub
		String[] resultat = null;
		try{
			requete.execute("SELECT id_classe FROM eleves WHERE nom = '" + nom_eleve + "' AND prenom = '" + prenom_eleve + "'");
			ResultSet rs = requete.getResultSet();
			
			if(rs.next()){
				requete.execute("SELECT niveau,professeur FROM classes WHERE id = " + rs.getInt("id_classe"));
				rs = requete.getResultSet();
				if(rs.next()) {
					resultat = new String[2];
					resultat[0] = rs.getString("niveau");
					resultat[1] = rs.getString("professeur");
				}
			}
		} catch( Exception e) {
			e.printStackTrace();
		}
		return resultat;
	}

	/**
	 * Renvoie VRAI en cas d'erreur pour eviter toute insertion erronnee.
	 * @param niveau
	 * @param professeur
	 * @return VRAI si la classe existe
	 */
	public boolean classeDejaExistante(String niveau, String professeur) {
		// TODO Auto-generated method stub
		boolean b = true;

		try{
			requete.execute("SELECT id FROM classes WHERE niveau = '" + niveau + "' and professeur = '" + professeur + "'");
			ResultSet rs = requete.getResultSet();
			
			b = rs.next();
		}catch(Exception e){ 
			e.printStackTrace();
		}
		return b;
	}

	public int recup_id_classe(Object valueAt, Object valueAt2) {
		// TODO Auto-generated method stub
		String niveau = (String) valueAt;
		String professeur = (String) valueAt2;
		
		int i = -1;
		
		try{
			requete.execute("SELECT id from CLASSES where niveau = '" + niveau + "' AND professeur = '" + professeur +"'");
			
			ResultSet rs = requete.getResultSet();
			
			rs.next();
			
			i = rs.getInt("id");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return i;
	}

	public void getEmprunteurs(int valueAt) {
		// TODO Auto-generated method stub
		
		requete_model = "SELECT nom,prenom, niveau, date_emprunt, date_rendu FROM classes,eleves,emprunts WHERE Eleves.id = Emprunts.id_eleve AND Classes.id = id_classe AND id_livre = " + valueAt;
		
		
		model_tableau = new ListeModel(new String[] {"nom","prenom","niveau","date_emprunt","date_rendu"});
		
		rafraichir_tableau();
	}

	public String getTitreLivre(Object valueAt) {
		// TODO Auto-generated method stub
		
		int i = (Integer) valueAt;
		String s = null;
		
		try{
			requete_tmp = "SELECT titre FROM livres WHERE id = " + i;
			requete.execute(requete_tmp);
			ResultSet rs = requete.getResultSet();
			rs.next();
			s = rs.getString("titre");
			System.out.println("ID " + i + " TITRE : " + s);
		} catch(SQLException e) {
			afficherErreur(requete_tmp, e);
		}
		
		return s;
	}

	/**
	 * 
	 * @param bdd_id
	 * @return 0: niveau ; 1 : instit
	 * @throws SQLException 
	 */
	public String[] getClasseParId(int bdd_id) {
		// TODO Auto-generated method stub
		String[] res = new String[2];
		
		try {
			requete_tmp = "SELECT niveau,professeur FROM classes where ID = " + bdd_id;
			requete.execute(requete_tmp);
			ResultSet rs = requete.getResultSet();
			rs.next();
			res[0] = rs.getString("niveau");
			res[1] = rs.getString("professeur");
		}catch(Exception e) {
			afficherErreur(requete_tmp, e);
		}
		return res;
	}

	/**
	 * 
	 * @param bdd_id
	 * @return 0: nom, 1: prenom
	 */
	public String[] getEleveParId(int bdd_id) {
		String[] res = new String[2];
		
		try{
			requete_tmp = "SELECT nom,prenom FROM eleves where ID = "  + bdd_id;
			requete.execute(requete_tmp);
			ResultSet rs = requete.getResultSet();
			rs.next();
			res[0] = rs.getString("nom");
			res[1] = rs.getString("prenom");
		} catch (Exception e) {
			afficherErreur(requete_tmp, e);
		}
		return res;
	}

	public void supprimerLivre(int id_livre) {
		// TODO Auto-generated method stub
		supprimerEmpruntsParLivre(id_livre);
		supprimer(new String[] {Integer.toString(id_livre)}, new String[] {"id"}, "livres");
	}

	private void supprimerEmpruntsParLivre(int id_livre) {
		// TODO Auto-generated method stub
		try{
			requete_tmp = "DELETE FROM emprunts WHERE id_livre = " + id_livre + " AND date_rendu is not null";
			requete.execute(requete_tmp);
		}catch(SQLException e) {
			afficherErreur(requete_tmp, e);
		}
	}
	
	private void afficherErreur(String requete, Exception e) {
		System.out.println("Requete deffectueuse : " + requete);
		e.printStackTrace();
	}
}
