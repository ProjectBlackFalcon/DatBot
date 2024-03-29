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
public class CinematicMessage extends NetworkMessage {
	public static final int ProtocolId = 6053;

	private int cinematicId;

	public int getCinematicId() { return this.cinematicId; }
	public void setCinematicId(int cinematicId) { this.cinematicId = cinematicId; };

	public CinematicMessage(){
	}

	public CinematicMessage(int cinematicId){
		this.cinematicId = cinematicId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.cinematicId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cinematicId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
