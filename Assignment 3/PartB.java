import java.util.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.*;
import javax.swing.event.*;
import javax.swing.*;
import java.util.List;

public class PartB {
	public static void main(String[] args){
		
		ArrayList<PoliceUnit> list_of_police = readPoliceCSV("police.csv");
		ArrayList<Suspect> suspect_list = readSuspectCSV("suspects.csv");
		ArrayList<Station> station_list = new ArrayList<Station>();
		station_list.add(new Station("Downtown", new Point(25,5), list_of_police.size()));
		station_list.add(new Station("Midtown", new Point(80,30), list_of_police.size()));
		station_list.add(new Station("Uptown", new Point(10,90), list_of_police.size()));
		station_list.add(new Station("Lazytown", new Point(70,90), list_of_police.size()));
		int dog = (int)Math.ceil((double)suspect_list.size()/2); 
		Kennel kennel = new Kennel(new Point (50,50), dog);
		for (PoliceUnit police: list_of_police){
			police.setsuspect_list(suspect_list);
			police.setkennel_list(kennel);
			police.setstation_list(station_list);
			}
		
		
		final SimulationTableModel tableModel = new SimulationTableModel(list_of_police);
		tableModel.setrows(list_of_police.size());
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				SimulationFrame simFrame = new SimulationFrame("Police Simulator");
				SimulationTablePanel tablePanel = new SimulationTablePanel(tableModel);
				simFrame.getContentPane().add(tablePanel);
				simFrame.setVisible(true);
			}
		});
		
		
		SwingWorker worker = new SwingWorker<Void, SimulationTableModel>(){
			public Void doInBackground(){
				int count = 0;
				int maxCycles = 60;
				long lastCycle= 0;				
				ExecutorService executorService = Executors.newFixedThreadPool(list_of_police.size());				
				while(count < maxCycles){
					if(System.currentTimeMillis() - lastCycle >= 1000){
						for(PoliceUnit p: list_of_police){
								executorService.execute(p);
							}
							lastCycle = System.currentTimeMillis();
							count++;
							this.publish(tableModel);
												
						}
				}
				executorService.shutdown();
				if(executorService.isShutdown()){
					police_unit(list_of_police);
					suspect_unit(suspect_list);
				}
				return null;

				}
			
				protected void process(List<SimulationTableModel> tableModels){
					for(SimulationTableModel model: tableModels) {
					model.fireTableDataChanged();
				}
			}
				
	};
	worker.execute();
			
		
	}

	
	public static ArrayList<PoliceUnit> readPoliceCSV (String filename){
	try {
		        Scanner scanner = new Scanner(new File(filename));
		        ArrayList<PoliceUnit> none_list = new ArrayList<>();
				while (scanner.hasNextLine()) {
				    
				    String[] data = scanner.nextLine().split(",");
				    if(data[0].equals("id")){
				        continue;
				    }
				    int x = Integer.parseInt(data[1]);
				    int y = Integer.parseInt(data[2]);
					none_list.add(new PoliceUnit(data[0], new Point(x,y), data[3], data[4],null));
				}
				return none_list;
			} catch (FileNotFoundException e) {
				
				return null;
			}
	}
	
	
	public static void police_unit (ArrayList<PoliceUnit> policeWriteOut){
		FileWriter writing_to_file = null;
		try {
				writing_to_file = new FileWriter("police-output.csv");
				writing_to_file.append("id,x.location,y.location,status,dog,suspect\n");
				for (PoliceUnit pu: policeWriteOut) {
					String x = (String.format("%d,", (int)pu.getLocation().getX()));
					String y = (String.format("%d,", (int)pu.getLocation().getY()));
					writing_to_file.append(pu.getid()+(",")+x+y+pu.getStatus()+(",")+pu.current_dog()+(","));
					
					if (pu.getSuspectId() != null) {
						writing_to_file.append(pu.getSuspectId());
					}
					writing_to_file.append("\n");	
				}
			}
		catch (Exception e){
			System.out.print("Error in file" + e);
		}
		finally{
			try{
				writing_to_file.flush();
				writing_to_file.close();
			}
			catch(Exception e){
				System.out.println("Error" +e);
			}
		}
		}
		
		public static void suspect_unit (ArrayList<Suspect> suspectWriteOut){
			FileWriter writing_to_file = null;
			try {
					writing_to_file = new FileWriter("suspects-output.csv");
					writing_to_file.append("id,x.location,y.location,status,police unit\n");
					for (Suspect s: suspectWriteOut) {
						String x = (String.format("%d,", (int)s.getLocation().getX()));
						String y = (String.format("%d,", (int)s.getLocation().getY()));
						writing_to_file.append(s.getid() + (",")+x+y+s.status()+(","));
						
						if (s.id_police() != null) {
							writing_to_file.append(s.id_police());
						}
						writing_to_file.append("\n");	
					}
				}
			catch (Exception e){
				System.out.print("Error in file" + e);
			}

			finally{
				try{
					writing_to_file.flush();
					writing_to_file.close();
					
					
				}
				catch(Exception e){
					System.out.println("Error"+ e);
				}
			}
			}
			public static ArrayList<Suspect> readSuspectCSV (String filename){
				try {
					        Scanner scanner = new Scanner(new File(filename));
					        ArrayList<Suspect> sus = new ArrayList<>();
							while (scanner.hasNextLine()) {
							    
							    String[] data = scanner.nextLine().split(",");
							    if(data[0].equals("id")){
							        continue;
							    }
							    int x = Integer.parseInt(data[1]);
							    int y = Integer.parseInt(data[2]);
								sus.add(new Suspect(data[0], new Point(x,y), data[3], ""));
							}
							    return sus;
						} catch (FileNotFoundException e) {
						
							return null;
						}
				}

	
	
}