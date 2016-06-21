package net.coderodde.games.chess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * This class implements a canvas component rendering a chess board.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jun 21, 2016)
 */
public final class ChessCanvas extends Canvas {
    
    private static final int DEFAULT_BOARD_WIDTH  = 8;
    private static final int DEFAULT_BOARD_HEIGHT = 8;
    
    /**
     * The default thickness of the chess board border in pixels.
     */
    private static final int DEFAULT_BORDER_THICKNESS = 2;
    
    /**
     * The minimum width and height of a chess board cell in pixels.
     */
    private static final int MINIMUM_CELL_SIZE = 20;
    
    /**
     * The preferred width and height of a chess board cell in pixels.
     */
    private static final int PREFERRED_CELL_SIZE = 50;
    
    /**
     * The default white cell (background) color.
     */
    private static final Color DEFAULT_WHITE_CELL_COLOR = 
                     new Color(230, 230, 230);
   
    /**
     * The default black cell (background) color.
     */
    private static final Color DEFAULT_BLACK_CELL_COLOR = 
                     new Color(212, 194, 144);
    
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    
    private Color whiteCellColor = DEFAULT_WHITE_CELL_COLOR;
    private Color blackCellColor = DEFAULT_BLACK_CELL_COLOR;
    private Color borderColor = DEFAULT_BORDER_COLOR;
    private final int width;
    private final int height;
    private final ChessPiece[][] chessBoard;
    
    /**
     * The thickness of the cell border in pixels.
     */
    private int borderThickness = DEFAULT_BORDER_THICKNESS;
    
    public ChessCanvas(final int width, 
                       final int height) {
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
        this.chessBoard = new ChessPiece[this.height][this.width];
        
        final int minimumWidth = (MINIMUM_CELL_SIZE + borderThickness) *
                                 width + borderThickness;
        
        final int minimumHeight = (MINIMUM_CELL_SIZE + borderThickness) *
                                  height + borderThickness;
        
        final int preferredWidth = (PREFERRED_CELL_SIZE + borderThickness) *
                                    width + borderThickness;
        
        final int preferredHeight = (PREFERRED_CELL_SIZE + borderThickness) *
                                    height + borderThickness;
        
        setMinimumSize(new Dimension(minimumWidth, minimumHeight));
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    public ChessCanvas() {
        this(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT);
    }
    
    public ChessPiece[][] getChessBoard() {
        return chessBoard;
    }
    
    public Color getWhiteCellColor() {
        return whiteCellColor;
    }
    
    public Color getBlackCellColor() {
        return blackCellColor;
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setWhiteCellColor(final Color newWhiteCelColor) {
        if (newWhiteCelColor != null) {
            whiteCellColor = newWhiteCelColor;
        }
    }
    
    public void setBlackCellColor(final Color newBlackCellColor) {
        if (newBlackCellColor != null) {
            blackCellColor = newBlackCellColor;
        }
    }
    
    public void setBorderColor(final Color newBorderColor) {
        if (newBorderColor != null) {
            borderColor = newBorderColor;
        }
    }
    
    @Override
    public void update(final Graphics g) {
        final int availableCanvasWidth  = getWidth() - 
                                          (width + 1) * borderThickness;
        final int availableCanvasHeight = getHeight() -
                                          (height + 1) * borderThickness;
        
        final int preliminaryCellWidth  = availableCanvasWidth / width;
        final int preliminaryCellHeight = availableCanvasHeight / height; 
        
        final int cellSize = Math.min(preliminaryCellWidth,
                                      preliminaryCellHeight);
        
        final int totalChessBoardWidth = 
                width * (cellSize + borderThickness) + borderThickness;
        
        final int totalChessBoardHeight = 
                height * (cellSize + borderThickness) + borderThickness;
        
        // Compute the start coordinates so that the actual chess board is 
        // painted at the center of the actual canvas.
        final int startX = (getWidth() - totalChessBoardWidth) / 2;
        final int startY = (getHeight() - totalChessBoardHeight) / 2;
        
        Color currentCellColor = whiteCellColor;
        
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                g.setColor(currentCellColor);
                
                g.fillRect(startX + borderThickness + 
                                   (cellSize + borderThickness) * x, 
                        
                           startY + borderThickness + 
                                   (cellSize + borderThickness) * y, 
                           cellSize, 
                           cellSize);
                
                currentCellColor = (currentCellColor == blackCellColor ? 
                                    whiteCellColor : 
                                    blackCellColor);
            }

            if (width % 2 == 0) {
                currentCellColor = (currentCellColor == blackCellColor ? 
                                    whiteCellColor : 
                                    blackCellColor);
            }
        }
        
        g.setColor(borderColor);
        
        // Draw the vertical borders:
        for (int x = 0; x <= width; ++x) {
            g.fillRect(startX + x * (cellSize + borderThickness), 
                       startY,
                       borderThickness,
                       totalChessBoardHeight);
        }
        
        // Draw the horizontal borders:
        for (int y = 0; y <= height; ++y) {
            g.fillRect(startX,
                       startY + y * (cellSize + borderThickness),
                       totalChessBoardWidth,
                       borderThickness);
        }
    }
    
    @Override
    public void paint(final Graphics g) {
        update(g);
    }
}
