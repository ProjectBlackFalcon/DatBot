package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class HumanOptionEmote extends HumanOption {
	public static final int ProtocolId = 407;

	public int emoteId;
	public double emoteStartTime;

	public HumanOptionEmote(){
	}

	public HumanOptionEmote(int emoteId, double emoteStartTime){
		this.emoteId = emoteId;
		this.emoteStartTime = emoteStartTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.emoteId);
			writer.writeDouble(this.emoteStartTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.emoteId = reader.readByte();
			this.emoteStartTime = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("emoteId : " + this.emoteId);
		//Network.appendDebug("emoteStartTime : " + this.emoteStartTime);
	//}
}