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
public class TreasureHuntStepFollowDirectionToPOI extends TreasureHuntStep {
	public static final int ProtocolId = 461;

	public int direction;
	public int poiLabelId;

	public TreasureHuntStepFollowDirectionToPOI(){
	}

	public TreasureHuntStepFollowDirectionToPOI(int direction, int poiLabelId){
		this.direction = direction;
		this.poiLabelId = poiLabelId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.direction);
			writer.writeVarShort(this.poiLabelId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.direction = reader.readByte();
			this.poiLabelId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("direction : " + this.direction);
		//Network.appendDebug("poiLabelId : " + this.poiLabelId);
	//}
}
