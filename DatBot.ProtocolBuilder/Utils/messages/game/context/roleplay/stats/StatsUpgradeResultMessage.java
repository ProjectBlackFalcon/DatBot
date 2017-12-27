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
public class StatsUpgradeResultMessage extends NetworkMessage {
	public static final int ProtocolId = 5609;

	private int result;
	private int nbCharacBoost;

	public int getResult() { return this.result; };
	public void setResult(int result) { this.result = result; };
	public int getNbCharacBoost() { return this.nbCharacBoost; };
	public void setNbCharacBoost(int nbCharacBoost) { this.nbCharacBoost = nbCharacBoost; };

	public StatsUpgradeResultMessage(){
	}

	public StatsUpgradeResultMessage(int result, int nbCharacBoost){
		this.result = result;
		this.nbCharacBoost = nbCharacBoost;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.result);
			writer.writeVarShort(this.nbCharacBoost);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.result = reader.readByte();
			this.nbCharacBoost = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
