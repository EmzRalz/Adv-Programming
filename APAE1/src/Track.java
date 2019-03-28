
public class Track extends LineSegment {
	private final static String name = "Track";
	private final static int capacity = 1;
	
	/**
	 * The track class overrrides lineSegment to make it's name and capacity
	 * rigid values, ie the capacity of a track is only 1 and name is track
	 * @param length is passed from initialisation
	 */
	public Track(int length) {
		super(name, length, capacity);
		
}
}