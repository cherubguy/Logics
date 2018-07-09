import java.util.*;
public class World {
	private ArrayList<World> relations = new ArrayList<World>();
	private ArrayList<String_1> props = new ArrayList<String_1>();
	
	public void World() {
		
	}
	public void World(String_1 prop) {
		this.props.add(prop);
	}
	public void World(ArrayList<String_1> props) {
		this.props.addAll(props);
	}
}
