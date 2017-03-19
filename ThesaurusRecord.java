/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p3
// FILE:             ThesaurusRecord.java
//
// TEAM:    Team 17
// Authors:
// Author1: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author2: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 */

public class ThesaurusRecord extends Record{
    private String key;					//word to find synonyms
    private ArrayList<String> synonyms = new ArrayList<String>();


	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public ThesaurusRecord(int numFiles) {

    	super(numFiles); //this is the constructor; it works by calling the Record constructor that we extended

    	clear();
    }

    /**
	 * This Comparator should simply behave like the default (lexicographic) compareTo() method
	 * for Strings, applied to the portions of the FileLines' Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class will simply return an
	 * instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {

		public int compare(FileLine l1, FileLine l2) {
			String l1Key = l1.getString().split(":")[0];
			String l2Key = l2.getString().split(":")[0];
			return l1Key.compareTo(l2Key);
		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
    }

	/**
	 * This method should simply create and return a new instance of the ThesaurusLineComparator class.
	 */
    public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
    }

	/**
	 * This method should (1) set the word to null and (2) empty the list of synonyms.
	 */
    public void clear() {
		key = null;
		try{
			synonyms.clear();
		}catch(NullPointerException ex){
			//null pointer is caught
		}
    }

	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
    public void join(FileLine w) {
    	if (this.isCleared()) {
    		key = w.getString().split(":")[0];
    		String fileLineString = w.getString().split(":")[1];
    		String[] newSynonyms = fileLineString.split(",");
    		for (int i = 0; i < newSynonyms.length; i++) synonyms.add(newSynonyms[i]);
    	}
    	else {
			String fileLineString = w.getString();
			fileLineString = fileLineString.split(":")[1];
			String[] newSynonyms = fileLineString.split(",");
			for (int i = 0; i < newSynonyms.length; i++) {
				for (String s : synonyms) {
					if (newSynonyms[i].compareTo(s) == 0) break;
					else if (newSynonyms[i].compareTo(s) > 0) continue;
					else if (newSynonyms[i].compareTo(s) < 0) synonyms.add(synonyms.indexOf(s), newSynonyms[i]);
				}
			}
    	}
    	/// this assumes that the method calling join will have already checked that the keys matched ***
    }

	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		String output = "";
		output += key + ":";
		for (String s : synonyms)
			output+= s + ",";
		return output;
	}


    public boolean isCleared() {
    	return (key == null && (synonyms == null || synonyms.size() == 0));
    }
}
