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

	private String name;
	private int worldX;
	private int worldY;
	private String ownername;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public String getOwnername() { return this.ownername; }
	public void setOwnername(String ownername) { this.ownername = ownername; };

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
	}

}
