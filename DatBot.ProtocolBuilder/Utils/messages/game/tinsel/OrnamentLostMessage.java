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
public class OrnamentLostMessage extends NetworkMessage {
	public static final int ProtocolId = 6770;

	private int ornamentId;

	public int getOrnamentId() { return this.ornamentId; }
	public void setOrnamentId(int ornamentId) { this.ornamentId = ornamentId; };

	public OrnamentLostMessage(){
	}

	public OrnamentLostMessage(int ornamentId){
		this.ornamentId = ornamentId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.ornamentId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.ornamentId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
