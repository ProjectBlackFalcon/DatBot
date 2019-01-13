package protocol.network.types.game.presets;

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
public class SpellForPreset extends NetworkMessage {
	public static final int ProtocolId = 557;

	private int spellId;
	private List<Integer> shortcuts;

	public int getSpellId() { return this.spellId; }
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public List<Integer> getShortcuts() { return this.shortcuts; }
	public void setShortcuts(List<Integer> shortcuts) { this.shortcuts = shortcuts; };

	public SpellForPreset(){
	}

	public SpellForPreset(int spellId, List<Integer> shortcuts){
		this.spellId = spellId;
		this.shortcuts = shortcuts;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.spellId);
			writer.writeShort(this.shortcuts.size());
			int _loc2_ = 0;
			while( _loc2_ < this.shortcuts.size()){
				writer.writeShort(this.shortcuts.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.shortcuts = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readShort();
				this.shortcuts.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
