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
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
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
	
	//start process of acquiring synonyms
	FileIterator iteratorToUse;
	String stringToUse;
	
	//loop for all files in list
	for(int i=0; i < fileList.size(); i++){
		
		iteratorToUse = fileList.get(i);
		stringToUse = null;	//TODO Change null
		//TODO is this right??
		r.join( new FileLine(/*STRING*/stringToUse, iteratorToUse) );
		
		//TODO dont know if this should be here
		writeToFile(writer);
	
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
