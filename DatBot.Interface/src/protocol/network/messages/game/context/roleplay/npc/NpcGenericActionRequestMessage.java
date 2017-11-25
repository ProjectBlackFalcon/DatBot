package protocol.network.messages.game.context.roleplay.npc;

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
public class NpcGenericActionRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5898;

	public int npcId;
	public int npcActionId;
	public double npcMapId;

	public NpcGenericActionRequestMessage(){
	}

	public NpcGenericActionRequestMessage(int npcId, int npcActionId, double npcMapId){
		this.npcId = npcId;
		this.npcActionId = npcActionId;
		this.npcMapId = npcMapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.npcId);
			writer.writeByte(this.npcActionId);
			writer.writeDouble(this.npcMapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.npcId = reader.readInt();
			this.npcActionId = reader.readByte();
			this.npcMapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("npcId : " + this.npcId);
		//Network.appendDebug("npcActionId : " + this.npcActionId);
		//Network.appendDebug("npcMapId : " + this.npcMapId);
	//}
}