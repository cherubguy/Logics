import java.util.*;

public class ExprStr {
	private String str;
	private boolean isAtomic;
	private int startingIndex; // starting and closing i are just indexes of the start and end of the main operator, not just the length of the string
	private int closingIndex;
	private char operator;
	private int operatorIndex;
	
	private ArrayList<String> auxOps;
	private boolean hasAuxOps;
	private boolean hasModalOp;
	private boolean negated;
	private boolean necessary;
	private boolean possible;
	private boolean notNecessary;
	private boolean notPossible;
	
	private ExprStr rHalf;
	private ExprStr lHalf;
	private ExprStr[] parts = new ExprStr[2];
	private boolean expanded;
	
	public ExprStr() {
		
	}
	public ExprStr(String str) {
		
		this.str = str;
		//this.expanded = false;
		//System.out.println("======= CONSTRUCTING " + this.str + " =======");
		this.balanceBrackets(); // balance brackets first before checking if isAtomic so that regex works
		/*
		 * This constructor does several things:
		 * 		1 - allocates the passed in str to a field
		 * 		2 - brackets are balanced via balanceBrackets, if there are an uneven number of r | l brackets it removes
		 * 			the unecessary ones, if the number of sets of brackets is equal to or larger than the number of operators 
		 * 			it removes the outermost sets so that the string has the appropriate amount of brackets
		 * 		3 - checks against a regex whether or not the str contains only one alpha char, in which
		 * 			case it is isAtomic
		 * 		4 - if the str is isAtomic:
		 * 			a - hasAuxOps is set to false if the str is only 1 char long, and all other fields to do with any operator are set to trivial values
		 * 			b - the number of auxillary operations is then counted by findAuxOps()
		 * 		5 - if the str is not isAtomic
		 * 			a - the operators on the str are then found by findOperator() and hasAuxOps is set
		 * 			b - the number of auxillary operations is then counted by findAuxOps()
		 * 			c - the str is split into 2 parts
		 * 			d - 2 new ExprStrs are constructed with the parts passed in
		 * 		
		 */
		if (this.str.matches("[!~?]*[a-z]{1}")) { // if the string contains only one alpha char, then it is isAtomic
			this.isAtomic = true;
			//System.out.println("isAtomic = true");
		}else {
			this.isAtomic = false;
			//System.out.println("isAtomic = false");
		}
		if (this.isAtomic == true) {
			this.startingIndex = 0;
			this.closingIndex = str.length() - 1;
			this.operatorIndex = -1;
			this.operator = ' ';
	
			
			
			this.findAuxOps();
			
			//System.out.println("operator = " + this.operator);
			//System.out.println("operatorIndex = " + this.operatorIndex);
			//System.out.println("startingIndex = " + this.startingIndex);
			//System.out.println("hasAuxOps = " + this.hasAuxOps);
			//System.out.println();
		}else {
			this.findOperator();
			this.findAuxOps();
			//System.out.println("hasAuxOps = " + this.hasAuxOps);
			//System.out.println("rh =  " + this.str.substring(this.operatorIndex + 2));
			//System.out.println("lh =  " + this.str.substring(startingIndex, operatorIndex - 1));
			//System.out.println();
			rHalf = new ExprStr(this.str.substring(this.operatorIndex + 2));
			lHalf = new ExprStr(this.str.substring(startingIndex, operatorIndex - 1));
			
			parts[0] = lHalf;
			parts[1] = rHalf;
			
		}
	}
	
	@Override
	public String toString() {
		return this.str;
	}
	public String printExprStr() {
		if (this.operator != ' ') {
			return (this.auxOps + "Part 1: " + this.parts[0].printExprStr() +  " " + this.operator + " Part 2: " + this.parts[1].printExprStr());
		}else {
			return (this.auxOps + this.str);
		}
	}
	
	public void setString(String str) {
		this.str = str;
	}
	public void setExpanded(boolean b) {
		this.expanded = b;
	}
	public String getString() {
		return this.str;
	}
	public boolean getIsAtomic() {
		return this.isAtomic;
	}
	public int getStartingIndex() {
		return this.startingIndex;
	}
	public int getClosingIndex() {
		return this.closingIndex;
	}
	public int getOperatorIndex() {
		return this.operatorIndex;
	}
	public char getOperator() {
		return this.operator;
	}
	public ArrayList<String> getAuxOps(){
		return this.auxOps;
	}
	public boolean getHasAuxOps() {
		return this.hasAuxOps;
	}
	public boolean getHasModalOp() {
		return this.hasModalOp;
	}
	public boolean getNegated() {
		return this.negated;
	}
	public boolean getNecessary() {
		return this.necessary;
	}
	public boolean getPossible() {
		return this.possible;
	}
	public boolean getNotNecessary() {
		return this.notNecessary;
	}
	public boolean getNotPossible() {
		return this.notPossible;
	}
	public ExprStr[] getParts() {
		return this.parts;
	}
	public boolean getExpanded() {
		return this.expanded;
	}
	
	
	
	/*
	 * SETS OF BRACKETS SHOULD ALWAYS BE ONE LESS THAN OPERATOR NUM UNLESS THERE ARE AUX OPS
	 */
	public ExprStr addTilda() { // NOT WORKING PROPERLY
		//System.out.println("======= TILDA TO BE ADDED =======");
		
		String s = this.str;
	
		if (this.isAtomic == true) {
			s = "~" + s;
		}else {
			if (this.hasAuxOps == false) { 
					s = "~(" + s + ")";
			}else {
					s = "~" + s;
			}
		}
		
		
		
		ExprStr newExprStr = new ExprStr(s);
		
		newExprStr = newExprStr.removeDoubleNegV2();
		
		return newExprStr;
	}
	public void balanceBrackets() {
		//System.out.println(this.str + " before balancing");
		int r_b = 0;
		int l_b = 0;
		int last_r_b_i = -1;
		int first_l_b_i = -1;
		int opNum = 0;
		
		
		/*
		 * First count how many operators, left and right brackets there are
		 */
		for (int i = 0; i < this.str.length(); i ++) {
			if (this.str.charAt(i) == 'v' ||
					this.str.charAt(i) == '=' ||
					this.str.charAt(i) == '^' ||
					this.str.charAt(i) == '>') {
				opNum ++;
			}else if (this.str.charAt(i) == '(') {
				l_b ++;
			}else if (this.str.charAt(i) == ')') {
				r_b ++;
			}
		}
		/*
		 * Then remove brackets if left and right are not even
		 */
		if (r_b > l_b) {
			for (int i = 0; i < r_b - l_b; i ++) {
				last_r_b_i = this.str.lastIndexOf(')');
				this.str = this.str.substring(0, last_r_b_i); // the last bracket will never have anything after it (??)
				r_b -= 1;
			}	
		}else if(l_b > r_b) {
			for (int i = 0; i < l_b - r_b; i ++) {
				first_l_b_i = this.str.indexOf('(');
				this.str = this.str.substring(first_l_b_i + 1);	// no auxOps on the beginning of a 'part', so no need to scan for that
				l_b -= 1;
			}
		}
		/*
		 * Then remove brackets if there are too many sets, there are too many sets if there are as many sets as there are
		 * operators, there should always be x operators and x - 1 sets of brackets
		 */
		//System.out.println("l_b = " + l_b + ", opNum = " + opNum);
		
		int opOffset = opNum - l_b;
		//System.out.println("opNum - l_b = " + opOffset);
		if (opNum - l_b < 1 && this.str.indexOf('(') == 0) { // triggered if there are too many bracket sets and no aux ops for this expr
			for (int i = 0; i <= opOffset; i ++) {
				last_r_b_i = this.str.lastIndexOf(')');
				if (last_r_b_i != -1) {
					this.str = this.str.substring(0, last_r_b_i); // the last bracket will never have anything after it (??)
					r_b -= 1;
				}
				first_l_b_i = this.str.indexOf('(');
				if (first_l_b_i != -1) {
					this.str = this.str.substring(first_l_b_i + 1);		
					l_b -= 1;
				}
			}
		}
	//System.out.println(this.str + " after balancing");
	}
	/*
	 * this function below will return a list of all the necessary/possible operators that work on a particular statement/main operator
	 * their order in the list will tell you which order of magnitude each operator is, there are only 5 combinations; ~ ~! ~? ! ? but they can
	 * stack on top of one another 
	 */
	public void findAuxOps() {
		this.auxOps = new ArrayList<String>();
		String result;
		int end;
		if ((this.isAtomic == true && this.str.length() == 1) ||
				(this.isAtomic == false && this.startingIndex == 0)) { // these are the two cases where there is no space for auxOps, so return
			//System.out.println("no space for auxOps");
			this.hasAuxOps = false;
			this.hasModalOp = false;
			this.negated = false;
			this.necessary = false;
			this.possible = false;
			this.notNecessary = false;
			this.notPossible = false;
			return;
		}else {
			//System.out.println("space for auxOps");
			if (this.isAtomic == true) { // if the str is isAtomic and has auxOps then start iterating backwards from the char before the isAtomic char
				end = this.str.length() - 2;
			}else { // if the str is not iatomic and has auxOps startingIndex must be the outermost set of braces, start iterating from the char before
				end = this.startingIndex - 1;
			}
			for (int i = 0; i <= end; i ++) {
				if (!(i + 1 > end)) {
					if (this.str.substring(i, i + 2).matches("~[!|?]")) {
						this.auxOps.add(this.str.substring(i, i + 2) + ' ');
						i ++; // to make sure that some operators arent counted twice increase i by 1 if you add 2 chars to auxOps
					}else {
						result = "";
						result += this.str.charAt(i);
						result += ' ';
						this.auxOps.add(result);
					}
				}else {
					result = "";
					result += this.str.charAt(i);
					result += ' ';
					this.auxOps.add(result);
				}
			}
		}
		
		/*
		 * this block below just makes the variable negated true or false, for easy access
		 */
		//System.out.println("AUXOPS " + auxOps);
		if (this.auxOps.size() != 0) { // negation applying to the actual operator and not either !|? must be the first auxOp before the startingIndex
			//System.out.println("auxOps size != 0");
			this.hasAuxOps = true;
			if (this.auxOps.get(0).equals("~ ")) {
				this.negated = true;
				this.hasModalOp = false;
			}else {
				this.negated = false;
			}
			if (this.auxOps.get(0).equals("! ")) {
				this.necessary = true;
				this.hasModalOp = true;
			}else {
				this.necessary = false;
			}
			if (this.auxOps.get(0).equals("? ")) {
				this.possible = true;
				this.hasModalOp = true;
			}else {
				this.possible = false;
				
			}
			if (this.auxOps.get(0).equals("~! ")) {
				this.notNecessary = true;
				this.hasModalOp = true;
			}else {
				this.notNecessary = false;
			}
			if (this.auxOps.get(0).equals("~? ")) {
				//System.out.println("not possible is true");
				this.notPossible = true;
				this.hasModalOp = true;
			}else {
				this.notPossible = false;
			}
		}else {
			this.hasAuxOps = false; // i dont think this block is ever reached but its good for trying to break
			this.hasModalOp = false;
			this.negated = false;
			this.necessary = false;
			this.possible = false;
			this.notNecessary = false;
			this.notPossible = false;
		}
		//System.out.println("negated = " + this.negated);
		//System.out.println("added to auxOps: " + this.auxOps);
	}
	
	public void removeDoubleNeg() {
		for (int i = 0; i < this.auxOps.size() - 1; i ++) { // removes double neg from auxOps
			if (this.auxOps.get(i).equals("~ ") && this.auxOps.get(i + 1).equals("~ ")){
				if (i == 0) {
					this.negated = !this.negated; // makes sure that negation field is switched
				}
				this.auxOps.remove(i);
				this.auxOps.remove(i);
				
				i -= 1;
				//System.out.println("removed double neg from auxOps: " + this.auxOps);
			}else if(this.auxOps.get(i).equals("~ ") &&
					(this.auxOps.get(i + 1).substring(0, 2).equals("~!") || this.auxOps.get(i + 1).substring(0, 2).equals("~?"))) {
				if (i == 0) {
					this.negated = !this.negated; // makes sure that negation field is switched
				}
				String tmpStr = this.auxOps.get(i + 1);
				this.auxOps.remove(i); // this will remove both elements
				this.auxOps.remove(i);
				this.auxOps.add(i, tmpStr.substring(1)); // this then replaces the offending thing at index i with the thing at i + 1 but minus the tilda
				//System.out.println("removed double neg from auxOps: " + this.auxOps);
				
			
				
			}
		}
		for (int i = 0; i < this.str.length() - 2; i ++) { // removes double neg from str
			if (this.str.charAt(i) == '~' && this.str.charAt(i + 1) == '~'){
				char[] charArray = this.str.toCharArray();
				this.str = "";
				int count = 0;
				for (char c : charArray) {
					if (count != i && count != i + 1) {
						this.str += c;
					}
					count ++;
				}
				i -= 1;
				//System.out.println("removed double neg from str: " + this.str);
			}
		}
		//System.out.println();
	}
	public ExprStr removeDoubleNegV2() {
		String[] s = this.str.split("~~");
		String joinedS = "";
		for (String segment : s) {
			joinedS += segment;
		}
		
		ExprStr newExprStr = new ExprStr(joinedS);
		return newExprStr;
	}
	
	public void findOperator() { // need to find a way to get both necessary and possible, and their negations either side
		
		int maxDiff = 0;
		int tmpClosingIndex = -1;
		int tmpOpeningIndex = -1;
		String operators = "v^>=";
		
		for(int i = 0; i < this.str.length(); i ++) {
			if (operators.indexOf(this.str.charAt(i)) != -1) {
				tmpClosingIndex = this.findPartnerBracket(i, 'r');
				tmpOpeningIndex = this.findPartnerBracket(i, 'l');
				if (tmpOpeningIndex == -2 && tmpClosingIndex == -2) { // if the expr has no brackets
					this.startingIndex = 0;
					this.closingIndex = this.str.length() - 1;
					//this.hasAuxOps = false; // if the operation has no brackets then it must also have no auxOps
					this.operator = this.str.charAt(i);
					this.operatorIndex = i;
					//System.out.println("operator = " + this.operator);
					//System.out.println("operatorIndex = " + this.operatorIndex);
					//System.out.println("startingIndex = " + this.startingIndex);
					return;
					
				}else {
					if (tmpClosingIndex - tmpOpeningIndex > maxDiff) {
						maxDiff = tmpClosingIndex - tmpOpeningIndex;
						this.startingIndex = tmpOpeningIndex;
						this.closingIndex = tmpClosingIndex;
						//this.hasAuxOps = true;
						this.operator = this.str.charAt(i);
						this.operatorIndex = i;
					}
				}
			}
		}
		//System.out.println("operator = " + this.operator);
		//System.out.println("operatorIndex = " + this.operatorIndex);
		//System.out.println("startingIndex = " + this.startingIndex);
		//System.out.println("hasAuxOps = " + this.hasAuxOps);
	}
	
	public int findPartnerBracket(int initialChar, char direction) {
		if(direction == 'r') {
			int r_b = 0;
			int partnerB = -1;
			int i = initialChar + 1;
			boolean found = false;
			while(found == false) {
				if(this.str.charAt(i) == '(') {
					r_b ++;
				}else if(this.str.charAt(i) == ')'){
					r_b -= 1;
				}
				if (r_b == -1) {
					partnerB = i;
					found = true;
				}else if(r_b == 0 && i == this.str.length() - 1) {
					partnerB = -2; // -2 means that the char doesnt have a bracket
					found = true;
				}
				i ++;
			}
			return partnerB;
		}else if(direction == 'l') {
			int l_b = 0;
			int partnerB = -1;
			int i = initialChar - 1;
			boolean found = false;
			while(found == false) {
				if(this.str.charAt(i) == ')') {
					l_b ++;
				}else if(this.str.charAt(i) == '('){
					l_b -= 1;
				}
				if (l_b == -1) {
					partnerB = i;
					found = true;
				}else if(l_b == 0 && i == 0) {
					partnerB = -2; // -2 means that the char doesnt have a bracket
					found = true;
				}
				i -= 1;
			}
			return partnerB;
		}else {
			return -1; // -1 means something fucked up
		}
	}
	
}
