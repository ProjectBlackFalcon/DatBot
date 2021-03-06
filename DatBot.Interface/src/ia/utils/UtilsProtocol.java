package ia.utils;

import java.util.Random;

import game.movement.CellMovement;
import protocol.network.Network;
import protocol.network.messages.game.actions.fight.GameActionFightCastRequestMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPositionRequestMessage;
import protocol.network.messages.game.context.fight.GameFightReadyMessage;
import protocol.network.messages.game.context.fight.GameFightTurnFinishMessage;

public class UtilsProtocol {

	Network network;

	public UtilsProtocol(Network network) {
		this.network = network;
	}

	/**
	 * Start the fight
	 * 
	 * @author baptiste
	 */
	public void fightReady() throws Exception {
		network.sendToServer(new GameFightReadyMessage(true), GameFightReadyMessage.ProtocolId, "Ready");
	}

	/**
	 * Stop being ready
	 * 
	 * @author jikiw
	 */
	public void fightNotReady() throws Exception {
		network.sendToServer(new GameFightReadyMessage(false), GameFightReadyMessage.ProtocolId, "Not ready");
	}

	/**
	 * Set the position of the player at the begining of the fight
	 * 
	 * @author baptiste
	 */
	public void setBeginingPosition(int cellId) throws Exception {
		GameFightPlacementPositionRequestMessage gameFightPlacementPositionRequestMessage = new GameFightPlacementPositionRequestMessage(cellId);
		network.sendToServer(gameFightPlacementPositionRequestMessage, GameFightPlacementPositionRequestMessage.ProtocolId, "Placement position : " + cellId);
	}

	/**
	 * End the turn
	 * 
	 * @author baptiste
	 */
	public void endTurn() throws Exception {
		network.append("Ending turn");
		network.sendToServer(new GameFightTurnFinishMessage(false), GameFightTurnFinishMessage.ProtocolId, "End turn");
	}

	/**
	 * Move the player during fight using MP
	 * 
	 * @param : int cellId
	 * @return boolean moved
	 * @author baptiste
	 */
	public boolean moveTo(int cellId) throws Exception {
		CellMovement mov = this.network.getMovement().MoveToCell(cellId);
		
		System.out.println("Mov movTo: " + mov +" to cellId " + cellId + " from " +  this.network.getIntelligence().getMain().getInfo().getDisposition().getCellId());
		System.out.println(mov.path);
		
		
		if (mov == null || mov.path == null) {
			return false;
		}
		else if (this.network.getInfo().getCellId() == cellId) {
			return true;
		}
		else {
			mov.performMovement();
			if (this.network.getInfo().getCellId() == cellId) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Cast spell
	 * 
	 * @param : int id, int cellId
	 * @author baptiste
	 */
	public void castSpell(int id, int cellId) throws Exception {
		network.append("Casting " + id + " to " + cellId);
		GameActionFightCastRequestMessage gameActionFightCastRequestMessage = new GameActionFightCastRequestMessage(id, cellId);
		network.sendToServer(gameActionFightCastRequestMessage, GameActionFightCastRequestMessage.ProtocolId, "Cast spell");
	}
	
	public void stop(double deviation) throws InterruptedException {
		double gauss = new Random().nextGaussian();
		long timeStoped = (long) (Math.abs(gauss * deviation) * 1000);
		System.out.println("---- Sleeping : " + timeStoped + " ----");
		Thread.sleep(timeStoped);
	}

}
