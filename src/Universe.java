import java.util.*;
public class Universe {
	private ArrayList<World> worlds = new ArrayList<World>();
	private boolean trans;
	private boolean reflex;
	private boolean symm;
	private boolean hereditary;
	
	public Universe(boolean trans, boolean reflex, boolean symm, boolean hereditary) {
		this.trans = trans;
		this.reflex = reflex;
		this.symm = symm;
		this.hereditary = hereditary;
	}
	public void add_world(World new_world) {
		this.worlds.add(new_world);
	}
}
