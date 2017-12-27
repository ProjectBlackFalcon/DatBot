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

	private int durability;
	private int durabilityMax;

	public int getDurability() { return this.durability; };
	public void setDurability(int durability) { this.durability = durability; };
	public int getDurabilityMax() { return this.durabilityMax; };
	public void setDurabilityMax(int durabilityMax) { this.durabilityMax = durabilityMax; };

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

}
