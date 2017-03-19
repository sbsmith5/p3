/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p3
// FILE:             FileLinePriorityQueue
//
// TEAM:    we need a team name still guys
// Authors:
// Author1: (Aleysha Becker,ambecker5@wisc.edu,ambecker5,001)
// Author2: (Sidney Smith,sbsmith5@wisc.edu,sbsmith5,001)
// Author3: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
//
// ---------------- OTHER ASSISTANCE CREDITS
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method.
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
    // TODO
	private FileLine[] queue; //
    private Comparator<FileLine> cmp;
    private int maxSize;
    private int numItems; //

    public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		queue = new FileLine[maxSize]; //
		numItems = 0; //

		// TODO
    }

    public FileLine removeMin() throws PriorityQueueEmptyException {
//    	if (numItems <= 0) throw new PriorityQueueEmptyException();
//		FileLine temp = queue[0];
//		queue[0] = null;
//		numItems--;
//		return temp;

    	if (numItems <= 0) throw new PriorityQueueEmptyException();
    	// while not at end of array/number of items
    		// overwrite [0] with one
    		// increment up
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

    public void insert(FileLine fl) throws PriorityQueueFullException {
    	if (numItems >= maxSize) throw new PriorityQueueFullException(); //if it's full
//    	int i = 0;
//    	while (queue[i] != null) i++; //finds where queue is null
//    	queue[i] = fl; //adds passed in FileLine to non-full index
//    	for (int j = i+1; j < numItems; j++) {
//    		//finds where to place the passed in FileLine
//    		if (cmp.compare(queue[i], queue[j]) < 0) {
//    			//places the passed in FileLine in correct position according to ProrityQueue
//    			FileLine temp = queue[i];
//    			queue[i] = queue[j];
//    			queue[j] = temp;
//    			i++;
//    			// j < maxSize with break at queue[j] == null
//    		}
//
//    		else {
//    			numItems++;
//    			break;
//    		}
//    	}
    	FileLine temp;
    	System.out.println("numitems: "+numItems);
    	// adds value if queue is empty
    	if (numItems == 0) {
    		queue[0] = fl;
    		++numItems;
    		//System.out.println("added first value");
    		return;
    	}

    	// have to add in order
    	for (int i = 0; i < numItems; ++i) {
    		if (cmp.compare(queue[i], fl) > 0) {
    			// is more alphabetical, must add fl and then start moving it down
    			temp = queue[i];
    			queue[i] = fl;
    			fl = temp;
    			//System.out.println("compare run on i = "+i);
    		}
    	}
    	
    	// to add final value from for loop
    	queue[numItems] = fl;

    	++numItems; // increment queue
    }

    public boolean isEmpty() {
    	return (numItems == 0);

    }

}
