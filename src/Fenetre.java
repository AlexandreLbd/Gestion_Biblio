import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;


public class Fenetre extends JFrame implements WindowListener{

	private JPanel panneau;
	private JTable tableau;
	private fiche fiche_courante;
	private int indice_classe; //Sert a savoir dans quelle classe on evolue
	private int indice_eleve;
	private Classe classe_cour;
	private Eleve eleve_cour;
	
	public static enum fiche{ CLASSES, ELEVES, EMPRUNTS, LIVRES};
	
	
	public Fenetre() {
		
		super("Thot BCD");
		
		this.setPreferredSize(new Dimension (1024,700));
		//Centrer la fenetre
		
		this.setLocation(0,0);
		
		this.setSize(new Dimension(1024,700));
		
		this.setResizable(true);
		
		fiche_courante = fiche.CLASSES;
		this.setContentPane(this.panneau());

		this.setVisible(true);
		
		this.addWindowListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		indice_classe = -1;
		indice_eleve = -1;
	}
	
	public JPanel panneau_classe() {
		return panneau;
	}

public JPanel panneau() {
		
		tableau = null;
		panneau = null;
		
		panneau = new JPanel();
		
		panneau.setLayout(new BoxLayout(panneau, BoxLayout.PAGE_AXIS));
		
		/*
		final JLabel titre = new JLabel ("ELEVES");
		titre.setSize(300, 100);
		titre.setFont(new Font("Comic Sans MS", Font.BOLD, 32)); 
		
		panneau.add(titre);
		*/
		
		/*
		panneau.setPreferredSize(new Dimension(800,600));
		panneau.setSize(new Dimension(800,600));
		*/
		
		
		JButton bouton_voir = null;
		JButton bouton_ajouter = null;
		JButton bouton_retour = null;
		JButton bouton_supprimer = null;
		JButton bouton_livres = null;
		
		bouton_supprimer = new JButton("Supprimer");
		
		switch(fiche_courante){
			case CLASSES :
				bouton_livres = new JButton("Livres");
				bouton_voir = new JButton("Voir / Modifier");
				bouton_voir.addActionListener(new Vision_Classe());
				bouton_retour = new JButton ("Quitter");
				
			break;
			case ELEVES : 
				bouton_voir = new JButton ("Emprunts");
				bouton_voir.addActionListener(new Vision_eleve());
				bouton_retour = new JButton("Retour");
			break;
			case EMPRUNTS :
				bouton_voir = new JButton("Rendre le livre");
				bouton_voir.addActionListener(new Rendre_livre());
				bouton_ajouter = new JButton("Emprunter un livre");
				bouton_supprimer.setText("Oublier les emprunts");
				bouton_retour = new JButton("Retour");
			break;
		} 
		
		tableau = new JTable();
		tableau.setModel(main.gbd.getListeModel());
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane panneau_tableau = new JScrollPane(tableau);
		panneau_tableau.setSize(new Dimension(800,600));
		
		//panneau.add(tableau);
		
		final JLabel label_titre = new JLabel ();
		label_titre.setSize(300, 100);
		label_titre.setFont(new Font("Comic Sans MS", Font.BOLD, 32)); 
		
		panneau.add(label_titre);
		
		panneau.add(panneau_tableau);
		
		JPanel zone_boutons = new JPanel();
		
		tableau.update(getGraphics());		
		
		
		
		bouton_retour.addActionListener(new Action_Retour());
		
		if(fiche_courante != fiche.EMPRUNTS) {
			switch(fiche_courante){
			case CLASSES :
				bouton_ajouter = new JButton("Ajouter une classe");
			break;
			case ELEVES :
				label_titre.setText("Classe de " + classe_cour.getNiveau() + "(" + classe_cour.getInstit() + ")");
				bouton_ajouter = new JButton("Ajouter un élève");
			break;
			}
			//bouton_ajouter = new JButton("Ajouter 1 " + valeur_titre);
			bouton_ajouter.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					/*String[] entetes = new String[tableau.getColumnCount()];
					for(int i = 0; i < tableau.getColumnCount(); i++)
						entetes[i] = tableau.getColumnName(i);
					Fenetre_Ajouter fa = new Fenetre_Ajouter(valeur_titre, entetes, indice_classe);
					*/
					DialogAjouter dialogue_ajouter = new DialogAjouter(getSelf(), "Ajouter", true, indice_classe);
					dialogue_ajouter.setVisible(true);
				}
				
			});
		}else{
			label_titre.setText("Emprunts de " + eleve_cour.getNom() + " " + eleve_cour.getPrenom() + "(" + classe_cour.getNiveau() + ")");
			bouton_ajouter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String[] donnees_livre = main.gbd.getEmpruntNonRendu(indice_eleve);
					
					if(donnees_livre == null){	
						panneau = new Panneau_livres(true, getSelf());
						setContentPane(panneau);
						repaint();
					} else {
						JOptionPane.showMessageDialog(
								null,
								"L'eleve n'a pas rendu " + donnees_livre[0] + ":" + donnees_livre[1],
								"Erreur", 
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
			
		bouton_supprimer = new JButton("Supprimer");
		bouton_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				String objet_a_suppr;
				int ligne = tableau.getSelectedRow();

				String message_option_pane = new String();
					
				if(fiche_courante != fiche.EMPRUNTS) {
					objet_a_suppr = tableau.getValueAt(ligne, 0) + " " +
							tableau.getValueAt(ligne, 1);
		
					
					if(fiche_courante == fiche.CLASSES){
						objet_a_suppr += "? Cela supprimera egalement tous les eleves et leurs emprunts.";
					} else if(fiche_courante == fiche.ELEVES) {
						objet_a_suppr += "? Cela supprimera egalement tous les emprunts de cet eleve.";
					}
					message_option_pane = "Voulez vous supprimer " + objet_a_suppr;
				} else {
					message_option_pane = "Oublier tous les emprunts rendus de cet élève ?";
				}
				
				int rep = JOptionPane.showConfirmDialog(
						null,
						message_option_pane,
						"Supprimer",
						JOptionPane.YES_NO_OPTION);
				
				if(rep == JOptionPane.YES_OPTION){
					
					//On supprime
					ArrayList<String[]> eleves_irreguliers;
					String message;
					String[] livre_non_rendu;
					int i;
					
					switch(fiche_courante) {
					case CLASSES:
						int id_classe = main.gbd.recup_id_classe(tableau.getValueAt(ligne, 0), tableau.getValueAt(ligne,1));
						Classe classe_cour = new Classe(id_classe);
						eleves_irreguliers = main.gbd.getElevesIrreguliers(id_classe);
						if(eleves_irreguliers != null) {
							message = "Impossible de supprimer la classe, les eleves suivants doivent rendre leurs livres : \n";
							for(i = 0; i < eleves_irreguliers.size(); i++)
								message += eleves_irreguliers.get(i)[0] + " " + eleves_irreguliers.get(i)[1] + "\n";
								JOptionPane.showMessageDialog(
										null,
										message,
										"Erreur", 
										JOptionPane.ERROR_MESSAGE);
						} else {
							main.gbd.supprimerClasse(id_classe);
						}
					break;
					case ELEVES:
						int id_eleve = main.gbd.recup_id_eleve(tableau.getValueAt(ligne, 0), tableau.getValueAt(ligne,1));
						livre_non_rendu = main.gbd.getEmpruntNonRendu(id_eleve);
						if(livre_non_rendu == null) {
							
							//main.gbd.supprimer(new String[] {Integer.toString(id_eleve)}, new String[] {"id"}, "eleves");
							main.gbd.supprimerEleve(id_eleve);
						}else{
								JOptionPane.showMessageDialog(
										null,
										"Il faut que l'eleve ait rendu " + livre_non_rendu[0] + ":" + livre_non_rendu[1] + ""
												+ " pour le supprimer",
										"Erreur", 
										JOptionPane.ERROR_MESSAGE);
						}
					break;
					
					case EMPRUNTS:
							main.gbd.supprimerEmpruntsParEleve(indice_eleve);
					
					}
					/*
					String[] res;
					String[] champs;
					
					res = new String[tableau.getColumnCount()];
					champs = new String[res.length];
					
					for( i = 0; i < res.length; i++){
						System.out.println("SUPPR i = " + i + " ROW  = " + tableau.getSelectedRow());
						res[i] = tableau.getValueAt(ligne, i).toString();
						champs[i] = tableau.getColumnName(i);
						
					}
					//main.gbd.supprimer(res,champs, label_titre.getText());*/
		} 
			}});
		
		if(bouton_livres != null) {
			bouton_livres.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					panneau = new Panneau_livres(false, getSelf());
					setContentPane(panneau);
					repaint();
				}
			});
			zone_boutons.add(bouton_livres);
		}
			
		zone_boutons.add(bouton_voir);
		zone_boutons.add(bouton_ajouter);
		zone_boutons.add(bouton_supprimer);
		zone_boutons.add(bouton_retour);
			
		panneau.add(zone_boutons);
		
		return panneau;
		
	}

	public Fenetre getSelf() {
		return this;
	}
	
	public int get_Indice_Eleve() {
		return indice_eleve;
	}

	

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.setVisible(false);
		main.gbd.extinction();
	}

	
	public class Vision_Classe implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int ligne = tableau.getSelectedRow();
			if(ligne != -1){
				indice_classe = main.gbd.recup_id_classe(tableau.getValueAt(ligne, 0), tableau.getValueAt(ligne,1));
				//indice_classe = ligne;
				
				classe_cour = new Classe(indice_classe);
				
				main.gbd.selectionner_conditionnel(new String[] {"nom","prenom"}, "eleves", "id_classe", ligne);
				tableau.setModel(main.gbd.getListeModel());
				fiche_courante = fiche.ELEVES;
				setContentPane(panneau());
				validate();
			}else {
				erreurSelectLigne();
			}
		}};
		
	public class Vision_eleve implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			int ligne = tableau.getSelectedRow();
			if(ligne != -1){
				//Voir les emprunts faits par l'eleve
				
				indice_eleve = main.gbd.recup_id_eleve(tableau.getValueAt(ligne, 0), tableau.getValueAt(ligne, 1));

				eleve_cour = new Eleve(indice_eleve);
				
				main.gbd.selectionner_conditionnel_ordre(new String[] {"id_livre", "titre_livre", "date_emprunt", "date_rendu"}, "emprunts", "id_eleve", indice_eleve, "id");
				
				fiche_courante = fiche.EMPRUNTS;
				
				setContentPane(panneau());
				validate();
			}else {
				erreurSelectLigne();
			}
		};		
	}
	
	public class Rendre_livre implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			int ligne = tableau.getSelectedRow();
			if(ligne != -1) {
				main.gbd.terminerEmprunt(indice_eleve,
						tableau.getValueAt(ligne, 0).toString(),
						tableau.getValueAt(ligne, 2).toString());
			} else {
				erreurSelectLigne();
			}
		}
	}
	
	public class Action_Supprimer implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
		
			int ligne = tableau.getSelectedRow();
			int tmp;
			
		
				switch (fiche_courante) {
				
					case LIVRES :
						//Trouver si le livre est encore emprunte
						//Effacer les emprunts concernant ce livre
					break;
					
					case EMPRUNTS :
						main.gbd.supprimerEmpruntsParEleve(indice_eleve);
						//Sinon message
					break;
					
					case ELEVES : 
						tmp = main.gbd.recup_id_eleve(tableau.getValueAt(ligne, 0), tableau.getValueAt(ligne, 1));
						main.gbd.supprimerEleve(tmp);
					break;
					
					case CLASSES :
						main.gbd.supprimerClasse(ligne);
					break;
						
				}
		
		}
		
	}
	
	public class Action_Retour implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			switch (fiche_courante){
			
				case LIVRES :
					if(indice_eleve != -1) { 
						fiche_courante = fiche.EMPRUNTS;
						main.gbd.selectionner_conditionnel(new String[] {"id_livre, id_eleve, date_emprunt, date_rendu"}, "emprunts", "id_eleve", indice_eleve);
					} else {
						fiche_courante = fiche.CLASSES;
						main.gbd.selectionner((new String[]{"niveau", "professeur"}), "classes");
					}
				break;
			
				case EMPRUNTS :
					indice_eleve = -1;
					eleve_cour = null;
					fiche_courante = fiche.ELEVES;
					main.gbd.selectionner_conditionnel(new String[] {"nom","prenom"}, "eleves", "id_classe", indice_classe);
				break;
				
				case ELEVES :
					indice_classe = -1;
					classe_cour = null;
					fiche_courante = fiche.CLASSES;
					main.gbd.selectionner((new String[]{"niveau", "professeur"}), "classes");
				break;
				
				case CLASSES : 
					setVisible(false);
					main.gbd.extinction();	
					System.exit(0);
				break;
			}
				
			setContentPane(panneau());
			validate();
		}
		
	}

	public void erreurSelectLigne() {
		JOptionPane.showMessageDialog(
				null,
				"Il faut selectionner une ligne pour cette action !",
				"Erreur", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	
	/*
	public class Action_Quitter implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
			main.gbd.extinction();	
			System.exit(0);
		}
		
		
	}*/
}
