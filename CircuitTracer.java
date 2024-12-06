import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail 
 * @author Brayden Xenos
 */
public class CircuitTracer {

	/**
	 * Launch the program.
	 * 
	 * @param args three required arguments:
	 *             first arg: -s for stack or -q for queue
	 *             second arg: -c for console output or -g for GUI output
	 *             third arg: input file name
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); // create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {

		// TODO: print out clear usage instructions when there are problems with
		// any command line args
		System.out.println("\n                 ERROR: Invalid command line arguments.");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("Usage: java CircuitTracer -s|-q -c|-g <inputfile>");
		System.out.println("  -s for stack or -q for queue. -c for console or -g for GUI.");
		System.out.println("  <inputfile> is the name of the file to read for the circuit board.");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("Example of valid input: \"java CircuitTracer -s -c grid.dat\"");
		System.out.println("----------------------------------------------------------------------------\n");
	}

	/**
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @author Brayden Xenos
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		// TODO: parse and validate command line args - first validation provided
		if (args.length != 3) {
			printUsage();
			return; // exit the constructor immediately
		}

		// Validates the first arg as either -s or -q
		if (!args[0].equals("-s") && !args[0].equals("-q")) {
			printUsage();
			return;
		}
		// if it passes the checks, then it will be stored in the variable
		String storageType = args[0];

		// Validates the second arg as either -c or -g
		if (!args[1].equals("-c") && !args[1].equals("-g")) {
			printUsage();
			return;
		}
		// if it passes the checks, then it will be stored in the variable
		String outputType = args[1];

		String fileName = args[2]; // store the third arg as the filename

		// TODO: initialize the Storage to use either a stack or queue
		// *PSEUDO-CODE* initialize an empty Storage object called stateStore that
		// stores objects of type TraceState
		Storage<TraceState> stateStore = null;

		if (storageType.equals("-s")) {
			// stack
			stateStore = Storage.getStackInstance();
		} else {
			// queue
			stateStore = Storage.getQueueInstance();
		}

		// TODO: read in the CircuitBoard from the given file
		CircuitBoard board = null;
		try {
			board = new CircuitBoard(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName + " - " + e);
			return;
		} catch (InvalidFileFormatException e) {
			System.out.println("Invalid file format: " + fileName + " - " + e);
			return;
		}

		// TODO: run the search for best paths
		// *PSEUDO-CODE* initialize an empty List called bestPaths that stores objects
		// of type TraceState
		ArrayList<TraceState> bestPaths = new ArrayList<TraceState>();

		// *PSEUDO-CODE* add a new initial TraceState object (a path with one trace) to
		// stateStore for each open position adjacent to the starting component
		// initializing x and y to the starting point of the board
		int x = board.getStartingPoint().x;
		int y = board.getStartingPoint().y;

		// initialize a new TraceState object called tracer
		// TraceState tracer = new TraceState(board, x, y);
		// stateStore.store(tracer);

		// checking each open adjacent posiotion to that starting point "tracer"
		if (board.isOpen(x + 1, y)) { // RIGHT
			TraceState tracer1 = new TraceState(board, x + 1, y);
			stateStore.store(tracer1);
		}
		if (board.isOpen(x - 1, y)) { // LEFT
				TraceState tracer2 = new TraceState(board, x - 1, y);
				stateStore.store(tracer2);
		}
		if (board.isOpen(x, y + 1)) { // DOWN
			TraceState tracer3 = new TraceState(board, x, y + 1);
			stateStore.store(tracer3);
		}
		if (board.isOpen(x, y - 1)) { // UP
			TraceState tracer4 = new TraceState(board, x, y - 1);
			stateStore.store(tracer4);
		}

		// *PSEUDO-CODE* while (!stateStore.isEmpty)
		while (!stateStore.isEmpty()) {

			// *PSEUDO-CODE* retrieve the next TraceState object from stateStore
			TraceState currentTraceState = stateStore.retrieve();

			// *PSEUDO-CODE* if that TraceState object is a solution (ends with a position
			// adjacent to the ending component),
			if (currentTraceState.isSolution()) {

				// *PSEUDO-CODE* if bestPaths is empty or the TraceState object's path is equal
				// in length to one of the TraceStates in bestPaths,
				if (bestPaths.isEmpty() || currentTraceState.pathLength() == bestPaths.get(0).pathLength()) {

					// *PSEUDO-CODE* add it to bestPaths
					bestPaths.add(currentTraceState);

					// *PSEUDO-CODE* else if that TraceState object's path is shorter than the paths
					// in bestPaths,
				} else if (currentTraceState.pathLength() < bestPaths.get(0).pathLength()) {

					// *PSEUDO-CODE* clear bestPaths and add the current TraceState as the new
					// shortest path
					bestPaths.clear();
					bestPaths.add(currentTraceState);
				}
			}

			// *PSEUDO-CODE* else generate all valid next TraceState objects from the
			// current TraceState and add them to stateStore
			else {
				// initializing x and y to the current point of the board
				x = currentTraceState.getRow();
				y = currentTraceState.getCol();
				// checking each open adjacent position again, but this time for the currentTraceState
				if (currentTraceState.isOpen(x + 1, y)) { // RIGHT
					TraceState tracer1 = new TraceState(currentTraceState, x + 1, y);
					stateStore.store(tracer1);
				}
				if (currentTraceState.isOpen(x - 1, y)) { // LEFT
					TraceState tracer2 = new TraceState(currentTraceState, x - 1, y);
					stateStore.store(tracer2);
				}
				if (currentTraceState.isOpen(x, y + 1)) { // DOWN
					TraceState tracer3 = new TraceState(currentTraceState, x, y + 1);
					stateStore.store(tracer3);
				}
				if (currentTraceState.isOpen(x, y - 1)) { // UP
					TraceState tracer4 = new TraceState(currentTraceState, x, y - 1);
					stateStore.store(tracer4);
				}
			}
		}

		// TODO: output results to console or GUI, according to specified choice
		if (outputType.equals("-c")) {
			// console output
			for (TraceState path : bestPaths) {
				System.out.println(path.getBoard().toString());
			}
		} else {
			// Did not attempt extra credit
			System.out.println("GUI output not implemented.");
			return;
		}
	}

} // class CircuitTracer
