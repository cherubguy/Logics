import java.util.*;

import javax.print.attribute.Size2DSyntax;
public class Universe  {
	private UniverseContainer parentContainer;
	private String universeName;
	private ArrayList<World> worlds;
	private int worldCount;
	private boolean reflex;
	private boolean trans;
	private boolean symm;
	private boolean hereditary;
	
	public Universe() {
		this.worlds = new ArrayList<World>();
		this.worldCount = 0;
	}
	public Universe(UniverseContainer uc) {
		parentContainer = uc;
		int count = uc.getUCount();
		String countStr = Integer.toString(count);
		this.universeName = "";
		this.universeName += 'u' + countStr;
		this.worlds = new ArrayList<World>();
		this.worldCount = 0;
	}
	public Universe(UniverseContainer uc, boolean reflex, boolean trans, boolean symm, boolean hereditary) {
		parentContainer = uc;
		int count = uc.getUCount();
		String countStr = Integer.toString(count);
		this.universeName = "";
		this.universeName += 'u' + countStr;
		this.trans = trans;
		this.reflex = reflex;
		this.symm = symm;
		this.hereditary = hereditary;
		if (this.symm && this.trans) { // symmetry and transitivty makes reflexivity
			this.reflex = true;
		}
		this.worlds = new ArrayList<World>();
		this.worldCount = 0;
	}
	public Universe(UniverseContainer uc, ArrayList<World> worlds, int worldCount, boolean reflex, boolean trans, boolean symm, boolean hereditary) {
		parentContainer = uc;
		int count = uc.getUCount();
		String countStr = Integer.toString(count);
		this.universeName = "";
		this.universeName += 'u' + countStr;
		this.worlds = worlds;
		this.worldCount = worldCount;
		this.trans = trans;
		this.reflex = reflex;
		this.symm = symm;
		this.hereditary = hereditary;
		if (this.symm && this.trans) { // symmetry and transitivty makes reflexivity
			this.reflex = true;
		}
		if (this.reflex && this.symm) {
			this.trans = true;
		}
		
	}
	/*
	 * To clone a universe, i had to use this constructor to create a new list of worlds, and then update the relations for
	 * each world, for which i needed the getWorld function which takes the worlds name, so that i could find a the counter-part to an old world 
	 * in the new universe, to update the new relation accordingly
	 */
	public Universe(Universe u) { // creates a copy of the other universe
		//System.out.println("======= COPYING UNIVERSE =======");
		this.parentContainer = u.parentContainer;
		int count = parentContainer.getUCount();
		String countStr = Integer.toString(count);
		this.universeName = "";
		this.universeName += 'u' + countStr;
		this.worlds = new ArrayList<World>();
		for (World w : u.getWorlds()) {
			World copyWorld = new World(w);
			worlds.add(copyWorld);
			System.out.println("HASHCODES for relations of Universe Copy: ");
			System.out.println("Original hash: " + w.getRelations().hashCode());
			System.out.println("Copy hash: " + copyWorld.getRelations().hashCode());
			//System.out.println("Original world: " + w + " +++ ");
			//System.out.println("Copied world: " + copyWorld);
		}
		/*for (World w : worlds) {
			System.out.println("For world: " + w);
			System.out.println(w.getRelations().size());
			for (Relation r : w.getRelations()) {
				System.out.println("NO#1 CHECKING WORLDS AT RELATIONS");
				
				System.out.println(r);
				System.out.println("Copy: " + w);
				System.out.println("W1 of relation at copy: " + r.getW1());
				System.out.println("W2 of relation at copy: " + r.getW2());
				System.out.println();
			}
		}*/
		//System.out.println("UPDATING RELATIONS");
		
		for(World w1 : worlds) { // have to copy the relations over in a second step, because all the worlds must be instantiated first
			//System.out.println(w1);
			
			for (Relation r : w1.getRelations()) {
				
				//System.out.println(r);
				for (World comparedW : u.getWorlds()) {
					//System.out.println(comparedW);
					if (comparedW == r.getW2()) {
						for (World w2 : worlds) {
							//System.out.println(w2);
							if (w2.getWorldName().equals(comparedW.getWorldName())) {
							
								r.updateApplicableWorlds(w1, w2);
								//System.out.println("updated " + r);
							}
						}
						
					}
				}
			}
		}
		/*for (World w : worlds) {
			for (Relation r : w.getRelations()) {
				System.out.println("NO#2 CHECKING WORLDS AT RELATIONS");
				System.out.println(r);
				System.out.println("Copy: " + w);
				System.out.println("W1 of relation at copy: " + r.getW1());
				System.out.println("W2 of relation at copy: " + r.getW2());
			}
		}*/
		this.worldCount = new Integer (u.getWorldCount());
		this.trans = u.trans;
		this.reflex = u.reflex;
		this.symm = u.symm;
		this.hereditary = u.hereditary;
		if (this.symm && this.trans) { // symmetry and transitivty makes reflexivity
			this.reflex = true;
		}
		if (this.reflex && this.symm) {
			this.trans = true;
		}
		/*
		 * had to make a new copy of the arrayList of worlds so that it wasnt referencing
		 * back to the same arrayList all the time
		 */
	}
	public String toString() {
		return this.universeName + ": " + this.worlds.toString();
		//return "Universe";
	}
	public void addWorld(World newWorld) {
		this.worlds.add(newWorld);
		//System.out.println(this.worldCount);
		this.worldCount ++;
		
		//System.out.println("WORLD COUNT INCREMENTED at u: " + this);
		//System.out.println(this.worldCount);
	}
	public UniverseContainer getContainer() {
		return parentContainer;
	}
	public ArrayList<World> getWorlds(){
		return this.worlds;
	}
	public World getWorld(String worldName) {
		for (World w : worlds) {
			if (w.getWorldName().equals(worldName)) {
				return w;
			}
		}
		World errorWorld = new World();
		System.out.println("ERROR WORLD RETURNED");
		return errorWorld;
	}
	public int getWorldCount() {
		return this.worldCount;
	}
	public boolean getReflex() {
		return this.reflex;
	}
	public boolean getTrans() {
		return this.trans;
	}
	public boolean getSymm() {
		return this.symm;
	}
	public boolean getHereditary() {
		return this.hereditary;
	}
	public void setUniverseAsParent() {
		for (World w : worlds) {
			w.setParentUniverse(this);
		}
	}
}
