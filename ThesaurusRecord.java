/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p3
// FILE:             ThesaurusRecord.java
//
// TEAM:    Team 17
// Authors:
// Author1: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author2: (Sidney Smith,sbsmith5@wisc.edu,sbsmith5,001)
// Author3: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
//
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 */

public class ThesaurusRecord extends Record{
	private String key;					//word to find synonyms
	private ArrayList<String> synonyms = new ArrayList<String>(); //list that contains all synonyms


	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 * @param numFiles - the number of files in the record
	 */
	public ThesaurusRecord(int numFiles) {

		super(numFiles); //this is the constructor; it works by calling the Record constructor that we extended

		clear(); //makes sure all data is cleared before invoking any methods
	}

	/**
	 * This Comparator should simply behave like the default (lexicographic) compareTo() method
	 * for Strings, applied to the portions of the FileLines' Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class will simply return an
	 * instance of this class.
	 * @param l1 - a line in a file to be compared with another line
	 * @param l2 - a line in a file to be compared with the other line
	 * @return l1key.compareTo(l2key) - returns 0, 1, or -1 according to which is more
	 * 		   higher up in the alphabet than the other
	 * 		   0 - if the same
	 *         1 - if l1Key is higher in the alphabet
	 *        -1 - if l2Key is higher in the alphabet
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {

		public int compare(FileLine l1, FileLine l2) {
			String l1Key = l1.getString().split(":")[0];
			String l2Key = l2.getString().split(":")[0];
			return l1Key.compareTo(l2Key);
		}
		/**
		 * This method checks to make sure that the object passed into the method is of the same type
		 * as the class.
		 * @return this.equals(o) - true if is of same type, false if of different type
		 */
		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the ThesaurusLineComparator class.
	 * @return new ThesaurusLineComparator - returns new instance of ThesaurusLineComparator class
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
		}
	}

	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 * @param w - a line in a file, used to compare to current record of synonyms
	 */
	public void join(FileLine w) {
		if (this.isCleared()) {
			key = w.getString().split(":")[0];
			String fileLineString = w.getString().split(":")[1];
			String[] newSynonyms = fileLineString.split(",");
			//adds words all to synonym list
			for (int i = 0; i < newSynonyms.length; i++) synonyms.add(newSynonyms[i]);
		}
		else {
			String fileLineString = w.getString();
			fileLineString = fileLineString.split(":")[1];
			String[] newSynonyms = fileLineString.split(",");

			Arrays.sort(newSynonyms); //sorts array to compare
			Collections.sort(synonyms); //sorts to compare

			//COMPARES EACH WORD TO EACH OTHER AND ADDS IN RIGHT ORDER TO RECORD//
			for (int i = 0; i < newSynonyms.length; i++) {
				for (int j = 0; j < synonyms.size(); ++j) {
					if (newSynonyms[i].compareTo(synonyms.get(j)) == 0) {
						j = 0;
						break; //if the same leaveloop
					}
					else if (newSynonyms[i].compareTo(synonyms.get(j)) > 0) {
						if (j == synonyms.size()-1){ //adds to end if furthest in alphabetical order
							synonyms.add(newSynonyms[i]);
						}
						continue;
					}
					else if (newSynonyms[i].compareTo(synonyms.get(j)) < 0) {
						synonyms.add(synonyms.indexOf(synonyms.get(j)), newSynonyms[i]); //adds at right index
						break;
					}
				}
			}
		}
		Collections.sort(synonyms); //sorts in alpha-numerical order
	}
	/**
	 * Formats the information in the synonyms Arraylist to be writtent output file
	 * @return output - the string to be written to output file
	 */
	public String toString() {
		String output = "";
		output += key + ":";
		for (String s : synonyms)
			output+= s + ",";
		return output;
	}
	/**
	 * Checks if synonyms was cleared of all items stored in it.
	 * @return true if there are no items stored, false if there are items still stored.
	 */
	public boolean isCleared() {
		return (key == null && (synonyms == null || synonyms.size() == 0));
	}
}
