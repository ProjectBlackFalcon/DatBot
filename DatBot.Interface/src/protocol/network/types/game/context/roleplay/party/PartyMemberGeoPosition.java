package protocol.network.types.game.context.roleplay.party;

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
public class PartyMemberGeoPosition extends NetworkMessage {
	public static final int ProtocolId = 378;

	public int memberId;
	public int worldX;
	public int worldY;
	public double mapId;
	public int subAreaId;

	public PartyMemberGeoPosition(){
	}

	public PartyMemberGeoPosition(int memberId, int worldX, int worldY, double mapId, int subAreaId){
		this.memberId = memberId;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.memberId);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.memberId = reader.readInt();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("memberId : " + this.memberId);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("mapId : " + this.mapId);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
	//}
}
