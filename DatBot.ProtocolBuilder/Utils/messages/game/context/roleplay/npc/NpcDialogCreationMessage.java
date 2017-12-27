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
public class NpcDialogCreationMessage extends NetworkMessage {
	public static final int ProtocolId = 5618;

	private double mapId;
	private int npcId;

	public double getMapId() { return this.mapId; };
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getNpcId() { return this.npcId; };
	public void setNpcId(int npcId) { this.npcId = npcId; };

	public NpcDialogCreationMessage(){
	}

	public NpcDialogCreationMessage(double mapId, int npcId){
		this.mapId = mapId;
		this.npcId = npcId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
			writer.writeInt(this.npcId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
			this.npcId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
