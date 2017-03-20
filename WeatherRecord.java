/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          Program2
// FILE:             GameApp.java
//
// TEAM:    (Team 17, NEED TEAM NAME)
// Author1: (Sidney Smith,sbsmith5@wisc.edu,sbsmith5,001)
// Author2: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author3: (Roberto O'Dogherty,rodogherty@wisc.edu,o-dogherty,001)
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
	FileLine li; //line of text in a file
	private int station; //station number of a line of weather data
	private int date; //the date of a line of weather data
	double[] readings; // the readings in a line of weather data

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 * @param numFiles - the number of files in the record
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles); // again, this calls the parent constructor
		readings = new double[6];
		clear(); //make sure no data is stored before invoking any methods
	}

	/**
	 * This comparator should first compare the stations associated with the given FileLines. If
	 * they are the same, then the dates should be compared.
	 * @param l1 - a line of data from file to be parsed and compared to
	 * @param l2 - a line of data from file to be parsed and compared to
	 * @return date1.compareTo(date2) - compares which date is earlier
	 * 		    0 - if they are the same date
	 * 			1 - if date1 is earlier than date2
	 * 		   -1 - if date 2 is earlier than date1
	 * @return station1.compareTo(station2) - compares the two stations
	 * 			0 - if stations are the same
	 * 			1 - if station1 is greater than station2
	 * 		   -1 - if station2 is greather than station1
	 */
	private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
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

		/**
		 * this  method checks if the object passed in is of the same type as the class
		 * @return this.equals(o) - true if they are of same type, false if not
		 */
		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the WeatherLineComparator
	 * class.
	 * @return new WeatherLineComparator() - returns new instance of the WeatherLineComparator
	 */
	public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
	}

	/**
	 * This method should fill each entry in the data structure containing
	 * the readings with Double.MIN_VALUE
	 * @throws NullPointerException - if index i is negative or greater than the length of readings
	 */
	public void clear() {
		try{
			for (int i=0; i < readings.length ; i++)
				readings[i] = Double.MIN_VALUE;
		}catch(NullPointerException e){
			//nullPointerException is caught
		}

	}

	/**
	 * This method should parse the String associated with the given FileLine to get the station, date, and reading
	 * contained therein. Then, in the data structure holding each reading, the entry with index equal to the parameter
	 * FileLine's index should be set to the value of the reading. Also, so that
	 * this method will handle merging when this WeatherRecord is empty, the station and date associated with this
	 * WeatherRecord should be set to the station and date values which were similarly parsed.
	 * @param li - line in file to be parsed, compared and added to readings
	 */
	public void join(FileLine li) {

		//create an array of the strings in FileLine li
		String[] line = li.getString().split(",");

		if (!isCleared()) {
			this.li = li;
			this.station = Integer.parseInt(line[0]);
			this.date = Integer.parseInt(line[1]);
		}
		// index is saved in the iterator, set this index to the data
		readings[li.getFileIterator().getIndex()] = Double.parseDouble(line[2]) ;


	}

	/**
	 * This method formats the information of weather data
	 * @return output + "0" - the output to be written into output file
	 */
	public String toString() {
		String output = "";

		output+= station + ","+ date +",";	//ensure date and station are not added as "-"

		for (int i = 2; i < readings.length; i++) {
			if (readings[i] == Double.MIN_VALUE) output+= "-";
			else output+= readings[i];
			if (! (i == readings.length - 1) ) output+= ",";
		}

		return output + "0";
	}
	/**
	 * This method checks to see if all information was cleared out of station, date, and readings variables
	 * @return station == 0 && date == 0 && readings.length == 0 - true if none have any data stored inside, false
	 * if data is stored inside any of these variables
	 */
	public boolean isCleared() {
		return (station == 0 && date == 0 && readings.length == 0);
	}
	/**
	 * This method returns the station number of the weather data
	 * @return station - returns the station number of the weather data
	 */
	public int getStation() {
		return station;
	}
	/**
	 * This method returns the date of the weather data
	 * @return date - returns the date of the weather data
	 */
	public int getDate() {
		return date;
	}
}
