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
public class LivingObjectChangeSkinRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5725;

	private int livingUID;
	private int livingPosition;
	private int skinId;

	public int getLivingUID() { return this.livingUID; };
	public void setLivingUID(int livingUID) { this.livingUID = livingUID; };
	public int getLivingPosition() { return this.livingPosition; };
	public void setLivingPosition(int livingPosition) { this.livingPosition = livingPosition; };
	public int getSkinId() { return this.skinId; };
	public void setSkinId(int skinId) { this.skinId = skinId; };

	public LivingObjectChangeSkinRequestMessage(){
	}

	public LivingObjectChangeSkinRequestMessage(int livingUID, int livingPosition, int skinId){
		this.livingUID = livingUID;
		this.livingPosition = livingPosition;
		this.skinId = skinId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.livingUID);
			writer.writeByte(this.livingPosition);
			writer.writeVarInt(this.skinId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.livingUID = reader.readVarInt();
			this.livingPosition = reader.readByte();
			this.skinId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
