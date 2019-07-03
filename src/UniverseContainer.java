import java.util.*;

public class UniverseContainer {
	private ArrayList<Universe> universes;
	private int uCount;
	
	public UniverseContainer() {
		universes = new ArrayList<Universe>();
		uCount = 0;
	}
	public void addUniverse(Universe u) {
		universes.add(u);
		uCount ++;
	}
	public int getUCount() {
		return uCount;
	}
	public ArrayList<Universe> getUniverses(){
		return universes;
	}
}
