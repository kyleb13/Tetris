/**
 * 
 */
package view;

import model.Point;

/**
 * @author kbev4
 * @version .
 */
public enum ConvertedPieces {
    /** */
    I(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)),

    /** */
    J(new Point(0, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2)),

    /** */
    L(new Point(2, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2)),

    /** */
    O(new Point(1, 2), new Point(2, 2), new Point(1, 1), new Point(2, 1)),

    /** */
    S(new Point(0, 2), new Point(1, 2), new Point(1, 1), new Point(2, 1)),

    /** */
    T(new Point(1, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2)),

    /** */
    Z(new Point(1, 2), new Point(2, 2), new Point(0, 1), new Point(1, 1));
    
    /**
     * 
     */
    private Point[] myPoints;
    
    /**
     * 
     * @param p1 .
     */
    ConvertedPieces(final Point... p1) {
        myPoints = p1;
    }
    
    /**
     * 
     * @return .
     */
    public Point[] getPoints() {
        return myPoints.clone();
    }
}
