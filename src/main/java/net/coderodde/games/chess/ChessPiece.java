package net.coderodde.games.chess;

import java.util.Objects;

/**
 * This class describes a piece on a board by specifying its type and color.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jun 21, 2016) 
 */
public class ChessPiece {
   
    private final ChessColor color;
    private final ChessPieceType type;
    
    public ChessPiece(final ChessColor color, final ChessPieceType type) {
        this.color = 
                Objects.requireNonNull(color, 
                                       "The input piece color is null.");
        this.type =
                Objects.requireNonNull(type,
                                       "The input piece type is null.");
    }
    
    public ChessColor getColor() {
        return color;
    }
    
    public ChessPieceType getType() {
        return type;
    }
    
    public String getRepresentationString() {
        if (color.equals(ChessColor.WHITE)) {
            switch (type) {
                case PAWN:
                    return "\u2659";
                    
                case KNIGHT:
                    return "\u2658";
                    
                case BISHOP:
                    return "\u2657";
                    
                case ROOK:
                    return "\u2656";
                    
                case QUEEN:
                    return "\u2655";
                    
                case KING:
                    return "\u2654";
                    
                default:
                    throw new IllegalStateException(
                            "Should never get here: the piece type is unknown."
                            );
            }
        }
        
        if (color.equals(ChessColor.BLACK)) {
            switch (type) {
                case PAWN:
                    return "\u265f";
                    
                case KNIGHT:
                    return "\u265e";
                    
                case BISHOP:
                    return "\u265d";
                    
                case ROOK:
                    return "\u265C";
                    
                case QUEEN:
                    return "\u265B";
                    
                case KING:
                    return "\u265A";
                    
                default:
                    throw new IllegalStateException(
                            "Should never get here: the piece type is unknown."
                            );
            }
        }
        
        throw new IllegalStateException(
                "Should never get here: the piece coloris neither white, nor " +
                "black.");
    }
}
