//Prince Bhatia 
//pbha310
//contains all the methods that are genric from other methods to make them work
import java.awt.*;
public abstract  class MovingShape {
	protected  int marginWidth, marginHeight; 
	protected  int x, y;            
	protected  int width, height;            
	protected  MovingPath path;            
	protected  Color borderColor, fillColor;        
	protected  boolean selected = false;    
	
	public MovingShape() {
		this(10, 20, 50, 20, 500, 500, Color.orange, Color.yellow, Path.FALLING); 
	}

	
	public MovingShape(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, Path pathType) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		marginWidth = mw;
		marginHeight = mh;
		borderColor = bc;
		fillColor = fc;
		setPath(pathType);
	}

	
	public int getX() { return x; }
	
	public int getY() { return y;}
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setWidth(int w) { width = w; }
	
	public void setHeight(int h) { height = h; }
	
	public boolean isSelected() { return selected; }
	
	public void setSelected(boolean s) { selected = s; }
	
	public void setBorderColor(Color c) { borderColor = c; }
	
	public void setFillColor(Color fc) { fillColor = fc; }
	
	public String toString() {
		return "[" + this.getClass().getName() + "," + x + "," + y + "]";
	}

	
	public void setMarginSize(int w, int h) {
		marginWidth = w;
		marginHeight = h;
	}

	
	public void drawHandles(Graphics g) {
		
		if (isSelected()) {
			g.setColor(Color.black);
			g.fillRect(x -2, y-2, 4, 4);
			g.fillRect(x + width -2, y + height -2, 4, 4);
			g.fillRect(x -2, y + height -2, 4, 4);
			g.fillRect(x + width -2, y-2, 4, 4);
		}
	}

	
	public abstract  void draw(Graphics g); 

	
	public abstract boolean contains(Point mousePt); 
		

	
	public void setPath(Path pathID) {
		switch (pathID) {
			case BOUNCING : {
				path = new BouncingPath(5,10);
				break;
			}
			case FALLING: {
				path = new FallingPath(5,10);
				break;
			}
		}
	}

	
	public void move() {
		path.move();
	}

	

	public abstract class MovingPath {
		protected int deltaX, deltaY; 

		
		public MovingPath() { }

		
		public abstract void move();
	}

	
	public class FallingPath extends MovingPath {

		 
		public FallingPath(int dx, int dy) {
			deltaX = dx *2;
			
		 }

		
		public void move() {
			 x = x + deltaX;
			 

			 if ((x < 0) && (deltaX < 0)) {
				 deltaX = -deltaX;
				 x = 0;
			 }
			 else if ((x + width > marginWidth) && (deltaX > 0)) {
				 deltaX = -deltaX;
				 x = marginWidth - width;
			 }
			 			 }
			 
		}
	
	public class BouncingPath extends MovingPath {

		 
		public BouncingPath(int dx, int dy) {
			deltaX = dx;
			deltaY = dy;
		 }

		
		public void move() {
			 x = x + deltaX;
			 y = y + deltaY;

			 if ((x < 0) && (deltaX < 0)) {
				 deltaX = -deltaX;
				 x = 0;
			 }
			 else if ((x + width > marginWidth) && (deltaX > 0)) {
				 deltaX = -deltaX;
				 x = marginWidth - width;
			 }
			 if ((y< 0) && (deltaY < 0)) {
				 deltaY = -deltaY;
				 y = 0;
			 }
			 else if((y + height > marginHeight) && (deltaY > 0)) {
				 deltaY = -deltaY;
				 y = marginHeight - height;
			 }
		}
	}
}
