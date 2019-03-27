import javax.lang.model.element.*;
import java.awt.*;
import java.io.*;

class Station {
	private String name;
	private Point location;
	private int maxCops;


	public Station (String name,Point location, int maxCops){
		this.name = name;
		this.location = location;
		this.maxCops = maxCops;
	}
	

	public synchronized boolean entered (){
			if (this.maxCops >0){
				this.maxCops --;
			return true;
			}else {
				return false;
			}
	}
	
	public synchronized boolean hasSpace(){
			if (this.maxCops > 0){
				return true;
			}
			else{
				return false;
			}
		}
	public synchronized void leave (){
			this.maxCops ++;
		}
	
	public Point getLocation(){
		return this.location;
	
		
	
	}
	
	
}
	