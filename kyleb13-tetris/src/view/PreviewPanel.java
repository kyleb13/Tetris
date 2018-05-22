/**
 * 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.border.StrokeBorder;
import model.TetrisPiece;

/**
 * @author Kyle Beveridge
 * @version .
 */
public class PreviewPanel extends JPanel implements Observer {

    /**
     * 
     */
    private static final long serialVersionUID = 1925792094333100614L;
    
    /**
     * 
     */
    private static final int GRID_SIZE = 6;
    
    /**
     * 
     */
    private static final int SCALE = 20;
    
    /**
     * 
     */
    private static final double HALF = SCALE / 2;
    
    /**
     * 
     */
    private TetrisPiece myPiece;
    
    /**
     * 
     */
    public PreviewPanel() {
        super();
        this.setBorder(new StrokeBorder(new BasicStroke(2), Color.RED));
        myPiece = TetrisPiece.I;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCALE * GRID_SIZE, SCALE * GRID_SIZE);
    }
    
    @Override
    public void paintComponent(final Graphics g1) {
        super.paintComponent(g1);
        final Graphics2D g2d = (Graphics2D) g1;
        g2d.setColor(Color.BLACK);
        final Point[] pts = centerShape();
        for (int i = 0; i < pts.length; i++) {
            final Point p = pts[i];
            g2d.fillRect((int) p.getX(), (int) p.getY(), SCALE, SCALE);
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect((int) p.getX(), (int) p.getY(), SCALE, SCALE);
            g2d.setColor(Color.BLACK);
        }
    }
    
    /**
     * 
     * @return .
     */
    private Point[] centerShape() {
        model.Point[] p2 = new model.Point[2 + 2];
        p2 = newPts();
        final Point[] out = new Point[p2.length];
        int cnt = 0;
        for (int i = p2.length - 1; i >= 0; i--) {
            final Point current = convertPoint(p2[i]);
            switch (myPiece) {
                case I:
                    current.setLocation(current.getX() + SCALE, current.getY() + HALF);
                    break;
                case J:
                    current.setLocation(current.getX() + SCALE + HALF, current.getY() + SCALE);
                    break;
                case L:
                    current.setLocation(current.getX() + SCALE + HALF, current.getY() + SCALE);
                    break;
                case O:
                    current.setLocation(current.getX() + SCALE, current.getY() + SCALE);
                    break;
                case S:
                    current.setLocation(current.getX() + SCALE + HALF, current.getY() + SCALE);
                    break;
                case T:
                    current.setLocation(current.getX() + SCALE + HALF, current.getY() + SCALE);
                    break;
                case Z: 
                    current.setLocation(current.getX() + SCALE + HALF, current.getY() + SCALE);
                    break;
                default:
                    break;
            }
            out[cnt++] = (Point) current.clone();
        }
        return out;
    }
    
    /**
     * 
     * @return .
     */
    private model.Point[] newPts() {
        model.Point[] p2 = new model.Point[2 + 2];
        if (myPiece == TetrisPiece.I) {
            p2 = TetrisPiece.I.getPoints();
        } else if (myPiece == TetrisPiece.O) {
            p2 = TetrisPiece.O.getPoints();
        } else {
            if (myPiece == TetrisPiece.J) {
                p2 = ConvertedPieces.J.getPoints();
            } else if (myPiece == TetrisPiece.L) {
                p2 = ConvertedPieces.L.getPoints();
            } else if (myPiece == TetrisPiece.S) {
                p2 = ConvertedPieces.S.getPoints();
            } else if (myPiece == TetrisPiece.T) {
                p2 = ConvertedPieces.T.getPoints();
            } else if (myPiece == TetrisPiece.Z) {
                p2 = ConvertedPieces.Z.getPoints();
            }
        }
        return p2;
    }
    /**
     * 
     * @param thePoint .
     * @return .
     */
    private Point convertPoint(final model.Point thePoint) {
        return new Point(thePoint.x() * SCALE, thePoint.y() * SCALE);    
    }

    @Override
    public void update(final Observable arg0, final Object arg1) {
        if (arg1 instanceof TetrisPiece) {
            myPiece = (TetrisPiece) arg1;
            this.repaint();
        }
        
    }
    

}
