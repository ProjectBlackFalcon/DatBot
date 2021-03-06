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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class SkillActionDescription extends NetworkMessage {
	public static final int ProtocolId = 102;

	private int skillId;

	public int getSkillId() { return this.skillId; }
	public void setSkillId(int skillId) { this.skillId = skillId; };

	public SkillActionDescription(){
	}

	public SkillActionDescription(int skillId){
		this.skillId = skillId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.skillId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.skillId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
