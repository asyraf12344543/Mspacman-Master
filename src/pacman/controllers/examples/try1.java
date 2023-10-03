package pacman.controllers.examples;

import java.util.*;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public final class try1 extends Controller<MOVE>
{
	
	@Override
	public MOVE getMove(Game game, long timeDue) 
	{
		int MinDist = 20;
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		
		int powerPillIndices [] = game.getActivePowerPillsIndices();
		int pillIndices [] = game.getActivePillsIndices();
		GHOST ghost [] = GHOST.values();
		MOVE lastMoveMade = game.getPacmanLastMoveMade();
		
		if (powerPillIndices.length > 0)
		{
			int temp =0;
			
			for(GHOST ghostIn : GHOST.values())
			{
				if (game.getGhostEdibleTime(ghostIn) > 0 )
				{
					temp = 0;
					int ghostNear = 0;
					
					for(int i = 0; i<ghost.length; i++) 
					{
						if((game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghostIn), lastMoveMade)) <= temp)
						{
							temp = game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghostIn), lastMoveMade);
							ghostNear = i;
						}
					}
					return game.getNextMoveTowardsTarget(pacManIndex, game.getGhostCurrentNodeIndex(ghost[ghostNear]), lastMoveMade, DM.EUCLID);
				}
				if (game.getDistance(game.getGhostCurrentNodeIndex(ghostIn), pacManIndex, DM.EUCLID) < MinDist)
				{
					temp = 0;
					int ghostNear = 0;
					
					for(int i = 0; i<ghost.length; i++) 
					{
						if((game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghostIn), lastMoveMade)) < temp)
						{
							temp = game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(ghostIn), lastMoveMade);
							ghostNear = i;
						}
					}
					
					return game.getNextMoveAwayFromTarget(pacManIndex, game.getGhostCurrentNodeIndex(ghost[ghostNear]), lastMoveMade, DM.EUCLID);
				}
				else	
					return game.getNextMoveTowardsTarget(pacManIndex, game.getClosestNodeIndexFromNodeIndex(pacManIndex, powerPillIndices, DM.EUCLID), lastMoveMade, DM.EUCLID);
			}
				
		}
		else if (pillIndices.length > 0)
		{
			for(GHOST ghostIn : GHOST.values())
			{
				if (game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostIn), pacManIndex) < MinDist )
				{
					int temp = 0;
					int ghostNear = 0;
					
					for(int i = 0; i<ghost.length; i++) 
					{
						if(game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost[i]), pacManIndex) <= temp)
						{
							temp = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost[i]), pacManIndex);
							ghostNear = i;
						}
					}
					
					return game.getNextMoveAwayFromTarget(pacManIndex, game.getGhostCurrentNodeIndex(ghost[ghostNear]), lastMoveMade, DM.EUCLID);
				}
				else
				{
					int temp = 0;
					int pillNear = 0;
					
					for(int i = 0; i<pillIndices.length; i++) 
					{
						if(game.getShortestPathDistance(pillIndices[i], pacManIndex) <= temp)
						{
							temp = game.getShortestPathDistance(pillIndices[i], pacManIndex);
							pillNear = i;
						}
					}
					return game.getNextMoveTowardsTarget(pacManIndex, pillIndices[pillNear], lastMoveMade, DM.EUCLID);
				}
			}
			
		}
		
		return null;
	}
}

