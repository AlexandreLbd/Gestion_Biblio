import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DialogAjouter extends JDialog{
	
	/**
	 * Ajouter Classe ou enfant via bool
	 * Classe : faux, enfant : vrai
	 */
	private String str1;
	private String str2;
	private int id_classe;
	
	
	public DialogAjouter(JFrame parent, String titre, boolean modal, int p_id_classe) {
		super(parent, titre, modal);
		
		setSize(250,200);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		id_classe = p_id_classe;
		initComponent();
	
	}
	
	private void initComponent() {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		
		final JTextField champ1;
		final JTextField champ2;
		
		JButton btn_confirmer;
		JButton btn_annuler;
		
		JPanel panel1, panel2, panel_btns;
		
		panel1 = new JPanel();

		champ1 = new JTextField();
		champ1.setPreferredSize(new Dimension(100,25));
	
		
		panel2 = new JPanel();			

		champ2 = new JTextField();
		champ2.setPreferredSize(new Dimension(150,25));
		
		btn_confirmer = new JButton("Ajouter");
		btn_annuler = new JButton("Annuler");
		
		
		btn_annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				setVisible(false);
			}
		});
		
		if(id_classe != -1) {
			//Enfant
			setTitle("Ajouter un eleve");
			label0 = new JLabel("Ajouter un eleve");
			label1 = new JLabel("Nom");		
			label2 = new JLabel("Prenom");

			btn_confirmer.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String nom_eleve = champ1.getText().trim();
					String prenom_eleve = champ2.getText().trim();
					
					if(nom_eleve.length() > 0 && prenom_eleve.length() > 0) {
						String[] classe_eleve = main.gbd.getClasse(nom_eleve, prenom_eleve);
						if(classe_eleve == null) {
							String[] valeurs = new String[]{ nom_eleve, prenom_eleve, Integer.toString(id_classe)};
							String[] champs = new String[]{ "nom", "prenom", "id_classe"};
							main.gbd.ajouter(valeurs, champs, "eleves");
							setVisible(false);
						} else {
							//Erreur, eleve deja existant
							JOptionPane.showMessageDialog(
									null,
									"Erreur !\n" + "L'enfant est deja enregistre dans la classe de " + classe_eleve[1] + "(" + classe_eleve[0] + ")",
									"Erreur", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			});
		} else {
			setTitle("Ajouter une classe");
			label0 = new JLabel("Ajouter une nouvelle classe");
			label1 = new JLabel("Niveau");
			label2 = new JLabel("Professeur");
			
			btn_confirmer.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					String niveau = champ1.getText().trim();
					String professeur = champ2.getText().trim();
					
					if(niveau.length() > 0 && niveau.length() > 0) {
						if(!main.gbd.classeDejaExistante(niveau,professeur)) {
							String[] valeurs = new String[]{ niveau,professeur};
							String[] champs = new String[]{ "niveau", "professeur"};
							main.gbd.ajouter(valeurs,champs,"classes");
							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(
									null,
									"Erreur !\n" + "Cette classe existe déjà !",
									"Erreur", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		
		JPanel panel0 = new JPanel();
		panel0.add(label0);
		
		panel1.add(label1);
		panel1.add(champ1);
		
		panel2.add(label2);
		panel2.add(champ2);
		
		panel_btns = new JPanel();
		
		panel_btns.add(btn_confirmer);
		panel_btns.add(btn_annuler);
		
		JPanel principal_panneau = new JPanel();
		principal_panneau.setLayout(new BoxLayout(principal_panneau, BoxLayout.PAGE_AXIS));
		principal_panneau.add(panel0);
		principal_panneau.add(panel1);
		principal_panneau.add(panel2);
		principal_panneau.add(panel_btns);
		
		
		setContentPane(principal_panneau);
		
	}
}
