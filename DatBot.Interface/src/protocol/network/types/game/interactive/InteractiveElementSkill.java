package protocol.network.types.game.interactive;

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
public class InteractiveElementSkill extends NetworkMessage {
	public static final int ProtocolId = 219;

	private int skillId;
	private int skillInstanceUid;

	public int getSkillId() { return this.skillId; };
	public void setSkillId(int skillId) { this.skillId = skillId; };
	public int getSkillInstanceUid() { return this.skillInstanceUid; };
	public void setSkillInstanceUid(int skillInstanceUid) { this.skillInstanceUid = skillInstanceUid; };

	public InteractiveElementSkill(){
	}

	public InteractiveElementSkill(int skillId, int skillInstanceUid){
		this.skillId = skillId;
		this.skillInstanceUid = skillInstanceUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.skillId);
			writer.writeInt(this.skillInstanceUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.skillId = reader.readVarInt();
			this.skillInstanceUid = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public String toString()
	{
		return "InteractiveElementSkill [skillId=" + skillId + ", skillInstanceUid=" + skillInstanceUid + "]";
	}

}
