import java.util.*;
import javax.swing.*;
import java.awt.*;

public class UniverseDisplay extends JComponent{
	private JPanel parentPanel;
	private Universe universe;
	private int worldCount;
	private Rectangle outerRect, innerRect;
	private ArrayList<Point> universeShape;
	private Polygon universePolygon;
	
	public UniverseDisplay() {
		
	}
	public UniverseDisplay(Universe u, JPanel p) {
		parentPanel = p;
		universe = u;
		worldCount = u.getWorldCount();
		universeShape = new ArrayList<Point>();
	}
	public void createUniverseShape(int vertices, double angleOffset, Rectangle r) {
		if (vertices < 1) throw new IllegalArgumentException ("Vertices must be > 0");
	    double step = 2 * Math.PI / vertices;
	    int[] x = new int[vertices];
	    int[] y = new int[vertices];
	    int xrad = r.width / 2;
	    int yrad = r.height / 2;
	    for (int i = 0; i < vertices; i++) {
	        x[i] = r.x + xrad + (int) (Math.cos(angleOffset + i * step) * xrad);
	        y[i] = r.y + yrad + (int) (Math.sin(angleOffset + i * step) * yrad);
	        Point newP = new Point(x[i], y[i]);
	        //universeShape.add(newP);
	        universe.getWorlds().get(i).setPoint(newP);
	        //System.out.println(universe.getWorlds().get(i) + " set Point: " + newP);
	    }
	    //Polygon p = new Polygon(x, y, vertices);
	    //return p;
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		g2.setColor(Color.black);
		int height = this.getPreferredSize().height - 1;
		int width = this.getPreferredSize().width - 1;
		g2.drawRect(0, 0, width, height);
		g2.drawRect(15, 15, width - 30, height - 30);
		innerRect = new Rectangle(60, 60, width - 120, height - 120); // 15 px in from either side
		if (worldCount >= 2) {
			// create polygon
			//System.out.println("MULTIPLE POINTS");
		}else if (worldCount == 1) {
			// create point
			//System.out.println("ONE POINT");
		}
		//universeShape = new ArrayList<Point>(); // reset all the points
		//System.out.println("UNIVERSE IN METHOD BEFORE SHAPE CREATED: " + universe);
		createUniverseShape(worldCount, 60.0, innerRect);
		for (int i = 0; i < universe.getWorlds().size(); i ++) {
			Point p = universe.getWorlds().get(i).getPoint();
			World w = universe.getWorlds().get(i);
			int gap = 15;
			char[] worldName = w.getWorldName().toCharArray();
			g2.drawChars(worldName, 0, worldName.length, p.x, p.y);
			for (ExprStr e : w.getExpressions()) {
				char[] chars = e.getString().toCharArray();
				g2.drawChars(chars, 0, chars.length, p.x, p.y + gap);
				gap += 15;
			}
		}
		//System.out.println("UNIVERSE IN METHOD AFTER SHAPE CREATED: " + universe);
		for (World w : universe.getWorlds()) {
			for (Relation r : w.getRelations()) {
				//System.out.println(r);
				RelationDisplay rd = new RelationDisplay(r);
				g2.drawLine(rd.getW1P().x, rd.getW1P().y, rd.getW2P().x, rd.getW2P().y);
				//System.out.println(rd.getW1P().x);
				//System.out.println(rd.getW1P().y);
				//System.out.println(rd.getBleh().x);
				//System.out.println(rd.getBleh().y);
				if (r.getW1Receiving()) {
					//g2.drawLine(rd.getW1P().x, rd.getW1P().y, rd.getW1ArrowHeads()[0].x, rd.getW1ArrowHeads()[0].y);
					//g2.drawLine(rd.getW1P().x, rd.getW1P().y, rd.getW1ArrowHeads()[1].x, rd.getW1ArrowHeads()[1].y);
					g2.setColor(Color.darkGray);
					g2.fill(rd.getArrow1());
					
					g2.draw(rd.getArrow1());
				}
				if (r.getW2Receiving()) {
					//g2.drawLine(rd.getW2P().x, rd.getW2P().y, rd.getW2ArrowHeads()[0].x, rd.getW2ArrowHeads()[0].y);
					//g2.drawLine(rd.getW2P().x, rd.getW2P().y, rd.getW2ArrowHeads()[1].x, rd.getW2ArrowHeads()[1].y);
					g2.setColor(Color.darkGray);
					g2.fill(rd.getArrow2());
					g2.draw(rd.getArrow2());
				}
				if (universe.getReflex()) {
					//g2.draw(rd.getCyclicalArrowArc());
					g2.drawArc((int) rd.getCyclicalArrowArc().getX(), (int) rd.getCyclicalArrowArc().getY(), (int) rd.getCyclicalArrowArc().getWidth(), 
							(int) rd.getCyclicalArrowArc().getHeight(), (int) rd.getCyclicalArrowArc().getAngleStart(), (int) rd.getCyclicalArrowArc().getAngleExtent());
					g2.setColor(Color.darkGray);
					g2.fill(rd.getCyclicalArrowHead());
					g2.draw(rd.getCyclicalArrowHead());
				}
				 // FIX THIS
				
			}
		}
		//g.drawPolygon(universePolygon);
		//System.out.println("PAINTED");
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(parentPanel.getBounds().width, parentPanel.getBounds().height);
	}
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(100, 100);
	}
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(500, 500);
	}
}
