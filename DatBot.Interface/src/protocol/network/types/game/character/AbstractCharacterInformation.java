package protocol.network.types.game.character;

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
public class AbstractCharacterInformation extends NetworkMessage {
	public static final int ProtocolId = 400;

	private long id;
	
	public long getId(){ return this.id; };

	public AbstractCharacterInformation(){
	}

	public AbstractCharacterInformation(long id){
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarUhLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
