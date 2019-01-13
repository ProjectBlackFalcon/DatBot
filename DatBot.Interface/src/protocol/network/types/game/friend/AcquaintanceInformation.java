package protocol.network.types.game.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.friend.AbstractContactInformations;

@SuppressWarnings("unused")
public class AcquaintanceInformation extends AbstractContactInformations {
	public static final int ProtocolId = 561;

	private int playerState;

	public int getPlayerState() { return this.playerState; }
	public void setPlayerState(int playerState) { this.playerState = playerState; };

	public AcquaintanceInformation(){
	}

	public AcquaintanceInformation(int playerState){
		this.playerState = playerState;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.playerState);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerState = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
