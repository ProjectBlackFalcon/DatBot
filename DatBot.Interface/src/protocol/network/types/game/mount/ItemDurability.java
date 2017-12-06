package protocol.network.types.game.mount;

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
public class ItemDurability extends NetworkMessage {
	public static final int ProtocolId = 168;

	public int durability;
	public int durabilityMax;

	public ItemDurability(){
	}

	public ItemDurability(int durability, int durabilityMax){
		this.durability = durability;
		this.durabilityMax = durabilityMax;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.durability);
			writer.writeShort(this.durabilityMax);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.durability = reader.readShort();
			this.durabilityMax = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("durability : " + this.durability);
		//Network.appendDebug("durabilityMax : " + this.durabilityMax);
	//}
}
