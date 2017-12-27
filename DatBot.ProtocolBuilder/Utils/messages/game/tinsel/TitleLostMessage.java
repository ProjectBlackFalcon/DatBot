package protocol.network.messages.game.tinsel;

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
public class TitleLostMessage extends NetworkMessage {
	public static final int ProtocolId = 6371;

	private int titleId;

	public int getTitleId() { return this.titleId; };
	public void setTitleId(int titleId) { this.titleId = titleId; };

	public TitleLostMessage(){
	}

	public TitleLostMessage(int titleId){
		this.titleId = titleId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.titleId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.titleId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
