import java.util.*;

public class Pathfinder {
    private final int[][] grid; // Reference to the game grid
    private final BoundaryManager boundaryManager; // For checking blocked tiles

    public Pathfinder(int[][] grid, BoundaryManager boundaryManager) {
        this.grid = grid;
        this.boundaryManager = boundaryManager;
    }

    // Breadth-First Search to find the shortest path
    public List<int[]> findPath(int startRow, int startCol, int targetRow, int targetCol) {
    	//System.out.println("Start: (" + startRow + ", " + startCol + ")");
    //	System.out.println("Target: (" + targetRow + ", " + targetCol + ")");
    	//System.out.println("Is target blocked? " + boundaryManager.isBlocked(targetRow, targetCol));

    	 System.out.println("Start: (" + startRow + ", " + startCol + ")");
    	 System.out.println("Target: (" + targetRow + ", " + targetCol + ")");
        
        // Directions for UP, DOWN, LEFT, RIGHT movements
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        boolean[][] visited = new boolean[grid.length][grid[0].length]; // Track visited tiles
        Queue<List<int[]>> queue = new LinkedList<>(); // Store possible paths

        // Initialize with the starting position
        List<int[]> initialPath = new ArrayList<>();
        initialPath.add(new int[] {startRow, startCol});
        queue.add(initialPath);

        visited[startRow][startCol] = true; // Mark start as visited

        // BFS loop to explore the grid
        while (!queue.isEmpty()) {
            List<int[]> path = queue.poll();
            int[] current = path.get(path.size() - 1);

            System.out.println("Currently at: (" + current[0] + ", " + current[1] + ")");
            if (current[0] == targetRow && current[1] == targetCol) {
                System.out.println("Reached the target!");
                return path;
            }

            // Explore neighbors in all directions (UP, DOWN, LEFT, RIGHT)
            for (int[] direction : directions) { // Explore neighbors (UP, DOWN, LEFT, RIGHT)
                int newRow = current[0] + direction[0];
                int newCol = current[1] + direction[1];

                // **Add this bounds check here:**
                if (newRow >= 0 && newRow < grid.length &&
                    newCol >= 0 && newCol < grid[0].length) {
                    
                    // Valid index: Check if move is valid
                    if (isValidMove(newRow, newCol, visited)) {
                        visited[newRow][newCol] = true; // Mark as visited

                        List<int[]> newPath = new ArrayList<>(path); // Create new path
                        newPath.add(new int[]{newRow, newCol}); // Add neighbor to path
                        queue.add(newPath); // Add new path to queue
                    }
                } else {
                    System.out.println("Invalid index: (" + newRow + ", " + newCol + ")");
                }
            }
        }
        
        System.out.println("No valid path found");
        return null;
    }

    
    private boolean isValidMove(int row, int col, boolean[][] visited) {//validate if a move is within bounds and not blocked
    	return row >= 0 && row < grid.length &&
    	           col >= 0 && col < grid[0].length &&
    	           !boundaryManager.isBlocked(row, col) &&
    	           !visited[row][col];
    }
}
