import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LineSegment {
	private String name;
	private int length;
	private int capacity;
	protected ArrayBlockingQueue <String> trainQueue;
	private ReentrantLock nextSeg = new ReentrantLock();
	private Condition enoughRoom = nextSeg.newCondition();

	/**
	 * This is the superclass for creating railRoads, it requires each segment to have the following:
	 * @param name, stores the name of the segment
	 * @param length, stores the length of the segment
	 * @param capacity, stores how many trains this segment can hold
	 */

	public LineSegment(String name, int length, int capacity) {
		this.name = name;
		this.length = length;
		this.capacity = capacity;
		trainQueue = new ArrayBlockingQueue<String>(capacity);

	}

	/**
	 * addTrain will check the capacity of the train queue, if it
	 * is equal to or less than 0, then the thread will have to wait.
	 * If train is removed from this queue or the is enough room in the first place
	 * then the thread can progress to adding its id.
	 * @param train, is the id passed from a Train object.
	 */

	public void addTrain(String train) {
		//	
		try {
			nextSeg.lock();

			if(trainQueue.remainingCapacity() <= 0) {
				//	System.out.println();
				enoughRoom.await(); }
		} catch (InterruptedException e) {
			System.out.print("The train queue has been interrupted");} finally {

				trainQueue.add(train);
				nextSeg.unlock();
			}}


	/**
	 * Takes an int to calculate and return the time it would take to
	 * travel the segment
	 * @param speed should be passed from the Train
	 * @return travel time to the Train thread
	 */

	public int getTravelTime(int speed) {
		//locks added to ensure that the correct speed is returned
		nextSeg.lock();
		int travelTime = length/speed * 1000;
		nextSeg.unlock();
		return travelTime;


	}

	/**
	 * method to return a String of the track and its occupants to the caller
	 * @return String of track name and train ids
	 */
	public String stringSegment() {

		String trainList = trainQueue.toString();
		String occupancyOfSegment ="|=== " + name + " " + trainList + "===|";

		return occupancyOfSegment;

	}

	/**
	 * Method to remove a train id from the segment, it will send a signal once
	 * this action is performed, to let the first waiting thread check the capacity of
	 * the queue as it will have changed as a result.
	 * @param train
	 */
	public void removeTrain(String train) {

		nextSeg.lock();
		trainQueue.remove(train); 
		enoughRoom.signal();
		nextSeg.unlock();

	}
	
}