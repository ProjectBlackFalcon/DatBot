package protocol.network.messages.game.context.fight.challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ChallengeInfoMessage extends NetworkMessage {
	public static final int ProtocolId = 6022;

	public int challengeId;
	public double targetId;
	public int xpBonus;
	public int dropBonus;

	public ChallengeInfoMessage(){
	}

	public ChallengeInfoMessage(int challengeId, double targetId, int xpBonus, int dropBonus){
		this.challengeId = challengeId;
		this.targetId = targetId;
		this.xpBonus = xpBonus;
		this.dropBonus = dropBonus;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.challengeId);
			writer.writeDouble(this.targetId);
			writer.writeVarInt(this.xpBonus);
			writer.writeVarInt(this.dropBonus);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.challengeId = reader.readVarShort();
			this.targetId = reader.readDouble();
			this.xpBonus = reader.readVarInt();
			this.dropBonus = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("challengeId : " + this.challengeId);
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("xpBonus : " + this.xpBonus);
		//Network.appendDebug("dropBonus : " + this.dropBonus);
	//}
}
