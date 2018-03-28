package protocol.network.messages.game.friend;

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
public class SpouseStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6265;

	private boolean hasSpouse;

	public boolean isHasSpouse() { return this.hasSpouse; }
	public void setHasSpouse(boolean hasSpouse) { this.hasSpouse = hasSpouse; };

	public SpouseStatusMessage(){
	}

	public SpouseStatusMessage(boolean hasSpouse){
		this.hasSpouse = hasSpouse;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.hasSpouse);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.hasSpouse = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
