package net.coderodde.games.chess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class implements a canvas component rendering a chess board.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jun 21, 2016)
 */
public final class ChessCanvas extends Canvas 
implements MouseListener, MouseMotionListener {
    
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
    
    private static final Color DEFAULT_OWN_HIGHLIGHT_COLOR = Color.GREEN;
    private static final Color DEFAULT_ENEMY_HIGHLIGHT_COLOR = Color.ORANGE;
    
    private Color whiteCellColor      = DEFAULT_WHITE_CELL_COLOR;
    private Color blackCellColor      = DEFAULT_BLACK_CELL_COLOR;
    private Color borderColor         = DEFAULT_BORDER_COLOR;
    private Color ownHighlightColor   = DEFAULT_OWN_HIGHLIGHT_COLOR;
    private Color enemyHighlightColor = DEFAULT_ENEMY_HIGHLIGHT_COLOR;
    
    private final int width;
    private final int height;
    private final ChessPiece[][] chessBoard;
    private final ChessColor[][] highlightBoard;
    private ChessColor humanColor;
    
    private int currentSelectedCellX; 
    private int currentSelectedCellY;
    
    private int previousSelectedCellX;
    private int previousSelectedCellY;
    
    private volatile boolean disallowMouse;
    private volatile boolean disallowKeyboard;
    
    /**
     * The thickness of the cell border in pixels.
     */
    private int borderThickness = DEFAULT_BORDER_THICKNESS;
    
    private ChessColor humanPlayerColor = ChessColor.WHITE;
    
    public ChessCanvas(final int width, 
                       final int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        this.chessBoard = new ChessPiece[this.height][this.width];
        this.highlightBoard = new ChessColor[this.height][this.width];
        
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
        
        currentSelectedCellX = previousSelectedCellX = this.width / 2;
        currentSelectedCellY = previousSelectedCellY = this.height / 2;
        
        this.humanColor = ChessColor.WHITE;
        
        addMouseMotionListener(this);
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
    
    public ChessColor getHumanPlayerColor() {
        return humanPlayerColor;
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
                if (highlightBoard[y][x] != null) {
                    if (highlightBoard[y][x] == humanPlayerColor) {
                        g.setColor(ownHighlightColor);
                    } else {
                        g.setColor(enemyHighlightColor);
                    }
                } else {
                    g.setColor(currentCellColor);
                }
                
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
        
        g.setFont(new Font("Verdana", Font.PLAIN, 50));
        
        // Draw the chess pieces:
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (chessBoard[y][x] != null) {
                    g.drawString(chessBoard[y][x].getRepresentationString(), 
                                 startX + borderThickness + x * (cellSize + borderThickness),
                                 startY + borderThickness + y * (cellSize + borderThickness));
                }
            }
        }
    }
    
    @Override
    public void paint(final Graphics g) {
        update(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int computeCellSize() {
        final int boardWidth  = getWidth()  - borderThickness * (width + 1);
        final int boardHeight = getHeight() - borderThickness * (height + 1);
        
        final int tmpCellWidth  = boardWidth  / width;
        final int tmpCellHeight = boardHeight / height;
        
        return Math.min(tmpCellWidth, tmpCellHeight);
    }
    
    /**
     * Converts the pixel coordinate {@code (x, y)} to the coordinates of the 
     * board cell containing that pixels, and stores the result in 
     * {@code result}. If the input coordinates specify a pixel on the board 
     * border or outside of entire board, sets {@code result.x} to a negative
     * value.
     * 
     * @param x      the {@code x} coordinate of the pixel.
     * @param y      the {@code y} coordinate of the pixel.
     * @param result the point holding the converted coordinates.
     */
    private void convertCanvasCoordinatesToCellCoordinates(final int x, 
                                                           final int y,
                                                           final Point result) {
        final int cellSize = computeCellSize();
        
        final int boardWidth = (cellSize + borderThickness) * width +
                                borderThickness;
        final int leftMarginWidth  = (getWidth() - boardWidth) / 2;
        
        if (x < leftMarginWidth + borderThickness) {
            result.x = -1;
            return;
        }
        
        if (x >= leftMarginWidth + boardWidth) {
            result.x = -1;
            return;
        }
        
        final int boardHeight = (cellSize + borderThickness) * height + 
                                 borderThickness;
        
        final int topMarginHeight = (getHeight() - boardHeight) / 2;
        
        if (y < topMarginHeight + borderThickness) {
            result.x = -1;
            return;
        }
        
        if (y >= topMarginHeight + boardHeight) {
            result.x = -1;
            return;
        }
        
        final int tmp = cellSize + borderThickness;
        final int newx = (x - leftMarginWidth - borderThickness) % tmp;
        final int newy = (y - topMarginHeight - borderThickness) % tmp;
        
        if (newx < cellSize && newy < cellSize) {
            result.x = (x - leftMarginWidth - borderThickness) / tmp;
            result.y = (y - topMarginHeight - borderThickness) / tmp;
            return;
        }
        
        result.x = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = new Point();
        
        convertCanvasCoordinatesToCellCoordinates(e.getX(), e.getY(), p);
        
        if (p.x >= 0) {
            if (p.x != previousSelectedCellX || p.y != previousSelectedCellY) {
                highlight(p.x, p.y, humanColor);
                highlight(previousSelectedCellX, previousSelectedCellY, null);
                previousSelectedCellX = p.x;
                previousSelectedCellY = p.y;
                
                System.out.println("repaint");
                repaint();
            }
        } else {
            highlight(previousSelectedCellX, previousSelectedCellY, null);
            repaint();
        }
        
        
        System.out.println(p);
    }
    
    private void highlight(final int x, final int y, final ChessColor color) {
        highlightBoard[y][x] = color;
    }
}
