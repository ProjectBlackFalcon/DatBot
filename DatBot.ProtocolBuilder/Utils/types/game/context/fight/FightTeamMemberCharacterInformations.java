package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.FightTeamMemberInformations;

@SuppressWarnings("unused")
public class FightTeamMemberCharacterInformations extends FightTeamMemberInformations {
	public static final int ProtocolId = 13;

	private String name;
	private int level;

	public String getName() { return this.name; };
	public void setName(String name) { this.name = name; };
	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };

	public FightTeamMemberCharacterInformations(){
	}

	public FightTeamMemberCharacterInformations(String name, int level){
		this.name = name;
		this.level = level;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.name);
			writer.writeVarShort(this.level);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.name = reader.readUTF();
			this.level = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
