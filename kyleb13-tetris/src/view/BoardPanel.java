/**
 * 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Block;
import model.Board;

/**
 * @author Kyle Beveridge
 * @version .
 */
public class BoardPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -3256290564832372809L;
    
    /**
     * 
     */
    private static final Dimension PREFERED_DIM = new Dimension(300, 600);
    
    /**
     * 
     */
    private static final int BOARD_WIDTH = 10;
    
    /**
     * 
     */
    private static final int BOARD_HEIGHT = 24;
    
    /**
     * 
     */
    private static final int SCALE = 30;
    
    /**
     * 
     */
    private final Board myBoard;
    
    /**
     * 
     */
    private boolean myEnd;
    
    /**
     * 
     */
    private List<Block[]> myList = new ArrayList<Block[]>();
    
    /**
     * 
     */
    public BoardPanel() {
        super();
        myBoard = new Board();
        myBoard.addObserver(new BoardChangeListener());
        myEnd = false;
    }
    
    @Override
    public void paintComponent(final Graphics g1) {
        super.paintComponent(g1);
        final Graphics2D g2 = (Graphics2D) g1;
        if (myEnd) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, (int) PREFERED_DIM.getWidth(), (int) PREFERED_DIM.getHeight());
            g2.setColor(Color.RED);
            g2.drawString("Game Over!", 
                (int) PREFERED_DIM.getWidth() / 2 - SCALE, 
                (int) PREFERED_DIM.getHeight() / 2 - SCALE);
        } else {
            g2.setColor(Color.WHITE);
            for (int a = 0; a < myList.size(); a++) {
                final Block[] temp = myList.get(a);
                for (int b = 0; b < temp.length; b++) {
                    if (temp[b] != null) {
                        g2.fill(new Rectangle2D.Double(b * SCALE + 2, 
                            a * SCALE + 2, SCALE, SCALE));
                        g2.setColor(Color.BLUE);
                        g2.setStroke(new BasicStroke(1));
                        g2.drawRect(b * SCALE + 2, a * SCALE + 2, SCALE, SCALE);
                        g2.setColor(Color.WHITE);
                    }
                }
            }
        }
        repaint();
    }

    /**
     * 
     */
    protected void start() {
        myBoard.newGame();
    }
   
    /**
     * 
     */
    protected void updateGame() {
        myBoard.step();
        repaint();
    }
    
    /**
     * 
     */
    protected void moveLeft() {
        myBoard.left();
    }
    
    /**
     * 
     */
    protected void moveRight() {
        myBoard.right();
    }
    
    /**
     * 
     */
    protected void moveDown() {
        myBoard.down();
    }
    
    /**
     * 
     */
    protected void dropDown() {
        myBoard.drop();
    }
    
    /**
     * 
     */
    protected void clockwiseRotate() {
        myBoard.rotateCCW();
    }
    
    
    /**
     * 
     * @param o1 .
     */
    protected void addBoardObserver(final Observer o1) {
        myBoard.addObserver(o1);
    }
    
    /**
     * 
     */
    protected void newBoard() {
        myEnd = false;
        myBoard.newGame();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return PREFERED_DIM;   
    }
    
    /**
     * @author kbev4
     * @version .
     */
    private class BoardChangeListener implements Observer {

        /**
         * 
         */
        @Override
        public void update(final Observable arg0, final Object arg1) {
            if (arg1 instanceof List<?>) {
                final List<Block[]> data = makeCopyList();
                int rowcnt = data.size() - 1;
                int colcnt = 0;
                final List<?> temp = (List<?>) arg1;
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i) instanceof Block[]) {
                        final Block[] current = ((Block[]) temp.get(i)).clone();
                        for (int a = 0; a < current.length; a++) {
                            if (rowcnt >= 0) {
                                data.get(rowcnt)[colcnt++] = current[a];
                            }
                        }
                    }
                    colcnt = 0;
                    rowcnt--;
                }
                myList = data;
            } else if (arg1 instanceof Boolean) {
                myEnd = true;
            }
        }
        
        /**
         * 
         * @return .
         */
        private List<Block[]> makeCopyList() {
            final List<Block[]> copy = new ArrayList<Block[]>();
            for (int i = 0; i < BOARD_HEIGHT - 2 - 2; i++) {
                copy.add(new Block[BOARD_WIDTH]);
            }
            return copy;
        }
    }
}
