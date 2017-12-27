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

	private int type;
	private int monsterId;
	private long kamas;
	private double dareId;

	public int getType() { return this.type; };
	public void setType(int type) { this.type = type; };
	public int getMonsterId() { return this.monsterId; };
	public void setMonsterId(int monsterId) { this.monsterId = monsterId; };
	public long getKamas() { return this.kamas; };
	public void setKamas(long kamas) { this.kamas = kamas; };
	public double getDareId() { return this.dareId; };
	public void setDareId(double dareId) { this.dareId = dareId; };

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
	}

}
