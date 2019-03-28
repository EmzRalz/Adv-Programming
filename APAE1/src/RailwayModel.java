import java.util.LinkedList;

public class RailwayModel {
	private LinkedList<LineSegment> railRoad = new LinkedList<LineSegment>();
	
	/**
	 * RailwayModel contains the segments added to it in a linkedList and only takes
	 * LineSegment objects
	 */
	
	public RailwayModel() {
		
	}
	
	public void addSegment(LineSegment segment) {
		railRoad.add(segment);
	}
	
	public LinkedList<LineSegment> getList(){
		return railRoad;
	}
	
}
