package protocol.network.types.game.data.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.Item;

@SuppressWarnings("unused")
public class SpellItem extends Item {
	public static final int ProtocolId = 49;

	private int spellId;
	private int spellLevel;

	public int getSpellId() { return this.spellId; }
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public int getSpellLevel() { return this.spellLevel; }
	public void setSpellLevel(int spellLevel) { this.spellLevel = spellLevel; };

	public SpellItem(){
	}

	public SpellItem(int spellId, int spellLevel){
		this.spellId = spellId;
		this.spellLevel = spellLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.spellId);
			writer.writeShort(this.spellLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.spellId = reader.readInt();
			this.spellLevel = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
