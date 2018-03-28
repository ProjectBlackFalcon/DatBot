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

	private int npcId;
	private int npcActionId;
	private double npcMapId;

	public int getNpcId() { return this.npcId; }
	public void setNpcId(int npcId) { this.npcId = npcId; };
	public int getNpcActionId() { return this.npcActionId; }
	public void setNpcActionId(int npcActionId) { this.npcActionId = npcActionId; };
	public double getNpcMapId() { return this.npcMapId; }
	public void setNpcMapId(double npcMapId) { this.npcMapId = npcMapId; };

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
	}

}
