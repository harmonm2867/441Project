import java.util.Scanner;
import java.util.List; 
import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;
public class GridMover {
    private static int currentRow = 3; // Player starting pt. 
    private static int currentCol = 5; 
    private static GridVisualizer visualizer; // Updates Visualizer
    private static SerialPort port = SerialPort.getCommPort("COM7");
    
    
    public static void main(String[] args) {
    	
    	Grid grid = new Grid();
    	Scanner scanner = new Scanner (System.in);    	
    	BoundaryManager boundaryManager = grid.getBoundaryManager();    	
    	Pathfinder pathfinder = new Pathfinder(grid.getGridArray(), boundaryManager);
    	
    	String imagePath = "src/gameboardFlip.png"; // Path
        visualizer = new GridVisualizer(grid, boundaryManager, imagePath);
       
        System.out.println("Initial Position: " + grid.getCurrentPosition());
        visualizer.updatePosition(currentRow, currentCol); // Start at initial position, prev coords
        port.openPort();
        port.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);     
        //We found a star
     
        int targetRow = 0;
        int targetCol = 0; 
        
        int [][]gridArray = grid.getGridArray(); 
       
   /*     List<int[]> path = pathfinder.findPath(currentRow, currentCol, targetRow, targetCol);
  //Logic of pathfinder 
        if (path != null) {
            for (int[] step : path) {
                int row = step[0];
                int col = step[1];
             
                visualizer.updatePosition(row, col);// Update the player's position step by step along the path   
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No valid path found");
        }       
 */      
  //Logic of not pathfinder      
        while (true) {
            System.out.print("Enter coordinates to move (row,col) or 'exit' to quit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length != 2) {
                System.out.println("Invalid input. Please enter in the format 'row,col'.");
                continue;
            }
            try {
                targetRow = Integer.parseInt(parts[0].trim());
                targetCol = Integer.parseInt(parts[1].trim());

                if (grid.isBlocked(targetRow, targetCol)) {
                    System.out.println("Cannot move to (" + targetRow + ", " + targetCol + "). Blocked!");
                } else {
                    List<int[]> path = pathfinder.findPath(currentRow, currentCol, targetRow, targetCol);
                    if (path != null) {
                        for (int[] step : path) {
                            int row = step[0];
                            int col = step[1];

                            // Move player in the grid and update the visualization
                            if (grid.moveTo(row, col)) {
                                visualizer.updatePosition(row, col);
                                try {
                                    Thread.sleep(700); // Delay for visual effect
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //send data to arduino
                       
                    	//PrintWriter out = new PrintWriter(port.getOutputStream(),true);
                        for(int[] step : path) {
                        	String row = String.valueOf(step[0]);
                        	String col = String.valueOf(step[1]);
                        	String message = "<";
                        	message = message.concat(row);
                        	message = message.concat(",");
                        	message = message.concat(col);
                        	message = message.concat(">");
                        	//out.print(message);
                        	System.out.println(message);
                        	byte[] writeByte = new byte[message.length()];
                        	for(int i = 0; i <message.length(); i++) {
                        		writeByte[i] = (byte)(message.charAt(i));
                        	}
                        	int bytesTxed = port.writeBytes(writeByte, message.length());
                        	System.out.println("Byes Transmitted: " + bytesTxed);
                        	
                        	//out.flush();
                        }
                       // out.print("<a>");
                        //out.flush();
                        byte[] endByte = {'<','a','>'};
                    	port.writeBytes(endByte, 3);
                        //port.closePort();
                        
                        currentRow = targetRow; // Update current position after movement
                        currentCol = targetCol;
                    } else {
                        System.out.println("No valid path found to (" + targetRow + ", " + targetCol + ").");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid numbers. Please enter valid integer coordinates.");
            }        
        }
        port.closePort();
        visualizer.dispose(); // Dispose of visualizer
        scanner.close(); // Close scanner when done
    }
    private static void displayPath(int targetRow, int targetCol) {
        System.out.println("Path to (" + targetRow + ", " + targetCol + "):");

        while (currentRow != targetRow || currentCol != targetCol) {
            System.out.println("(" + currentRow + ", " + currentCol + ")"); // Print the current position
            visualizer.updatePosition(currentRow, currentCol);   // Highlight the current position on the visualizer
            
            try {
                Thread.sleep(700);  // Delay; can adjust later. Does it matter? 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }           
            if (currentRow < targetRow) currentRow++;
            else if (currentRow > targetRow) currentRow--;
            if (currentCol < targetCol) currentCol++;
            else if (currentCol > targetCol) currentCol--;
        }
        System.out.println("Reached destination: (" + targetRow + ", " + targetCol + ")"); // Final position update and print
        visualizer.updatePosition(currentRow, currentCol);
    }
}
