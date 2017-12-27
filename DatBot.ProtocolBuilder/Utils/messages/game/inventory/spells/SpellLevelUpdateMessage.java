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
public class SpellLevelUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6743;

	private SpellItem spell;

	public SpellItem getSpell() { return this.spell; };
	public void setSpell(SpellItem spell) { this.spell = spell; };

	public SpellLevelUpdateMessage(){
	}

	public SpellLevelUpdateMessage(SpellItem spell){
		this.spell = spell;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			spell.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spell = new SpellItem();
			this.spell.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
