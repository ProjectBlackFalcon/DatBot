package protocol.network.messages.game.context.roleplay;

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
public class MapFightCountMessage extends NetworkMessage {
	public static final int ProtocolId = 210;

	private int fightCount;

	public int getFightCount() { return this.fightCount; }
	public void setFightCount(int fightCount) { this.fightCount = fightCount; };

	public MapFightCountMessage(){
	}

	public MapFightCountMessage(int fightCount){
		this.fightCount = fightCount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightCount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightCount = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
