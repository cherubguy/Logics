
public class Prop {
	private char operator = ' ';
	private boolean negation;
	private boolean necessary;
	private boolean possible;
	private Prop[] props;
	private char atom;
	
	public Prop() {
		
	}
	public Prop(Prop prop_1, Prop prop_2, char operator, boolean negation, boolean necessary, boolean possible) { // construct proposition with operands and operator
		this.props[0] = prop_1;
		this.props[1] = prop_2;
		this.operator = operator;
		this.negation = negation;
		this.necessary = necessary;
		this.possible = possible;
	}
	public Prop(char prop, boolean negation, boolean necessary, boolean possible) { // construct proposition with single atom
		this.atom = prop;	
		this.negation = negation;
		this.necessary = necessary;
		this.possible = possible;
	}
}
