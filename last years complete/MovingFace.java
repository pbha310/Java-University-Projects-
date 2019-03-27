//Prince Bhatia 
//pbha310
// contains my emoji which I created 
import java.awt.*;
public class MovingFace extends MovingOval {
	
	
	public MovingFace() {
		super(10, 20, 50, 20, 500, 500, Color.orange, Color.yellow, Path.FALLING); // the default properties
	}
	
	public void setWidth(int w) { width = w; 
	height = w;}
	
	
	public void setHeight(int h) { height = h; 
	width=h;}

	
	public MovingFace(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, Path pathType) {
		super(x,y,Math.min(h,w),Math.min(h,w),mw,mh,bc,fc,pathType);
	}


		public void draw(Graphics g) {
			super.draw(g);
			Graphics2D g2d = (Graphics2D) g;     
			g2d.fillArc((x + width/4), (y + height/2), width/2, height/2, 0, 180); 
			g2d.fillOval ((x + width/4), (y + height/4), width/4 , height/4 ); 
			g2d.fillOval ((x + width/2), (y + height/4), width/4, height/4); 
			drawHandles(g);
			}
	
	public boolean contains(Point mousePt) {
		double dx, dy;
		Point EndPt = new Point(x + width, y + height);
		dx = (2 * mousePt.x - x - EndPt.x) / (double) width;
		dy = (2 * mousePt.y - y - EndPt.y) / (double) height;
		return dx * dx + dy * dy < 1.0; 
	}

}
