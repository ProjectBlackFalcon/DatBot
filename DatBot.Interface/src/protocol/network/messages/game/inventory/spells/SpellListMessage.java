package protocol.network.messages.game.inventory.spells;

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
import protocol.network.types.game.data.items.SpellItem;

@SuppressWarnings("unused")
public class SpellListMessage extends NetworkMessage {
	public static final int ProtocolId = 1200;

	private boolean spellPrevisualization;
	private List<SpellItem> spells;

	public boolean isSpellPrevisualization() { return this.spellPrevisualization; }
	public void setSpellPrevisualization(boolean spellPrevisualization) { this.spellPrevisualization = spellPrevisualization; };
	public List<SpellItem> getSpells() { return this.spells; }
	public void setSpells(List<SpellItem> spells) { this.spells = spells; };

	public SpellListMessage(){
	}

	public SpellListMessage(boolean spellPrevisualization, List<SpellItem> spells){
		this.spellPrevisualization = spellPrevisualization;
		this.spells = spells;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.spellPrevisualization);
			writer.writeShort(this.spells.size());
			int _loc2_ = 0;
			while( _loc2_ < this.spells.size()){
				this.spells.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellPrevisualization = reader.readBoolean();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.spells = new ArrayList<SpellItem>();
			while( _loc3_ <  _loc2_){
				SpellItem _loc15_ = new SpellItem();
				_loc15_.Deserialize(reader);
				this.spells.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
