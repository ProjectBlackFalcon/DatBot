package protocol.network.messages.game.context.fight;

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
public class GameFightPauseMessage extends NetworkMessage {
	public static final int ProtocolId = 6754;

	private boolean isPaused;

	public boolean isIsPaused() { return this.isPaused; }
	public void setIsPaused(boolean isPaused) { this.isPaused = isPaused; };

	public GameFightPauseMessage(){
	}

	public GameFightPauseMessage(boolean isPaused){
		this.isPaused = isPaused;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.isPaused);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.isPaused = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
