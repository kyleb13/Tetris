/*
 * TCSS 305, Spring 2017
 * Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.StrokeBorder;

/**
 * Main gui class for tetris program.
 * @author Kyle Beveridge
 * @version 6/2/17
 */
public class TetrisGui extends JPanel {

    /**
     * Auto-Generated serialization UID.
     */
    private static final long serialVersionUID = 6531871547384086288L;
    
    /**
     * How often the game refreshes.
     */
    private static final int DELAY = 1000;
    
    /**
     * Preferred size of main panel.
     */
    private static final Dimension PREF_SIZE = new Dimension(304, 604);
    
    
    /**
     * Main frame.
     */
    private final JFrame myFrame;
    
    /**
     * Panel the game is played on.
     */
    private final BoardPanel myPanel;
    
    /**
     * JLabel that displays game ended and paused info.
     */
    private final JLabel myText = new JLabel();
    
    /**
     * timer used to update game.
     */
    private final Timer myTimer;
    
    /**
     * Panel used for piece preview.
     */
    private final PreviewPanel myPreview;
    
    /**
     * Indicates whether game is paused or not.
     */
    private boolean myPaused;
    
    /**
     * Indicates running state of game.
     */
    private boolean myRunning = true;

    /**
     * Currrent Score.
     */
    private int myScore = 0;
    
    /**
     * Current Lines cleared.
     */
    private int myCleared = 0;

    /**
     * Constructor for gui. start() does most
     * of the work. 
     */
    public TetrisGui() {
        super();
        myFrame = new JFrame("Tetris");
        myPanel = new BoardPanel();
        myPreview = new PreviewPanel();
        myTimer = new Timer(DELAY, e -> myPanel.updateGame());
    }
    
    /**
     * Sets up all of the essential components
     * of the gui.
     */
    public void start() {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myPanel.add(myText);
        addMenuBar();
        final JPanel eastdiv = new JPanel(new BorderLayout());
        final JPanel northeast = new JPanel();
        final JPanel southeast = new JPanel();
        southeast.setBackground(Color.WHITE);
        northeast.setBackground(Color.WHITE);
        northeast.setLayout(new FlowLayout(FlowLayout.LEFT));
        southeast.setLayout(new BoxLayout(southeast, BoxLayout.Y_AXIS));
        addControlInfo(southeast);
        southeast.setPreferredSize(new Dimension((int) 
            (PREF_SIZE.getWidth() - PREF_SIZE.getWidth() / (2 + 2)), 
            (int) (PREF_SIZE.getHeight() / 2 - PREF_SIZE.getHeight() / (2 * 2 * 2))));
        northeast.add(myPreview);
        eastdiv.add(northeast, BorderLayout.NORTH);
        eastdiv.add(southeast, BorderLayout.SOUTH);
        myFrame.addKeyListener(new InputControls());
        myPanel.addBoardObserver(new EndAndScoreObserver());
        myPanel.addBoardObserver(myPreview);
        myPanel.setBorder(new StrokeBorder(new BasicStroke(2), Color.RED));
        myPanel.setBackground(Color.BLACK);
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(myPanel);
        myFrame.add(eastdiv, BorderLayout.EAST);        
        myFrame.add(this, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }
    
    /**
     * Adds the control info to a JPanel.
     * @param thePan the panel that recieves the info.
     */
    private void addControlInfo(final JPanel thePan) {
        final JLabel lab = new JLabel("<html><p>"
            + "<strong>Default Game Controls:</strong><br/>"
            + "<span style=\"text-decoration: underline;\">Move Right-</span> "
            + "Right arrow or 'd'<br/>"
            + "<span style=\"text-decoration: underline;\">Move Left-</span> "
            + "Left arrow or 'a'<br/>"
            + "<span style=\"text-decoration: underline;\">Move Down-</span> "
            + "Down arrow or 's'<br/>"
            + "<span style=\"text-decoration: underline;\">Drop Down-</span> "
            + "Space Bar<br/>"
            + "<span style=\"text-decoration: underline;\">Rotate Clockwise-</span> "
            + "Up arrow or 'w'<br/>"
            + "<span style=\"text-decoration: underline;\">Pause Game-</span> "
            + "'p'<br/></p></html>");
        thePan.add(lab);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
            (int) (PREF_SIZE.getWidth() + 2), 
            (int) (PREF_SIZE.getHeight() + 2));    
    }
    
    /**
     * Adds the menu bar to the frame.
     */
    private void addMenuBar() {
        final JMenuBar bar = new JMenuBar();
        final JMenu options = new JMenu("Options");
        final JMenuItem newgame = new JMenuItem("New Game");
        final JMenuItem endgame = new JMenuItem("End Game");
        final JMenuItem quit = new JMenuItem("Quit");
        endgame.addActionListener(e -> {
            myTimer.stop();
            myText.setText("<html><span style=\"color: yellow\">Game Ended By User.</html>");
            myRunning = false;
            endgame.setEnabled(false);
            newgame.setEnabled(true);
        });
        newgame.addActionListener(e -> {
            myText.setText("");
            myPanel.newBoard();
            myTimer.start();
            myRunning = true;
            endgame.setEnabled(true);
            newgame.setEnabled(false);
        });
        quit.addActionListener(e -> System.exit(0));
        endgame.setEnabled(false);
        options.add(newgame);
        options.add(endgame);
        options.add(quit);
        bar.add(options);
        myFrame.setJMenuBar(bar);
    }
    
    
    /**
     * Private class that processes key events.
     * @author Kyle Beveridge
     * @version 6/2/17
     */
    private class InputControls extends KeyAdapter {
        @Override
        public void keyPressed(final KeyEvent e1) {
            final int code = e1.getKeyCode();
            if (myRunning) {
                if (code == KeyEvent.VK_P) {
                    if (myPaused) {
                        myTimer.start();
                        myPaused = false;
                    } else {
                        myTimer.stop();
                        myPaused = true;
                    }
                } else if (((Boolean) myPaused).equals(false)) {
                    if (leftCode(code)) {
                        myPanel.moveLeft();
                    } else if (rightCode(code)) {
                        myPanel.moveRight();
                    } else if (code == KeyEvent.VK_SPACE) {
                        myPanel.dropDown();
                    } else if (downCode(code)) {
                        myPanel.moveDown();
                    } else if (rotateCode(code)) {
                        myPanel.clockwiseRotate();
                    }
                }
            }
        }
        
        /**
         * Indicates whether the given code is valid for moving left.
         * @param code1 The code.
         * @return Whether the code is correct (boolean).
         */
        private boolean leftCode(final int code1) {
            boolean correct = false;
            if (code1 == KeyEvent.VK_LEFT || code1 == KeyEvent.VK_A) {
                correct = true;
            }
            return correct;
        }
        
        /**
         * Indicates whether the given code is valid for moving right.
         * @param code1 The code.
         * @return Whether the code is correct (boolean).
         */
        private boolean rightCode(final int code1) {
            boolean correct = false;
            if (code1 == KeyEvent.VK_RIGHT || code1 == KeyEvent.VK_D) {
                correct = true;
            }
            return correct;
        }
        
        /**
         * Indicates whether the given code is valid for moving down.
         * @param code1 The code.
         * @return Whether the code is correct (boolean).
         */
        private boolean downCode(final int code1) {
            boolean correct = false;
            if (code1 == KeyEvent.VK_DOWN || code1 == KeyEvent.VK_S) {
                correct = true;
            }
            return correct;
        }
        
        /**
         * Indicates whether the given code is valid for rotating.
         * @param code1 The code.
         * @return Whether the code is correct (boolean).
         */
        private boolean rotateCode(final int code1) {
            boolean correct = false;
            if (code1 == KeyEvent.VK_UP || code1 == KeyEvent.VK_W) {
                correct = true;
            }
            return correct;
        }
    }
    
    /**
     * Watches for the end of the game and score changes.
     * @author Kyle Beveridge
     * @version 6/2/17
     */
    private class EndAndScoreObserver implements Observer {

        @Override
        public void update(final Observable arg0, final Object arg1) {
            if (arg1 instanceof Boolean) {
                myTimer.stop();
            } else if (arg1 instanceof Integer[]) {
                myCleared += ((Integer[]) arg1).length;
            }
            
        }
        
    }
}
