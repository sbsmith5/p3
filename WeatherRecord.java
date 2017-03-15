/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          Program2
// FILE:             GameApp.java
//
// TEAM:    (Team 17, NEED TEAM NAME)
// Author1: (Sidney Smith,sbsmith5@wisc.edu,sbsmith5,001)
// Author2: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
//
// ---------------- OTHER ASSISTANCE CREDITS
// Persons: N/A
//
// Online sources:N/A
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * readings stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{
    // TODO declare data structures required
	String[] lineFile1;
	String[] lineFile2;
	private int station;
	int date;
	//double[] readings;
	private ArrayList<Double> readings;
	

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
    }
	
	/**
	 * This comparator should first compare the stations associated with the given FileLines. If 
	 * they are the same, then the dates should be compared. 
	 */
    private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			// TODO implement compare() functionality
			//split the first line in order to access station and date information
			String[] lineFile1 = l1.getString().split(",");
			String[] lineFile2 = l2.getString().split(",");
			
			//assign station information in order to compare
			Integer station1 = Integer.parseInt(lineFile1[0]);
			Integer station2 = Integer.parseInt(lineFile2[0]);
			
			//check to see if the stations are the same. If so, return the compared dates.
			if (station1.equals(station2)){
				Integer date1 = Integer.parseInt(lineFile1[1]);
				Integer date2 = Integer.parseInt(lineFile2[1]);
				
				return date1.compareTo(date2);
			}
			else{
				return station1.compareTo(station2);
			}
		}
		
		public boolean equals(Object o) {
			return this.equals(o);
		}
    }
    
	/**
	 * This method should simply create and return a new instance of the WeatherLineComparator
	 * class.
	 */
    public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
    }
	
	/**
	 * This method should fill each entry in the data structure containing
	 * the readings with Double.MIN_VALUE
	 */
    public void clear() {
		// TODO initialize/reset data members
    	for (int i=0; i<readings.size(); i++)
    		readings.set(i, Double.MIN_VALUE);
    }

	/**
	 * This method should parse the String associated with the given FileLine to get the station, date, and reading
	 * contained therein. Then, in the data structure holding each reading, the entry with index equal to the parameter 
	 * FileLine's index should be set to the value of the reading. Also, so that
	 * this method will handle merging when this WeatherRecord is empty, the station and date associated with this
	 * WeatherRecord should be set to the station and date values which were similarly parsed.
	 */
    public void join(FileLine li) {
		// TODO implement join() functionality
    	
    	//create an array of the strings in FileLine li
    	String[] line = li.getString().split(",");
    	
    	//assign the station and date
    	this.station = Integer.parseInt(line[0]);
    	this.date = Integer.parseInt(line[1]);
    	
    	//add the readings to the weather record for this station and date
    	for (int i = 2; i<line.length; i++)
    		this.readings.add(Double.parseDouble(line[i]));
    	
    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		// TODO
		
		return null;
    }
}
