import java.util.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModalModeller extends JFrame implements ActionListener, ItemListener{
	private UniverseContainer uContainer;
	private ArrayList<ExprStr> expressions;
	private ArrayList<Universe> universes;
	
	private JPanel leftPanel, rightPanel, buttonPanel, propsPanel, displayPanel;
	private JLabel inputL, buttonsL, propsL, universesL;
	private JTextField inputField;
	private JButton generateButton, clearButton;
	private JRadioButton reflexB, transB, symmB, hereditaryB;
	private JComboBox universesCombo;
	private Font font;
	
	private boolean reflexive;
	private boolean transitive;
	private boolean symmetric;
	private boolean hereditary;
	
	public ModalModeller() {
		super("Modal Modeller"); 
		
		uContainer = new UniverseContainer();
		expressions = new ArrayList<ExprStr>(); 
		universes = new ArrayList<Universe>();
		
		//setLayout(new GridBagLayout());
		setSize(1100, 650);
		setLayout(new GridLayout(0, 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		font = new Font("Sans-serif", Font.PLAIN, 20);
		
		
		setUpPanels();
		setVisible(true);
	}
	public void setUpTextInputField() {
		inputL = new JLabel("Enter a Proposition: ");
		inputField = new JTextField(20);
		inputField.addActionListener( // anonymous inner class
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String s = inputField.getText();
						ExprStr inputExpression = new ExprStr(s);
						//System.out.println("ADDED PROP");
						expressions.add(inputExpression);
						inputField.setText(""); // clears the input field to enter more props
						updatePropsPanel(s);
						//System.out.println(s);
					}
				});
		//add(inputField);
	}
	public void setUpGenerateButton(){
		generateButton = new JButton("Generate Model");
		generateButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * put the whole thing here
						 */
						/*displayPanel.removeAll();
						universes = new ArrayList<Universe>(); // make sure universes from old generations are not included
						
						
						Universe u = new Universe(reflexive, transitive, symmetric, hereditary);
						World w = new World(u);
						u.addWorld(w);
						Tree t = new Tree();
						t.setRoot(t);
						
						//ExprStr conclusion = expressions.get(expressions.size() - 1).addTilda(); // negating the conclusion
						//expressions.remove(expressions.size() - 1);
						//expressions.add(conclusion);
						t.setData(expressions, 0, expressions.size());
						t.setInitialWorlds(w);
						t.setInitialUniverses(u);
						t.evaluateModalExpressionV2(u);
						//t.printTree(t, 4);
						
						ArrayList<Tree> childlessNodes = new ArrayList<Tree>();
						childlessNodes = t.findChildlessNodes(childlessNodes);
						for (Tree node : childlessNodes) {
							node.addExpressionsToWorlds(node.getUniverse());
							universes.add(node.getUniverse());
							System.out.println("Node: " + node + " Universe: " + node.getUniverse());
							for (World world : node.getUniverse().getWorlds()) {
								System.out.println(world.getExpressions());
								System.out.println("RELATIONS BEFORE DRAWING: " + world.getRelations());
							}
							System.out.println();
						}
						updateUniversesCombo();*/
						
						
						
						
						
						Universe testU = new Universe(uContainer);
						World w1 = new World(testU);
						World w2 = new World(testU);
						testU.addWorld(w1);
						testU.addWorld(w2);
						w1.addRelation(w2, false, true);
						
						Universe testCopyU = new Universe(testU);
						testCopyU.setUniverseAsParent();
						
						System.out.println("ORIGINAL WORLDS: " + testU.getWorlds());
						System.out.println("COPY WORLDS: " + testCopyU.getWorlds());
						
						System.out.println("ORIGINAL RELATION: " + testU.getWorlds().get(0).getRelations().hashCode());
						System.out.println("COPY RELATION: " + testCopyU.getWorlds().get(0).getRelations().hashCode());
						
						/*
						 * THE ABOVE PROVES THAT THE COPYING FUNCTION IS NOT WORKNG AS INTENDED
						 */
						
						
						
						displayPanel.removeAll();
						uContainer = new UniverseContainer();
						
						Universe u = new Universe(uContainer, reflexive, transitive, symmetric, hereditary);
						uContainer.addUniverse(u);
						
						World w = new World(u);
						u.addWorld(w);
						/*
						 * need to add reflexivity manually here because otherwise it is only added when worlds are expanded
						 * upon. The other universe properties only matter when there is more than one world, so those
						 * can all be added in the expandForPossibility() method in World
						 */
						if (reflexive) { 
							w.addRelation(w, true, true);
						}
						
						//ExprStr conclusion = expressions.get(expressions.size() - 1).addTilda(); // negating the conclusion
						//expressions.remove(expressions.size() - 1);
						//expressions.add(conclusion);
						w.addExpression(expressions);
						w.expandAcrossWorldsV2();
						for (Universe uni : uContainer.getUniverses()) {
							System.out.println("Universe " + uni);
						}
						System.out.println("");
						
						
						//t.printTree(t, 4);
						
						
						updateUniversesCombo();
						generateButton.setEnabled(false);
						
					}
				});
	}
	public void setUpPropsPanel() {
		
		propsPanel = new JPanel();
		propsPanel.setLayout(new GridBagLayout());
		propsPanel.setBackground(Color.white);
		propsL = new JLabel("Current Propositions:");
		
	}
	public void updatePropsPanel(String s) {
		JLabel newPropL = new JLabel(s);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.CENTER;
		propsPanel.add(newPropL, constraints);
		propsPanel.revalidate();
		propsPanel.repaint();
	}
	public void setUpButtons() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		GridBagConstraints labelConstraints = new GridBagConstraints();
		
		buttonsL = new JLabel("Universe Parameters");
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.insets = new Insets(0, 0, 10, 0);
		labelConstraints.gridwidth = 2;

		labelConstraints.anchor = GridBagConstraints.WEST;
		buttonPanel.add(buttonsL, labelConstraints);
		
		labelConstraints = new GridBagConstraints(); // reset label constraints
		
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 1;
		
		buttonConstraints.anchor = GridBagConstraints.WEST;
		labelConstraints.gridx = 1;
		labelConstraints.gridy = buttonConstraints.gridy;
		
		labelConstraints.anchor = GridBagConstraints.WEST;
		
		reflexB = new JRadioButton();
		reflexB.addActionListener(this);
		reflexB.setFont(font);
		JLabel reflexL = new JLabel("Reflexive"); // can have with int as well for hor alignment
		
		transB = new JRadioButton();
		transB.addActionListener(this);
		transB.setFont(font);
		JLabel transL = new JLabel("Transitive");
		
		symmB = new JRadioButton();
		symmB.addActionListener(this);
		symmB.setFont(font);
		JLabel symmL = new JLabel("Symmetric");
		
		hereditaryB = new JRadioButton();
		hereditaryB.addActionListener(this);
		hereditaryB.setFont(font);
		JLabel hereditaryL = new JLabel("Hereditary");
		
		buttonPanel.add(reflexB, buttonConstraints);
		buttonPanel.add(reflexL, labelConstraints);
		buttonConstraints.gridy = 2;
		labelConstraints.gridy = 2;
		
		buttonPanel.add(transB, buttonConstraints);
		buttonPanel.add(transL, labelConstraints);
		buttonConstraints.gridy = 3;
		labelConstraints.gridy = 3;
		
		buttonPanel.add(symmB, buttonConstraints);
		buttonPanel.add(symmL, labelConstraints);
		buttonConstraints.gridy = 4;
		labelConstraints.gridy = 4;
		
		buttonPanel.add(hereditaryB, buttonConstraints);
		buttonPanel.add(hereditaryL, labelConstraints);
	}
	public void setUpClearButton(){
		clearButton = new JButton("Clear All");
		clearButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						reflexB.setSelected(false); // clear all of the universe properties
						reflexive = false;
						transB.setSelected(false);
						transitive = false;
						symmB.setSelected(false);
						symmetric = false;
						hereditaryB.setSelected(false);
						hereditary = false;
						
						expressions = new ArrayList<ExprStr>(); // reset expressions
						propsPanel.removeAll(); // remove display of expressions
						propsPanel.revalidate();
						propsPanel.repaint();
						
						universes = new ArrayList<Universe>();
						displayPanel.removeAll();
						displayPanel.revalidate();
						displayPanel.repaint();
						
						universesCombo.removeAllItems();
						universesCombo.revalidate();
						universesCombo.repaint();
						/*
						 * removing all from the combo doesnt seem to visually
						 * remove all of the components
						 */
						generateButton.setEnabled(true);
					}
				});
	}
	public void setUpUniversesCombo() {
		universesL = new JLabel("Possible Universes");
		universesCombo = new JComboBox();
		universesCombo.setModel((MutableComboBoxModel) new DefaultComboBoxModel());
		universesCombo.setMaximumRowCount(20);
		universesCombo.addItemListener(this);
		universesCombo.setFont(font);
		
	}
	public void updateUniversesCombo() {
		universesCombo.removeAllItems();
		for (Universe u : uContainer.getUniverses()) {
			universesCombo.addItem(u);
		}
		System.out.println("SELECTED U: " + (Universe) universesCombo.getSelectedItem());
		UniverseDisplay ud = new UniverseDisplay((Universe) universesCombo.getSelectedItem(), displayPanel);
		displayPanel.removeAll();
		displayPanel.add(ud);
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	public void setUpDisplayPanel() {
		displayPanel = new JPanel();
		displayPanel.setLayout(new BorderLayout());
	}
	
	public void setUpPanels() {
		setUpTextInputField();
		setUpGenerateButton();
		setUpPropsPanel();
		setUpButtons();
		setUpClearButton();
		setUpUniversesCombo();
		setUpDisplayPanel();
		
		
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setLayout(new GridBagLayout());
		rightPanel.setLayout(new GridBagLayout());
		
		leftPanel.setBackground(Color.gray);
		rightPanel.setBackground(Color.white);
		/*
		 * create a new constraints for each new component, to avoid bugs
		 */
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		leftPanel.add(inputL, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		leftPanel.add(inputField, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 15, 0, 0);
		leftPanel.add(generateButton, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		leftPanel.add(propsL, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		leftPanel.add(propsPanel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.ipadx = 30;
		constraints.ipady = 70;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 15, 0, 0);
		leftPanel.add(buttonPanel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.WEST;
		leftPanel.add(clearButton, constraints);
		
		// ====================
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 0, 0, 0);
		rightPanel.add(universesL, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipadx = 150;
		constraints.insets = new Insets(10, 0, 0, 0);
		rightPanel.add(universesCombo, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		//constraints.weighty = 0.5;
		constraints.ipady = 400;
		constraints.ipadx = 400;
		constraints.insets = new Insets(10, 10, 10, 10);
		//constraints.fill = GridBagConstraints.BOTH;
		//displayPanel.add(new JLabel("POOP"));
		rightPanel.add(displayPanel, constraints);
		
		add(leftPanel);
		add(rightPanel);
	}
	public void actionPerformed(ActionEvent e) {
		if (reflexB.isSelected() == true) {
			reflexive = true;
			System.out.println("R");
		}else {
			reflexive = false;
			System.out.println("NOT R");
		}
		if (transB.isSelected() == true) {
			transitive = true;
			System.out.println("T");
		}else {
			transitive = false;
		}
		if (symmB.isSelected() == true) {
			symmetric = true;
			System.out.println("S");
		}else {
			symmetric = false;
		}
		if (hereditaryB.isSelected() == true) {
			hereditary = true;
			System.out.println("H");
		}else {
			hereditary = false;
		}
	}
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			UniverseDisplay ud = new UniverseDisplay((Universe) universesCombo.getSelectedItem(), displayPanel);
			displayPanel.removeAll();
			displayPanel.add(ud);
			displayPanel.revalidate();
			displayPanel.repaint();
		}
	}
}
