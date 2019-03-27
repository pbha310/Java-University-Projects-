import java.util.*;
import java.awt.*;


class PoliceUnit implements Runnable{ 
	private String id;
	private Point location;
	private String status;
	private Suspect suspect;
	private String suspectId;
	private Station assignedStation;
	private String dog;
	private ArrayList<Suspect> suspect_list; 
	private ArrayList<Station> station_list;
	private Kennel kennel;
	private Point destination = null;
	
	public PoliceUnit(String id, Point location, String status, String dog,String suspectId){
		this.id = id;
		this.location = location;
		this.status = status;
		this.dog = dog;
		this.suspectId = suspectId;
	}
	public void setsuspect_list(ArrayList<Suspect> suspect_list){
		this.suspect_list = suspect_list;
	}
	
	public void setkennel_list(Kennel kennel){
			this.kennel = kennel;
	}
	
	public void setstation_list(ArrayList<Station> station_list){
		this.station_list = station_list;
	}
	
	public String getStatus(){
			return this.status;
	}
	
	public String getid(){
		return this.id;
	}
	
	public void setStatus(String status){
				 this.status= status;
	}
	
	public void setlocation(Point location) {
		this.location = location;
	}
	
	
	
	public Point getLocation(){
		return this.location;
	}
	
	

	
	public boolean moveToPoint(Point destination, int moves){
		int x = (int)this.location.getX();
		int y = (int)this.location.getY();
		int dest_x =(int)destination.getX();
		int dest_y = (int)destination.getY();
		while(moves>0){
	
			if(x<dest_x){
				x++;
			}else if(x>dest_x){
				x--;
			}else if(y>dest_y){
				y--;
			}else if(y<dest_y){
				y++;
			}
			
			
			moves--;
		}
		
		if(x == dest_x && y == dest_y){
			this.location = new Point(x,y);
			return true;
	
		}
		this.location = new Point(x,y);
		return false;
	}
	
	 public String toString(){
		return status + ", " + location;
		
	}
	
	public String current_dog(){
		return this.dog;
	}
	
	public String getSuspectId(){
		return this.suspectId;
	}
	
	
	
	public Suspect getClosestSuspect() {
		double shortestDistance=5555;
		Suspect target_needed = null;
		int y = (int) this.location.getY();
		int x = (int) this.location.getX();
		
		for(Suspect sus: suspect_list){
		    if (sus.status().equals("Unassigned")){
	
		    int new_var_x = (int)sus.getLocation().getX() - x;
		    int new_var_y = (int)sus.getLocation().getY() -y;
		    int end_result = (int) Math.sqrt(new_var_x*new_var_x + new_var_y * new_var_y);
		    
		    if(shortestDistance > end_result ){
		        shortestDistance = end_result;
		        target_needed = sus;
	    	}
		}
		}

		return target_needed;
		}
		
		
		public Station getClosestStation() {
			double shortestDistance=5555;
			Station target_need = null;
			int y = (int) this.location.getY();
			int x = (int) this.location.getX();
			
			for(Station s: station_list){
				if (s.hasSpace()){
					
				    int new_var_x = (int)s.getLocation().getX() - x;
				    int new_var_y = (int)s.getLocation().getY() -y;
				    int end_result = (int) Math.sqrt(new_var_x*new_var_x + new_var_y * new_var_y);
				    
				    if(shortestDistance > end_result ){
				        shortestDistance = end_result;
				        target_need = s;
			    	}
				}
			}

			return target_need;
			}
		
	
	
	public void run(){
		String approch_k = "Approaching Kennel";
		String standby = "Standby";
		
		try {
			
			if(this.status.equals(standby)){
				Suspect suspec = getClosestSuspect();
				if(suspec != null){
					this.status = approch_k;
					this.suspect = suspec;
					this.suspectId = this.suspect.getid();
					this.suspect.status_Set("Assigned");
					this.suspect.setid_police(this.id);
					this.suspect.set_unipolice(this);
					
					
				}
			}
		
		else if(this.status.equals(approch_k)){
			this.destination = this.kennel.getLocation();
			if (moveToPoint(this.destination, 3)){
				this.status = "At Kennel";
			}
		}
		else if (this.status.equals("Approaching Suspect")){
			this.destination = this.suspect.getLocation();
				if (moveToPoint(this.destination, 4)){
					this.status = "At Scene";
					this.suspect.status_Set("Caught");
				}
		
		}
		else if (this.status.equals("At Kennel")){
			if (this.dog.equals("No")){
				if (this.kennel.get_Dog()){
					this.dog = "Yes";
					this.destination = this.suspect.getLocation();
					this.status = "Approaching Suspect";
				}
			}else{
				this.kennel.return_dog();
				this.dog = "No";
				this.status = "Returning";
			}
		}
		
				
		else if (this.status.equals("Returning")){
			Station new_station = getClosestStation();
			this.destination = new_station.getLocation();
			if (moveToPoint(this.destination, 3)){
				if (new_station.entered()){
					new_station.leave();
					this.status = standby;
					this.suspect.status_Set("Jailed");
					this.suspect.set_location(this.destination);
					this.suspect.setid_police(null);
					this.suspect.set_unipolice(null);
					this.destination = null;
					this.suspectId = null;
					this.suspect = null;
											
				}
			}
		}
		else if(this.status.equals("At Scene")){
			Thread.sleep(4000);
			this.suspect.set_location(this.location);
			this.status = approch_k;
		}

			
	  
}
catch (Exception e){
	System.out.print("Error");
}

}
}