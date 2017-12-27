package protocol.network.messages.game.dare;

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
public class DareRewardConsumeValidationMessage extends NetworkMessage {
	public static final int ProtocolId = 6675;

	private double dareId;
	private int type;

	public double getDareId() { return this.dareId; };
	public void setDareId(double dareId) { this.dareId = dareId; };
	public int getType() { return this.type; };
	public void setType(int type) { this.type = type; };

	public DareRewardConsumeValidationMessage(){
	}

	public DareRewardConsumeValidationMessage(double dareId, int type){
		this.dareId = dareId;
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.dareId);
			writer.writeByte(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareId = reader.readDouble();
			this.type = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
