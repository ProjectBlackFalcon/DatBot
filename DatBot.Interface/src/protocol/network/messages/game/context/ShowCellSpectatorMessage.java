package protocol.network.messages.game.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.ShowCellMessage;

@SuppressWarnings("unused")
public class ShowCellSpectatorMessage extends ShowCellMessage {
	public static final int ProtocolId = 6158;

	private String playerName;

	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };

	public ShowCellSpectatorMessage(){
	}

	public ShowCellSpectatorMessage(String playerName){
		this.playerName = playerName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.playerName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
