import java.util.*; 
import javax.swing.*;

public class Logics_main implements Runnable {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Logics_main());
		/*
		//ExprStr s = new ExprStr("~!(~p ^ !~?((q v ~q) ^ (p ^ q)))");
		//ExprStr s = new ExprStr("~((q v r) v (r ^ ~r))");
		//ExprStr s = new ExprStr("(q v s) ^ (p ^ ~r)");
		//ExprStr s = new ExprStr("~(p v q)");
		//ExprStr s = new ExprStr("~r > (q ^ r)");
		//ExprStr s = new ExprStr("r > q");
		//ExprStr s = new ExprStr("~((q v r) v (r ^ ~r)) = r");
		//ExprStr s = new ExprStr("!(p ^ ?r) v ((!~p = ~q) ^ (p > ~q))");
		//ExprStr s = new ExprStr("p v ~p");
		//ExprStr s = new ExprStr("?(~p ^ !~?((q v ~q) ^ (p ^ q)))");
		ExprStr s = new ExprStr("!(p v ~p)");
		//ExprStr s = new ExprStr("q v ?(p v ~p)");
		//ExprStr s1 = new ExprStr("?(p v ~p)");
		ExprStr s2 = new ExprStr("??(!p v ~?q)");
		//ExprStr s3 = new ExprStr("!(!r v ?p)");
		ExprStr s4 = new ExprStr("!(s v ~r)");
		//ExprStr s5 = new ExprStr("??p");
		//ExprStr s6 = new ExprStr("?p");
		//ExprStr s7 = new ExprStr("!p");
		//ExprStr s8 = new ExprStr("~!p");
		//ExprStr s9 = new ExprStr("!(q v ?p)");
		/*
		 * this is an example of where some of the cl logic needs to be deconstructed before we can
		 * continue expanding across worlds so maybe a expression with no auOps needs to be deconstructed first
		 */
		//ExprStr s10 = new ExprStr("p");
		//ExprStr s11 = new ExprStr("~?p");
		//s = s.addTilda();
			
		
		/*
		 * get all the expressions, add the tilda to the conclusion
		 * then have a loop which expands each string across worlds
		 * then evaluate each expression at each of the worlds, check for contradictions
		 */
		/*		
		Universe u = new Universe();
		World w = new World(u);
		u.addWorld(w);
		Tree t = new Tree();
		t.setRoot(t);
		ArrayList<ExprStr> expressions = new ArrayList<ExprStr>(); 
		expressions.add(s);
		expressions.add(s4);
		t.setData(expressions, 0, expressions.size());
		t.setInitialWorlds(w);
		t.setInitialUniverses(u);
		//t.setInitialWorldData();
		t.evaluateModalExpressionV2(u);
		t.printTree(t, 4);
				
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		ArrayList<Tree> childlessNodes = new ArrayList<Tree>();
		childlessNodes = t.findChildlessNodes(childlessNodes);
		for (Tree node : childlessNodes) {
			node.addExpressionsToWorlds(node.getUniverse());
			System.out.println("Node: " + node + " Universe: " + node.getUniverse());
			for (World world : node.getUniverse().getWorlds()) {
				System.out.println(world.getExpressions());
			}
			System.out.println();
		}*/
	}
	public void run() {
		ModalModeller m = new ModalModeller();
	}
		/*ArrayList<String> test = new ArrayList<String>();
		if (test == null) {
			System.out.println("NULL");
		}else {
			System.out.println("NOT NULL");
		}
		if (test.size() == 0) {
			System.out.println("SIZE 0");
		}else {
			System.out.println("SIZE > 0");
		}*/
		
		//String_1 prop = new String_1("~p");
		//String_1 prop3 = new String_1("~!(~p ^ !~?((q v ~q) ^ (p ^ q)))");
		//String_1[] bits = prop3.find_parts(6);
		//System.out.println(bits[1]);
		//System.out.println(bits[0]);
		//ArrayList<String> str = prop3.find_aux_ops(start_index, atomic);
		
		// ============================
		/*String_1 prop = new String_1("~!(~p ^ !~?((q v ~q) ^ (p ^ q)))");
		String_1 prop_2 = new String_1("~!(~p)");

		Prop prop1 = prop.construct_prop();
		System.out.println(prop1.get_props()[0]);
		System.out.println(prop1.get_props()[1].get_props()[0].get_operator());
		System.out.println(prop1);*/
		//==========================
		
		/*System.out.println(prop1.get_aux_ops());
		System.out.println(prop1 + "HELLO");
		String printed = prop1.print_prop();
		System.out.println(printed + "hellllloooo");
		System.out.println(prop1.get_props());*/
		
		//String_1 prop = new String_1("p v (q = ~r)");
		//String_1 prop = new String_1("(p v (p ^ ~p)) v t");
		//String_1 prop = new String_1("p v ~p");
		
		/*Tuple tuple = prop.find_operator();
		String_1[] parts = prop.find_parts(tuple.index);
		for (String_1 str : parts) {
			System.out.println(str);
		}
		
		prop = prop.add_tilda(tuple.index);
		Tree x = new Tree(prop);
		x.evaluate_expression();
		boolean validity = x.CL_val();
		x.print_tree(x, 4);*/
		
		
		
		

}
