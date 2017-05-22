import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class main {

	public HashMap<Integer,Livre> livres;
	public static Gestionnaire_Bdd gbd ;//= new Gestionnaire_Bdd();;
	
	public static void main(String[] args){
		
		gbd = new Gestionnaire_Bdd();
		
		for(int i = 0; i < args.length; i++)
			System.out.println(args[i]);
		
		if(args.length > 0  && args[0].compareTo("-admin") == 0) {
			boolean quit = false;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String commande = null;
			do { 
				System.out.println("Tapez une requete SQL : \nTapez MONTRER pour montrer les tables\nQUITTER pour quitter");
				try {
					commande = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if((commande.compareTo("QUITTER") == 0) || commande == null) 
					quit = true;
				else if(commande.compareTo("MONTRER") == 0) 
					gbd.executerRequeteBrute("SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES" , true);
				else
					gbd.executerRequeteBrute(commande, true);			
			} while (!quit);
		} else {
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
	
		
		try {
			int i = 0;
			
			/*gbd.executerRequeteBrute("DROP TABLE livres", false);
			gbd.creerTables();*/
			
			//BufferedWriter bfw = new BufferedWriter(new FileWriter("text1.txt"));
			BufferedReader bfr = new BufferedReader(new FileReader("text1.txt"));
			String ligne;
			String[] livre_brut;
			String requete;
			
			/*
			 * IMPORTATION XML
			 docBuilder = docBuilderFactory.newDocumentBuilder();
			
			Document doc = docBuilder.parse(new File("estDoc2.xml"));
			
			//Normalisation du texte
			doc.getDocumentElement().normalize();
			
			NodeList liste_nodes = doc.getElementsByTagName("DATA");
			
			System.out.println(liste_nodes.getLength());
			
			for (i = 0 ; i < liste_nodes.getLength() ; i++){
				
				Node node = liste_nodes.item(i);
				
				if(node.getNodeType() == node.ELEMENT_NODE){
					//System.out.println(node.getTextContent());
					ligne = bfr.readLine();
						bfw.write(ligne + node.getTextContent() + "£");
										bfw.newLine();
				}
				
			}*/
		/*	
			requete = "DELETE FROM livres";
			gbd.executerRequeteBrute(requete,false);
			
			boolean b = false;
			while((ligne = bfr.readLine()) != null){
				livre_brut = ligne.split("£");
				while(livre_brut.length < 4 || livre_brut[0].isEmpty()){
					ligne = bfr.readLine();
					livre_brut = ligne.split("£");
				}
				
/*
					for(int s = 0; s < livre_brut.length; s++)
						System.out.println(livre_brut[s]);

				
				requete = "INSERT INTO livres (ID,titre,auteur,edition,est_documentaire) VALUES ("
						+ livre_brut[0]+ ","
						+ "'" + livre_brut[1] + "',"
						+ "'" + livre_brut[2] + "',"
						+ "'" + livre_brut[3] + "',";
						
				if(livre_brut.length == 5){
					if(livre_brut[4].contains("FICTION"))
						b = false;
					if(livre_brut[4].contains("documentaire"))
						b = true;
					
					if(b)
						requete += "TRUE);";
					else
						requete += "FALSE);";
				} else {
					requete += "FALSE);";
				}
				
				//System.out.println(++i);
				
				gbd.executerRequeteBrute(requete,false);
			}
				i = -2;
				BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
				while (i != - 1){
					System.out.println("Tapez un numéro de livre ou -1 pour quitter");
					
					i = Integer.parseInt((clavier.readLine()));
					
					if(i == -2)
						i = -1;
					else{
						requete = "SELECT * from Livres WHERE ID=" + i;
						gbd.executerRequeteBrute(requete, true);
					}
				}
					
				
				//gbd.executerRequeteBrute("SELECT * FROM livres");*/
				
				
			//}
			
			bfr.close();
			//bfw.close();
			/*
			String[] prenoms = resultats(doc, "prenom_eleve");
			String[] nom = resultats(doc, "nom_eleve");
			String[] niveaux = resultats(doc, "cours_eleve");
			String[] maitres = resultats(doc, "maitre_eleve");
			
			ArrayList<Eleve> liste_eleves = new ArrayList<Eleve>(); 
			
			System.out.println(prenoms.length);
			 
			for(i = 0; i < prenoms.length; i++){
				
				liste_eleves.add(new Eleve(prenoms[i], nom[i], niveaux[i], maitres[i]));
				//System.out.println(prenoms[i] + " " + nom[i] + " " + niveaux[i] + " " + maitres[i]);
			}
			
			
			
			/*

			for (i = 0; i < liste_eleves.size(); i++) {
				
				Eleve eleve_tmp = liste_eleves.get(i);
				
				requete.execute("INSERT INTO eleves "
						+ "VALUES ('" + i + "',"
						+ "'" + eleve_tmp.getNom() + "',"
						+ "'" + eleve_tmp.getPrenom() + "',"
						+ "'" + eleve_tmp.getNiveau() + "',"
						+ "'" + eleve_tmp.getInstit() + "'"
						+ ");");
				/* Il faudra modifier le getInstit 
				* De facon a passer par SQL get instit from where niveau
				
				
				ResultSet rs = requete.executeQuery("SELECT * FROM eleves");
				
				while(rs.next())
					System.out.println(rs.getInt(1) + " "
							+ rs.getString(2));
				
				
				connexion.commit();
			}
				/*Fermeture de la base
				 * SHUTDOWN est indispensable AVANT le close.
				 
			
		*/		
				} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			new Fenetre();
		}
		return;
		
		//gbd.extinction();
	}


public static String[] resultats(Document pdoc, String ptag) {
	
	NodeList liste_nodes = pdoc.getElementsByTagName(ptag);
	
	String[] res = new String[liste_nodes.getLength()];
	
	int i;
	
	for (i = 0 ; i < liste_nodes.getLength() ; i++){
		
		Node node = liste_nodes.item(i);
		
		if(node.getNodeType() == node.ELEMENT_NODE)
		res[i] = node.getTextContent();
	}
	
	return res;
}



}
	


