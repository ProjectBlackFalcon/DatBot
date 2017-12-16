package Game.combat;

import protocol.network.Network;
import protocol.network.messages.game.context.GameEntitiesDispositionMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPositionRequestMessage;
import protocol.network.messages.game.context.fight.GameFightPlacementPossiblePositionsMessage;
import protocol.network.messages.game.context.fight.GameFightReadyMessage;
import protocol.network.messages.game.context.fight.GameFightSynchronizeMessage;

public class Fight {
	
	// Init fight
	GameFightPlacementPossiblePositionsMessage gameFightPlacementPossiblePositionsMessage;  // Available placement position
	GameEntitiesDispositionMessage gameEntitiesDispositionMessage;  // Disposition
	GameFightSynchronizeMessage gameFightSynchronizeMessage;  // Recap
	
	public static void setBeginingPosition() throws Exception{
		// TODO LYSANDRE
		int cellId = 0;
		GameFightPlacementPositionRequestMessage gameFightPlacementPositionRequestMessage = new GameFightPlacementPositionRequestMessage(cellId);
		Network.sendToServer(gameFightPlacementPositionRequestMessage, GameFightPlacementPositionRequestMessage.ProtocolId, "Placement position : " + cellId);
	}
	
	public static void fightReady() throws Exception{
		Network.sendToServer(new GameFightReadyMessage(true), GameFightReadyMessage.ProtocolId, "Ready");
	}
	
	
	
}
