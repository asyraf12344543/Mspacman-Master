package pacman.controllers.examples;

import java.util.*;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public final class try0 extends Controller<MOVE> 
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
			int temp = 0;
			int ghostEdible = 0;
			
			for(int i = 0; i<ghost.length; i++) 
			{
				if(game.getGhostEdibleTime(ghost[i]) <= temp)
				{
					temp = game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex;
					ghostEdible = i;
				}
			}
			
			if (game.getGhostEdibleTime(ghost[ghostEdible]) > 0 )
			{
				temp = 0;
				int ghostNear = 0;
				
				for(int i = 0; i<ghost.length; i++) 
				{
					if((game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex) <= temp)
					{
						temp = game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex;
						ghostNear = i;
					}
				}
				return game.getNextMoveTowardsTarget(pacManIndex, game.getGhostCurrentNodeIndex(ghost[ghostNear]), lastMoveMade, DM.EUCLID);
			}
			else if (game.getGhostCurrentNodeIndex(ghost[0])- pacManIndex < MinDist || game.getGhostCurrentNodeIndex(ghost[1])- pacManIndex < MinDist || game.getGhostCurrentNodeIndex(ghost[2])- pacManIndex < MinDist || game.getGhostCurrentNodeIndex(ghost[3])- pacManIndex < MinDist)
			{
				temp = 0;
				int ghostNear = 0;
				
				for(int i = 0; i<ghost.length; i++) 
				{
					if((game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex) <= temp)
					{
						temp = game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex;
						ghostNear = i;
					}
				}
				
				return game.getNextMoveAwayFromTarget(pacManIndex, game.getGhostCurrentNodeIndex(ghost[ghostNear]), lastMoveMade, DM.EUCLID);
			}
			else
			{
				temp = 0;
				int powerPillNear = 0;
				
				for(int i = 0; i<powerPillIndices.length; i++) 
				{
					if((powerPillIndices[i]- pacManIndex) <= temp)
					{
						temp = powerPillIndices[i]- pacManIndex;
						powerPillNear = i;
					}
				}
				
				return game.getNextMoveTowardsTarget(pacManIndex, powerPillIndices[powerPillNear], lastMoveMade, DM.EUCLID);
			}
				
		}
		else if (pillIndices.length > 0)
		{
			if (game.getGhostCurrentNodeIndex(ghost[0])- pacManIndex < MinDist || game.getGhostCurrentNodeIndex(ghost[1])- pacManIndex < MinDist || game.getGhostCurrentNodeIndex(ghost[2])- pacManIndex < MinDist || game.getGhostCurrentNodeIndex(ghost[3])- pacManIndex < MinDist)
			{
				int temp = 0;
				int ghostNear = 0;
				
				for(int i = 0; i<ghost.length; i++) 
				{
					if((game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex) <= temp)
					{
						temp = game.getGhostCurrentNodeIndex(ghost[i])- pacManIndex;
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
					if((pillIndices[i]- pacManIndex) <= temp)
					{
						temp = pillIndices[i]- pacManIndex;
						pillNear = i;
					}
				}
				return game.getNextMoveTowardsTarget(pacManIndex, pillIndices[pillNear], lastMoveMade, DM.EUCLID);
			}
		}
		else
			return null;
	}
}
