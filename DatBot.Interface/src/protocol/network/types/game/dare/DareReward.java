package protocol.network.types.game.dare;

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
public class DareReward extends NetworkMessage {
	public static final int ProtocolId = 505;

	public int type;
	public int monsterId;
	public long kamas;
	public double dareId;

	public DareReward(){
	}

	public DareReward(int type, int monsterId, long kamas, double dareId){
		this.type = type;
		this.monsterId = monsterId;
		this.kamas = kamas;
		this.dareId = dareId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.type);
			writer.writeVarShort(this.monsterId);
			writer.writeVarLong(this.kamas);
			writer.writeDouble(this.dareId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readByte();
			this.monsterId = reader.readVarShort();
			this.kamas = reader.readVarLong();
			this.dareId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("type : " + this.type);
		//Network.appendDebug("monsterId : " + this.monsterId);
		//Network.appendDebug("kamas : " + this.kamas);
		//Network.appendDebug("dareId : " + this.dareId);
	//}
}
