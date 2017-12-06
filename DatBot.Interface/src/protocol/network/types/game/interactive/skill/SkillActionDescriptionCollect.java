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
import protocol.network.types.game.interactive.skill.SkillActionDescriptionTimed;

@SuppressWarnings("unused")
public class SkillActionDescriptionCollect extends SkillActionDescriptionTimed {
	public static final int ProtocolId = 99;

	public int min;
	public int max;

	public SkillActionDescriptionCollect(){
	}

	public SkillActionDescriptionCollect(int min, int max){
		this.min = min;
		this.max = max;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.min);
			writer.writeVarShort(this.max);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.min = reader.readVarShort();
			this.max = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("min : " + this.min);
		//Network.appendDebug("max : " + this.max);
	//}
}
