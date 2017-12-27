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
public class ChallengeTargetsListRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5614;

	private int challengeId;

	public int getChallengeId() { return this.challengeId; };
	public void setChallengeId(int challengeId) { this.challengeId = challengeId; };

	public ChallengeTargetsListRequestMessage(){
	}

	public ChallengeTargetsListRequestMessage(int challengeId){
		this.challengeId = challengeId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.challengeId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.challengeId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
