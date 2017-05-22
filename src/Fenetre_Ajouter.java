import java.awt.Dimension ;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


public class Fenetre_Ajouter extends JFrame{
	/**
	 * La fenetre qui va permettre d'ajouter n'importe quoi a la BDD
	 */

	JLabel titre; 
	JButton confirmation;
	JButton annulation;
	JLabel[] entetes;
	JTextField[] champs_txt;
	String nom_table;//Nom de la table SQL correspondante
	ActionListener listener_ajout;
	
	/**
	 * 
	 * @param p_titre Titre correspondant a la classe
	 * @param p_entetes Entetes correspondant aux champs 
	 * @param classe Ne sert qu'a entrer des eleves, NULL si on rentre autre chose que des eleves.
	 */
	public Fenetre_Ajouter(String p_titre, String[] p_entetes, final int indice_classe) {
		super();
		
		nom_table = p_titre;
		titre = new JLabel("Ajouter un élément dans les " + p_titre);
		
		if(p_entetes != null){
			
			entetes = new JLabel[p_entetes.length];
			champs_txt = new JTextField[p_entetes.length];
			
			for(int i = 0; i < p_entetes.length; i++){
				entetes[i] = new JLabel(p_entetes[i]);
				champs_txt[i] = new JTextField();
			}	
		}
		
		confirmation = new JButton("Ajouter");
		
		confirmation.addActionListener(listener_ajout = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean b = true;
				String[] valeurs = new String[champs_txt.length];
				int i = 0;
				
				while(b && i < champs_txt.length){
					b = (champs_txt[i].getText() != null && champs_txt[i].getText() != "");
					
					if(b)
						valeurs[i] = champs_txt[i].getText();
					
					i++;
				}
				
				if(!b) {
					//Message d'erreur, les champs doivent être tous remplis
				} else {
					String[] entetes_txt = new String[entetes.length];
					
					for(i = 0; i < entetes.length; i++)
						entetes_txt[i]= entetes[i].getText();
					
					System.out.println("indice classe : " + indice_classe);
					
					if(indice_classe == -1)
						main.gbd.ajouter(valeurs, entetes_txt, nom_table);
					else{
						String[] valeurs2 = new String[valeurs.length+1];
						String[] entetes_txt2 = new String[entetes_txt.length+1];
						
						for(i = 0; i < valeurs.length; i++){
							valeurs2[i] = valeurs[i];
							entetes_txt2[i] = entetes_txt[i];
						}			
						valeurs2[i] = Integer.toString(indice_classe);
						entetes_txt2[i] = "id_classe";
						
						main.gbd.ajouter(valeurs2, entetes_txt2, nom_table);
					}
				}		
				setVisible(false);
			}
		});
		
		JPanel panneau = new JPanel();
		panneau.setMinimumSize(new Dimension(200,400));
		panneau.setLayout(new BoxLayout(panneau, BoxLayout.PAGE_AXIS));
		
		panneau.add(titre);
		for(int i = 0; i < entetes.length; i ++)
			panneau.add(entetes[i]);
		
		for(int i = 0; i < champs_txt.length; i ++){
			panneau.add(champs_txt[i]);
		}
		
		annulation = new JButton("Annuler");
		
		annulation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);				
			}
		});
		
		panneau.add(confirmation);
		panneau.add(annulation);		
		
		this.setContentPane(panneau);
		
		this.setSize(200, 400);
		this.setResizable(false);
		
		this.setVisible(true);
	}
	
		
	
}
