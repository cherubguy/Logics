
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils.*;
import java.util.*; 
public class Tree {
	
	private Tree left, mid, right;
	private String_1 data;
	private ArrayList<Integer> branch_nums = new ArrayList<Integer>() {{
		add(0);
	}}; // find a better way to do this than double braces!

	public Tree(String_1 init_data) {
		left = null;
		mid = null;
		right = null;
		this.data = init_data;
	}
	public Tree() {
		left = null;
		mid = null;
		right = null;
	}
	public void set_data(String_1 data) {
		this.data = data;
	}
	public void set_left(Tree x) {
		left = x;
		x.branch_nums.addAll(this.branch_nums.subList(1, branch_nums.size()));
		x.branch_nums.add(1);
	}
	public void set_mid(Tree x) {
		mid = x;
		x.branch_nums.addAll(this.branch_nums.subList(1, branch_nums.size()));
	}
	public void set_right(Tree x) {
		right = x;
		x.branch_nums.addAll(this.branch_nums.subList(1, branch_nums.size()));
		x.branch_nums.add(2);
	}
	public String_1 get_data() {
		return this.data;
	}
	public Tree get_left() {
		return this.left;
	}
	public Tree get_mid() {
		return this.mid;
	}
	public Tree get_right() {
		return this.right;
	}
	public void add_branch_num(int n) {
		branch_nums.add(n);
	}
	@Override
	public String toString() {
		return this.data.str + " " +  this.branch_nums;
	}
	public void print_tree(Tree node, int lvl) {
		String str = StringUtils.repeat(" ", (lvl * 4)); // repeat is from other library
		System.out.println(str + node.data.str);
		if(node.left != null) {
			System.out.print("(L)");
			print_tree(node.left, lvl + 1);
		}
		if(node.mid != null) {
			System.out.print("(M)");
			print_tree(node.mid, lvl + 1);
		}
		if(node.right != null) {
			System.out.print("(R)");
			print_tree(node.right, lvl + 1);
		}
	}
	public ArrayList<Tree> find_empty(ArrayList<Tree> list_x){
		if(this.left == null & this.mid == null & this.right == null) {	
			list_x.add(this);
		}
		Tree[] child_nodes = {this.left, this.mid, this.right};
		for(int i = 0; i < 3; i ++) {
			if (child_nodes[i] != null){
				list_x = child_nodes[i].find_empty(list_x);
			}
		}
		return list_x;
	}
	public ArrayList<Tree> nodes_and_branches(ArrayList<Tree> list_x){
		list_x.add(this);
		Tree[] child_nodes = {this.left, this.mid, this.right};
		for(int i = 0; i < 3; i ++) {
			if(child_nodes[i] != null) {
				list_x = child_nodes[i].nodes_and_branches(list_x);
			}
		}
		return list_x;
	}
	public ArrayList<Tuple> atoms_and_branches(ArrayList<Tuple> list_x) {
		if(this.data.str.indexOf(' ') == -1) {
			Tuple atom_and_branch = new Tuple(this, this.branch_nums);
			list_x.add(atom_and_branch);
		}
		Tree[] child_nodes = {this.left, this.mid, this.right};
		for(int i = 0; i < 3; i ++) {
			if(child_nodes[i] != null) {
				list_x = child_nodes[i].atoms_and_branches(list_x);
			}
		}
		return list_x;			
	}
	public ArrayList<Tree> find_branch_members(Tree empty_node, ArrayList<Tree> all_nodes){ //starts at bottom node and compares all nodes
		ArrayList<Tree> members = new ArrayList<Tree>();
		
		for (Tree compared_node : all_nodes) {
			if (compared_node.branch_nums.size() <= empty_node.branch_nums.size()) {
				if (compared_node.branch_nums.size() == empty_node.branch_nums.size()) {
					if (compared_node.branch_nums.equals(empty_node.branch_nums)) {
						members.add(compared_node);
					}
				}else if (compared_node.branch_nums.equals(empty_node.branch_nums.subList(0, compared_node.branch_nums.size()))) {
					members.add(compared_node);
				}
			}
		}
		return members;
	}
	public boolean contradictory_branch(Tree empty_node, ArrayList<Tree> all_nodes) {
		
		ArrayList<Tree> members = this.find_branch_members(empty_node, all_nodes);
		
		boolean tilda_1;
		
		System.out.println("Branch members: " + members);
		System.out.println();
		for(Tree individual_atom : members) {
			if (individual_atom.data.str.length() <= 2) {
				System.out.println("Individual atom: " + individual_atom);
				if (individual_atom.data.str.charAt(0) == '~') {
					tilda_1 = true;
				}else {
					tilda_1 = false;
				}
				for(Tree compared_atom : members) {
					if (compared_atom.data.str.length() <= 2){
						System.out.println("Compared atom: " + compared_atom);
						System.out.println();
						if (tilda_1 == true) {
							if (compared_atom.data.str.equals(individual_atom.data.str.substring(1))) {
								System.out.println("CONTRADICTION!");
								System.out.println();
								return true; // there is a contradiction
							}
						}else {
							if (compared_atom.data.str.equals('~' + individual_atom.data.str)) {
								System.out.println("CONTRADICTION!");
								System.out.println();
								return true; // there is a contradiction
							}
						}
					}
				}
			}
		}
		return false; // no contradiction
	}
	public boolean CL_val() {
		System.out.println("Checking CL validity:");
		System.out.println();
		ArrayList<Tree> tmp_1 = new ArrayList<Tree>();
		ArrayList<Tree> tmp_2 = new ArrayList<Tree>();
		ArrayList<Tree> all_nodes = this.nodes_and_branches(tmp_2);
		ArrayList<Tree> empty_nodes = this.find_empty(tmp_1);
		System.out.println(empty_nodes);
		int count = 0;
		for (Tree empty_node : empty_nodes) {
			if (this.contradictory_branch(empty_node, all_nodes) == true) {
				count ++;
			}
		}
		System.out.println("No of Contradictions: " + count + '/' + empty_nodes.size() + " needed for validity");
		System.out.println();
		if (count == empty_nodes.size()) {
			System.out.println("The inference is valid in CL");
			System.out.println();
			return true; // is CL valid, no counter examples
		}else {
			System.out.println("The inference is not valid in CL");
			System.out.println();
			return false; // is not CL valid, counter example
		}
	}
	public void print_branch_breakdown(char operator, boolean tilda, Tree empty_node) {
		if(tilda == true) {
			tilda = false;
		}else {
			tilda = true;
		}
		System.out.println("Branched for " + tilda + ' ' + operator + ", below " + empty_node.data + " turns into: ");
		System.out.println("Left branch: " + empty_node.left.data + " branch numbers: " + empty_node.left.branch_nums);
		System.out.println("Right branch: " + empty_node.right.data + " branch numbers: " + empty_node.right.branch_nums);
		System.out.println();
	}
	public void print_lane_breakdown(char operator, boolean tilda, Tree empty_node) {
		if(tilda == true) {
			tilda = false;
		}else {
			tilda = true;
		}
		System.out.println("Laned for " + tilda + ' ' + operator + ", below node " + empty_node.data + " turns into: ");
		System.out.println("1st slot: " + empty_node.mid.data + ", branch numbers: " + empty_node.mid.branch_nums);
		System.out.println("2nd slot: " + empty_node.mid.mid.data + ", branch numbers: " + empty_node.mid.mid.branch_nums);
		System.out.println();
	}
	public void print_eq_breakdown(char operator, boolean tilda, Tree empty_node) {
		if(tilda == true) {
			tilda = false;
		}else {
			tilda = true;
		}
		System.out.println("Branched for " + tilda + ' ' + operator + ", below node " + empty_node.data + " turns into: ");
		System.out.println("Left branch, 1st slot: " + empty_node.left.data + ", branch numbers: " + empty_node.left.branch_nums);
		System.out.println("             2nd slot: " + empty_node.left.mid.data + ", branch numbers: " + empty_node.left.mid.branch_nums);
		System.out.println("And");
		System.out.println("Right branch, 1st slot: " + empty_node.right.data + ", branch numbers: " + empty_node.right.branch_nums);
		System.out.println("              2nd slot: " + empty_node.right.mid.data + ", branch numbers: " + empty_node.right.mid.branch_nums);
		System.out.println();
	}
	// ===============================
	public void branch(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets();
		String_1 new_expression2 = parts[1].balance_brackets();
		System.out.println(new_expression1.operator);
		System.out.println(new_expression2.operator);
		
		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_left(x);
			
			Tree y = new Tree(new_expression2);
			empty_node.set_right(y);
			empty_node.print_branch_breakdown(operator, tilda, empty_node);
		}
	}
	public void lane(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets();
		String_1 new_expression2 = parts[1].balance_brackets();
		
		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_mid(x);
			
			Tree y = new Tree(new_expression2);
			empty_node.mid.set_mid(y);
			empty_node.print_lane_breakdown(operator, tilda, empty_node);
		}
	}
	public void branch_if(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets().add_tilda(parts[0].find_operator().index);
		String_1 new_expression2 = parts[1].balance_brackets();
		
		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_left(x);
			
			Tree y = new Tree(new_expression2);
			empty_node.set_right(y);
			empty_node.print_branch_breakdown(operator, tilda, empty_node);
		}
	}
	public void branch_eq(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets();
		String_1 new_expression2 = parts[1].balance_brackets();
		
		String_1 ne3 = new_expression1.add_tilda(new_expression1.find_operator().index);
		String_1 ne4 = new_expression2.add_tilda(new_expression2.find_operator().index);
		
		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_left(x);
			
			Tree a = new Tree(ne3);
			empty_node.set_right(a);
			
			Tree y = new Tree(new_expression2);
			empty_node.left.set_mid(y);
			
			Tree b = new Tree(ne4);
			empty_node.right.set_mid(b);
			print_eq_breakdown(operator, tilda, empty_node);
		}
	}
	// =========== FALSE vvv
	public void lane_false_or(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets().add_tilda(parts[0].find_operator().index);
		String_1 new_expression2 = parts[1].balance_brackets().add_tilda(parts[1].find_operator().index);
		
		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_mid(x);
			
			Tree y = new Tree(new_expression2);
			empty_node.mid.set_mid(y);
			empty_node.print_lane_breakdown(operator, tilda, empty_node);
		}
	}
	public void branch_false_and(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets().add_tilda(parts[0].find_operator().index);
		String_1 new_expression2 = parts[1].balance_brackets().add_tilda(parts[1].find_operator().index);
		System.out.println(new_expression1.operator);
		System.out.println(new_expression2.operator);

		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_left(x);
			
			Tree y = new Tree(new_expression2);
			empty_node.set_right(y);
			empty_node.print_branch_breakdown(operator, tilda, empty_node);
		}
	}
	public void lane_false_if(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets();
		String_1 new_expression2 = parts[1].balance_brackets().add_tilda(parts[1].find_operator().index);

		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_mid(x);
			
			Tree y = new Tree(new_expression2);
			empty_node.mid.set_mid(y);
			empty_node.print_lane_breakdown(operator, tilda, empty_node);
		}
	}
	public void branch_false_eq(char operator, int operator_i, boolean tilda, ArrayList<Tree> empty_nodes) {
		String_1[] parts = this.data.find_parts(operator_i);
		String_1 new_expression1 = parts[0].balance_brackets();
		String_1 new_expression2 = parts[1].balance_brackets();
		System.out.println(new_expression1.str);
		System.out.println(new_expression2.str);
		System.out.println(new_expression1.find_operator().index);
		String_1 ne3 = parts[0].balance_brackets().add_tilda(new_expression1.find_operator().index);
		String_1 ne4 = parts[1].balance_brackets().add_tilda(new_expression2.find_operator().index);
		System.out.println(new_expression1.str);
		System.out.println(new_expression2.str);
		System.out.println(ne3.str);
		System.out.println(ne4.str);

		for(Tree empty_node : empty_nodes) {
			Tree x = new Tree(new_expression1);
			empty_node.set_left(x);
			
			Tree a = new Tree(ne3);
			empty_node.set_right(a);
			
			Tree b = new Tree(new_expression2);
			empty_node.left.set_mid(b);
			
			Tree y = new Tree(ne4);
			empty_node.right.set_mid(y);
			print_eq_breakdown(operator, tilda, empty_node);
		}
	}
	// ====================================
	public void evaluate_expression() {
		System.out.println("======= EVALUATING " + this.data.str + " =======");
		Tuple operator_index_tilda_tuple = this.data.find_operator();
		
		char operator = operator_index_tilda_tuple.operator;
		int operator_i = operator_index_tilda_tuple.index;
		boolean negation = operator_index_tilda_tuple.negation;
		
		ArrayList<Tree> empty_nodes = new ArrayList<Tree>();
		empty_nodes = this.find_empty(empty_nodes);
		
		if(operator_i != -1) {
			System.out.println("Parent node: " + this.data);
			System.out.println("Tuple: " + operator + ", " + operator_i + ", " + negation);
			System.out.println("Empty nodes: " + empty_nodes);
			System.out.println();
			
			if (operator == 'v' & negation == false){
				this.branch(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.left.evaluate_expression();
					new_parent.right.evaluate_expression();
				}
			}else if(operator == '^' & negation == false) {
				this.lane(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.mid.evaluate_expression();
					new_parent.mid.mid.evaluate_expression();
				}
			}else if(operator == '>' & negation == false) {
				this.branch_if(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.left.evaluate_expression();
					new_parent.right.evaluate_expression();
				}
			}else if(operator == '=' & negation == false) {
				this.branch_eq(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.left.evaluate_expression();
					new_parent.left.mid.evaluate_expression();
					new_parent.right.evaluate_expression();
					new_parent.right.mid.evaluate_expression();
				}
				// Now if negation is true ================================
			}else if(operator == 'v' &negation == true) {
				this.lane_false_or(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.mid.evaluate_expression();
					new_parent.mid.mid.evaluate_expression();
				}
			}else if(operator == '^' & negation == true) {
				this.branch_false_and(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.left.evaluate_expression();
					new_parent.right.evaluate_expression();
				}
			}else if(operator == '>' & negation == true) {
				this.lane_false_if(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.mid.evaluate_expression();
					new_parent.mid.mid.evaluate_expression();
				}
			}else if(operator == '=' & negation == true) {
				this.branch_false_eq(operator, operator_i, negation, empty_nodes);
				for(Tree new_parent : empty_nodes) {
					new_parent.left.evaluate_expression();
					new_parent.left.mid.evaluate_expression();
					new_parent.right.evaluate_expression();
					new_parent.right.mid.evaluate_expression();
				}
			}else {
				System.out.println("Something broke!");
				System.out.println();
			}
		}else {
			System.out.println("All inferences deconstructed!");
			System.out.println();
		}
	}
}
