/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p3
// FILE:             Reducer.java
//
// TEAM:    Team 17
// Author1: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author2: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
// Author3: (Roberto O'Dogherty, rodogherty@wisc.edu, o-dogherty, 001)
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file.
 */
public class Reducer {
	// list of files for stocking the PQ
	private List<FileIterator> fileList;
	//type of file, directory name, and name of output file
	private String type,dirName,outFile;
	//a record of all data associated with a line
	private Record r;
	//queue to store data
	private FileLinePriorityQueue priorityq;
	//checks priority according to key
	private Comparator<FileLine> cmp;
	//the last added item to priority queue
	private FileLine lastAdded;

	/**
	 * The main class takes in the command line arguments, checks to make sure they fit the program,
	 * and runs the program.
	 * @param command line arguments should follow format:
	 * 			java Reducer weather <directory> <output_file>
	 *			java Reducer thesaurus <directory> <output_file>
	 *
	 */
	public static void main(String[] args) {
		if (args.length != 3) { //if command-line arguments are not added correctly
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1); //stop the program
		}
		//assigns command-line arguments with all types
		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		//creates instance of reducer to merge data from files
		Reducer r = new Reducer(type, dirName, outFile);

		//start program
		r.run();


	}

	/**
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 * @param type - either type weather or type thesaurus
	 * @param dirName - the name of the directory
	 * @param outFile - name of the output file
	 */
	public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
	}

	/**
	 * Carries out the file merging algorithm described in the assignment description.
	 * The algorithm:
	 * 		1. Take one entry from each input file and insert them into the queue.
	 *		2. Create an empty record r.
	 * 		3. While the queue is nonempty: remove the minimum entry e from the queue
	 * 		   and compare its key to the key associated with r. If they are the same
	 *         (or r is empty), merge e with r. Otherwise, write r to the output file,
	 *         clear r, and then merge e with r. Either way, take the next entry e'
	 *         from the same file from which e came and insert it into the queue
	 *         (again, if this file has been exhausted, do nothing).
	 * 	    4. Write r to the output file (this step is necessary because otherwise
	 * 		   the last record would never get written).
	 */
	public void run() {

		File dir = new File(dirName);	//where directory
		File[] files = dir.listFiles();	//list of input files
		Arrays.sort(files);
		r = null;	//make our record null

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();

		for(int i = 0; i < files.length; i++) {
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
				fileList.add(new FileIterator(f.getAbsolutePath(), i + 2)); //i + 2 for use in WeatherRecord
			}
		}
		//Generate our record
		switch (type) {
		case "weather":
			//creates weather record according to size of the list
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			//creates thesaurus record according to size of list
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
			break;
		}
		// get comparator
		cmp = r.getComparator();

		//Create files for outputing and writing to memory
		File output;
		FileWriter writer = null;

		//create memory writer
		try{
			output = new File(outFile); //makes an output file
			writer = new FileWriter(output); //to write to output file
		}catch(Exception e){
			System.out.println("IOE Exception thrown");
		}

		// initialize priorityQueue
		priorityq =  new FileLinePriorityQueue(fileList.size(), cmp);



		try {
			//for every file in the list, tries to add to priority queue
			for (FileIterator iterator : fileList) {
				if (iterator.hasNext()) {
					lastAdded = iterator.next(); // line in file to be added
					priorityq.insert(lastAdded);
				}
			}
		}
		//if full, cannot be added
		catch (PriorityQueueFullException e) {
			e.printStackTrace();
		}
		//INFO TO BE WRITTEN TO OUTPUT FILE
		while (!priorityq.isEmpty()) {
			try {
				//remove last entry to be added to output file
				FileLine current = priorityq.removeMin();
				if (r.isCleared()) { //if record is empty
					//parses content according to the type of data into correct format + adds
					r.join(current);
					lastAdded = current;
				}
				else {
					//if they are equal, sorts in order
					if (cmp.compare(current, lastAdded) == 0) {
						r.join(current);
						lastAdded = current;

					}
					else {
						//if not equal, clears the record to add new data
						this.writeToFile(writer); //writes to outputfile what was in record
						r.clear(); //removes all data from record
						r.join(current); //adds current data to record
						lastAdded = current;
					}
					if (priorityq.isEmpty()) { //to add last line to writer
						this.writeToFile(writer);
					}
				}
				if (current.getFileIterator().hasNext()) {
					//inserts current item into priority queue and updates the iterator
					priorityq.insert(current.getFileIterator().next());
				}

			} catch (PriorityQueueFullException e) {
				e.printStackTrace();
			} catch (PriorityQueueEmptyException e) {
				e.printStackTrace();
			}
		}
		//Close file
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Error when closing file\nFile not closed");
		}

	}
	/**
	 * This method writes to the output file.
	 * @param writer - the FileWriter that writes the information into the output file
	 */
	public void writeToFile(FileWriter writer){

		try{
			writer.append(r.toString().substring(0,r.toString().length()-1)+"\n"); //writes data into output file
		}catch(IOException e){
			System.out.println("failed to write to file");
		}

	}

}
