package protocol.network.types.game.paddock;

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
public class PaddockInformationsForSell extends NetworkMessage {
	public static final int ProtocolId = 222;

	public String guildOwner;
	public int worldX;
	public int worldY;
	public int subAreaId;
	public int nbMount;
	public int nbObject;
	public long price;

	public PaddockInformationsForSell(){
	}

	public PaddockInformationsForSell(String guildOwner, int worldX, int worldY, int subAreaId, int nbMount, int nbObject, long price){
		this.guildOwner = guildOwner;
		this.worldX = worldX;
		this.worldY = worldY;
		this.subAreaId = subAreaId;
		this.nbMount = nbMount;
		this.nbObject = nbObject;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.guildOwner);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeVarShort(this.subAreaId);
			writer.writeByte(this.nbMount);
			writer.writeByte(this.nbObject);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildOwner = reader.readUTF();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.subAreaId = reader.readVarShort();
			this.nbMount = reader.readByte();
			this.nbObject = reader.readByte();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("guildOwner : " + this.guildOwner);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("nbMount : " + this.nbMount);
		//Network.appendDebug("nbObject : " + this.nbObject);
		//Network.appendDebug("price : " + this.price);
	//}
}
