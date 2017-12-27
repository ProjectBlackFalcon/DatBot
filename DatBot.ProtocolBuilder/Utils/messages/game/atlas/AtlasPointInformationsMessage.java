package protocol.network.messages.game.atlas;

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
import protocol.network.types.game.context.roleplay.AtlasPointsInformations;

@SuppressWarnings("unused")
public class AtlasPointInformationsMessage extends NetworkMessage {
	public static final int ProtocolId = 5956;

	private AtlasPointsInformations type;

	public AtlasPointsInformations getType() { return this.type; };
	public void setType(AtlasPointsInformations type) { this.type = type; };

	public AtlasPointInformationsMessage(){
	}

	public AtlasPointInformationsMessage(AtlasPointsInformations type){
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			type.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = new AtlasPointsInformations();
			this.type.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
