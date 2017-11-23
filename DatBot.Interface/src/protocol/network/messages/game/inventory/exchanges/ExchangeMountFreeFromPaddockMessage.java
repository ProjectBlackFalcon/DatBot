package protocol.network.messages.game.inventory.exchanges;

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
public class ExchangeMountFreeFromPaddockMessage extends NetworkMessage {
	public static final int ProtocolId = 6055;

	public String name;
	public int worldX;
	public int worldY;
	public String liberator;

	public ExchangeMountFreeFromPaddockMessage(){
	}

	public ExchangeMountFreeFromPaddockMessage(String name, int worldX, int worldY, String liberator){
		this.name = name;
		this.worldX = worldX;
		this.worldY = worldY;
		this.liberator = liberator;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeUTF(this.liberator);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.name = reader.readUTF();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.liberator = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("liberator : " + this.liberator);
	//}
}
