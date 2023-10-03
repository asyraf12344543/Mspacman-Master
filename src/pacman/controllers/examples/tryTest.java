package pacman.controllers.examples;

import pacman.controllers.Controller;
//import pacman.game.Constants.DM;
//import pacman.game.Constants.GHOST;
//import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

import static pacman.game.Constants.*;

public final class tryTest extends Controller<MOVE> 
{
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) 
	{
		int MinDist = 20;
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		int powerPillIndices [] = game.getActivePowerPillsIndices();
		int pillIndices [] = game.getActivePillsIndices();
		GHOST minGhost = null;
		int minDistance=Integer.MAX_VALUE;
		
		
		////////////Loop/Check for every ghost////////////
		for(GHOST ghost : GHOST.values())
		{
			////////////If any of the ghost is close and cannot be eaten, Pacman runaway////////////
			if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0)
			{
				if(game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghost)) < MinDist)
					return game.getNextMoveAwayFromTarget(pacManIndex, game.getGhostCurrentNodeIndex(ghost), DM.PATH);	
			}
			
			////////////If any of the ghost can be eaten and close, Assign ghost to minGhost////////////
			else if (game.getGhostEdibleTime(ghost) > 0)
			{
				if( game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghost)) < minDistance)
				{
					minGhost = ghost;
					minDistance = game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghost));
				}
			}
		}
		
		////////////If minGhost is not null, then chase that minGhost////////////
		if(minGhost != null)
			return game.getNextMoveTowardsTarget(pacManIndex, game.getGhostCurrentNodeIndex(minGhost), DM.PATH);		
		////////////Get the nearest power pills first, then the pills////////////
		else if(powerPillIndices.length > 0)
			return game.getNextMoveTowardsTarget(pacManIndex, game.getClosestNodeIndexFromNodeIndex(pacManIndex, powerPillIndices, DM.PATH), DM.PATH);
		else if(pillIndices.length > 0)
			return game.getNextMoveTowardsTarget(pacManIndex, game.getClosestNodeIndexFromNodeIndex(pacManIndex, pillIndices, DM.PATH), DM.PATH);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		return MOVE.NEUTRAL;
	}

}
