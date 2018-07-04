
public class String_1 {
	String str;
	char operator;
	int operator_i;
	boolean tilda;
	
	public String_1() {
		
	}
	public String_1(String x) {
		this.str = x;
		//Tuple tuple = this.find_operator2();
		//System.out.println("CALLED ON CONSTRUCTION");
		//this.operator = tuple.operator;
		//this.operator_i = tuple.index;
		//this.tilda = tuple.tilda;
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
		//String_1 new_str_1 = new String_1();
		//int l_b = 0;		
		int r_b = 0;		
		int op_num = 0;
		System.out.println("Bracket balancing for: " + this.str);
		System.out.println();
		
		String operators;
		operators = "v^>=";
		
		//if (tilda == true) {
			//op_num ++;
		//}
		
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
			//new_str_1 = new String_1(this.str); // THIS IS FOR IF STRING_1 HAS FIELDS FOR OPERATOR ETC
			//return new_str_1;
			return this;
		}else {
			System.out.println("Final expression: " + this.str);
			System.out.println();
			//new_str_1 = new String_1(this.str); 
			//return new_str_1;
			return this;
		}
	}
	public String_1 balance_brackets2(boolean tilda) { //ORIGINAL
		String_1 new_str_1 = new String_1();
		int l_b = 0;		
		int r_b = 0;		
		int op_num = 0;
		System.out.println("Bracket balancing for :" + this.str);
		System.out.println();
		
		String operators;
		operators = "v^>=";
		
		if (tilda == true && this.str.substring(0, 2).equals("~(")) {
			this.str = this.str.substring(1);
			System.out.println(this.str + " Tilda removed");
			if (this.str.substring(0, 2).equals("(~")) {
				this.str = this.str.substring(1);
				System.out.println(this.str + " Bracket removed");
				op_num ++;
			}					
		}
		for (int i = 0; i <= this.str.length() - 1; i ++) {
			if (this.str.substring(i).length() == 1) {
				if (this.str.substring(i).equals("(")) {
					l_b ++;
				} else if (this.str.substring(i).equals(")")) {
					r_b ++;
				} else if (operators.contains(this.str.substring(i))) {
					op_num ++;
				}
			}else {
				if (this.str.substring(i, i + 1).equals("(")) {
					l_b ++;
				} else if (this.str.substring(i, i + 1).equals(")")) {
					r_b ++;
				} else if (operators.contains(this.str.substring(i, i + 1))) {
					op_num ++;
				}
			}
		}		
		if (l_b < r_b) {
			int diff1 = r_b - l_b;
			for(int i = 0; i < (diff1); i ++) {
				int x = this.str.lastIndexOf(")");
				this.str = this.str.substring(0, x);
				System.out.println(this.str + " Right b removed");
				r_b -= 1;
			}
		} else if (l_b > r_b) {
			int diff2 = l_b - r_b;
			for (int i = 0; i < (diff2); i ++) {
				int x = this.str.indexOf("(");
				if (x == 0) {
					this.str = this.str.substring(1);
					System.out.println(this.str + " Left b removed");
					l_b -= 1;
				} else {
					this.str = this.str.substring(0, x) + 
							this.str.substring(x + 1);
					System.out.println(this.str + " Left b removed");
					l_b -= 1;
				}
			}
		}
		if(this.str.length() >= 3) {
			if (this.str.substring(0, 2).equals("~(") == false) { // Need an .equals() method for this string?
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
			System.out.println("Final expression = " + this.str);
			System.out.println();
			new_str_1 = new String_1(this.str);
			return new_str_1;
		}else {
			System.out.println("Final expression = " + this.str);
			System.out.println();
			new_str_1 = new String_1(this.str);
			return new_str_1;
		}
	}
	public String_1 remove_double_neg() {
		for(int i = 0; i < this.str.length(); i ++) {
			if(this.str.charAt(i) == '~') {
				if(this.str.charAt(i + 1) == '~') {
					this.str = this.str.substring(0,  i) + this.str.substring(i + 2);
					System.out.println("Double neg removed");
				}
			}
		}
		String_1 new_expression = new String_1(this.str);
		return new_expression;
	}
	public Tuple find_operator2() {
		//System.out.println(this.str);
		boolean tilda = false;
		int max_diff = 0;
		char operator = 'z';
		int operator_i = -1;
		String operators = "v^>=";
		int closing_index;
		int opening_index;
		
		for(int i = 0; i < this.str.length(); i ++) {
			if (operators.indexOf(this.str.charAt(i)) != -1) {
				//System.out.println("fuuuuuuuuu");
				closing_index = this.find_partner_bracket(i, 'r');
				opening_index = this.find_partner_bracket(i, 'l');
				if (opening_index == -2 && closing_index == -2) { // if the char has no brackets
					Tuple operator_index_tilda_tuple = new Tuple(this.str.charAt(i), i, false);
					return operator_index_tilda_tuple;
				}else {
					if (closing_index - opening_index > max_diff) {
						//System.out.println("THIS BIT DONE DID");
						max_diff = closing_index - opening_index;
						operator = this.str.charAt(i);
						//System.out.println(operator);
						operator_i = this.str.indexOf(operator);
					}
					if(this.str.substring(0, opening_index).indexOf('~') != 1) {
						tilda = true;
					}else {
						tilda = false;
					}
				}
			}
		}
		Tuple operator_index_tilda_tuple = new Tuple(operator, operator_i, tilda);
		return operator_index_tilda_tuple;
	}
	public Tuple find_operator() {
		boolean tilda = false;
		int max_diff = 0;
		char operator = 'n';
		int index = -1;
		String operators = "v^>=";
		int closing_index = -2;
		int opening_index = -2;
		
		for(int i = 0; i < this.str.length(); i ++) {
			if (operators.indexOf(this.str.charAt(i)) != -1){
				if (this.str.indexOf(")", i) == -1 ||
						this.str.substring(0, i).lastIndexOf("(") == -1) {
					Tuple operator_index_tilda_tuple = new Tuple(this.str.charAt(i), i, false);
					return operator_index_tilda_tuple;
				}else {
					boolean found = false;
					int j = i;
					int brackets = 0;
					
					while(found == false) {
						if(this.str.charAt(j) == '(') {
							brackets += 1;
						}else if(this.str.charAt(j) == ')'){
							brackets -= 1;
						}
						if (this.str.charAt(j) == ')' && brackets == 0){
							closing_index = this.str.indexOf(")", j + 1);
							found = true;
						}else if(this.str.charAt(j) == ')' && brackets == -1) {
							closing_index = j;
							found = true;
						}
						j ++;
					}
					
					found = false;
					int h = i;
					brackets = 0;
					while(found == false) {
						if(this.str.charAt(h) == ')') {
							brackets += 1;
						}else if(this.str.charAt(h) == '('){
							brackets -= 1;
						}
						
						if (this.str.charAt(h) == '(' & brackets == 0) {
							opening_index = this.str.substring(0, h).lastIndexOf('(');
							found = true;
						}else if(this.str.charAt(h) == '(' & brackets == -1) {
							opening_index = h;
							found = true;
						}
						h -= 1;
					}
					if (closing_index == -1 && opening_index == -1) {
						operator = this.str.charAt(i);
						index = i;
						tilda = false;
						Tuple operator_index_tilda_tuple = new Tuple(operator, index, tilda);
						return operator_index_tilda_tuple;
					}else if(closing_index - opening_index > max_diff) {
						max_diff = closing_index - opening_index;
						operator = this.str.charAt(i);
						index = i;
						if(this.str.substring(0, opening_index).indexOf('~') != 1) {
							tilda = true;
						}else {
							tilda = false;
						}
					}
				}
			}
		}
		Tuple operator_index_tilda_tuple = new Tuple(operator, index, tilda);
		return operator_index_tilda_tuple;
	}
	public String_1 add_tilda(int operator_i) {
		if(this.str.length() <= 4) {
			this.str = '~' + this.str;
		}else if(this.find_partner_bracket(operator_i, 'l') != -2) {
			if(this.str.charAt(this.find_partner_bracket(operator_i, 'l') - 1) == '~') {
				this.str = '~' + this.str;
			}
		//}else if(this.str.substring(0, 2).equals("~(")) {
			//if(this.find_partner_bracket(1, 'r') == this.str.length() - 1) {
				//new_expression.str = '~' + this.str;
			//}
		}else{
			this.str = "~(" + this.str + ')';
		}
		System.out.println("Tilda added: " + this.str);
		System.out.println();
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
		//String_1 new_expression = new String_1(this.str); // THIS IS FOR IF STRING_1 HAS FIELDS FOR OPERATOR ETC
		//return new_expression;
		return this;
	}
}
