package protocol.network.messages.game.context.roleplay.lockable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.lockable.LockableStateUpdateAbstractMessage;

@SuppressWarnings("unused")
public class LockableStateUpdateHouseDoorMessage extends LockableStateUpdateAbstractMessage {
	public static final int ProtocolId = 5668;

	public int houseId;
	public int instanceId;
	public boolean secondHand;

	public LockableStateUpdateHouseDoorMessage(){
	}

	public LockableStateUpdateHouseDoorMessage(int houseId, int instanceId, boolean secondHand){
		this.houseId = houseId;
		this.instanceId = instanceId;
		this.secondHand = secondHand;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.houseId);
			writer.writeInt(this.instanceId);
			writer.writeBoolean(this.secondHand);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.houseId = reader.readVarInt();
			this.instanceId = reader.readInt();
			this.secondHand = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
	//}
}
