package ia.utils;

import java.util.Random;

import protocol.network.Network;
import protocol.network.messages.game.context.fight.GameFightPlacementPositionRequestMessage;
import protocol.network.messages.game.context.fight.GameFightReadyMessage;

public class UtilsProtocol {
	
	Network network;
	
	public UtilsProtocol(Network network){
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
	
	
	public void stop(double deviation) throws InterruptedException {
		double gauss = new Random().nextGaussian();
		long timeStoped = (long) (Math.abs(gauss * deviation) * 1000);
		System.out.println("---- Sleeping : " + timeStoped + " ----");
		Thread.sleep(timeStoped);
	}

}
