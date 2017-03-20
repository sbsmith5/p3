/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p3
// FILE:             FileLinePriorityQueue
//
// TEAM:    Team 17
// Author1: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author2: (Sidney Smith,sbsmith5@wisc.edu,sbsmith5,001)
// Author3: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
//
// ---------------- OTHER ASSISTANCE CREDITS
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Arrays;
import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method.
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
	private FileLine[] queue; //the priority queue to be used
	private Comparator<FileLine> cmp; //the comparator that chooses the order of items
	private int maxSize; //the maximum size of the queue
	private int numItems; //number of items in queue

	/**
	 * The constructor initializes the comparator, the maximum size of the queue, initializes the queue according
	 * to the maximum size, and sets the number of items in the queue.
	 *@param initalsize - the start size of the queue
	 *@param cmp - the comparator used to compare the priority of each item
	 */
	public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		queue = new FileLine[maxSize];
		numItems = 0;
	}
	/**
	 * This method removes and returns last-priority item in the queue.
	 * @throws PriorityQueueEmptyException - if there are no items stored in queue
	 * @return temp - the FileLine object of minimum priority that is removed
	 */
	public FileLine removeMin() throws PriorityQueueEmptyException {

		if (numItems <= 0) throw new PriorityQueueEmptyException();
		int i = 0; // removing index 0
		FileLine temp = queue[0]; // return initial item
		while (i + 1 < queue.length && i+1 < numItems) {
			queue[i] = queue[i+1]; // overwrite current value with next
			i++; // increment to next item
		}
		--numItems;
		queue[numItems] = null; // remove duplicated item at end of list that was not overwritten
		return temp;
	}
	/**
	 * This method inserts a FileLine object into the queue in the correct position according to its
	 * priority and updates the number of items in teh queue
	 * @param fl - the FileLine to be inserted into the queue
	 * @throws PriorityQueueFullException - if the queue is full
	 */
	public void insert(FileLine fl) throws PriorityQueueFullException {
		if (numItems >= maxSize) throw new PriorityQueueFullException(); //if it's full
		FileLine temp;
		if (numItems == 0) {
			queue[0] = fl;
			++numItems;
			return;
		}

		// have to add in order
		for (int i = 0; i < numItems; ++i) {
			if (cmp.compare(queue[i], fl) > 0) {
				// is more alphabetical, must add fl and then shift down
				temp = queue[i];
				queue[i] = fl;
				fl = temp;
			}
		}

		// to add final value from for loop
		queue[numItems] = fl;

		++numItems; // increment number of items in queue
	}
	/**
	 * Checks if there are any items stored in the queue.
	 * @return true if no items in queue, false if there are still items in queue
	 */
	public boolean isEmpty() {
		return (numItems == 0);
	}

}
