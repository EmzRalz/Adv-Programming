import java.util.LinkedList;

public class RailwayReporter implements Runnable {
	private LinkedList <LineSegment>segment;
	
	/**
	 * Railway Reporter has a run method that iterates over the railway
	 * segments and prints out what occupants each segments have.
	 * It will sleep after it has printed out the entirety of the array for 1 second
	 * @param segment is the LinkedList of track segments
	 */
	
	public RailwayReporter(LinkedList <LineSegment>segment) {
		this.segment = segment;
	}
	
	public void run() {
	
		//starts an infinite loop of printing
		while(true){
			
			//to print everything in one line, I am concatenating the strings produced from each segment
			String y = "";
		for(LineSegment x : segment) {
			y += x.stringSegment();
			
		}
		//prints the resulting String
		System.out.println(y);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Railway reporter interrupted");
			e.printStackTrace();
		} 
	}
}}

