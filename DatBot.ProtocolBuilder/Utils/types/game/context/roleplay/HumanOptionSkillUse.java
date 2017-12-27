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

	private int elementId;
	private int skillId;
	private double skillEndTime;

	public int getElementId() { return this.elementId; };
	public void setElementId(int elementId) { this.elementId = elementId; };
	public int getSkillId() { return this.skillId; };
	public void setSkillId(int skillId) { this.skillId = skillId; };
	public double getSkillEndTime() { return this.skillEndTime; };
	public void setSkillEndTime(double skillEndTime) { this.skillEndTime = skillEndTime; };

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
	}

}
