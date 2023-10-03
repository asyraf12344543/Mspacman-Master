package pacman.controllers.examples;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public final class MyPacMan2 extends Controller<MOVE> {

	/*This controller utilizes 3 tactics, in order of importance:
	1. Check if there are any ghost nearby.
		- if yes, then run away
		- if no, go to number 2.
	2. Check the distance of Power Pills
	 	- if near, go towards Power Pill
	 	- if no, go to number 3.
	3. Go to the nearest pill*/
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		int powerPillIndices[] = game.getActivePowerPillsIndices();
		int pillIndices[] = game.getActivePillsIndices();
		GHOST ghost[] = GHOST.values();
		MOVE lastMoveMade = game.getPacmanLastMoveMade();

		int closestPowerPill = findClosestPowerPill(game, pacManIndex, powerPillIndices);
		int closestPill = findClosestPill(game, pacManIndex, pillIndices);

		//Tactics 1: if there are any ghost nearby (less than 20), run away; else go to tactics 2
		if (isGhostNearby(game, pacManIndex)) {
			
			GHOST nearestGhost = findNearestGhost(game, pacManIndex, ghost);
			return game.getNextMoveAwayFromTarget(pacManIndex, game.getGhostCurrentNodeIndex(nearestGhost), lastMoveMade, DM.EUCLID);
			
		//Tactics 2: if the Power Pills are nearby, get Power Pill; else go to tactics 3
		} else {
			
			if (closestPowerPill != -1) {
				
				return game.getNextMoveTowardsTarget(pacManIndex, closestPowerPill, lastMoveMade, DM.EUCLID);
				
			//Tactics 3: if the Pills are nearby, get Pill; else move naturally
			} else if (closestPill != -1) {
				
				return game.getNextMoveTowardsTarget(pacManIndex, closestPill, lastMoveMade, DM.EUCLID);
				
			} else {
				
				// No active pills or power pills remaining
				return MOVE.NEUTRAL;
			}
		}
	}

	// This method checks if any ghost is within a certain distance from Pac-Man.
	public boolean isGhostNearby(Game game, int pacManIndex) {
		
		GHOST[] ghosts = GHOST.values();
		for (GHOST ghost : ghosts) {
			
			int ghostIndex = game.getGhostCurrentNodeIndex(ghost);
			int distance = game.getShortestPathDistance(pacManIndex, ghostIndex);
			if (distance < 20) {
				
				return true;
			}
		}
		return false;
	}

	// This method finds the nearest ghost to Pac-Man.
	public GHOST findNearestGhost(Game game, int pacManIndex, GHOST[] ghosts) {
		
		GHOST nearestGhost = ghosts[0];
		int shortestDistance = Integer.MAX_VALUE;

		for (GHOST ghost : ghosts) {
			
			int ghostIndex = game.getGhostCurrentNodeIndex(ghost);
			int distance = game.getShortestPathDistance(pacManIndex, ghostIndex);

			if (distance < shortestDistance) {
				
				nearestGhost = ghost;
				shortestDistance = distance;
			}
		}
		return nearestGhost;
	}

	// This method finds the closest active pill to Pac-Man.
	public int findClosestPowerPill(Game game, int pacManIndex, int[] powerPillIndices) {
		
		int closestPowerPill = -1;
		int shortestDistance = Integer.MAX_VALUE;

		for (int powerPillIndex : powerPillIndices) {
			
			int distance = game.getShortestPathDistance(pacManIndex, powerPillIndex);

			if (distance < shortestDistance) {
				
				closestPowerPill = powerPillIndex;
				shortestDistance = distance;
			}
		}

		return closestPowerPill;
	}

	// This method finds the closest active pill to PacMan.
	public int findClosestPill(Game game, int pacManIndex, int[] pillIndices) {
		
		int closestPill = -1;
		int shortestDistance = Integer.MAX_VALUE;

		for (int pillIndex : pillIndices) {
			
			int distance = game.getShortestPathDistance(pacManIndex, pillIndex);

			if (distance < shortestDistance) {
				
				closestPill = pillIndex;
				shortestDistance = distance;
			}
		}
		return closestPill;
	}
}
