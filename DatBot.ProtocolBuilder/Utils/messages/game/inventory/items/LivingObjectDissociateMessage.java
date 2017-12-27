package protocol.network.messages.game.inventory.items;

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
public class LivingObjectDissociateMessage extends NetworkMessage {
	public static final int ProtocolId = 5723;

	private int livingUID;
	private int livingPosition;

	public int getLivingUID() { return this.livingUID; };
	public void setLivingUID(int livingUID) { this.livingUID = livingUID; };
	public int getLivingPosition() { return this.livingPosition; };
	public void setLivingPosition(int livingPosition) { this.livingPosition = livingPosition; };

	public LivingObjectDissociateMessage(){
	}

	public LivingObjectDissociateMessage(int livingUID, int livingPosition){
		this.livingUID = livingUID;
		this.livingPosition = livingPosition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.livingUID);
			writer.writeByte(this.livingPosition);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.livingUID = reader.readVarInt();
			this.livingPosition = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
