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
public class SkillActionDescriptionCraft extends SkillActionDescription {
	public static final int ProtocolId = 100;

	public int probability;

	public SkillActionDescriptionCraft(){
	}

	public SkillActionDescriptionCraft(int probability){
		this.probability = probability;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.probability);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.probability = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("probability : " + this.probability);
	//}
}