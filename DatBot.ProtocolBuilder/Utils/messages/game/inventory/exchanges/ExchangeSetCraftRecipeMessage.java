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
public class ExchangeSetCraftRecipeMessage extends NetworkMessage {
	public static final int ProtocolId = 6389;

	private int objectGID;

	public int getObjectGID() { return this.objectGID; };
	public void setObjectGID(int objectGID) { this.objectGID = objectGID; };

	public ExchangeSetCraftRecipeMessage(){
	}

	public ExchangeSetCraftRecipeMessage(int objectGID){
		this.objectGID = objectGID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.objectGID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectGID = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
