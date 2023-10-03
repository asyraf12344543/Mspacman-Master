package pacman.controllers.examples;

import java.util.ArrayList;
import java.util.List;

import pacman.game.Constants.MOVE;

public class GameState {
    private int pacmanNodeIndex;
    private static List<Integer> ghostNodeIndices;

    public GameState(int pacmanNodeIndex, List<Integer> ghostNodeIndices) {
        this.pacmanNodeIndex = pacmanNodeIndex;
        GameState.ghostNodeIndices = ghostNodeIndices;
    }

    public int getPacmanCurrentNodeIndex() {
        return pacmanNodeIndex;
    }

    public static List<Integer> getGhostNodeIndices() 
    {
        return ghostNodeIndices;
    }

    public MOVE[] getPossibleMoves(int currentNodeIndex) {
        // TODO: Implement logic to retrieve possible moves at a given node
        // For example, you can check the neighboring nodes and return valid moves

        List<MOVE> possibleMoves = new ArrayList<MOVE>();

        // Add possible moves based on the neighboring nodes

        // ...

        // Convert the list of possible moves to an array
        MOVE[] movesArray = new MOVE[possibleMoves.size()];
        return possibleMoves.toArray(movesArray);
    }
}
