package net.coderodde.games.chess;

/**
 * This class maintains the game state of a chess match.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jun 21, 2016)
 */
public class ChessGameState {
    
    private final ChessPiece[][] board = new ChessPiece[8][8];
    
    public ChessGameState() {
        populateBoard();
    }
    
    public ChessColor checkVictory() {
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board.length; ++x) {
                final ChessPiece currentCellPiece = board[y][x];
                
                if (currentCellPiece != null 
                        && currentCellPiece.getType()
                                           .equals(ChessPieceType.KING)) {
                    
                }
            }
        }
        
        // No victory yet.
        return null;
    }
    
    private void populateBoard() {
        board[7] = new ChessPiece[] {
            new ChessPiece(ChessColor.BLACK, ChessPieceType.ROOK),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.KNIGHT),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.BISHOP),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.KING),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.QUEEN),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.BISHOP),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.KNIGHT),
            new ChessPiece(ChessColor.BLACK, ChessPieceType.ROOK),
        };
        
        for (int x = 0; x < 8; ++x) {
            board[6][x] = new ChessPiece(ChessColor.BLACK, ChessPieceType.PAWN);
        }
        
        for (int x = 0; x < 8; ++x) {
            board[1][x] = new ChessPiece(ChessColor.WHITE, ChessPieceType.PAWN);
        }
        
        board[0] = new ChessPiece[] {
            new ChessPiece(ChessColor.WHITE, ChessPieceType.ROOK),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.KNIGHT),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.BISHOP),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.KING),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.QUEEN),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.BISHOP),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.KNIGHT),
            new ChessPiece(ChessColor.WHITE, ChessPieceType.ROOK),
        };
    }
}
