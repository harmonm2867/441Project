import javax.swing.*;
import java.awt.*;

public class GridVisualizer extends JFrame {
    private static final int GRID_SIZE = 36;
    private JPanel[][] gridPanels;
    private Grid grid;
    private JLabel background;
    private ImageIcon gameboardIcon;  // Add an image variable
    private BoundaryManager boundaryManager; //boundary manager ...

    public GridVisualizer(Grid grid, BoundaryManager boundaryManager, String imagePath) {
        this.grid = grid;
        this.boundaryManager = boundaryManager; //assn the boundary man
        this.gridPanels = new JPanel[GRID_SIZE][GRID_SIZE];
      
        gridPanels = new JPanel[grid.getRows()][grid.getCols()];  // Initialize the panel array
        
        // Load the image
        gameboardIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(1138, 1138, Image.SCALE_SMOOTH));
        initializeGrid();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  // Make sure frame is visible
    }
    private void initializeGrid() {	
    	//initial settings for grid
    	setUndecorated(true);
        setSize(1000, 1000);
        setResizable(false);

        background = new JLabel(gameboardIcon);
    	background.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
    	add(background);
        setAlwaysOnTop(true);
      
        String[][] displayGrid = grid.convertGridToStringArray(); //Method to handle all that string int shit. 
        for (int i = 0; i < displayGrid.length; i++) {
            for (int j = 0; j < displayGrid[i].length; j++) {
                JLabel label = new JLabel(displayGrid[i][j]);  // Display string value
                label.setHorizontalAlignment(JLabel.CENTER);
               // gridPanels[i][j].add(label);  // Add to the panel
            }
        }
        
        
        //create 36x36 grid
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // create square for grid
                JLabel imageLabel = new JLabel();
                imageLabel.setText("(" + row + "," + col + ")");
                imageLabel.setHorizontalTextPosition(JLabel.CENTER);
                imageLabel.setVerticalTextPosition(JLabel.CENTER);
                imageLabel.setFont(new Font("Arial", Font.PLAIN, 8));
                panel.setBackground(null);
                panel.setOpaque(false);

                // Add the image label to the panel
                panel.add(imageLabel);

                gridPanels[row][col] = panel;
                background.add(panel);
            }
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public void updatePosition(int row, int col) {
        // Run the update on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Reset all cells to the default state
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    gridPanels[i][j].setBackground(null);
                    gridPanels[i][j].setOpaque(false);
                    
                    if (boundaryManager.isBlocked(i, j)) {
                        gridPanels[i][j].setBackground(Color.GRAY);  // Blocked tiles in gray
                        gridPanels[i][j].setOpaque(true);
                    }
                }
            }           
            gridPanels[row][col].setOpaque(true);
            gridPanels[row][col].setBackground(Color.RED);

            // Ensure the visual changes are applied
            this.revalidate();
            this.repaint();
        });
    }    
}