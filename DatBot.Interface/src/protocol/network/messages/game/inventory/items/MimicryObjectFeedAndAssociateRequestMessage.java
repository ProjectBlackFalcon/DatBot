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
import protocol.network.messages.game.inventory.items.SymbioticObjectAssociateRequestMessage;

@SuppressWarnings("unused")
public class MimicryObjectFeedAndAssociateRequestMessage extends SymbioticObjectAssociateRequestMessage {
	public static final int ProtocolId = 6460;

	private int foodUID;
	private int foodPos;
	private boolean preview;

	public int getFoodUID() { return this.foodUID; }
	public void setFoodUID(int foodUID) { this.foodUID = foodUID; };
	public int getFoodPos() { return this.foodPos; }
	public void setFoodPos(int foodPos) { this.foodPos = foodPos; };
	public boolean isPreview() { return this.preview; }
	public void setPreview(boolean preview) { this.preview = preview; };

	public MimicryObjectFeedAndAssociateRequestMessage(){
	}

	public MimicryObjectFeedAndAssociateRequestMessage(int foodUID, int foodPos, boolean preview){
		this.foodUID = foodUID;
		this.foodPos = foodPos;
		this.preview = preview;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.foodUID);
			writer.writeByte(this.foodPos);
			writer.writeBoolean(this.preview);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.foodUID = reader.readVarInt();
			this.foodPos = reader.readByte();
			this.preview = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
