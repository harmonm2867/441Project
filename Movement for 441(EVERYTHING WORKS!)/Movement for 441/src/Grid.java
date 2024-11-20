public class Grid {
    private int[][] grid; //grid array
    private int currentRow,currentCol;
    private int rows,cols; 
    private BoundaryManager boundaryManager; //boundary manager. 
 
    public Grid() {
        rows = 36;
        cols = 36;
        grid = new int[rows][cols];   
        boundaryManager = new BoundaryManager(rows, cols); // Initialize BoundaryManager and set boundaries

        fillGrid();        // Fill the grid with coordinates
        initializeBoundaries();
    }
    public BoundaryManager getBoundaryManager() {//checkin something
        return boundaryManager;
    }
    private void fillGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;  // Mark all tiles as open (int type)
               // isBlocked[i][j] = false;  // Default all tiles to passable
            }
        }
    }  
    private void initializeBoundaries() {
        boolean[][] boundaries = boundaryManager.getBoundaries();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boundaries[i][j]) {
                    grid[i][j] = 1; // Mark boundary tiles as blocked
                }
            }
        }
    }
    public boolean isBlocked(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return true; // Treat out-of-bounds as blocked
        }
        return grid[row][col] == 1;
    }
    public boolean moveTo(int row, int col) {
        if (isBlocked(row, col)) {
            System.out.println("Cannot move to (" + row + ", " + col + "). Blocked!");
            return false; // Move is denied
        }
        // Clear current position
        grid[currentRow][currentCol] = 0; // Set current position to empty
        // Update to the new position
        currentRow = row;
        currentCol = col;
        grid[currentRow][currentCol] = 1; // Mark new position as blocked
        return true;
    }    
    public int getCurrentPosition() {
        return grid[currentRow][currentCol];
    }
    public int[][] getGridArray() {
        return this.grid;
    }
    public String[][] convertGridToStringArray() { //The method here that works on all that String int shiz feeling cute may delete later. 
        String[][] stringGrid = new String[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                stringGrid[i][j] = String.valueOf(grid[i][j]); // Convert int to String
            }
        }
        return stringGrid;
    }
	public int getRows() {//WHAT ARE THESE FOR?!
		return grid.length;
	}
	public int getCols() {//I NEED SLEEP
		return grid[0].length;
	}	
}