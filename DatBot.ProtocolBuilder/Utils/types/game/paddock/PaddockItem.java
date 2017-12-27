package protocol.network.types.game.paddock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.ObjectItemInRolePlay;
import protocol.network.types.game.mount.ItemDurability;

@SuppressWarnings("unused")
public class PaddockItem extends ObjectItemInRolePlay {
	public static final int ProtocolId = 185;

	private ItemDurability durability;

	public ItemDurability getDurability() { return this.durability; };
	public void setDurability(ItemDurability durability) { this.durability = durability; };

	public PaddockItem(){
	}

	public PaddockItem(ItemDurability durability){
		this.durability = durability;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			durability.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.durability = new ItemDurability();
			this.durability.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
