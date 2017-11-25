package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class HumanOptionSkillUse extends HumanOption {
	public static final int ProtocolId = 495;

	public int elementId;
	public int skillId;
	public double skillEndTime;

	public HumanOptionSkillUse(){
	}

	public HumanOptionSkillUse(int elementId, int skillId, double skillEndTime){
		this.elementId = elementId;
		this.skillId = skillId;
		this.skillEndTime = skillEndTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.elementId);
			writer.writeVarShort(this.skillId);
			writer.writeDouble(this.skillEndTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.elementId = reader.readVarInt();
			this.skillId = reader.readVarShort();
			this.skillEndTime = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("elementId : " + this.elementId);
		//Network.appendDebug("skillId : " + this.skillId);
		//Network.appendDebug("skillEndTime : " + this.skillEndTime);
	//}
}