package protocol.network.messages.game.atlas.compass;

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
public class CompassResetMessage extends NetworkMessage {
	public static final int ProtocolId = 5584;

	private int type;

	public int getType() { return this.type; };
	public void setType(int type) { this.type = type; };

	public CompassResetMessage(){
	}

	public CompassResetMessage(int type){
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
