package protocol.network.messages.game.context.roleplay.houses;

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
public class HouseToSellFilterMessage extends NetworkMessage {
	public static final int ProtocolId = 6137;

	public int areaId;
	public int atLeastNbRoom;
	public int atLeastNbChest;
	public int skillRequested;
	public long maxPrice;

	public HouseToSellFilterMessage(){
	}

	public HouseToSellFilterMessage(int areaId, int atLeastNbRoom, int atLeastNbChest, int skillRequested, long maxPrice){
		this.areaId = areaId;
		this.atLeastNbRoom = atLeastNbRoom;
		this.atLeastNbChest = atLeastNbChest;
		this.skillRequested = skillRequested;
		this.maxPrice = maxPrice;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.areaId);
			writer.writeByte(this.atLeastNbRoom);
			writer.writeByte(this.atLeastNbChest);
			writer.writeVarShort(this.skillRequested);
			writer.writeVarLong(this.maxPrice);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.areaId = reader.readInt();
			this.atLeastNbRoom = reader.readByte();
			this.atLeastNbChest = reader.readByte();
			this.skillRequested = reader.readVarShort();
			this.maxPrice = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("areaId : " + this.areaId);
		//Network.appendDebug("atLeastNbRoom : " + this.atLeastNbRoom);
		//Network.appendDebug("atLeastNbChest : " + this.atLeastNbChest);
		//Network.appendDebug("skillRequested : " + this.skillRequested);
		//Network.appendDebug("maxPrice : " + this.maxPrice);
	//}
}