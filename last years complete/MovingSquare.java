//Prince Bhatia 
//pbha310
//contains moving square I created 
import java.awt.*;
public class MovingSquare extends MovingRectangle {
	
		public MovingSquare() {
		this(10, 20, 50, 20, 500, 500, Color.orange, Color.yellow, Path.FALLING); 
	}

	
	public MovingSquare(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, Path pathType) {
		super(x,y,Math.min(h,w),Math.min(h,w),mw,mh,bc,fc,pathType);
	}

		public void setWidth(int w) { width = w; 
	height = w;}
	
	
	public void setHeight(int h) { height = h; 
	width=h;}
	
	

	

	

}
