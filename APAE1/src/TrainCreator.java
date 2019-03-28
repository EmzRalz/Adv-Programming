import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class TrainCreator implements Runnable {
	private LinkedList <LineSegment>segment;

	/**
	 * This class creates train objects and sets the train on the first element of
	 * LinkedList it has been passed. Each element of the linked list contains
	 * an ArrayBlockingQueue that holds the String Id of the Train. I decided to
	 * make it hold strings to be more general than specifically Train objects.
	 *
	 */
	public TrainCreator(LinkedList <LineSegment>segment) {
		this.segment = segment;

	}
	
	public void run(){
		//Setting variable references outside the while loop
		int i = 1;
		Train n;
		
		Random random = new Random();
		Random sleeper = new Random();
		//the while loop will start an infinite loop of train making
		while(true) {
			
				String id = String.valueOf(i);
				//there is a boolean in Train class will set the speed depending if the int is 1 or 0
				// based on what the random generator passes it;
				n = new Train(id,random.nextInt(2), segment);
				//adds the train to the first segment, but checks there is room first and puts the thread
				// to wait until there is
				segment.getFirst().addTrain(id);
				//creates a new train thread
				Thread train = new Thread(n);
				//initialises the thread
				train.start();
				i++;
			
			try {
				Thread.sleep(sleeper.nextInt(10000));
			} catch (InterruptedException e) {
				System.out.println("waiting to add more trains");
				e.printStackTrace();
			}
		}}}









