
public class Station extends LineSegment {

	private int length;
	
	/**
	 * sub class of LineSegment that overrides the getTravelTime method to 
	 * add the 5 seconds required for the passengers to exit the train
	 * @param name
	 * @param length
	 * @param capacity
	 */
	public Station(String name, int length, int capacity) {
		super(name, length, capacity);
		this.length = length;
		
	}
	@Override
	public int getTravelTime(int speed) {
		//1 second equals 1000
		//5 seconds for exit time added
			int travelTime = ((length/speed) + 5) * 1000;
			return travelTime;
			
	}
}
