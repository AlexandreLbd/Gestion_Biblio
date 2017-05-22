import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DialogAjouterLivre extends JDialog {
	private int numero_livre;
	private String titre_livre, auteur_livre, illustrateur_livre, edition_livre;
	private JTextField champ_numero, champ_titre, champ_auteur, champ_illustrateur, champ_edition;
	private JRadioButton radio_oui, radio_non;
	private JLabel lbl_num, lbl_titre, lbl_auteur, lbl_illus, lbl_edition, lbl_doc;
	private boolean estDocumentaire;
	
	public DialogAjouterLivre(JFrame parent, String titre, boolean modal) {
		super(parent, titre, modal);
		
		setSize(500,300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		initComponent();

	}

	private void initComponent() {
		
		//Panneau numero
		JPanel panel_num = new JPanel();
		lbl_num = new JLabel("Numero");
		champ_numero = new JTextField();
		champ_numero.setPreferredSize(new Dimension(50,25));
		champ_numero.setText(main.gbd.getPremierNumeroLibre());
		/*champ_numero.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				 SEULEMENT DES CHIFFRES 
			  }*/
		panel_num.add(lbl_num);
		panel_num.add(champ_numero);
		
		
		JPanel panel_titre = new JPanel();
		lbl_titre = new JLabel("Titre");
		champ_titre = new JTextField();
		champ_titre.setPreferredSize(new Dimension(100,25));
		panel_titre.add(lbl_titre);
		panel_titre.add(champ_titre);
		
		
		JPanel panel_auteur = new JPanel();
		lbl_auteur = new JLabel("Auteur");
		champ_auteur = new JTextField();
		champ_auteur.setPreferredSize(new Dimension(100,25));
		panel_auteur.add(lbl_auteur);
		panel_auteur.add(champ_auteur);
		
		
		JPanel panel_illus = new JPanel();
		lbl_illus = new JLabel("Illustrateur");
		champ_illustrateur = new JTextField();
		champ_illustrateur.setPreferredSize(new Dimension(100,25));
		panel_titre.add(lbl_illus);
		panel_titre.add(champ_illustrateur);
		
		
		JPanel panel_editeur = new JPanel();
		lbl_edition = new JLabel("Edition");
		champ_edition = new JTextField();
		champ_edition.setPreferredSize(new Dimension(75,25));
		panel_editeur.add(lbl_edition);
		panel_editeur.add(champ_edition);
		
		
		JPanel panel_estDoc = new JPanel();
		lbl_doc = new JLabel("Est un documentaire");
		radio_oui = new JRadioButton("OUI");
		radio_non = new JRadioButton("NON");
		ButtonGroup bg = new ButtonGroup();
		bg.add(radio_oui);
		bg.add(radio_non);
		radio_non.setSelected(true);
		panel_estDoc.add(lbl_doc);
		panel_estDoc.add(radio_oui);
		panel_estDoc.add(radio_non);
		
		
		JButton btn_OK = new JButton("Ajouter");
		JButton btn_annuler = new JButton("Annuler");
		
		
		btn_annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				setVisible(false);
			}
		});
		
		
		btn_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> liste_erreurs = new ArrayList<String>();
				numero_livre = Integer.valueOf(champ_numero.getText());
				titre_livre = (champ_titre.getText()).trim();
				auteur_livre = champ_auteur.getText();
				illustrateur_livre = champ_illustrateur.getText();
				edition_livre = champ_edition.getText();
				
				if(numero_livre < 1)
					liste_erreurs.add("Numero invalide");
					
				if(titre_livre.length() == 0) 
					liste_erreurs.add("Il faut un titre");
				
				if(main.gbd.numeroLivreDejaEnregistre(numero_livre))
					liste_erreurs.add("Numero de livre deja enregistre");
				
				estDocumentaire = radio_oui.isSelected();
				
				System.out.println("Est Documentaire : " + estDocumentaire);
				
				if(liste_erreurs.size() > 0) {
					String montrer_erreur = "\t";
					for(int i = 0; i < liste_erreurs.size(); i++)
						montrer_erreur += liste_erreurs.get(i) + "\n\t";
					
					JOptionPane.showMessageDialog(
							null,
							"Erreur !\n" + montrer_erreur,
							"Erreur", 
							JOptionPane.ERROR_MESSAGE);
				} else { // on ajoute le livre
					String[] valeurs = new String[7];
					
					valeurs[0] = Integer.toString(numero_livre);
					valeurs[1] = titre_livre;
					valeurs[2] = auteur_livre;
					valeurs[3] = illustrateur_livre;
					valeurs[4] = edition_livre;
					valeurs[5] = "FALSE";
					
					if(estDocumentaire) 
						valeurs[6] = "TRUE";
					else
						valeurs[6] = "FALSE";
					
					String[] champs = new String[7];
					
					champs[0] = "ID";
					champs[1] = "titre";
					champs[2] = "auteur";
					champs[3] = "illustrateur";
					champs[4] = "edition";
					champs[5] = "est_emprunte";
					champs[6] = "est_documentaire";
					
					main.gbd.ajouter(valeurs, champs, "livres");
					setVisible(false);
				}
			}
		});
		
		
		JPanel panel_btns = new JPanel();
		panel_btns.add(btn_OK);
		panel_btns.add(btn_annuler);
		
		
		JPanel principal_panneau = new JPanel();
		principal_panneau.add(panel_num);
		principal_panneau.add(panel_titre);
		principal_panneau.add(panel_auteur);
		principal_panneau.add(panel_illus);
		principal_panneau.add(panel_editeur);
		principal_panneau.add(panel_estDoc);
		principal_panneau.add(panel_btns);
		
		
		setContentPane(principal_panneau);
	
		
	}
	
	
	
}
