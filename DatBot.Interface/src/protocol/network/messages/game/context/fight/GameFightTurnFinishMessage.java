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
public class GameFightTurnFinishMessage extends NetworkMessage {
	public static final int ProtocolId = 718;

	private boolean isAfk;

	public boolean isIsAfk() { return this.isAfk; }
	public void setIsAfk(boolean isAfk) { this.isAfk = isAfk; };

	public GameFightTurnFinishMessage(){
	}

	public GameFightTurnFinishMessage(boolean isAfk){
		this.isAfk = isAfk;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.isAfk);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.isAfk = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
