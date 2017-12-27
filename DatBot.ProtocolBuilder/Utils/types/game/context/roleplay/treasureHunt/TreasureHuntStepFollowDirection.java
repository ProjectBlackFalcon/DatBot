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
public class TreasureHuntStepFollowDirection extends TreasureHuntStep {
	public static final int ProtocolId = 468;

	private int direction;
	private int mapCount;

	public int getDirection() { return this.direction; };
	public void setDirection(int direction) { this.direction = direction; };
	public int getMapCount() { return this.mapCount; };
	public void setMapCount(int mapCount) { this.mapCount = mapCount; };

	public TreasureHuntStepFollowDirection(){
	}

	public TreasureHuntStepFollowDirection(int direction, int mapCount){
		this.direction = direction;
		this.mapCount = mapCount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.direction);
			writer.writeVarShort(this.mapCount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.direction = reader.readByte();
			this.mapCount = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
