package protocol.network.messages.game.context.roleplay.delay;

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
public class GameRolePlayDelayedActionFinishedMessage extends NetworkMessage {
	public static final int ProtocolId = 6150;

	private double delayedCharacterId;
	private int delayTypeId;

	public double getDelayedCharacterId() { return this.delayedCharacterId; };
	public void setDelayedCharacterId(double delayedCharacterId) { this.delayedCharacterId = delayedCharacterId; };
	public int getDelayTypeId() { return this.delayTypeId; };
	public void setDelayTypeId(int delayTypeId) { this.delayTypeId = delayTypeId; };

	public GameRolePlayDelayedActionFinishedMessage(){
	}

	public GameRolePlayDelayedActionFinishedMessage(double delayedCharacterId, int delayTypeId){
		this.delayedCharacterId = delayedCharacterId;
		this.delayTypeId = delayTypeId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.delayedCharacterId);
			writer.writeByte(this.delayTypeId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.delayedCharacterId = reader.readDouble();
			this.delayTypeId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}