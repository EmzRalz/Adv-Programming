import java.util.LinkedList;
import java.util.ListIterator;

public class Train implements Runnable  {
	private int speed;
	private String id;
	private final int slowSpeed = 10;
	private final int fastSpeed = 500;
	private LinkedList <LineSegment>segment;

	
	/**
	 * The Train object is initialised, and passed the linkedList of tracks and segments
	 *  This class has a run() method to get the train 'moving' along the tracks
	 * 
	 */
	public Train(String id, int speedDecider, LinkedList <LineSegment>segment) {
		this.id = id;
		if(speedDecider == 1) {
			this.speed = slowSpeed;
		} else this.speed = fastSpeed;
		this.segment = segment;
	}

	public String getTrainID() {
		return id;
	}

	public void run() {
		
		for(int i = 0; i < segment.size(); i ++){
			//as the train will need to check the next element of the list,
			//I have created an x variable to symbol the index i+1
			int x = i +1;
			//gets wait period
			int waitTime = segment.get(i).getTravelTime(speed); 

		
			try {
				//the train will sleep at its current stop for the length of time it
				//takes to travel the given element ie track or station it is at
				Thread.sleep(waitTime);
			} catch (InterruptedException e1) {
				System.out.println("Train journey along tracks interrupted");
				e1.printStackTrace();
			}
			
			if(x == segment.size()) {
				//if the train is at the last stop then the loop will break
				break;
			}
			
			//the train thread will then check the next stop, see if it has enough
			//room then adds the train to the next stop once it has room
			(segment.get(x)).addTrain(id);
			//as add train is a two step process the train is removed from the current stop after, 
			//as it is a quicker process, also in real life technically a train can be between a 
			//line and a station 
			(segment.get(i)).removeTrain(id);
	 
		} 
		//the train is then removed from the last stop
		(segment.getLast()).removeTrain(id);
	}
}


