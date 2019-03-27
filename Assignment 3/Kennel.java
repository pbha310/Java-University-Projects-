import javax.lang.model.element.*;
import java.awt.*;
import java.io.*;
class Kennel {
	
	private int maxUnits;
	private Point location; 
	
	public Kennel (Point location, int maxUnits ){
		
		this.location = location;
		this.maxUnits = maxUnits;
	}
	public synchronized void return_dog (){
		this.maxUnits ++;
	}
	public synchronized boolean get_Dog (){
		if (this.maxUnits >= 1){
			this.maxUnits --;
		return true;
		}
		return false;
		
	}
	public synchronized int maxUnits (){
		return this.maxUnits;
	}
	
	public Point getLocation(){
		return this.location;
			
		}
	
	}
	
	