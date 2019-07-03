import java.util.*;
import javax.swing.*;
import java.awt.*;
public class World {
	private Universe parentUniverse;
	private String worldName;
	private ArrayList<Relation> relations; // remember that a world can have itself as a relation!
	private ArrayList<ExprStr> expressions;
	private Point p;
	//private Tree worldTree;
	public World() {
		
	}
	public World(Universe u) {
		this.parentUniverse = u; // make sure the world has a reference to its universe
								 // dont forget to add the world to the universe aswell, though this cant be done through the constructor
		int count = u.getWorldCount();
		String countStr = Integer.toString(count);
		this.worldName = "";
		this.worldName += 'w' + countStr;
		this.relations = new ArrayList<Relation>();
		this.expressions = new ArrayList<ExprStr>();
		
	}
	public World(Universe u, ExprStr expression) { // constructor gives the world a universe and an expression
		this.parentUniverse = u;
		int count = u.getWorldCount();
		String countStr = Integer.toString(count);
		this.worldName = "";
		this.worldName += 'w' + countStr;
		this.relations = new ArrayList<Relation>();
		this.expressions = new ArrayList<ExprStr>();
		/*if (this.parentUniverse.getReflex()) {
			this.addRelation(this, true, true); 
		}*/
		this.expressions.add(expression);
	}
	public World(Universe u, ArrayList<ExprStr> expressions) {
		this.parentUniverse = u;
		int count = u.getWorldCount();
		String countStr = Integer.toString(count);
		this.worldName = "";
		this.worldName += 'w' + countStr;
		this.relations = new ArrayList<Relation>();
		/*if (this.parentUniverse.getReflex()) {
			this.addRelation(this, true, true); 
		}*/
		this.expressions = new ArrayList<ExprStr>();
		this.expressions.addAll(expressions);
	}
	public World(World w) {  // creates a copy
		/*this.relations = new ArrayList<Relation>();
		for (Relation r : w.relations) {
			Relation copyRelation = new Relation(r);
			relations.add(copyRelation);
		}*/
		
		this.parentUniverse = w.parentUniverse;
		this.worldName = w.worldName;
		this.expressions = new ArrayList<>(w.getExpressions());
		this.relations = new ArrayList<Relation>();
		for (Relation r : w.getRelations()) {
			Relation newR = new Relation(r);
			relations.add(newR);
		}
		
	}
	public void setParentUniverse(Universe u) {
		this.parentUniverse = u;
	}
	public void addRelation(World otherWorld, boolean w1Receiving, boolean w2Receiving ) {
		Relation r = new Relation(this, otherWorld, w1Receiving, w2Receiving);
		this.relations.add(r);
		System.out.println("Added relation: " + r);
	}
	public void addExpression(ExprStr e) {
		this.expressions.add(e);
	}
	public void addExpression(ArrayList<ExprStr> expressions) {
		this.expressions.addAll(expressions);
	}
	public void addExpression(ExprStr[] expressions) {
		for(ExprStr e : expressions) {
			this.expressions.add(e);
		}
	}
	public void setPoint(Point p) {
		this.p = p;
	}
	public String getWorldName() {
		return this.worldName;
	}
	public Universe getParentUniverse() {
		return this.parentUniverse;
	}
	public ArrayList<Relation> getRelations(){
		return this.relations;
	}
	public ArrayList<ExprStr> getExpressions(){
		return this.expressions;
	}
	public Point getPoint() {
		return p;
	}
	@Override
	public String toString() {
		return this.worldName;
	}
	
	/*
	 * figure out how this should recurse!!!!
	 */
	/*
	 * if the auxOp is ! then expand to all worlds that are related in the right way
	 * if the auxOps is ? create a new world, expand to that world and set its relation with the current world
	 */
	public void sortExpressions() { // selection sort
		/*
		 * expressions with ? and ~! need to be first, because they both result in new worlds
		 */
		for (int i = 0; i <= this.expressions.size() - 1; i ++) {
			// iterate through the list as many times as the list is long
			for (int j = 0; j <= this.expressions.size() - 2; j ++) {
				ExprStr firstExpression = this.expressions.get(j);
				ExprStr secondExpression = this.expressions.get(j + 1);
				String firstAuxOp1;
				String firstAuxOp2;
				if (firstExpression.getHasAuxOps() != false) { // if either of the expressions has no auxOps, still sort it by giving it a empty string which
					firstAuxOp1 = firstExpression.getAuxOps().get(0); // will have the lowest precedence
				}else {
					firstAuxOp1 = "";
				}
				if (secondExpression.getHasAuxOps() != false) {
					firstAuxOp2 = secondExpression.getAuxOps().get(0);
				}else {
					firstAuxOp2 = "";
				}
				if ((firstAuxOp1.equals("~ ") && firstAuxOp2.equals("")) || // cases where no auxOp comes after anything and needs swapping
						(firstAuxOp1.equals("? ") && firstAuxOp2.equals("")) ||
						(firstAuxOp1.equals("! ") && firstAuxOp2.equals("")) ||
						(firstAuxOp1.equals("~? ") && firstAuxOp2.equals("")) ||
						(firstAuxOp1.equals("~! ") && firstAuxOp2.equals("")) ||
						
						(firstAuxOp1.equals("? ") && firstAuxOp2.equals("~ ")) || // cases where negation comes after !|? needs to be swapped
						(firstAuxOp1.equals("! ") && firstAuxOp2.equals("~ ")) ||
						(firstAuxOp1.equals("~? ") && firstAuxOp2.equals("~ ")) ||
						(firstAuxOp1.equals("~! ") && firstAuxOp2.equals("~ ")) ||
						
						(firstAuxOp1.equals("! ") && firstAuxOp2.equals("? ")) || // cases where ? comes after ! needs to be swapped
						(firstAuxOp1.equals("~? ") && firstAuxOp2.equals("? ")) ||
						(firstAuxOp1.equals("! ") && firstAuxOp2.equals("~! ")) ||
						(firstAuxOp1.equals("~? ") && firstAuxOp2.equals("~! "))) {
					this.expressions.add(j, secondExpression); // swap
					this.expressions.remove(j + 2);
					//System.out.println("Swapped: " + firstExpression + secondExpression);
					//System.out.println(this.expressions);
						
						
					
				}
				
			}
		}
		
	}
	public void sortExpressionsV2() { // selection sort
		/*
		 * expressions with ? and ~! need to be first, because they both result in new worlds
		 */
		for (int i = 0; i <= this.expressions.size() - 1; i ++) {
			// iterate through the list as many times as the list is long
			for (int j = 0; j <= this.expressions.size() - 2; j ++) {
				ExprStr firstExpression = this.expressions.get(j);
				ExprStr secondExpression = this.expressions.get(j + 1);
				
				if ((secondExpression.getPossible() || secondExpression.getNotNecessary()) && 
						(firstExpression.getPossible() == false && firstExpression.getNotNecessary() == false)) {
					this.expressions.add(j, secondExpression); // swap
					this.expressions.remove(j + 2);
					System.out.println("Swapped under possible");
					System.out.println("Swapped: " + firstExpression + ", " + secondExpression);
					//System.out.println(this.expressions);
						
						
					
				}
				if ((secondExpression.getNecessary() || secondExpression.getNotPossible()) &&
						(firstExpression.getHasAuxOps() == false || firstExpression.getNegated())) {
					System.out.println(firstExpression.getIsAtomic());
					this.expressions.add(j, secondExpression); // swap
					this.expressions.remove(j + 2);
					System.out.println("Swapped under necessary");
					System.out.println("Swapped: " + firstExpression  + ", " + secondExpression);
				}
				
			}
		}
		
	}
	/*
	 * revisiting a previous world to see if something could be expanded more is only a problem when there are multiple expressions
	 * but that is going to be most of the time. ONCE YOU HAVE AN OR WITH A ? INSIDE IT AND INSTANTIATE A NEW WORLD 
	 * THE BITS ON THE OTHER SIDE OF THE BRANCH CANNOT ACCESS THAT NEW WORLD
	 */
	public void expandAcrossWorlds() {
		this.sortExpressions();
		
		
		for (ExprStr expression : this.expressions) { // do the expressions with ? as a first auxOp first
			//ExprStr expression = node.getData();
			System.out.println(expression);
			
			
			String str = expression.getString(); // this is the string of the original ExprStr
			char operator = expression.getOperator();
			int operatorIndex = expression.getOperatorIndex();
			ArrayList<String> auxOps = expression.getAuxOps();
			boolean hasAuxOps = expression.getHasAuxOps(); // this is the setup\
			boolean hasModalOp = expression.getHasModalOp();
			boolean negated = expression.getNegated();
			boolean necessary = expression.getNecessary();
			boolean possible = expression.getPossible();
			boolean notNecessary = expression.getNotNecessary();
			boolean notPossible = expression.getNotPossible();
			ExprStr[] parts = expression.getParts();
			boolean expanded = expression.getExpanded(); // to make sure that the same expression isnt expanded twice
			/* expand all with no necessary or possible first, incase there are some necessary or possible inside
			 * if you expand all of the ? second then you will have all the possible worlds to expand the 
			 * ! over, because this function is recursive, for EVERY world, all of the worlds that could be instantiated from that world will
			 * be if ? always comes first, SO FIND A WAY TO EVALUATE THE ? EXPRESSIONS FIRST before !
			 * mabe just sort the list of expressions by ? first somehow
			 */
			
			
			
			if (expanded == false) {
				
				
				
				
				
				
				
			//======================= OLD VVVV
				if (hasAuxOps != false) { // this is the base case, once all auxOps are expanded
					/*
					 * only expand if there are auxOps, cuts down run time
					 */
					String firstAuxOp = auxOps.get(0);
					if (firstAuxOp.equals("! ")) { 
						System.out.println("EXPANDED FOR NECESSARY");
						expression.setExpanded(true);
						str = str.substring(1); // remove the ! from the string ready for reevaluation
						//auxOps.remove(i); // remove the ! from auxOps ready for reevaluation
						ExprStr newExprStr = new ExprStr(str); // reeavaluate the expression for the new world
						/*
						 * each entry in the list of relations first value will always be the current world, so check
						 * to see if the world that it relates to actually would receive data from the current world, rather than just giving it	
						 */
						
						for (Relation r : this.relations) { // for every world original world can see, add the necessary expression
							if (r.getW2Receiving() == true) {
								r.getW2().addExpression(newExprStr); // becuase you are adding to expressions, sometimes things are being evaluated twice
																	 // which shouldnt be -- FIXED --
								r.getW2().expandAcrossWorlds();
							}
						}
						
				
					}else if (firstAuxOp.equals("? ")) {
						System.out.println("EXPANDED FOR POSSIBLE");
						expression.setExpanded(true);
						str = str.substring(1); // remove the ? from the string ready for reevaluation

						ExprStr newExprStr = new ExprStr(str); // reeavaluate the expression for the new world
						World newWorld = new World(this.parentUniverse, newExprStr);
						this.parentUniverse.addWorld(newWorld);
						/*
						 * if (parentUniverse.reflex == true){ blahblahblah
						 */
						//if (parentUniverse.getReflex()) {
							/*
							 * add a relation to itself, and to the new world
							 */
							//this.addRelation(this, true, true); // add reflexive relation to self
							//this.addRelation(newWorld, false, true); // add one way relation to other world
							//newWorld.addRelation(this, true, false); // add newWorlds relation back
						//}
						this.addRelation(newWorld, false, true); // THESE VALUES FOR RECEIVING ONLY FOR STANDARD UNIVERSES!!!!!!
						newWorld.addRelation(this, true, false); // need to add relation for each world
						newWorld.expandAcrossWorlds();
						
					
					}else if(firstAuxOp.equals("~! ")) { // means possible that not, so instantiate new world with negated version
						System.out.println("EXPANDED FOR POSSIBLE");
						expression.setExpanded(true);
						str = '~' + str.substring(2); // remove the ~! from the string ready for reevaluation
						ExprStr newExprStr = new ExprStr(str); // reeavaluate the expression for the new world
						World newWorld = new World(this.parentUniverse, newExprStr);
						this.parentUniverse.addWorld(newWorld);
						this.addRelation(newWorld, false, true); // these values for receiving are only for certain standarad universes!!!!!!
						newWorld.addRelation(this, true, false); // need to add relation for each world
						newWorld.expandAcrossWorlds();
						
					}else if(firstAuxOp.equals("~? ")) { // this is necessary not, add to all worlds negated version
						System.out.println("EXPANDED FOR NECESSARY");
						expression.setExpanded(true);
						str = '~' + str.substring(2);
						ExprStr newExprStr = new ExprStr(str);
						for (Relation r : this.relations) { // for every world original world can see, add the necessary expression
							if (r.getW2Receiving() == true) {
								r.getW2().addExpression(newExprStr);
								r.getW2().expandAcrossWorlds();
							}
						}
					}
					//String firstAuxOp = this.auxOps.get(this.auxOps.size() - 1);
					//int firstAuxOpIndex = this.auxOps.size() - 1;
					
					
				}
			}
			
		}
	}
	public void laneStatement(ExprStr e1, ExprStr e2) {
		System.out.println("Expressions added to world " + this + ": " + e1 + ", " + e2);
		System.out.println("Total expressions at world " + this + ": " + this.expressions);
	}
	public void branchStatement(Universe newU, ExprStr e1, ExprStr e2) {
		System.out.println("Universe split into: " + parentUniverse + " and " + newU);
		System.out.println("Expression added to world " + this + ", in universe " + this.parentUniverse + ": " + e1);
		System.out.println("Total expressions at this world " + this + ": " + this.expressions);
		System.out.println("Expression added to world " + newU.getWorld(this.worldName) + ", in universe " + newU + ": " + e2);
		System.out.println("Total expressions at this world " + newU.getWorld(this.worldName) + ": " + newU.getWorld(this.worldName).expressions);
	}
	public void expandForPossibility(ExprStr e) {
		World w = new World(parentUniverse);
		parentUniverse.addWorld(w);
		w.addExpression(e);
		System.out.println("New world " + w + " instantiated, current expressions: " + w.getExpressions());
		this.addRelation(w, false, true);
		w.addRelation(this, true, false);
		
		if (parentUniverse.getReflex()) {
			w.addRelation(w, true, true);
		}
		if (parentUniverse.getTrans()) {
			for (Relation r : this.relations) {
				if (r.getW1Receiving()) {
					w.addRelation(r.getW2(), true, false);
					r.getW2().addRelation(w, false, true);
				}
			}
		}
		if (parentUniverse.getSymm()) {
			this.addRelation(w, true, false);
			w.addRelation(this, false, true);
		}
		if (parentUniverse.getHereditary()) {
			for (ExprStr exp : this.expressions) {
				if (exp.getHasAuxOps() == false || exp.getNegated()) {
					w.addExpression(exp);
				}
			}
		}
		w.expandAcrossWorldsV2();
	}
	public void expandForNecessity(ExprStr e) {
		for (Relation r : relations) {
			if (r.getW2Receiving()) {
				r.getW2().addExpression(e);
				System.out.println("Relation on " + r + " in universe " + this.parentUniverse + " dictates that " + e + " is expanded to world " + r.getW2());
				r.getW2().expandAcrossWorldsV2();
			}
		}
	}
	public void expandAcrossWorldsV2() {
		System.out.println();
		System.out.println("=======   Expanding at universe: " + parentUniverse + ", at world: " + this + "   =======");
		System.out.println("Unsorted expressions at this world: " + expressions);
		this.sortExpressionsV2();
		System.out.println("Sorted expressions at this world: " + expressions);
		
		try {
			/*
			 * ConcurrentModificationException because often I am adding to the number of expressions 
			 * at a certain world, causing the below loop to bug out
			 */
			for (ExprStr expression : this.expressions) { // do the expressions with ? as a first auxOp first
			
				
				boolean expanded = expression.getExpanded(); 
				
				if (expanded == false) {
					System.out.println("Expanding for unexpanded expression: " + expression);
					expression.setExpanded(true);
					
					String eStr = expression.getString(); // this is the string of the original ExprStr
					char operator = expression.getOperator();
					int operatorIndex = expression.getOperatorIndex();
					ArrayList<String> auxOps = expression.getAuxOps();
					boolean hasAuxOps = expression.getHasAuxOps(); // this is the setup\
					boolean hasModalOp = expression.getHasModalOp();
					boolean negated = expression.getNegated();
					boolean necessary = expression.getNecessary();
					boolean possible = expression.getPossible();
					boolean notNecessary = expression.getNotNecessary();
					boolean notPossible = expression.getNotPossible();
					ExprStr[] parts = expression.getParts();
					
					
					if (operatorIndex == -1 && hasModalOp == false) {
						System.out.println("BASE CASE REACHED");
						return; // this is the base case
					}else {
						if (hasModalOp) {
							if (necessary) {
								ExprStr newE = new ExprStr(eStr.substring(1));
								this.expandForNecessity(newE);
							}else if (notPossible) {
								ExprStr newE = new ExprStr('~' + eStr.substring(2));
								this.expandForNecessity(newE);
							}else if (possible) {
								ExprStr newE = new ExprStr(eStr.substring(1));
								this.expandForPossibility(newE);
							}else if (notNecessary) {
								ExprStr newE = new ExprStr('~' + eStr.substring(2));
								this.expandForPossibility(newE);
							}
						}else {
							/*
							 * for any operation which causes a branch, the left branch is dealt with by the current universe
							 * and a copy universe is instantiated to continue with the right branch. expandAcrossWorlds() has to be
							 * called in the non-Modal operations as well because the expressions at each world must be sorted again
							 * before each pass, which is at the top of the function before the for loop. Make sure to copy the universe FIRST before you add 
							 * the expression to the current universe.
							 */
							if (negated == false) {
								if (operator == 'v'){
									Universe newU = new Universe(parentUniverse);
									newU.setUniverseAsParent();
									
									this.addExpression(parts[0]); // add one half to the current universe							
									
									parentUniverse.getContainer().addUniverse(newU);
									World equivalentWorld = newU.getWorld(this.worldName);
									equivalentWorld.addExpression(parts[1]); // and the other half to the equivalent in the newU
									this.branchStatement(newU, parts[0], parts[1]);
									
									this.expandAcrossWorldsV2();
									equivalentWorld.expandAcrossWorldsV2();
									
								}else if(operator == '^') {
									this.addExpression(parts);
									this.laneStatement(parts[0], parts[1]);
									this.expandAcrossWorldsV2();
									
								}else if(operator == '>') {
									Universe newU = new Universe(parentUniverse);
									newU.setUniverseAsParent();
									
									ExprStr newE = parts[0].addTilda();
									this.addExpression(newE); // add one half negated to the current universe						
									
									parentUniverse.getContainer().addUniverse(newU);
									World equivalentWorld = newU.getWorld(this.worldName);
									equivalentWorld.addExpression(parts[1]); // and the other half to the equivalent in the newU
									branchStatement(newU, newE, parts[1]);
									
									this.expandAcrossWorldsV2();
									equivalentWorld.expandAcrossWorldsV2();
									
								}else if(operator == '=') {
									Universe newU = new Universe(parentUniverse);
									newU.setUniverseAsParent();
									
									ExprStr newE1 = parts[0].addTilda();
									ExprStr newE2 = parts[1].addTilda();
									this.addExpression(parts);
																	
									
									parentUniverse.getContainer().addUniverse(newU);
									World equivalentWorld = newU.getWorld(this.worldName);
									equivalentWorld.addExpression(newE1);
									equivalentWorld.addExpression(newE2);
									
									this.expandAcrossWorldsV2();
									equivalentWorld.expandAcrossWorldsV2();								
									// Now if negation is true ================================
								}
							}else {
								if(operator == 'v') {
									ExprStr newE1 = parts[0].addTilda();
									ExprStr newE2 = parts[1].addTilda();
									this.addExpression(newE1);
									this.addExpression(newE2);
									this.laneStatement(newE1, newE2);
									this.expandAcrossWorldsV2();
									
								}else if(operator == '^') {
									Universe newU = new Universe(parentUniverse);
									newU.setUniverseAsParent();
									
									ExprStr newE1 = parts[0].addTilda();
									ExprStr newE2 = parts[1].addTilda();
									this.addExpression(newE1);
									
									
									parentUniverse.getContainer().addUniverse(newU);
									World equivalentWorld = newU.getWorld(this.worldName);
									equivalentWorld.addExpression(newE2);
									this.branchStatement(newU, newE1, newE2);
									
									this.expandAcrossWorldsV2();
									equivalentWorld.expandAcrossWorldsV2();
									
								}else if(operator == '>') {
									ExprStr newE = parts[1].addTilda();
									this.addExpression(parts[0]);
									this.addExpression(newE);
									this.laneStatement(parts[0], newE);
									this.expandAcrossWorldsV2();
									
								}else if(operator == '=') {
									Universe newU = new Universe(parentUniverse);
									newU.setUniverseAsParent();
									
									ExprStr newE1 = parts[0].addTilda();
									ExprStr newE2 = parts[1].addTilda();
									this.addExpression(parts[0]);
									this.addExpression(newE2);
									
									
									parentUniverse.getContainer().addUniverse(newU);
									World equivalentWorld = newU.getWorld(this.worldName);
									equivalentWorld.addExpression(newE1);
									equivalentWorld.addExpression(parts[1]);
									
									this.expandAcrossWorldsV2();
									equivalentWorld.expandAcrossWorldsV2();
									
									
								}
							}
						}
					}
					
					
					
				
					
				}else {
					System.out.println("Expression already expanded: " + expression);
				}
				
				
			}
		}catch(ConcurrentModificationException e) {
			System.out.println(e);
			return;
		}
		
	}
}
