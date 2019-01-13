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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class MonsterInGroupLightInformations extends NetworkMessage {
	public static final int ProtocolId = 395;

	private int genericId;
	private int grade;
	private int level;

	public int getGenericId() { return this.genericId; }
	public void setGenericId(int genericId) { this.genericId = genericId; };
	public int getGrade() { return this.grade; }
	public void setGrade(int grade) { this.grade = grade; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };

	public MonsterInGroupLightInformations(){
	}

	public MonsterInGroupLightInformations(int genericId, int grade, int level){
		this.genericId = genericId;
		this.grade = grade;
		this.level = level;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.genericId);
			writer.writeByte(this.grade);
			writer.writeShort(this.level);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.genericId = reader.readInt();
			this.grade = reader.readByte();
			this.level = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
