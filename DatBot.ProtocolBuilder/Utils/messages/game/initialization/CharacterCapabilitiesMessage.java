package protocol.network.messages.game.initialization;

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
public class CharacterCapabilitiesMessage extends NetworkMessage {
	public static final int ProtocolId = 6339;

	private int guildEmblemSymbolCategories;

	public int getGuildEmblemSymbolCategories() { return this.guildEmblemSymbolCategories; }
	public void setGuildEmblemSymbolCategories(int guildEmblemSymbolCategories) { this.guildEmblemSymbolCategories = guildEmblemSymbolCategories; };

	public CharacterCapabilitiesMessage(){
	}

	public CharacterCapabilitiesMessage(int guildEmblemSymbolCategories){
		this.guildEmblemSymbolCategories = guildEmblemSymbolCategories;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.guildEmblemSymbolCategories);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildEmblemSymbolCategories = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
