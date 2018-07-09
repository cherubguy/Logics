import java.util.*; 
public class Tuple {
	char operator = 'n';
	int index = -1;
	boolean negation = false;
	boolean necessary = false;
	boolean possible = false;
	Tree node = null;
	ArrayList<Integer> branch_nums = new ArrayList();
	
	public Tuple() {
	}
	public Tuple(char operator, int index, boolean tilda) {
		this.operator = operator;
		this.index = index;
		this.negation = tilda;
	}
	public Tuple(char operator, int index, boolean tilda, boolean necessary, boolean possible) {
		this.operator = operator;
		this.index = index;
		this.negation = tilda;
		this.necessary = necessary;
		this.possible = possible;
	}
	public String toString() {
		if (node == null) {
			String j = Boolean.toString(this.negation);
			String i = Integer.toString(this.index);
			String str = "(" + this.operator + ", " + i + ", " + j + ")";
			return str;
		}else {
			return "(" + node.get_data().str + " " + this.branch_nums + ")";
				
		}
	}
}

