/*
 *  ============================================================================================
 *  A1.java : Extends JFrame and contains a panel where shapes move around on the screen.
 *  Also contains start and stop buttons that starts animation and stops animation respectively.
 *  YOUR UPI:pbha310
 *  ============================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class A1 extends JFrame {
    AnimationPanel panel;  
    JButton startButton, stopButton, borderButton, fillButton; 
    JTextField heightText, widthText;
    JComboBox<ImageIcon> shapesComboBox, pathComboBox;


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new A1();
            }
        });
    }


    public A1() {
        super("Bouncing Application 2017");
        panel = new AnimationPanel();
        add(panel, BorderLayout.CENTER);
        add(setUpToolsPanel(), BorderLayout.NORTH);
        add(setUpButtons(), BorderLayout.SOUTH);
        addComponentListener(
            new ComponentAdapter() { 
                public void componentResized(ComponentEvent componentEvent) {
                    panel.resetMarginSize();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
        setVisible(true);
    }


    public JPanel setUpToolsPanel() {
      
        ImageIcon recangletButtonIcon = createImageIcon("rectangle.gif");
        ImageIcon squareButtonIcon = createImageIcon("square.gif");
        ImageIcon ovalButtonIcon = createImageIcon("oval.gif");
        ImageIcon faceButtonIcon = createImageIcon("face.gif");
        shapesComboBox = new JComboBox<ImageIcon>(new ImageIcon[] {recangletButtonIcon,squareButtonIcon,ovalButtonIcon,faceButtonIcon} );
        shapesComboBox.setToolTipText("Set shape");
        shapesComboBox.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                panel.setCurrentShapeType(shapesComboBox.getSelectedIndex());
            }
        });
      
        ImageIcon bounceButtonIcon = createImageIcon("bounce.gif");
		ImageIcon fallButtonIcon = createImageIcon("fall.gif");
        pathComboBox = new JComboBox<ImageIcon>(new ImageIcon[] {bounceButtonIcon, fallButtonIcon });
        pathComboBox.setToolTipText("Set Path");
        pathComboBox.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                panel.setCurrentPathType(pathComboBox.getSelectedIndex());
            }
        });
		
        heightText = new JTextField("100");
        heightText.setToolTipText("Set Height");
        heightText.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                try {
                    int newValue = Integer.parseInt(heightText.getText());
                    if (newValue > 0) 
                        panel.setCurrentHeight(newValue);
                    else
                        heightText.setText(panel.getCurrentHeight()+"");
                } catch (Exception ex) {
                    heightText.setText(panel.getCurrentHeight()+""); 
                }
            }
        });
        
        widthText = new JTextField("100");
        widthText.setToolTipText("Set Width");
        widthText.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                try {
                    int newValue = Integer.parseInt(widthText.getText());
                    if (newValue > 0) 
                        panel.setCurrentWidth(newValue);
                    else
                        widthText.setText(panel.getCurrentWidth()+"");
                } catch (Exception ex) {
                    widthText.setText(panel.getCurrentWidth()+""); 
                }
            }
        });
       
        fillButton = new JButton("Fill");
        fillButton.setToolTipText("Set Fill Color");
        fillButton.setForeground(panel.getCurrentFillColor());
        fillButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e) {
                Color newColor = JColorChooser.showDialog(panel, "Fill Color", panel.getCurrentFillColor());
                if ( newColor != null) {
                    fillButton.setForeground(newColor);
                    panel.setCurrentFillColor(newColor);
                }
            }
        });
        
        borderButton = new JButton("Border");
        borderButton.setToolTipText("Set Border Color");
        borderButton.setForeground(panel.getCurrentBorderColor());
        borderButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e) {
                Color newColor = JColorChooser.showDialog(panel, "Border Color", panel.getCurrentBorderColor());
                if ( newColor != null) {
                    borderButton.setForeground(newColor);
                    panel.setCurrentBorderColor(newColor);
                }
            }
        });
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
        toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
        toolsPanel.add(shapesComboBox);
        toolsPanel.add(new JLabel(" Path: ", JLabel.RIGHT));
        toolsPanel.add(pathComboBox);
        toolsPanel.add(new JLabel(" Height: ", JLabel.RIGHT));
        toolsPanel.add(heightText);
        toolsPanel.add(new JLabel(" Width: ", JLabel.RIGHT));
        toolsPanel.add(widthText);
        toolsPanel.add(borderButton);
        toolsPanel.add(fillButton);

        return toolsPanel;
    }

    
    public JPanel setUpButtons() {
        JPanel buttonPanel= new JPanel(new FlowLayout());
        
        startButton = new JButton("Start");
        startButton.setToolTipText("Start Animation");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                panel.start();  
            }
        });
       
        stopButton = new JButton("Stop");
        stopButton.setToolTipText("Stop Animation");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(false);
                startButton.setEnabled(true); 
                panel.stop();
             }
        });
        
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 30);
        slider.setToolTipText("Adjust Speed");
        slider.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
             JSlider source = (JSlider)e.getSource();
             if (!source.getValueIsAdjusting()) {
                 int value = (int) (source.getValue());  
                 TitledBorder tb = (TitledBorder) source.getBorder();
                 tb.setTitle("Anim delay = " + String.valueOf(value) + " ms"); 
                 panel.adjustSpeed(value); 
                 source.repaint();
             }
            }
        });
        TitledBorder title = BorderFactory.createTitledBorder("Anim delay = 30 ms");
        slider.setBorder(title);
     
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(slider);
        return buttonPanel;
    }

   
    protected static ImageIcon createImageIcon(String filename) {
        java.net.URL imgURL = A1.class.getResource(filename);
        return new ImageIcon(imgURL);
    }




}

