import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class DrawMaze extends JPanel {
   private static final int RECT_X = 20;
   private static final int RECT_Y = RECT_X;
   private static int RECT_WIDTH = 400;
   private static int RECT_HEIGHT = 400;

   private static SolvableMaze maze;

    public DrawMaze(int numCol, int numRows) {
        maze = new SolvableMaze(numCol, numRows);
        maze.setAlgorithm(new BreadthFirstSearch());
        maze.generateMaze();
    }

    public DrawMaze(DrawMaze src) {
        maze = new SolvableMaze(src.maze);
    }

   @Override
   protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        RECT_WIDTH = Math.min(getHeight(), getWidth()) - 40;
        RECT_HEIGHT = RECT_WIDTH;

        Maze.Node[][] board = maze.getBoard();

        int numRows = board.length;
        int numCol = board[0].length;
        
        int sizeOfCell = numRows > 0 && numCol > 0 ? Math.min(RECT_HEIGHT / numRows, RECT_WIDTH / numCol) : 0;

        // depth map
        if (color) {
            ArrayList<Maze.Node> queue = new ArrayList<>();
            Maze.Node start = maze.getStartPoint();
            queue.add(start);
            int red = 255;
            int green = 255;
            int blue = 255;
            ArrayList<Maze.Node> visited = new ArrayList<>();
            visited.add(start);
            int limit = 0;
            while (!queue.isEmpty()) {
                try {
                g.setColor(new Color(Math.abs(red % 256), Math.abs(green % 256), Math.abs(blue % 256)));
                } catch (Exception e) {
                    System.out.println(red);
                }
                Maze.Node prev = queue.get(0);
                for (Maze.Node current : prev.getEdgeSet()) {
                    if (!visited.contains(current)) {
                        g.fillRect(RECT_X + sizeOfCell * current.getX(), RECT_Y + sizeOfCell * current.getY(), sizeOfCell, sizeOfCell);
                        queue.add(current);
                        visited.add(current);
                    }
                }
                queue.remove(prev);

                final int BASE_X = 100;
                final int BASE_Y = 100;
                int BASE = numRows * numCol;
                final int MAX = 8 * Math.max(BASE / (BASE_X * BASE_Y), 1);
                if (limit % MAX == 0) {
                    int a = Math.max((BASE_X * BASE_Y) / (BASE), 1);
                    if (green >= 255 && blue >= 255 && red > 0) {
                        red -= a;  
                    }
                    else if (red >= 255 && green >= 255) {
                        blue += a;
                    }
                    else if (red >= 255 && blue <= 0) {
                        green += a;
                    }
                    else if (red >= 255 && green <= 0) {
                        blue -= a;
                    }
                    else if (green <= 0 && blue >= 255) {
                        red += a;
                    }
                    else if (red <= 0) {
                        green -= a;
                    }
                }
                limit += 1;
            }
        }


        // color search path
        g.setColor(Color.GRAY);
        for (Maze.Node n : maze.getVisited()) {
            g.fillRect(RECT_X + sizeOfCell * n.getX(), RECT_Y + sizeOfCell * n.getY(), sizeOfCell, sizeOfCell);
        }
        
        g.setColor(Color.GREEN);
        for (Maze.Node n: maze.getPath()) {
            g.fillRect(RECT_X + sizeOfCell * n.getX(), RECT_Y + sizeOfCell * n.getY(), sizeOfCell, sizeOfCell);
        }
    
        g.setColor(Color.BLACK);
    
        // draw maze
        if (walls) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCol; j++) {
                    int startX = RECT_X + sizeOfCell*j;
                    int startY = RECT_Y + sizeOfCell*i;

                    // the horizontal edges
                    if (i < numRows - 1 && !board[i][j].hasEdge(board[i+1][j])) {
                        g.drawLine(startX, startY + sizeOfCell, startX + sizeOfCell, startY + sizeOfCell);
                    }
                    
                    // the vertical edges 
                    if (j < numCol - 1 && !board[i][j].hasEdge(board[i][j+1])) {
                        g.drawLine(startX + sizeOfCell, startY, startX + sizeOfCell, startY + sizeOfCell);     
                    }
                }
            }
        }

        // draw the rectangle here
        g.drawRect(RECT_X, RECT_Y, sizeOfCell * numCol, sizeOfCell * numRows);
   }

   @Override
   public Dimension getPreferredSize() {
      // so that our GUI is big enough
      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
   }

   // actual methods
   /**
     * Set the SearchAlgorithm
     * 
     * @param search  the SearchAlgorithm
     */
    public void setAlgorithm(SearchAlgorithm search) {        
        maze.setAlgorithm(search);
    }
    
    /**
     * Set the end point
     * 
     * @param x  the x-position
     * @param y  the y-position
     * @throws IllegalArgumentException if x or y are out of range
     */
    public void setEndPoint(int x, int y) {
       maze.setEndPoint(x,y);
    }

    /**
     * Verify if the Maze is in a completed state
     * 
     * @return  true if solved else false
     */
    public boolean isSolved() {
        return maze.isSolved();
    }
    
    /**
     * Reset the Maze to its base state
     */
    public void reset() {
        maze.reset();
    }
    
    /**
     * Set the start point
     * 
     * @param x  the x-position
     * @param y  the y-position
     * @throws IllegalArgumentException if x or y are out of range
     */
    public void setStartPoint(int x, int y) {
        maze.setStartPoint(x,y);
    }
    
    /**
     * Update the Maze state
     * 
     * @throws  IllegalStateException if a parameter is not set, or if the Maze is solved
     */
    public void update() {
        maze.update();
    }

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
      DrawMaze mainPanel = new DrawMaze(START_X, START_Y);
      mainPanel.setStartPoint(0, 0);
      mainPanel.setEndPoint(START_X-1,START_Y-1);
      

      frame = new JFrame("DrawMaze");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(mainPanel);

      JButton button1 = new JButton("Reset");
      frame.add(button1);
      button1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            pause = true;
            mainPanel.reset();
            mainPanel.repaint();
        }
      });

      JButton button2 = new JButton("Start");
      frame.add(button2);
      button2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (pause) {
                pause = false;
                mainPanel.start();
            } else {
                pause = true;
            }
        }  
      });



      JButton button3 = new JButton("Step");
      frame.add(button3);
      button3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (!mainPanel.isSolved()) {
                mainPanel.update();
                mainPanel.repaint();
            }  
        }
      });

      JButton button4 = new JButton("Skip");
      frame.add(button4);
      button4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            while (!mainPanel.isSolved()) {
                mainPanel.update();
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
                mainPanel.setAlgorithm(getAlgorithm(algName));
                mainPanel.reset();
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
                walls = cb.isSelected();
                mainPanel.repaint();
            }
          });

        JCheckBox box2 = new JCheckBox("Color", false);
        frame.add(box2);
        box2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JCheckBox cb = (JCheckBox) evt.getSource();
                color = cb.isSelected();
                mainPanel.repaint();
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
                .addComponent(mainPanel))
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
                    .addComponent(mainPanel)))
        );

      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
      mainPanel.reset();
   }

   public void solve() {
        maze.reset();
        start();    
    }

    public void start() {
        pause = false;
        Timer timer = new Timer(40, new ActionListener() {
            int MAX = 40 * ((maze.getCols() * maze.getRows()) / (100 * 100)) + ((maze.getCols() * maze.getRows()) % 10000) / (2 * 10 * 10);
            @Override
            public void actionPerformed(ActionEvent evt) {
                int loop = 0;        
                while((!maze.isSolved() && loop++ < (MAX > 0 ? MAX : 1)) && !pause) {
                    maze.update();
                }
                repaint();
                if (maze.isSolved() || pause) {
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
                maze.resize(Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()));
                maze.generateMaze();
                maze.reset();
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
}