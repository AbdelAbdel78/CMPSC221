/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author acv
 * coauthor @abdel
 */
public class DrawingApplicationFrame extends JFrame {

    private final GridLayout gridLayout = new GridLayout(2, 1);
    private final JPanel line1 = new JPanel();
    private final JPanel line2 = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JLabel shapeLabel = new JLabel("Shape:");
    private final String[] shapeoptions = {"Line", "Oval", "Rectangle"};
    private final JComboBox<String> shapeChooser = new JComboBox<>(shapeoptions);
    private final JButton firstColorButton = new JButton("1st Color...");
    private final JButton secColorButton = new JButton("2nd Color...");
    private Color firstColor = Color.BLACK;
    private Color secColor = Color.WHITE;
    private final JButton undo = new JButton("Undo");
    private final JButton clear = new JButton("Clear");
    private final JLabel optionsLabel = new JLabel("Options:");
    private final JCheckBox fill = new JCheckBox("Filled");
    private final JCheckBox grad = new JCheckBox("Use Gradient");
    private final JCheckBox dash = new JCheckBox("Dashed");
    private final JLabel lineWidthLabel = new JLabel("Line Width:");
    private final JSpinner lineWidth = new JSpinner();
    private final JLabel dashLengthLabel = new JLabel("Dash Length:");
    private final JSpinner dashLength = new JSpinner();
    private DrawPanel drawPanel = new DrawPanel();
    private final JLabel mousePos = new JLabel("( , )");
    static ArrayList<MyShapes> itemsDrawn = new ArrayList<>();
    
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame() {
        super("Java 2D Drawings");
        
        // firstLine widgets
        line1.setBackground(Color.CYAN);
        line1.add(shapeLabel);
        line1.add(shapeChooser);
        line1.add(firstColorButton);
        line1.add(secColorButton);
        line1.add(undo);
        line1.add(clear);
        
        // secondLine widgets
        line2.setBackground(Color.CYAN);
        line2.add(optionsLabel);
        line2.add(fill);
        line2.add(grad);
        line2.add(dash);
        line2.add(lineWidthLabel);
        lineWidth.setValue(10);
        line2.add(lineWidth);
        line2.add(dashLengthLabel);
        line2.add(dashLength);
        
        // add top panel of two panels
        topPanel.setLayout(gridLayout);
        topPanel.add(line1);
        topPanel.add(line2);
        
        // add topPanel to North, drawPanel to Center, and statusLabel to South
        add(topPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setBackground(Color.WHITE);
        add(mousePos, BorderLayout.SOUTH);
        
        //add listeners and event handlers
        ButtonHandler buttonHandler = new ButtonHandler();
        firstColorButton.addActionListener(buttonHandler);
        secColorButton.addActionListener(buttonHandler);
        undo.addActionListener(buttonHandler);
        clear.addActionListener(buttonHandler);
    }

    // Create event handlers, if needed
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == firstColorButton) {
                firstColor = JColorChooser.showDialog(DrawingApplicationFrame.this, "Pick your color", firstColor);
                if (firstColor == null) { firstColor = Color.BLACK; }
//                firstColorButton.setBackground(firstColor);
            }
            else if (event.getSource() == secColorButton) {
                secColor = JColorChooser.showDialog(DrawingApplicationFrame.this, "Pick your color", secColor);
                if (secColor == null) { secColor = Color.WHITE; }
//                secColorButton.setBackground(secColor);
            }
            else if (event.getSource() == undo) {
                itemsDrawn.remove(itemsDrawn.size()-1);
                drawPanel.repaint();
            }
            else if (event.getSource() == clear) {
                itemsDrawn.removeAll(itemsDrawn);
                drawPanel.repaint();
            }
        }
    }
    
    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel {

        public DrawPanel() {
            MouseHandler mouseHandler = new MouseHandler();
            this.addMouseListener(mouseHandler);
            this.addMouseMotionListener(mouseHandler);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            //loop through and draw each shape in the shapes arraylist
            for (int i = 0; i < itemsDrawn.size(); i++) {
                itemsDrawn.get(i).draw(g2d);
            }
            g2d.dispose();
        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener {
            
            private MyShapes drawing;
            private Point startPoint;
            private Point endPoint;
            private Paint paint;
            private Stroke stroke;

            public void mousePressed(MouseEvent event) {
                startPoint = new Point(event.getX(), event.getY());
                
                if (grad.isSelected()) {
                    paint = new GradientPaint(0, 0, firstColor, 50, 50, secColor, true);
                } else {
                    paint = firstColor;
                }
                
                if (dash.isSelected()) {
                    stroke = new BasicStroke((Integer)lineWidth.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, new float[]{(Integer)dashLength.getValue()}, 0);
                } else {
                    stroke = new BasicStroke((Integer)lineWidth.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                }
                
                String selectedShape = (String)shapeChooser.getSelectedItem();
                if ("Line".equals(selectedShape)) {
                    drawing = new MyLine(startPoint, startPoint, paint, stroke);
                } else if ("Oval".equals(selectedShape)) {
                    drawing = new MyOval(startPoint, startPoint, paint, stroke, fill.isSelected());
                } else if ("Rectangle".equals(selectedShape)) {
                    drawing = new MyRectangle(startPoint, startPoint, paint, stroke, fill.isSelected());
                }
                itemsDrawn.add(drawing);
            }

            public void mouseReleased(MouseEvent event) {
                endPoint = new Point(event.getX(), event.getY());
                drawing.setEndPoint(endPoint);
                drawPanel.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                endPoint = new Point(event.getX(), event.getY());
                drawing.setEndPoint(endPoint);
                itemsDrawn.add(drawing);
                
                String position = "(" + event.getX() + "," + event.getY() + ")";
                mousePos.setText(position);
                
                drawPanel.repaint();
                itemsDrawn.remove(drawing);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                String position = "(" + event.getX() + "," + event.getY() + ")";
                mousePos.setText(position);
            }
        }
    }
}