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

	public int foodUID;
	public int foodPos;
	public boolean preview;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("foodUID : " + this.foodUID);
		//Network.appendDebug("foodPos : " + this.foodPos);
		//Network.appendDebug("preview : " + this.preview);
	//}
}