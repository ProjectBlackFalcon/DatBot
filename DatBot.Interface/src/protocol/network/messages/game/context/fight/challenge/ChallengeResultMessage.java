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
public class ChallengeResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6019;

	private int challengeId;
	private boolean success;

	public int getChallengeId() { return this.challengeId; }
	public void setChallengeId(int challengeId) { this.challengeId = challengeId; };
	public boolean isSuccess() { return this.success; }
	public void setSuccess(boolean success) { this.success = success; };

	public ChallengeResultMessage(){
	}

	public ChallengeResultMessage(int challengeId, boolean success){
		this.challengeId = challengeId;
		this.success = success;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.challengeId);
			writer.writeBoolean(this.success);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.challengeId = reader.readVarShort();
			this.success = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
