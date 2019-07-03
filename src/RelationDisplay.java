import java.util.*;
import java.awt.*;
import java.awt.Shape;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;



public class RelationDisplay {
	private Point tmp1, tmp2, w1, w2, w1ArrowSegmentPoint, w2ArrowSegmentPoint, midPoint;
	private Point[] w1ArrowHeads, w2ArrowHeads;
	private Shape arrow1, arrow2; // these may or may not be instantiated
	Polygon cyclicalArrowHead;
	Arc2D.Double cyclicalArrowArc;
	private double totalDistance;
	
	public RelationDisplay() {
		
	}
	public RelationDisplay(Relation r) {
		
		tmp1 = r.getW1().getPoint();
		tmp2 = r.getW2().getPoint();
		//if (!(tmp1.x == tmp2.x) && !(tmp1.y == tmp2.y)) { // if the line is not straight
		double xDiff = tmp2.x - tmp1.x;
		double yDiff = tmp2.y - tmp1.y;
		totalDistance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		double distanceRatio = 20 / totalDistance; // i want the line to start 20 px down the line
		double newX1 = (1 - distanceRatio) * tmp1.x + distanceRatio * tmp2.x;
		double newY1 = (1 - distanceRatio) * tmp1.y + distanceRatio * tmp2.y;
		w1 = new Point((int) newX1, (int) newY1);
		
		double newX2 = (1 - distanceRatio) * tmp2.x + distanceRatio * tmp1.x;
		double newY2 = (1 - distanceRatio) * tmp2.y + distanceRatio * tmp1.y;
		w2 = new Point((int) newX2, (int) newY2);
		
		midPoint = new Point((int)((tmp1.x + tmp2.x)/2.0), 
                (int)((tmp1.y + tmp2.y)/2.0));
		
		if (r.getW1Receiving()) {
			
			//w1ArrowHeads = findArrowHeads(w1, tmp1, tmp2);
			arrow1 = createArrowShape(w2, w1);
			
			
		}
		if (r.getW2Receiving()) {
			//w2ArrowHeads = findArrowHeads(w2, tmp2, tmp1);
			arrow2 = createArrowShape(w1, w2);
			
			
			
		}
		createCyclicalArrowShape(w1);
		/*
		 * just do this once for one of the worlds because we are going
		 * to cycle through all of them anyway
		 */
		
		
	}
	public Point getW1P() {
		return w1;
	}
	public Point getW2P() {
		return w2;
	}
	public Point getW1ArrowSegment() {
		return w1ArrowSegmentPoint;
	}
	public Point getW2ArrowSegment() {
		return w2ArrowSegmentPoint;
	}
	public Point getMidPoint() {
		return midPoint;
	}
	public Point[] getW1ArrowHeads() {
		return w1ArrowHeads;
	}
	public Point[] getW2ArrowHeads() {
		return w2ArrowHeads;
	}
	public Shape getArrow1() {
		return arrow1;
	}
	public Shape getArrow2() {
		return arrow2;
	}
	public Polygon getCyclicalArrowHead() {
		return cyclicalArrowHead;
	}
	public Arc2D.Double getCyclicalArrowArc() {
		return cyclicalArrowArc;
	}
	public Point[] findArrowHeads(Point w, Point p1, Point p2) {
		double distanceRatio = 40 / totalDistance; // new distance ratio for length along line which arrowhead will finish
		double newX1 = (1 - distanceRatio) * p1.x + distanceRatio * p2.x;
		double newY1 = (1 - distanceRatio) * p1.y + distanceRatio * p2.y;
		Point arrowSegmentPoint = new Point((int) newX1, (int) newY1);
		double xDiff = w.x - arrowSegmentPoint.x;
		double yDiff = w.y - arrowSegmentPoint.y;
		
		double angleInDegrees = 35.0;
		double angleInRadians = angleInDegrees * (Math.PI / 180.0);
		double adjacentSide = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		
		double oppositeSide = adjacentSide * Math.tan(angleInRadians);
		
		double hypotenuse = Math.sqrt(adjacentSide * adjacentSide + oppositeSide * oppositeSide);
		
		double[] displacement = getThirdPointDisplacement(adjacentSide, oppositeSide, hypotenuse);
		
		Point[] arrowHeads = new Point[2];
		double arrow1NewX = arrowSegmentPoint.x + displacement[0];
		double arrow1NewY = arrowSegmentPoint.y + displacement[1];
		double arrow2NewX = arrowSegmentPoint.x - displacement[0];
		double arrow2NewY = arrowSegmentPoint.y - displacement[1];
		System.out.println(arrow1NewX);
		System.out.println(arrow1NewY);
		System.out.println(arrow2NewX);
		System.out.println(arrow2NewY);
		Point newP1 = new Point((int) arrow1NewX, (int) arrow1NewY);
		Point newP2 = new Point((int) arrow2NewX, (int) arrow2NewY);
		System.out.println(newP1.hashCode());
		System.out.println(newP2.hashCode());
		
		arrowHeads[0] = newP1;
		arrowHeads[1] = newP2;
		return arrowHeads;
	}
	public Point[] findArrowHeadsV2(Point a, double length) {
		double newNegY = a.y - length;
		double newPosY = a.y + length;
		Point pPos = new Point((int) a.x, (int) newPosY);
		Point pNeg = new Point((int) a.x, (int) newNegY);
		Point[] result = new Point[2];
		result[0] = pPos;
		result[1] = pNeg;
		return result;
	}
	
	/*
	 * C is the unknown Point, A and B are known, the resulting x and y values are the displacement from
	 * either of the original two Points, there will be four solutions 
	 */
	public double[] getThirdPointDisplacement(double AB, double AC, double BC) {
		
		double y = ((AB * AB) + (AC * AC) - (BC * BC)) / (2 * AB);
		System.out.println("In getThirdPointDisplacement() y: " + y);
		double x = Math.sqrt(AC * AC - y * y);
		System.out.println("In getThirdPointDisplacement() x: " + x);
		double[] displacement = new double[] {x, y};
		return displacement;
	}
	
	public void createCyclicalArrowShape(Point w) {
		Point centerPoint = new Point(w.x, w.y - 40); // a point 40 px above the world info
		
		
		Rectangle2D r = new Rectangle2D.Double(centerPoint.x - 20, centerPoint.y - 20, 40, 40);
		cyclicalArrowArc = new Arc2D.Double(r, 0.0, 270.0, 1);
		cyclicalArrowHead = new Polygon();
		cyclicalArrowHead.addPoint(centerPoint.x + 20,  centerPoint.y);
		cyclicalArrowHead.addPoint(centerPoint.x + 40,  centerPoint.y);
		
		cyclicalArrowHead.addPoint(centerPoint.x + 20,  w.y - 20);
		cyclicalArrowHead.addPoint(centerPoint.x,  centerPoint.y);
		Area area = new Area();
		Area arcArea = new Area(cyclicalArrowArc);
		Area arrowArea = new Area(cyclicalArrowHead);
		area.add(arcArea);
		area.add(arrowArea);
		//return area;
		
		
	}
	public Shape createArrowShape(Point fromPt, Point toPt) {
		double distanceRatio = 40 / totalDistance; // new distance ratio for length along line which arrowhead will finish
		double newX1 = (1 - distanceRatio) * fromPt.x + distanceRatio * toPt.x;
		double newY1 = (1 - distanceRatio) * fromPt.y + distanceRatio * toPt.y;
		Point newFromPt = new Point((int) newX1, (int) newY1);
		
		 Polygon arrowPolygon = new Polygon();
		    arrowPolygon.addPoint(-6,1);
		    arrowPolygon.addPoint(3,1);
		    arrowPolygon.addPoint(3,3);
		    arrowPolygon.addPoint(6,0);
		    arrowPolygon.addPoint(3,-3);
		    arrowPolygon.addPoint(3,-1);
		    arrowPolygon.addPoint(-6,-1);


		    midPoint = midpoint(newFromPt, toPt);

		    double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

		    AffineTransform transform = new AffineTransform();
		    transform.translate(midPoint.x, midPoint.y);
		    double ptDistance = fromPt.distance(toPt);
		    double scale = ptDistance / 48.0; // 12 because it's the length of the arrow polygon.
		    transform.scale(scale, scale);
		    transform.rotate(rotate);

		    return transform.createTransformedShape(arrowPolygon);
	}
	public Point midpoint(Point p1, Point p2) {
	    return new Point((int)((p1.x + p2.x)/2.0), 
	                     (int)((p1.y + p2.y)/2.0));
	}
}
