public class BoundaryManager {
    private final boolean[][] boundaries;  // Stores blocked tiles

    public BoundaryManager(int rows, int cols) {
        boundaries = new boolean[rows][cols];  // Initialize boundaries
        initializeWalls();  // Set up boundaries directly
    }  
    public void addBoundary(int row, int col) { // Add a custom boundary at a specific tile
        boundaries[row][col] = true;
    }
    public boolean isBlocked(int row, int col) {
    	return boundaries[row][col];
    }
    void initializeWalls() { //sue me            
        for (int row = 1; row <= 35; row++) {addBoundary(row, 1);}
        for (int col = 2; col <= 4; col++) {
            for (int row = 1; row <= 18; row++) {addBoundary(row, col);}
        }
        for (int col = 5; col <= 34; col++) {
            for (int row = 1; row <= 2; row++) {addBoundary(row, col);}
        }
        for (int col = 32; col <= 35; col++) {
            for (int row = 1; row <= 21; row++) {addBoundary(row, col);}
        }
        for (int col = 9; col <= 14; col++) {
            for (int row = 6; row <= 18; row++) {addBoundary(row, col);}
        }
        for (int col = 16; col <= 17; col++) {
            for (int row = 6; row <= 35; row++) {addBoundary(row, col);}
        }
        for (int col = 18; col <= 22; col++) {
            for (int row = 13; row <= 21; row++) {addBoundary(row, col);}
        }
        for (int col = 18; col <= 22; col++) {
            for (int row = 6; row <= 9; row++) {addBoundary(row, col);}
        }
        for (int col = 26; col <= 31; col++) {
            for (int row = 6; row <= 9; row++) {addBoundary(row, col);}
        }
        for (int col = 26; col <= 31; col++) {
            for (int row = 13; row <= 21; row++) {addBoundary(row, col);}
        }
        for (int col = 4; col <= 6; col++) {
            for (int row = 21; row <= 24; row++) {addBoundary(row, col);}
        }
        for (int col = 6; col <= 9; col++) {
            for (int row = 24; row <= 26; row++) {addBoundary(row, col);}
        }
        for (int col = 24; col <= 24; col++) {
            for (int row = 17; row <= 31; row++) {addBoundary(row, col);}
        }
        for (int col = 11; col <= 13; col++) {
            for (int row = 21; row <= 23; row++) {addBoundary(row, col);}
        }
    }
	public boolean[][] getBoundaries() {
		return boundaries;
	} 

}
