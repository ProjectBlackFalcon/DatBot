package protocol.network.messages.game.guild;

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
public class GuildSpellUpgradeRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5699;

	private int spellId;

	public int getSpellId() { return this.spellId; }
	public void setSpellId(int spellId) { this.spellId = spellId; };

	public GuildSpellUpgradeRequestMessage(){
	}

	public GuildSpellUpgradeRequestMessage(int spellId){
		this.spellId = spellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.spellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
