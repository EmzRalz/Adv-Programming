import java.util.LinkedList;

public class RunMe {
	
	/**
	 * In RunMe segments are added to the RailwayModel
	 * which adds them to its linked list. The list but not
	 * the model is passed to the other classes
	 */
	
	public static void main(String [] args) {
	
	
	RailwayModel railRoad = new RailwayModel();
	
	Station fundee = new Station("Fundee",300,4);
	Track a = new Track(600);
	Station broughty = new Station("Broughty Ferry",460, 2);
	Track b = new Track(600);
	Station theToon = new Station("Arbroath", 400,3);
	
	railRoad.addSegment(fundee);
	railRoad.addSegment(a);
	railRoad.addSegment(broughty);
	railRoad.addSegment(b);
	railRoad.addSegment(theToon);
	
	//the railRoad LinkedList is passed to the RailwayReport and TrainCreator
	RailwayReporter rr = new RailwayReporter(railRoad.getList());
	TrainCreator tc = new TrainCreator(railRoad.getList());
	
	//reporter initialises the thread that prints the train journey
	//through the stations
	Thread reporter = new Thread(rr);
	reporter.start();
	
	//The train creator thread is initialised to start making trains
	Thread trains = new Thread(tc);
	trains.start();

}
	
	}

