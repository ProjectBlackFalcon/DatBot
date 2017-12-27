package protocol.network.messages.game.context.roleplay.fight;

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
public class GameRolePlayMonsterAngryAtPlayerMessage extends NetworkMessage {
	public static final int ProtocolId = 6741;

	private long playerId;
	private double monsterGroupId;
	private double angryStartTime;
	private double attackTime;

	public long getPlayerId() { return this.playerId; };
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public double getMonsterGroupId() { return this.monsterGroupId; };
	public void setMonsterGroupId(double monsterGroupId) { this.monsterGroupId = monsterGroupId; };
	public double getAngryStartTime() { return this.angryStartTime; };
	public void setAngryStartTime(double angryStartTime) { this.angryStartTime = angryStartTime; };
	public double getAttackTime() { return this.attackTime; };
	public void setAttackTime(double attackTime) { this.attackTime = attackTime; };

	public GameRolePlayMonsterAngryAtPlayerMessage(){
	}

	public GameRolePlayMonsterAngryAtPlayerMessage(long playerId, double monsterGroupId, double angryStartTime, double attackTime){
		this.playerId = playerId;
		this.monsterGroupId = monsterGroupId;
		this.angryStartTime = angryStartTime;
		this.attackTime = attackTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.playerId);
			writer.writeDouble(this.monsterGroupId);
			writer.writeDouble(this.angryStartTime);
			writer.writeDouble(this.attackTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerId = reader.readVarLong();
			this.monsterGroupId = reader.readDouble();
			this.angryStartTime = reader.readDouble();
			this.attackTime = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
