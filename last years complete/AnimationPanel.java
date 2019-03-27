//Prince Bhatia 
//pbha310

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class AnimationPanel extends JComponent implements Runnable {
	private static final int MAX_COUNT = 5;
    private Thread animationThread = null;    
    private ArrayList<MovingShape> shapes  = new ArrayList<MovingShape>();;
    private int currentWidth=100, currentHeight=50, currentShapeType; 
    private Path currentPath=Path.BOUNCING;                 
    private Color currentBorderColor = Color.orange;  
    private Color currentFillColor = Color.yellow;  
    private int delay = 30;         
    private int count =  0;
    JPopupMenu popup;                

    public AnimationPanel() {
            
            Insets insets = getInsets();
            int marginWidth = getWidth() - insets.left - insets.right;
            int marginHeight = getHeight() - insets.top - insets.bottom;
            popup = new JPopupMenu(); 
            makePopupMenu();
                
            addMouseListener( new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }
                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }
                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
                public void mouseClicked( MouseEvent e ) {
                    if (animationThread != null) {   
                        boolean found = false;
                        for (MovingShape newshape: shapes) {
                    			if (newshape.contains( e.getPoint())){
                                    found = true;
                    				newshape.setSelected(! newshape.isSelected());
                    				
                    			}
                    		}
                        if (!found) createNewShape(e.getX(), e.getY());
                            
                	}
                }

            });
        }

   
    protected void createNewShape(int x, int y) {
       
        Insets insets = getInsets();
        int marginWidth = getWidth() - insets.left - insets.right;
        int marginHeight = getHeight() - insets.top - insets.bottom;
        
        switch (currentShapeType) {
            case 0: {
                shapes.add(new MovingRectangle(x, y, currentWidth,	currentHeight, marginWidth, marginHeight, currentBorderColor, currentFillColor, currentPath));
                break;
            }
            case 1: {
                shapes.add(new MovingSquare(x, y, currentWidth,	currentHeight, marginWidth, marginHeight, currentBorderColor, currentFillColor, currentPath));
                break;
                
            }
            case 2: {
                shapes.add(new MovingOval(x, y, currentWidth,	currentHeight, marginWidth, marginHeight, currentBorderColor, currentFillColor, currentPath));
                break;
                
            }
            case 3: {
                shapes.add(new MovingFace(x, y, currentWidth,	currentHeight, marginWidth, marginHeight, currentBorderColor, currentFillColor, currentPath));
                break;
            }
        }
        count += 1;
    }

    
    public void setCurrentShapeType(int s) {
        currentShapeType = s;
    }

   
    public void setCurrentPathType(int index) {
        currentPath = Path.values()[index];
 		for (MovingShape newshape: shapes)
            if (newshape.isSelected()){
                newshape.setPath(currentPath);
            }
        }
        
    

	
	public int getCurrentWidth() {
		return currentWidth;
	}

	
	public int getCurrentHeight() {
		return currentHeight;
	}

	
	public void setCurrentWidth(int w) {
		currentWidth = w;
        for (MovingShape newshape:shapes)
		
			if ( newshape.isSelected()){
				newshape.setWidth(currentWidth);
            }
        }
	

	
	public void setCurrentHeight(int h) {
		currentHeight = h;
		for (MovingShape newshape:shapes)
			if ( newshape.isSelected()){
				newshape.setHeight(currentHeight);
                }
        }
        
	


	public Color getCurrentBorderColor() {
		return currentBorderColor;
	}

	
	public Color getCurrentFillColor() {
		return currentFillColor;
	}	
	
	
	public void setCurrentBorderColor(Color bc) {
		currentBorderColor = bc;
		for (MovingShape newshape:shapes)
			if ( newshape.isSelected()){
				newshape.setBorderColor(currentBorderColor);
                }
        }
	

   
    public void setCurrentFillColor(Color fc) {
        currentFillColor = fc;
        //complete this
		for (MovingShape newshape:shapes){
			if ( newshape.isSelected()){
				newshape.setFillColor(currentFillColor);
                }
       } 
    }


    public void resetMarginSize() {
        Insets insets = getInsets();
        int marginWidth = getWidth() - insets.left - insets.right;
        int marginHeight = getHeight() - insets.top - insets.bottom ;
		for (MovingShape newshape:shapes)
			newshape.setMarginSize(marginWidth,marginHeight );
    }


    public void clearAllShapes() {
        count = 0;
    }


    public void update(Graphics g){
        paint(g);
    }


    public void paintComponent(Graphics g) {
        for (MovingShape newshape:shapes) {
            newshape.move();
		    newshape.draw(g);
		}
    }

 
    protected void makePopupMenu() {
        JMenuItem menuItem;
  
        menuItem = new JMenuItem("Clear All");
        menuItem.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllShapes();
            }
        });
        popup.add(menuItem);
     }

    public void adjustSpeed(int newValue) {
        if (animationThread != null) {
            stop();
            delay = newValue;
            start();
        }
    }

    public void start() {
        animationThread = new Thread(this);
        animationThread.start();
    }

    public void stop() {
        if (animationThread != null) {
            animationThread = null;
        }
    }

    public void run() {
        Thread myThread = Thread.currentThread();
        while(animationThread==myThread) {
            repaint();
            pause(delay);
        }
    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep((long)milliseconds);
        } catch(InterruptedException ie) {}
    }
}

