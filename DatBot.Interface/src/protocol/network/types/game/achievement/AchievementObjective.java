package protocol.network.types.game.achievement;

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
public class AchievementObjective extends NetworkMessage {
	public static final int ProtocolId = 404;

	public int id;
	public int maxValue;

	public AchievementObjective(){
	}

	public AchievementObjective(int id, int maxValue){
		this.id = id;
		this.maxValue = maxValue;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.id);
			writer.writeVarShort(this.maxValue);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarInt();
			this.maxValue = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("maxValue : " + this.maxValue);
	//}
}
