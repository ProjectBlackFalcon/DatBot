package protocol.network.messages.game.script;

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
public class URLOpenMessage extends NetworkMessage {
	public static final int ProtocolId = 6266;

	private int urlId;

	public int getUrlId() { return this.urlId; }
	public void setUrlId(int urlId) { this.urlId = urlId; };

	public URLOpenMessage(){
	}

	public URLOpenMessage(int urlId){
		this.urlId = urlId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.urlId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.urlId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
