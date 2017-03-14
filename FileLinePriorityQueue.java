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
    	if (numItems <= 0) throw new PriorityQueueEmptyException(); 
		FileLine temp = queue[0];
		queue[0] = null;
		numItems--;
		return temp;
		
		

		//return null;
    }

    public void insert(FileLine fl) throws PriorityQueueFullException {
		if (numItems == maxSize) throw new PriorityQueueFullException();
		int i = 0;
		while (queue[i] != null) i++;
		queue[i] = fl;
		for (int j = i; j < maxSize; j++) {
			if (cmp.compare(queue[i], queue[j]) < 0) {
				FileLine temp = queue[i];
				queue[i] = queue[j];
				queue[j] = temp;
				i++;
				j++;
			}
			else break;
			}
		}

    public boolean isEmpty() {
		return (numItems == 0);
    }
}
