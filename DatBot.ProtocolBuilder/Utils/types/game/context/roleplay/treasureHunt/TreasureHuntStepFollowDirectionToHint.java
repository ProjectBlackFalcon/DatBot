package protocol.network.types.game.context.roleplay.treasureHunt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStep;

@SuppressWarnings("unused")
public class TreasureHuntStepFollowDirectionToHint extends TreasureHuntStep {
	public static final int ProtocolId = 472;

	private int direction;
	private int npcId;

	public int getDirection() { return this.direction; }
	public void setDirection(int direction) { this.direction = direction; };
	public int getNpcId() { return this.npcId; }
	public void setNpcId(int npcId) { this.npcId = npcId; };

	public TreasureHuntStepFollowDirectionToHint(){
	}

	public TreasureHuntStepFollowDirectionToHint(int direction, int npcId){
		this.direction = direction;
		this.npcId = npcId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.direction);
			writer.writeVarShort(this.npcId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.direction = reader.readByte();
			this.npcId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
