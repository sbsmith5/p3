/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          p3
// FILE:             Reducer.java
//
// TEAM:    Team 17
// Authors: 
// Author1: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author2: 
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file. 
 *
 */
public class Reducer {
    // list of files for stocking the PQ
    private List<FileIterator> fileList;
    private String type,dirName,outFile;
    private Record r;
    private FileLinePriorityQueue priorityq;
    private Comparator<FileLine> cmp;
    FileLine lastAdded;

    public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		
		
		Reducer r = new Reducer(type, dirName, outFile);
		
		r.run();
	
    }

	/**
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 */
    public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
    }

	/**
	 * Carries out the file merging algorithm described in the assignment description. 
	 */
    public void run() {
    	
		File dir = new File(dirName);	//where directory 
		File[] files = dir.listFiles();	//list of input files
		Arrays.sort(files);
		r = null;	//make our record null

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();
		
		for(int i = 0; i < files.length; i++) {
			//System.out.println(files[i].getAbsolutePath()); /*Check if correct files debugging*/
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
				fileList.add(new FileIterator(f.getAbsolutePath(), i + 2)); //i + 2 for use in WeatherRecord
			}
		}

		
		
		//Generate our record
	switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}
	
    
    // get comparator
    cmp = r.getComparator();
    
	//Create files for outputing and writing to memory
	File output;
	FileWriter writer = null;
	
	//create memory writer
	try{
		output = new File(outFile);
		writer = new FileWriter(output);
	}catch(Exception e){
		System.out.println("IOE Exception thrown");
	}
	
	// initialize priorityQueue
		priorityq =  new FileLinePriorityQueue(fileList.size(), cmp);
		
	/* start process of acquiring synonyms
	FileIterator iteratorToUse;
	String stringToUse;
	
	//loop for all files in list
	for(int i=0; i < fileList.size(); i++){
		
		iteratorToUse = fileList.get(i);
		stringToUse = null;	//TODO Change null
		//TODO is this right??
		r.join( new FileLine(/*STRINGstringToUse, iteratorToUse) );
		
		//TODO dont know if this should be here
		writeToFile(writer);
	
	} */ 
	try {
		for (FileIterator iterator : fileList) {
			lastAdded = iterator.next();
			priorityq.insert(lastAdded);
		}
	} 
	catch (PriorityQueueFullException e) {};
	
	while (! priorityq.isEmpty()) {
		try {
			FileLine current = priorityq.removeMin();
			if (r.isCleared()) {
				r.join(current);
				lastAdded = current;
			}
			else {
				if (cmp.compare(current, lastAdded) == 0) {
					r.join(current);
					lastAdded = current;
				}
				else {
					this.writeToFile(writer);
					r.clear();
					r.join(current);
					lastAdded = current;
					if (current.getFileIterator().hasNext()) {
						priorityq.insert(current.getFileIterator().next());
					}
				}
			}
		} catch (PriorityQueueFullException e) {
		} catch (PriorityQueueEmptyException e) {
		};
	}
	
	//Close file
	try {
		writer.close();
	} catch (IOException e) {
		System.out.println("Error when closing file\nFile not closed");
	}
	
    }
    
    /*This method writes to the file output*/
    public void writeToFile(FileWriter writer){
    	
    	try{
    		writer.append(r.toString()+"\n");
    	}catch(IOException e){
    		System.out.println("failed to write to file");
    	}
    	
    }
    
}
