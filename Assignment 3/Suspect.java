import javax.xml.stream.*;
import java.awt.*;
import java.io.*;

class Suspect {
	private String id;
	private Point location;
	private String status;
	private String id_police;
	private PoliceUnit unitPolice;
	
	
	public Suspect (String id, Point location, String status, String id_police ){
		this.id = id;
		this.location = location;
		this.status = status;
		this.id_police = id_police;
		
	}
	public synchronized void set_unipolice(PoliceUnit unitPolice){
		this.unitPolice = unitPolice;
	}
	public void setid_police(String id_police){
		this.id_police = id_police;
	}
	
	
	public String status() {
		return this.status;
	}
	public String getid(){
		return this.id;
	}	
	public String id_police(){
		return this.id_police;
	}
	public  synchronized boolean checkandsetstatus(String id_police) {
		if (this.status.equals("unassigned")){
			if (this.id_police == null ){
				this.id_police = id_police;
				return true;
				}
			}
		else{
			 

	}
	return false;
	}
	public void status_Set(String status){
		this.status = status;
	}
	public void set_location(Point location){
		this.location = location;
	}
	

	public Point getLocation(){
				return this.location;
			
	}
		
}