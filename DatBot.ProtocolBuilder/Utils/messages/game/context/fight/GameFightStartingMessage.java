package protocol.network.messages.game.context.fight;

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
public class GameFightStartingMessage extends NetworkMessage {
	public static final int ProtocolId = 700;

	private int fightType;
	private int fightId;
	private double attackerId;
	private double defenderId;

	public int getFightType() { return this.fightType; };
	public void setFightType(int fightType) { this.fightType = fightType; };
	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };
	public double getAttackerId() { return this.attackerId; };
	public void setAttackerId(double attackerId) { this.attackerId = attackerId; };
	public double getDefenderId() { return this.defenderId; };
	public void setDefenderId(double defenderId) { this.defenderId = defenderId; };

	public GameFightStartingMessage(){
	}

	public GameFightStartingMessage(int fightType, int fightId, double attackerId, double defenderId){
		this.fightType = fightType;
		this.fightId = fightId;
		this.attackerId = attackerId;
		this.defenderId = defenderId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.fightType);
			writer.writeVarShort(this.fightId);
			writer.writeDouble(this.attackerId);
			writer.writeDouble(this.defenderId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightType = reader.readByte();
			this.fightId = reader.readVarShort();
			this.attackerId = reader.readDouble();
			this.defenderId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
