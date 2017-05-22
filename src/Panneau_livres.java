import java.awt.BorderLayout;



import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;


public class Panneau_livres extends JPanel{
	private boolean emprunter;
	private Fenetre fenetre;
	private JTable tableau;

	Panneau_livres(boolean p_emprunter, final Fenetre p_fenetre) {
		super();
		
		emprunter = p_emprunter;
		fenetre = p_fenetre;
		
		main.gbd.selectionner(new String[]{"ID","titre","auteur"},"livres");
		
		
		JLabel label_titre = new JLabel();
		Font police = new Font("Comic Sans MS", Font.BOLD, 18); 
		tableau = new JTable();
		JScrollPane sub_tableau_pane = new JScrollPane(tableau);
		
		setLayout(new BorderLayout());		
		
		if(emprunter)
			label_titre.setText("Emprunter un livre");
		else
			label_titre.setText("Liste des livres");
		
		label_titre.setFont(police); 
		label_titre.setBounds(0,0,this.getWidth(), this.getHeight());
		add(label_titre, BorderLayout.NORTH);
		
		JPanel sub_panneau = new JPanel();
		sub_panneau.setLayout(new BoxLayout(sub_panneau, BoxLayout.PAGE_AXIS));
		
		JPanel panneau_entetes = new JPanel();
		//panneau_entetes.setLayout( new GridLayout(2,2));
		
		JPanel panneau_champs  = new JPanel();
		
		
		JLabel sub_panel_numero = new JLabel("N°");
		sub_panel_numero.setFont(police);
		sub_panel_numero.setSize(300, 25);
		sub_panel_numero.setHorizontalTextPosition(SwingConstants.LEFT);
		
		JLabel sub_panel_titre = new JLabel("Titre");
		sub_panel_titre.setFont(police);
		sub_panel_numero.setSize(150, 25);
		sub_panel_numero.setHorizontalTextPosition(SwingConstants.LEFT);
		
		final IntegerField sub_champ_num = new IntegerField();
		sub_champ_num.setPreferredSize(new Dimension(150,25));
		
		//final JTextField sub_champ_num = new JTextField();
		final JTextField sub_champ_titre = new JTextField();
		sub_champ_titre.setPreferredSize(new Dimension(150,25));
		
		//main.gbd.selectionner(new String[]{"ID","titre","auteur"}, "livres");
		
		tableau.setModel(main.gbd.getListeModel());
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//sub_tableau_pane.setSize(800,600);
		
		panneau_entetes.add(sub_panel_numero);
		panneau_entetes.add(sub_panel_titre);
		panneau_champs.add(sub_champ_num);
		panneau_champs.add(sub_champ_titre);		
		
		//panneau_entetes.setSize(200,500);
		
		JPanel panneau_top = new JPanel();
		panneau_top.setLayout(new BoxLayout(panneau_top, BoxLayout.PAGE_AXIS));
		panneau_top.add(panneau_entetes);
		panneau_top.add(panneau_champs);
		
		sub_panneau.add(panneau_top);
		
		sub_panneau.add(sub_tableau_pane);
		//sub_panneau.setSize(new Dimension(800,600));
		
		add(sub_panneau, BorderLayout.CENTER);
		
		JButton btn_emprunter = null;
		
		JPanel panneau_btns = new JPanel();
		
		if(emprunter) {
			btn_emprunter = new JButton("Emprunter");
			btn_emprunter.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int ligne = tableau.getSelectedRow();
					int id_livre = ((Integer) tableau.getValueAt(tableau.getSelectedRow(), 0));
					String titre_livre = ((String) tableau.getValueAt(tableau.getSelectedRow(), 1));
					if(ligne != -1){
						String[] donnees_emprunteur = main.gbd.livreDejaEmprunte(id_livre);
						if(donnees_emprunteur == null){
							int rep = JOptionPane.showConfirmDialog(
									null,
									"Voulez vous emprunter " + tableau.getValueAt(ligne, 1),
									"Emprunter",
									JOptionPane.YES_NO_OPTION);
							if(rep == JOptionPane.YES_OPTION){
								main.gbd.emprunterLivre(id_livre, titre_livre, fenetre.get_Indice_Eleve());
								main.gbd.selectionner_conditionnel_ordre(new String[] {"id_livre", "titre_livre", "date_emprunt", "date_rendu"}, "emprunts", "id_eleve", fenetre.get_Indice_Eleve(), "id");
								fenetre.setContentPane(fenetre.panneau());
								fenetre.validate();
								fenetre.repaint();
							}
						} else {
							//Le livre est deja emprunte
							JOptionPane.showMessageDialog(
									null,
									"Le livre est deja emprunte par " + donnees_emprunteur[0] + " " + donnees_emprunteur[1],
									"Erreur", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}				
			});
			
			JButton btn_retour = new JButton("Retour");
			btn_retour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(emprunter) {
						main.gbd.selectionner_conditionnel(new String[] {"id_livre", "titre_livre", "date_emprunt", "date_rendu"}, "emprunts", "id_eleve", fenetre.get_Indice_Eleve());
					} else {
						main.gbd.selectionner((new String[]{"niveau", "professeur"}), "classes");
					}
					fenetre.setContentPane(fenetre.panneau());
					fenetre.validate();
					fenetre.repaint();
				}
			});
			
			JButton btn_ajouter = new JButton("Ajouter un livre");
			btn_ajouter.addActionListener(new actionAjouter());
			
			JButton btn_supprimer = new JButton("Supprimer un livre");
			btn_supprimer.addActionListener(new actionSupprimer());
			
			panneau_btns.add(btn_emprunter);		
		}
		
		JButton btn_ajouter = new JButton("Ajouter un livre");
		btn_ajouter.addActionListener(new actionAjouter());

		
		JButton btn_supprimer = new JButton("Supprimer un livre");
		btn_supprimer.addActionListener(new actionSupprimer());
		
		
		JButton btn_retour = new JButton("Retour");
		btn_retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(emprunter) {
					main.gbd.selectionner_conditionnel(new String[] {"id_livre", "titre_livre", "date_emprunt", "date_rendu"}, "emprunts", "id_eleve", fenetre.get_Indice_Eleve());
				} else {
					main.gbd.selectionner((new String[]{"niveau", "professeur"}), "classes");
				}
				fenetre.setContentPane(fenetre.panneau());
				fenetre.validate();
				fenetre.repaint();
			}
		});
		
		JButton btn_getEmprunts = new JButton("Historique");
		btn_getEmprunts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableau.getSelectedRow();
				if(i != -1){
					i = (Integer) tableau.getValueAt(i, 0);
					String str = main.gbd.getTitreLivre(tableau.getValueAt(i, 0));
					fenetre.setContentPane(panneau_historique(i,str));
					fenetre.validate();
					fenetre.repaint();
				}
			}
		});
		
		panneau_btns.add(btn_ajouter);
		panneau_btns.add(btn_supprimer);
		panneau_btns.add(btn_getEmprunts);
		panneau_btns.add(btn_retour);
		
		add(panneau_btns, BorderLayout.SOUTH);
		
		setSize(fenetre.getContentPane().getSize());
		
		//NUMERO
		System.out.println("CLASSE : " + sub_champ_num.getDocument().getClass());
		sub_champ_num.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
		    
		  }
		  
		  public void removeUpdate(DocumentEvent e) {
			  if(sub_champ_num.getText() != ""){
			    	sub_champ_titre.setText("");
					main.gbd.selectionner_contenant(new String[]{"ID","titre","auteur"}, "livres", "ID", sub_champ_num.getText());
			    } else {
			    	main.gbd.selectionner(new String[]{"ID","titre","auteur"}, "livres");
			    }
			    tableau.setModel(main.gbd.getListeModel());
		  }
		  
		  public void insertUpdate(DocumentEvent e) {
			  
			  if(sub_champ_num.getText() != ""){
			    	sub_champ_titre.setText("");
					main.gbd.selectionner_contenant(new String[]{"ID","titre","auteur"}, "livres", "ID", sub_champ_num.getText());
			    } else {
			    	main.gbd.selectionner(new String[]{"ID","titre","auteur"}, "livres");
			    }
			    tableau.setModel(main.gbd.getListeModel());
			  }
		});
		  
		//TITRE
		sub_champ_titre.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  
			  }
			  public void removeUpdate(DocumentEvent e) {
				  if(sub_champ_num.getText() != ""){
				    	sub_champ_num.setText("");
						main.gbd.selectionner_contenant(new String[]{"ID","titre","auteur"}, "livres", "titre", sub_champ_titre.getText());
				    } else {
				    	main.gbd.selectionner(new String[]{"ID","titre","auteur"}, "livres");
				    }
				    tableau.setModel(main.gbd.getListeModel());
			  }
			  public void insertUpdate(DocumentEvent e) {
				  if(sub_champ_num.getText() != ""){
				    	sub_champ_num.setText("");
						main.gbd.selectionner_contenant(new String[]{"ID","titre","auteur"}, "livres", "titre", sub_champ_titre.getText());
				    } else {
				    	main.gbd.selectionner(new String[]{"ID","titre","auteur"}, "livres");
				    }
				    tableau.setModel(main.gbd.getListeModel());
			  }
			});
	}
	
	private JPanel getSelf() {
		return this;
	}
	
	private JPanel panneau_historique(int numero, String titre) {
		JPanel res = new JPanel();
		
		JLabel label_titre = new JLabel();
		Font police = new Font("Comic Sans MS", Font.BOLD, 18); 
		final JTable tableau = new JTable();
		JScrollPane sub_tableau_pane = new JScrollPane(tableau);
		
		res.setLayout(new BorderLayout());		
		
		label_titre.setText("Historique des emprunts : " + numero + ":" + titre);
		
		label_titre.setFont(police); 
		label_titre.setBounds(0,0,this.getWidth(), this.getHeight());
		res.add(label_titre, BorderLayout.NORTH);
		
		
		main.gbd.getEmprunteurs(numero);
		
		tableau.setModel(main.gbd.getListeModel());
		
		res.add(sub_tableau_pane, BorderLayout.CENTER);
		
		JButton bouton_retour = new JButton("Retour");
		bouton_retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fenetre.setContentPane(new Panneau_livres(emprunter, fenetre));
				fenetre.validate();
				fenetre.repaint();
			}
		});
				
		JPanel panneau_sud = new JPanel();
			
		panneau_sud.add(bouton_retour);
		
		res.add(panneau_sud, BorderLayout.SOUTH);
		
		return res;
	}
	
	class actionSupprimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int ligne = tableau.getSelectedRow();
			if(ligne == -1) {
				fenetre.erreurSelectLigne();
			}else {
				int id_livre = ((Integer) (tableau.getValueAt(ligne, 0)));
				String[] donnees_emprunteur;
				donnees_emprunteur = main.gbd.livreDejaEmprunte(Integer.valueOf(id_livre));
				if(donnees_emprunteur == null){
					int rep = JOptionPane.showConfirmDialog(
							null,
							"Voulez vous supprimer " + tableau.getValueAt(ligne, 1),
							"Supprimer un livre",
							JOptionPane.YES_NO_OPTION);
					if(rep == JOptionPane.YES_OPTION){		
						main.gbd.supprimerLivre(id_livre);
					}
				}else
					JOptionPane.showMessageDialog(
							null,
							"Le livre est emprunte par " + donnees_emprunteur[0] + " " + donnees_emprunteur[1],
							"Erreur", 
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class actionAjouter implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			DialogAjouterLivre dialogue_ajouter = new DialogAjouterLivre(fenetre, "Nom", true);
			dialogue_ajouter.setVisible(true);
		}
	}
}
