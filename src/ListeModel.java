import javax.swing.table.AbstractTableModel;


public class ListeModel extends AbstractTableModel {
	
	//Liste generee pour l'affichage

	private String[] entetes = { "niveau", "professeur" }; //valeurs par defaut
	private Object[][] rowData;
	private String nom_table;
	
	public ListeModel(){
		String requete = "SELECT niveau,professeur FROM classes";
	//	rowData = main.gbd.resultats_tableau(requete);
		
	}
	
	public ListeModel(String[] p_entetes) {
		changerEntetes(p_entetes);
	}
	
	public ListeModel(String[] p_entetes, String p_table) {
		changerEntetes(p_entetes);
		nom_table = p_table;
	}
	
	public void changerEntetes(String [] p_entetes) {
		String tmp;
		entetes = new String[p_entetes.length];
		
		for(int i = 0; i < p_entetes.length; i++){
			tmp = p_entetes[i].replace("_", " ");
			tmp = tmp.toUpperCase();
			entetes[i] = tmp;		
		}
	}
	
	
	public void  setRowData(Object[][] p_rowData){
		rowData = p_rowData;
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return entetes.length;
		
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowData.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return rowData[arg0][arg1];
	}
	
	public Class getClass(int n_colonne){
		return getValueAt(0,n_colonne).getClass();
	}
	
	public String getColumnName(int columnIndex) {
	    return entetes[columnIndex];
	}
	

	/*
	public void mise_a_jour(String requete) {
		main.gbd.executerRequeteBrute(requete, false);
		System.out.println("req : " + requete);
		rowData = main.gbd.resultats_tableau("SELECT niveau,professeur FROM classes");
		fireTableDataChanged();
	}*/

	public String getTitre() {
		// TODO Auto-generated method stub
			String res = new String();
			res += Character.toUpperCase(nom_table.charAt(0));
			res += nom_table.substring(1);
			return res;	}

}
