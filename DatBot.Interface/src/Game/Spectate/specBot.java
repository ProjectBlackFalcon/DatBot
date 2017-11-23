package Game.Spectate;

import java.util.List;

import protocol.frames.LatencyFrame;
import protocol.network.Network;
import protocol.network.messages.game.context.fight.GameFightJoinRequestMessage;
import protocol.network.messages.game.context.roleplay.MapRunningFightDetailsRequestMessage;
import protocol.network.messages.game.context.roleplay.MapRunningFightListRequestMessage;
import protocol.network.types.game.context.fight.FightExternalInformations;

public class specBot implements Runnable  {
	
	public List<FightExternalInformations> fightList;
	
	public specBot() throws Exception{
		run();
	}

	@Override
	public void run() {
		try {
			LatencyFrame.Sequence = 1; 
			Network.sendToServer(new MapRunningFightListRequestMessage(), MapRunningFightListRequestMessage.ProtocolId,"Fight list request");
			Network.waitForPacket();
			fightList = Network.fight.fights;
			Network.sendToServer(new MapRunningFightDetailsRequestMessage(fightList.get(0).fightId), MapRunningFightDetailsRequestMessage.ProtocolId,"Fight detail request");
			Network.waitForPacket();
			Network.sendToServer(new GameFightJoinRequestMessage(0,fightList.get(0).fightId), GameFightJoinRequestMessage.ProtocolId,"Connexion au serveur de kolizeum");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
