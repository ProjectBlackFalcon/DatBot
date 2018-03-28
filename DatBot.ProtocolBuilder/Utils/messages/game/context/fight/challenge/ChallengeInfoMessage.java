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

	private int challengeId;
	private double targetId;
	private int xpBonus;
	private int dropBonus;

	public int getChallengeId() { return this.challengeId; }
	public void setChallengeId(int challengeId) { this.challengeId = challengeId; };
	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getXpBonus() { return this.xpBonus; }
	public void setXpBonus(int xpBonus) { this.xpBonus = xpBonus; };
	public int getDropBonus() { return this.dropBonus; }
	public void setDropBonus(int dropBonus) { this.dropBonus = dropBonus; };

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
	}

}
