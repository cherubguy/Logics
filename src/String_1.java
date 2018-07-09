
public class String_1 {
	String str;
	char operator;
	int operator_i;
	boolean tilda;
	
	public String_1() {
		
	}
	public String_1(String x) {
		this.str = x;
	}
	@Override
	public String toString() {
		return this.str;
	}
	// my own custom methods vvv
	public int find_partner_bracket(int initial_char, char direction) {
		if(direction == 'r') {
			int r_b = 0;
			int partner_b = -1;
			int i = initial_char + 1;
			boolean found = false;
			while(found == false) {
				if(this.str.charAt(i) == '(') {
					r_b ++;
				}else if(this.str.charAt(i) == ')'){
					r_b -= 1;
				}
				if (r_b == -1) {
					partner_b = i;
					found = true;
				}else if(r_b == 0 && i == this.str.length() - 1) {
					partner_b = -2; // -2 means that the char doesnt have a bracket
					found = true;
				}
				i ++;
			}
			return partner_b;
		}else if(direction == 'l') {
			int l_b = 0;
			int partner_b = -1;
			int i = initial_char - 1;
			boolean found = false;
			while(found == false) {
				if(this.str.charAt(i) == ')') {
					l_b ++;
				}else if(this.str.charAt(i) == '('){
					l_b -= 1;
				}
				if (l_b == -1) {
					partner_b = i;
					found = true;
				}else if(l_b == 0 && i == 0) {
					partner_b = -2; // -2 means that the char doesnt have a bracket
					found = true;
				}
				i -= 1;
			}
			return partner_b;
		}else {
			return -1; // -1 means something fucked up
		}
	}
	public String_1[] find_parts(int operator_i) {
		int r_half_index = -1;
		int l_half_index = -1;

		if (this.str.charAt(operator_i + 2) == '(') {
			r_half_index = this.find_partner_bracket(operator_i + 2, 'r');
		}else if(this.str.substring(operator_i + 2, operator_i + 3) == "~(") {
			r_half_index = this.find_partner_bracket(operator_i + 3, 'r');
		}else {
			if(this.str.charAt(this.str.length() - 1) == ')') {
				r_half_index = this.str.length() - 2;
			}else {
				r_half_index = this.str.length() - 1;
			}
		}
		if (this.str.charAt(operator_i - 2) == ')') {
			l_half_index = this.find_partner_bracket(operator_i - 2, 'l');
			if(l_half_index != 0 && this.str.charAt(l_half_index - 1) == '~') {
				l_half_index -= 1;
			}
		}else {
			if(this.str.substring(0, 2).equals("~(")) {
				l_half_index = 2;
			}else {
				l_half_index = 0;
			}
		}
		String_1 r_half = new String_1(this.str.substring(operator_i + 2, r_half_index + 1));
		String_1 l_half = new String_1(this.str.substring(l_half_index, operator_i - 1));
		String_1[] parts = {l_half, r_half};
		return parts;
	}
	public String_1 balance_brackets() { //NEW METHOD	
		String_1 new_expression = new String_1();
		int r_b = 0;		
		int op_num = 0;
		System.out.println("Bracket balancing for: " + this.str);
		System.out.println();
		
		String operators;
		operators = "v^>=";
		
		for (int i = 0; i <= this.str.length() - 1; i ++) {
			if (this.str.substring(i).length() == 1) {
				if (this.str.substring(i).equals(")")) {
					r_b ++;
				} else if (operators.contains(this.str.substring(i))) {
					op_num ++;
				}
			}else {
				if (this.str.substring(i, i + 1).equals(")")) {
					r_b ++;
				} else if (operators.contains(this.str.substring(i, i + 1))) {
					op_num ++;
				}
			}
		}		
		if(this.str.length() >= 2) {
			if (this.str.substring(0, 2).equals("~(") == false) {
				while(op_num <= r_b && r_b != 0) {
					int i;
					i = this.str.indexOf("(");
					if (i == 0) {
						this.str = this.str.substring(1);
						System.out.println(this.str + " Redundant b removed");
					} else {
						this.str = this.str.substring(0, i) + this.str.substring(i + 1);
						System.out.println(this.str + " Redundant b removed");
					}
					int j;
					j = this.str.lastIndexOf(")");
					if (j == this.str.length()) {
						this.str = this.str.substring(0, this.str.length() - 1);
						System.out.println(this.str + " Redundant b removed");
					} else {
						this.str = this.str.substring(0, j) + this.str.substring(j + 1);
						System.out.println(this.str + " Redundant b removed");
					}
					r_b -= 1;
				}
			}
			System.out.println("Final expression: " + this.str);
			System.out.println();
			new_expression.str = this.str;
			return new_expression;//this;
		}else {
			System.out.println("Final expression: " + this.str);
			System.out.println();
			new_expression.str = this.str;
			return new_expression;//this;
		}
	}
	public Prop construct_prop(String_1 expression) {
		boolean negation = false;
		boolean necessary = false;
		boolean possible = false;
		
		Tuple tuple1 = expression.find_operator();
	
		int operator_i = tuple1.index;
		String_1[] parts = expression.find_parts(operator_i);
		
		String_1 e1 = parts[0].balance_brackets();
		String_1 e2 = parts[1].balance_brackets();
		
		if (e1.str.length() <= 4 && e2.str.length() > 4) { // if the first half is small enough but the second isnt, recurse into the second
			for (int i = 0; i < e1.str.length(); i ++) {
				if (e1.str.charAt(i) == '~') {
					negation = true;
				}else if (e1.str.charAt(i) == '*') {
					necessary = true;
				}else if (e1.str.charAt(i) == '?') {
					possible = true;
				}
			}
			Prop new_prop1 = new Prop(e1.str.charAt(e1.str.length() - 1), negation, necessary, possible);
			Prop new_prop2 = new Prop(new_prop1, e2.construct_prop(e2), tuple1.operator, negation, necessary, possible);
			return new_prop2;
		}else if (e2.str.length() <= 4 && e1.str.length() > 4) { // if the second half is small enough but the first isnt, recurse into the first
			for (int i = 0; i < e2.str.length(); i ++) {
				if (e2.str.charAt(i) == '~') {
					negation = true;
				}else if (e2.str.charAt(i) == '*') {
					necessary = true;
				}else if (e2.str.charAt(i) == '?') {
					possible = true;
				}
			}
			Prop new_prop1 = new Prop(e2.str.charAt(e2.str.length() - 1), negation, necessary, possible);
			Prop new_prop2 = new Prop(e1.construct_prop(e1), new_prop1, tuple1.operator, negation, necessary, possible); // need to find a way to get both necessary and possible
			return new_prop2;
		}else if (e2.str.length() <= 4 && e1.str.length() <= 4) { // both sides are atomic, this is the base case, no more recurses after this point
			boolean e2negation = false;
			boolean e2necessary = false;
			boolean e2possible = false;
			for (int i = 0; i < e1.str.length(); i ++) {
				if (e1.str.charAt(i) == '~') {
					negation = true;
				}else if (e1.str.charAt(i) == '*') {
					necessary = true;
				}else if (e1.str.charAt(i) == '?') {
					possible = true;
				}
			}
			for (int i = 0; i < e2.str.length(); i ++) {
				if (e2.str.charAt(i) == '~') {
					e2negation = true;
				}else if (e2.str.charAt(i) == '*') {
					e2necessary = true;
				}else if (e2.str.charAt(i) == '?') {
					e2possible = true;
				}
			}
			Prop new_prop1 = new Prop(e1.str.charAt(e1.str.length() - 1), negation, necessary, possible);
			Prop new_prop2 = new Prop(e2.str.charAt(e2.str.length() - 1), e2negation, e2necessary, e2possible);
			Prop new_prop3 = new Prop(new_prop1, new_prop2, tuple1.operator, tuple1.negation, tuple1.necessary, tuple1.possible);
			return new_prop3;
		}else {
			Prop new_prop = new Prop(e1.construct_prop(e1), e2.construct_prop(e2), tuple1.operator, negation, necessary, possible); // if neither side is small enough, recurse into both
			return new_prop;
		}
		
	}
	public Tuple find_operator() { // need to find a way to get both necessary and possible, and their negations either side
		boolean negation = false;
		boolean necessary = false;
		boolean possible = false;
		int max_diff = 0;
		char operator = 'z';
		int operator_i = -1;
		String operators = "v^>=";
		int closing_index;
		int opening_index;
		
		for(int i = 0; i < this.str.length(); i ++) {
			if (operators.indexOf(this.str.charAt(i)) != -1) {
				closing_index = this.find_partner_bracket(i, 'r');
				opening_index = this.find_partner_bracket(i, 'l');
				if (opening_index == -2 && closing_index == -2) { // if the char has no brackets
					Tuple operator_index_negation_tuple = new Tuple(this.str.charAt(i), i, false, false, false);
					return operator_index_negation_tuple;
				}else {
					if (closing_index - opening_index > max_diff) {
						max_diff = closing_index - opening_index;
						operator = this.str.charAt(i);
						operator_i = i;
					}
					if(this.str.substring(0, opening_index).indexOf('~') != 1) {
						negation = true;
					}else {
						negation = false;
					}
					for (int j = 0; j < opening_index; j ++) {
						if (this.str.substring(opening_index))
					}
				}
			}
		}
		Tuple operator_index_negation_tuple = new Tuple(operator, operator_i, negation);
		return operator_index_negation_tuple;
	}
	public String_1 add_tilda(int operator_i) {
		if(this.str.length() <= 4) {
			this.str = '~' + this.str;
			System.out.println("Tilda added: " + this.str);
			System.out.println();
		}else if(this.find_partner_bracket(operator_i, 'l') != -2) {
			System.out.println("bboopp");
			System.out.println(this.str.charAt(this.find_partner_bracket(operator_i, 'l') - 1));
			if(this.str.charAt(this.find_partner_bracket(operator_i, 'l') - 1) == '~') {
				this.str = '~' + this.str;
				System.out.println("Tilda added: " + this.str);
				System.out.println();
			}
		}else{
			this.str = "~(" + this.str + ')';
			System.out.println("Tilda added: " + this.str);
			System.out.println();
		}
		//System.out.println("Tilda added: " + this.str);
		//System.out.println();
		for(int i = 0; i < this.str.length(); i ++) { // removing double negs
			if(this.str.charAt(i) == '~') {
				if(this.str.charAt(i + 1) == '~') {
					this.str = this.str.substring(0,  i) + this.str.substring(i + 2);
					System.out.println("Double neg removed: " + this.str);
					System.out.println();
					System.out.println("Checking balance again for: " + this.str);
					System.out.println();
					this.balance_brackets();
				}
			}
		}
		String_1 new_expression = new String_1(this.str);
		return new_expression;//this;
	}
}
