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
 */
public class CircuitTracer {

	/** Launch the program. 
	 * 
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		
		//TODO: print out clear usage instructions when there are problems with
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
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		//TODO: parse and validate command line args - first validation provided
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}

		//Validates the first arg as either -s or -q
		if (!args[0].equals("-s") && !args[0].equals("-q")) {
			printUsage();
			return;
		}
		//if it passes the checks, then it will be stored in the variable
		String storageType = args[0];

		//Validates the second arg as either -c or -g
		if (!args[1].equals("-c") && !args[1].equals("-g")) {
			printUsage();
			return;
		}
		//if it passes the checks, then it will be stored in the variable
		String outputType = args[1];
	
		String fileName = args[2]; //store the third arg as the filename

		//TODO: initialize the Storage to use either a stack or queue
		if(storageType.equals("-s")) {
			//stack
			Storage<TraceState> storage = new Storage<TraceState>(Storage.DataStructure.stack);
		} else {
			//queue
			Storage<TraceState> storage = new Storage<TraceState>(Storage.DataStructure.queue);
		}
		
		//TODO: read in the CircuitBoard from the given file
		try {
			CircuitBoard board = new CircuitBoard(fileName);
			//TODO: run the search for best paths
			//THIS IS NOT DONE

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
			return;
		}
		
		//TODO: output results to console or GUI, according to specified choice
		if (outputType.equals("-c")) {
			//console output
		} else {
			System.out.println("GUI output not implemented.");
			return;
		}
	}
	
} // class CircuitTracer
