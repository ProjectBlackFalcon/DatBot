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
import protocol.network.messages.game.atlas.compass.CompassUpdateMessage;

@SuppressWarnings("unused")
public class CompassUpdatePartyMemberMessage extends CompassUpdateMessage {
	public static final int ProtocolId = 5589;

	public long memberId;
	public boolean active;

	public CompassUpdatePartyMemberMessage(){
	}

	public CompassUpdatePartyMemberMessage(long memberId, boolean active){
		this.memberId = memberId;
		this.active = active;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.memberId);
			writer.writeBoolean(this.active);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.memberId = reader.readVarLong();
			this.active = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("memberId : " + this.memberId);
		//Network.appendDebug("active : " + this.active);
	//}
}
