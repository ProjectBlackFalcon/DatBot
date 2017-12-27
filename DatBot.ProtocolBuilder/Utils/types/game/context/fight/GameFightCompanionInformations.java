package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.GameFightFighterInformations;

@SuppressWarnings("unused")
public class GameFightCompanionInformations extends GameFightFighterInformations {
	public static final int ProtocolId = 450;

	private int companionGenericId;
	private int level;
	private double masterId;

	public int getCompanionGenericId() { return this.companionGenericId; };
	public void setCompanionGenericId(int companionGenericId) { this.companionGenericId = companionGenericId; };
	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };
	public double getMasterId() { return this.masterId; };
	public void setMasterId(double masterId) { this.masterId = masterId; };

	public GameFightCompanionInformations(){
	}

	public GameFightCompanionInformations(int companionGenericId, int level, double masterId){
		this.companionGenericId = companionGenericId;
		this.level = level;
		this.masterId = masterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.companionGenericId);
			writer.writeVarShort(this.level);
			writer.writeDouble(this.masterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.companionGenericId = reader.readByte();
			this.level = reader.readVarShort();
			this.masterId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
