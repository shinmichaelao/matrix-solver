/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
/**
 *
 * @author Michael
 */
//dialog for entering in equations based off of Hovercraft Full of Eels' example on http://stackoverflow.com/questions/6988317/dynamically-add-components-to-a-jdialog

public class MatrixDialog extends JDialog{
    private java.util.List<JTextField> fields = new ArrayList();
    private JButton addNewFieldBtn = new JButton("Add New Field");
    private JButton submitBtn = new JButton("Submit");
    private JPanel centerPanel = new JPanel(new GridBagLayout());
    private int gridY = 0;

    public MatrixDialog() {
      //formatting magic
      this.setTitle("Equations");
      GridBagConstraints gbc = createGBC(0, gridY);
      centerPanel.add(new JLabel("Input your equations: "), gbc);
      gridY++;
      
      addNewFieldBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            addNewFieldAction(e);
         }
      });
      
      submitBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            submitAction(e);
         }
      });
      
      JPanel bottomPanel = new JPanel();
      bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
      JPanel addNewFieldPanel = new JPanel(new GridLayout(1, 0));
      addNewFieldPanel.add(addNewFieldBtn);
      addNewFieldPanel.add(new JLabel());
      JPanel submitPanel = new JPanel(new BorderLayout());
      submitPanel.add(submitBtn);
      bottomPanel.add(addNewFieldPanel);
      bottomPanel.add(Box.createVerticalStrut(5));
      bottomPanel.add(submitPanel);

      this.setLayout(new BorderLayout());
      this.add(centerPanel, BorderLayout.CENTER);
      this.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    //makes a new textfield to add another equation
    private void addNewFieldAction(ActionEvent e) {
      GridBagConstraints gbc = createGBC(0, gridY);
      centerPanel.add(new JLabel("Input Equation:"), gbc);
      gbc = createGBC(1, gridY);
      fields.add(new JTextField(10));
      centerPanel.add(fields.get(fields.size()-1), gbc);     
      gridY++;

      Window win = SwingUtilities.getWindowAncestor(addNewFieldBtn);
      if (win != null) {
         win.pack();
         win.setLocationRelativeTo(null);
      }
    }
    
    //turns the fields into a matrix and sends it to MatrixGUI class
    private void submitAction(ActionEvent e){
        if(this.fields.isEmpty()){
            this.setVisible(false);
            return;
        }
        java.util.List<Row> rows = new ArrayList<>();
        try{
            for(JTextField field: fields){
                rows.add(new Row(field.getText()));
            }
            MatrixGUI.storedMatrix = new Matrix(rows);
            MatrixGUI.solveText = "";
            MatrixGUI.solvable = true;
            reset();
            this.setVisible(false);
        }catch (Exception d){
            //lol don't do anything
        }
    }
    
    //Resets the dialog box for inputting new equations
    public void reset(){
        fields = new ArrayList();
        centerPanel.removeAll();
        gridY = 0;
        
        GridBagConstraints gbc = createGBC(0, gridY);
        centerPanel.add(new JLabel("Input your equations: "), gbc);
        gridY++;
        
    }
    
    //some more formatting magic
    private GridBagConstraints createGBC(int x, int y) {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      gbc.anchor = (x == 0) ? gbc.LINE_START : gbc.LINE_END;
      gbc.fill = (x == 0) ? gbc.BOTH : gbc.HORIZONTAL;
      gbc.insets = (x == 0) ? new Insets(5, 0, 5, 5) : new Insets(5, 5, 5, 0);
      return gbc;
   }
    
}
