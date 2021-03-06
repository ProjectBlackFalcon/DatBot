package protocol.network.types.game.interactive.skill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.interactive.skill.SkillActionDescription;

@SuppressWarnings("unused")
public class SkillActionDescriptionTimed extends SkillActionDescription {
	public static final int ProtocolId = 103;

	private int time;

	public int getTime() { return this.time; }
	public void setTime(int time) { this.time = time; };

	public SkillActionDescriptionTimed(){
	}

	public SkillActionDescriptionTimed(int time){
		this.time = time;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.time);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.time = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
