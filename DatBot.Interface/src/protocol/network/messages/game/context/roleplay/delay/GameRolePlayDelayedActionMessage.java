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
public class GameRolePlayDelayedActionMessage extends NetworkMessage {
	public static final int ProtocolId = 6153;

	public double delayedCharacterId;
	public int delayTypeId;
	public double delayEndTime;

	public GameRolePlayDelayedActionMessage(){
	}

	public GameRolePlayDelayedActionMessage(double delayedCharacterId, int delayTypeId, double delayEndTime){
		this.delayedCharacterId = delayedCharacterId;
		this.delayTypeId = delayTypeId;
		this.delayEndTime = delayEndTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.delayedCharacterId);
			writer.writeByte(this.delayTypeId);
			writer.writeDouble(this.delayEndTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.delayedCharacterId = reader.readDouble();
			this.delayTypeId = reader.readByte();
			this.delayEndTime = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("delayedCharacterId : " + this.delayedCharacterId);
		//Network.appendDebug("delayTypeId : " + this.delayTypeId);
		//Network.appendDebug("delayEndTime : " + this.delayEndTime);
	//}
}
