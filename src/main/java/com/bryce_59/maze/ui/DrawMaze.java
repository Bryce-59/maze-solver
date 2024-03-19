package com.bryce_59.maze.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.*;

import com.bryce_59.maze.create.*;
import com.bryce_59.maze.solve.*;

public class DrawMaze extends JPanel {
   private static final int RECT_X = 20;
   private static final int RECT_Y = RECT_X;
   private static int RECT_WIDTH = 400;
   private static int RECT_HEIGHT = 400;

   // create the GUI explicitly on the Swing event thread
   private static void createAndShowGui() {
      int START_X = 10;
      int START_Y = 10;

      // establish top row
      JLabel label1 = new JLabel("Height x Width: ");
      textField1 = new JTextField("10");
      JLabel label2 = new JLabel("x: ");
      textField2 = new JTextField("10");

      textField1.setPreferredSize( new Dimension( 50, 24 ) );
      textField2.setPreferredSize( new Dimension( 50, 24 ) );

      JButton submit = new JButton("Submit");
      submit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
              submitButtonActionPerformed(evt);
          }
      });

      START_X = Integer.parseInt(textField1.getText());
      START_Y = Integer.parseInt(textField2.getText());
      solvable = new SolvableMazeGraphic(START_X, START_Y);
      solvable.setStartPoint(0, 0);
      solvable.setEndPoint(START_X-1,START_Y-1);
      solvable.setAlgorithm(new BreadthFirstSearch());
      
      frame = new JFrame("DrawMaze");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(solvable);

      JButton button1 = new JButton("Reset");
      frame.add(button1);
      button1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            pause = true;
            solvable.reset();
            solvable.repaint();
        }
      });

      JButton button2 = new JButton("Start");
      frame.add(button2);
      button2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (pause) {
                pause = false;
                start();
            } else {
                pause = true;
            }
        }  
      });   

      JButton button3 = new JButton("Step");
      frame.add(button3);
      button3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (!solvable.isSolved()) {
                solvable.update();
                solvable.repaint();
            }  
        }
      });

      JButton button4 = new JButton("Skip");
      frame.add(button4);
      button4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            while (!solvable.isSolved()) {
                solvable.update();
            }
        }
      });

      String[] algorithms_u = { "DFS (Greedy)", "BFS (UCS)", "A* Search" };
      String[] algorithms_w = { "DFS", "BFS", "Greedy", "UCS", "A*" };
      JComboBox<String> algList = new JComboBox<>(algorithms_u);
        algList.setSelectedIndex(1);
        algList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JComboBox<String> cb = (JComboBox) evt.getSource();
                String algName = (String)cb.getSelectedItem();
                solvable.setAlgorithm(getAlgorithm(algName));
                solvable.reset();
                pause = true;
            }

            public static SearchAlgorithm getAlgorithm(String algName) {
                if (algName.contains("DFS")) {
                    return new DepthFirstSearch();
                } else if (algName.contains("BFS")) {
                    return new BreadthFirstSearch();
                }
                else if (algName.contains("A*")) {
                    return new AStarSearch();
                }
                return null;
            }
        });

        JCheckBox box1 = new JCheckBox("Walls", true);
        frame.add(box1);
        box1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JCheckBox cb = (JCheckBox) evt.getSource();
                solvable.toggleWalls(cb.isSelected());
                solvable.repaint();
            }
          });

        JCheckBox box2 = new JCheckBox("Color", false);
        frame.add(box2);
        box2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JCheckBox cb = (JCheckBox) evt.getSource();
                solvable.toggleColor(cb.isSelected());
                solvable.repaint();
            }
          });
      

      GroupLayout layout = new GroupLayout(frame.getContentPane());
      frame.getContentPane().setLayout(layout);

      layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(submit))
            .addGroup(layout.createSequentialGroup()
                .addComponent(button1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button4)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(box1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(box2))
            .addGroup(layout.createSequentialGroup()
                .addComponent(algList))
            .addGroup(layout.createSequentialGroup()
                .addComponent(solvable))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label1)
                    .addComponent(textField1)
                    .addComponent(label2)
                    .addComponent(textField2)
                    .addComponent(submit))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(algList))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(button1)
                    .addComponent(button2)
                    .addComponent(button3)
                    .addComponent(button4)
                    .addComponent(box1)
                    .addComponent(box2))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(solvable)))
        );

      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
      solvable.reset();
   }

   public static void solve() {
    solvable.reset();
        start();    
    }

    public static void start() {
        pause = false;
        Timer timer = new Timer(40, new ActionListener() {
            int MAX = 40 * ((solvable.getCols() * solvable.getRows()) / (100 * 100)) + ((solvable.getCols() * solvable.getRows()) % 10000) / (2 * 10 * 10);
            @Override
            public void actionPerformed(ActionEvent evt) {
                int loop = 0;        
                while((!solvable.isSolved() && loop++ < (MAX > 0 ? MAX : 1)) && !pause) {
                    solvable.update();
                }
                frame.repaint();
                if (solvable.isSolved() || pause) {
                    ((Timer)evt.getSource()).stop();
                }

            }
        });

        timer.start();
    }

    public static void submitButtonActionPerformed(ActionEvent evt) {
        int height = -1;
        try {
            height = Integer.parseInt(textField1.getText());
        } catch (NumberFormatException err) {}
        
        int width = -1;
        try {
            width = Integer.parseInt(textField2.getText());
        } catch (NumberFormatException err) {}
        
        if (height < 0 || width < 0) {
            textField1.setText(height < 0 ? "#ERR" : textField1.getText());
            textField2.setText(width < 0 ? "#ERR" : textField2.getText());
        }
        else {
            boolean inRange = true;            
            if (inRange) {
                solvable.resize  (Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()));
                solvable.generateMaze();
                solvable.setStartPoint(0,0);
                solvable.setEndPoint(solvable.getCols()-1, solvable.getRows()-1);
                solvable.reset();
            }
        }
        frame.repaint();
    }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }

   static JFrame frame;
   static boolean pause = true;
   static boolean color = false;
   static boolean walls = true;

   static JTextField textField1, textField2;
   static SolvableMazeGraphic solvable;
}