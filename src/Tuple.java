import java.util.*; 
public class Tuple {
	char operator = 'n';
	int index = -1;
	boolean tilda = false;
	Tree node = null;
	ArrayList<Integer> branch_nums = new ArrayList();
	
	public Tuple() {
	}
	public Tuple(Tree node, ArrayList<Integer> branch_nums) {
		this.node = node;
		this.branch_nums = branch_nums;
	}
	public Tuple(char operator, int index, boolean tilda) {
		this.operator = operator;
		this.index = index;
		this.tilda = tilda;
	}
	public String toString() {
		if (node == null) {
			String j = Boolean.toString(this.tilda);
			String i = Integer.toString(this.index);
			String str = "(" + this.operator + ", " + i + ", " + j + ")";
			return str;
		}else {
			return "(" + node.get_data().str + " " + this.branch_nums + ")";
				
		}
	}
}

