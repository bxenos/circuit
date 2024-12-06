import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail 
 * @author Brayden Xenos
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O';	//capital 'o', an open position
	private final char CLOSED = 'X';//a blocked position
	private final char TRACE = 'T';	//part of the trace connecting 1 to 2
	private final char START = '1';	//the starting component
	private final char END = '2';	//the ending component
	private final String ALLOWED_CHARS = "OXT12"; //useful for validating with indexOf

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @author Brayden Xenos
	 * 
	 * @param filename file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot open or read the file
	 * @throws InvalidFileFormatException for any file formatting or content issue
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		try (Scanner fileScan = new Scanner(new File(filename))) {

			//instantiation of ROWS, COLS, and board array
			if(!fileScan.hasNextInt()) {//checks if column is an integer
				fileScan.close();
				throw new InvalidFileFormatException("The number of rows must be an integer"); 
			}
			ROWS = fileScan.nextInt();

			if(!fileScan.hasNextInt()) { //checks if row is an integer
				fileScan.close();
				throw new InvalidFileFormatException("The number of columns must be an integer"); 
			}
			COLS = fileScan.nextInt();

			board = new char[ROWS][COLS];

			//these variables are to count the number of start and end points for validation
			int startCount = 0;
			int endCount = 0;
			
			// TODO: parse the given file to populate the char[][] 
			// throw FileNotFoundException if Scanner cannot read the file
			// throw InvalidFileFormatException if any issues are encountered while parsing the file
			for(int row = 0; row < ROWS; row++) { //loop through rows

				for(int col = 0; col < COLS; col++) { //loop through columns
					if(!fileScan.hasNext()) { //checks if there is a next character
						fileScan.close();
						throw new InvalidFileFormatException("Not enough characters in row " + row); 
					}

					char c = fileScan.next().charAt(0); //saves each character in row to c

					if(ALLOWED_CHARS.indexOf(c) == -1) { //this is checking to see if c is in ALLOWED_CHARS
						throw new InvalidFileFormatException("Invalid character '" + c + "' at row " + row + ", col " + col);
					}
					board[row][col] = c; //saves c to board[row][col]

					if(c == START) { //check to see if c is START
						startingPoint = new Point(row, col); //if it is, save the row and col to startingPoint
						startCount++; //increment startCount
						
					} else if(c == END) { //check to see if c is END
						endingPoint = new Point(row, col); //if it is, save the row and col to endingPoint
						endCount++; //increment endCount
					}
				}
				// this is to check for extra elements in the row, if there are any, throw a detailed exception
				if (fileScan.hasNext()) {
					String extraElement = fileScan.nextLine().trim();
					if (!extraElement.isEmpty()) {
						throw new InvalidFileFormatException("Extra elements found in row " + row);
					}
				}
			}
			//This check is to see if there are any extra rows in the file, if there are, throw a detailed exception
			if (fileScan.hasNext()) {
				throw new InvalidFileFormatException("Extra rows found in file");
			}

			if(startCount != 1 || endCount != 1) { //if startCount or endCount are not equal to 1, throw an exception
				throw new InvalidFileFormatException("Board should have 1 starting and end point, found " + startCount + " start points and " + endCount + " end points");
			}
			//close the scanner
			fileScan.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found. Please check the file name and try again.");
		}
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** Utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + " contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
