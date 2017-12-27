package protocol.network.messages.game.context.roleplay.stats;

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
public class StatsUpgradeRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5610;

	private boolean useAdditionnal;
	private int statId;
	private int boostPoint;

	public boolean isUseAdditionnal() { return this.useAdditionnal; };
	public void setUseAdditionnal(boolean useAdditionnal) { this.useAdditionnal = useAdditionnal; };
	public int getStatId() { return this.statId; };
	public void setStatId(int statId) { this.statId = statId; };
	public int getBoostPoint() { return this.boostPoint; };
	public void setBoostPoint(int boostPoint) { this.boostPoint = boostPoint; };

	public StatsUpgradeRequestMessage(){
	}

	public StatsUpgradeRequestMessage(boolean useAdditionnal, int statId, int boostPoint){
		this.useAdditionnal = useAdditionnal;
		this.statId = statId;
		this.boostPoint = boostPoint;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.useAdditionnal);
			writer.writeByte(this.statId);
			writer.writeVarShort(this.boostPoint);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.useAdditionnal = reader.readBoolean();
			this.statId = reader.readByte();
			this.boostPoint = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
