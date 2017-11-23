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
public class ExchangeMountsTakenFromPaddockMessage extends NetworkMessage {
	public static final int ProtocolId = 6554;

	public String name;
	public int worldX;
	public int worldY;
	public String ownername;

	public ExchangeMountsTakenFromPaddockMessage(){
	}

	public ExchangeMountsTakenFromPaddockMessage(String name, int worldX, int worldY, String ownername){
		this.name = name;
		this.worldX = worldX;
		this.worldY = worldY;
		this.ownername = ownername;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeUTF(this.ownername);
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
			this.ownername = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("ownername : " + this.ownername);
	//}
}
