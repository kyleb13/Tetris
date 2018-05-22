package view;

import java.awt.EventQueue;

/**
 * Main Class that starts the tetris program.
 * @author kbev4
 * @version .
 */
public final class TetrisMain {
    
    /**
     * Private constructor to prevent instantiation.
     */
    private TetrisMain() { }
    
    
    /**
     * Main that starts the gui.
     * @param theArgs Command Line args.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGui().start();
            }
        });
    }

}
