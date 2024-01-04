/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author abdel
 */
public class Assignment4 extends JFrame {

    private final JFrame frame = new JFrame();
    
    private final GridLayout gridLayout;
    private final JPanel panel = new JPanel();
    private final JPanel line2 = new JPanel();
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JButton button;
    private final JTextField input;

    public Assignment4() {
        super("Pizza Servings Calculator");
        gridLayout = new GridLayout(4, 1);
        panel.setLayout(gridLayout);
        
        label1 = new JLabel("Pizza Servings Calculator");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.red);
        label1.setFont(new Font("Serif", Font.BOLD, 26));
        label2 = new JLabel("Enter the size of the pizza in inches:");
        label3 = new JLabel("");
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        button = new JButton("Calculate Servings");
        input = new JTextField(4);

        
        inputHandler handler = new inputHandler();
        button.addActionListener(handler);
        
        
        panel.add(label1);
        line2.add(label2);
        line2.add(input);
        panel.add(line2);
        panel.add(line2);
        panel.add(button);
        panel.add(label3);
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setVisible(true);
    }

    private class inputHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            int size = Integer.parseInt(input.getText());
            double servings = Math.pow(size * 1.0 / 8, 2);
            label3.setText(String.format("A %d inch pizza will serve %.2f people.", size, servings));
        }
    }

    public static void main(String[] args) {
        
        new Assignment4();
        
    }
}
