import java.util.*; 
public class Logics_main {
	
	public static void main(String[] args) {
		//String_1 prop = new String_1("~((q v r) v (r ^ ~r))");
		//String_1 prop = new String_1("~(p v q)");
		//String_1 prop = new String_1("~r > (q ^ r)");
		//String_1 prop = new String_1("r > q");
		//String_1 prop = new String_1("~((q v r) v (r ^ ~r)) = r");
		
		//String_1 prop = new String_1("~p");
		String_1 prop = new String_1("p ^ ((q v ~q) ^ (p ^ q))");
		//String_1 prop = new String_1("p v (q = ~r)");
		//String_1 prop = new String_1("(p v (p ^ ~p)) v t");
		//String_1 prop = new String_1("p v ~p");
		Tuple tuple = prop.find_operator();
		String_1[] parts = prop.find_parts(tuple.index);
		for (String_1 str : parts) {
			System.out.println(str);
		}
		
		prop = prop.add_tilda(tuple.index);
		Tree x = new Tree(prop);
		x.evaluate_expression();
		boolean validity = x.CL_val();
		x.print_tree(x, 4);
		
		
		
	}	
}
