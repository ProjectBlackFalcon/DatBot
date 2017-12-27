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
public class ChallengeTargetUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6123;

	private int challengeId;
	private double targetId;

	public int getChallengeId() { return this.challengeId; };
	public void setChallengeId(int challengeId) { this.challengeId = challengeId; };
	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };

	public ChallengeTargetUpdateMessage(){
	}

	public ChallengeTargetUpdateMessage(int challengeId, double targetId){
		this.challengeId = challengeId;
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.challengeId);
			writer.writeDouble(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.challengeId = reader.readVarShort();
			this.targetId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
