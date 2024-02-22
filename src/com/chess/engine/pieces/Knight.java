package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATE){
            int candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
                        isEightColumnExclusion(this.piecePosition, currentCandidate)){
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new Move());
                }
                else{
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15;
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && candidateOffset == -10 || candidateOffset == 6;
    }
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && candidateOffset == -6 || candidateOffset == 10;
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17;
    }


}
