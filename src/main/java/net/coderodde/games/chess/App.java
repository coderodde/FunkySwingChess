package net.coderodde.games.chess;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author rodionefremov
 */
public class App {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Canvas canvas = new ChessCanvas();
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        frame.getContentPane().add(panel);
        frame.setMinimumSize(canvas.getMinimumSize());
        frame.setPreferredSize(canvas.getPreferredSize());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        putFrameToCenterOfScreen(frame);
        frame.setVisible(true);
    }
    
    private static void putFrameToCenterOfScreen(final JFrame frame) {
        final Dimension screenDimension = Toolkit.getDefaultToolkit()
                                                 .getScreenSize();
        
        frame.setLocation((screenDimension.width - frame.getWidth()) / 2, 
                          (screenDimension.height - frame.getHeight()) / 2);
    }
}
