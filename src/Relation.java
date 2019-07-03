
public class Relation {
	private World w1;
	private World w2;
	private World[] worlds;
	private boolean w1Receiving; // a field to determine which way the relation points
	private boolean w2Receiving;
	
	public Relation() {
		
	}
	public Relation(World w1, World w2, boolean w1Receiving, boolean w2Receiving) {
		this.w1 = w1;
		this.w2 = w2;
		this.worlds = new World[]{w1, w2};
		this.w1Receiving = w1Receiving;
		this.w2Receiving = w2Receiving;
	}
	/*public Relation(Relation r) {
		this(new World(r.w1), new World(r.w2), r.w1Receiving, r.w2Receiving);
		this.worlds = new World[] {w1, w2};
		
		
	}*/
	public Relation(Relation r) {
		this.w1 = r.getW1();
		this.w2 = r.getW2();
		this.worlds = new World[]{w1, w2};
		this.w1Receiving = r.getW1Receiving();
		this.w2Receiving = r.getW2Receiving();
	}
	public World getW1() {
		return this.w1;
	}
	public World getW2() {
		return this.w2;
	}
	public World[] getWorlds() {
		return this.worlds;
	}
	public boolean getW1Receiving() {
		return this.w1Receiving;
	}
	public boolean getW2Receiving() {
		return this.w2Receiving;
	}
	public void updateApplicableWorlds(World w1, World w2) {
		this.w1 = w1;
		this.w2 = w2;
		this.worlds = new World[]{w1, w2};
	}
	public String toString() {
		if (w1Receiving && w2Receiving) {
			return this.w1 + " < -- > " + this.w2;
		}else if (w1Receiving && !w2Receiving) {
			return this.w1 + " < -- " + this.w2;
		}else if (!w1Receiving && w2Receiving) {
			return this.w1 + " -- > " + this.w2;
		}else {
			return "no relation!";
		}
	}
}
