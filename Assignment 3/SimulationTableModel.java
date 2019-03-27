import java.awt.*;
import javax.swing.table.*;
import java.util.*;



public class SimulationTableModel extends AbstractTableModel {
	private int columns = 5;
	private int row;
	private String[] col_name;
	private ArrayList<PoliceUnit> unitpolice;
	
	public SimulationTableModel(ArrayList<PoliceUnit> list_of_police){
		this.col_name = new String[]{"id","location","status","dog","suspect"};
		this.unitpolice = list_of_police;
	}

	public void setrows(int size){
		this.row = size;
	}
	public int getRowCount() { return this.row; }
		
	public String getColumnName(int col) {return col_name[col];}
	
	public Object getValueAt(int row, int col) {
		
			PoliceUnit police = unitpolice.get(row);
			if (col==0){
				return police.getid();
			}
			else if(col==1){
				
				return String.format("(%s,%s)",(int)police.getLocation().getX(),(int)police.getLocation().getY());
			}
			else if(col ==2){
				return police.getStatus();
			}
			else if(col ==3){
				return police.current_dog();
			}
			else if(col ==4){
				return police.getSuspectId();
			}

			return null;
		}
	public int getColumnCount() { return columns; }
}

