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
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;

@SuppressWarnings("unused")
public class GameRolePlayNpcInformations extends GameRolePlayActorInformations {
	public static final int ProtocolId = 156;

	private int npcId;
	private boolean sex;
	private int specialArtworkId;

	public int getNpcId() { return this.npcId; };
	public void setNpcId(int npcId) { this.npcId = npcId; };
	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };
	public int getSpecialArtworkId() { return this.specialArtworkId; };
	public void setSpecialArtworkId(int specialArtworkId) { this.specialArtworkId = specialArtworkId; };

	public GameRolePlayNpcInformations(){
	}

	public GameRolePlayNpcInformations(int npcId, boolean sex, int specialArtworkId){
		this.npcId = npcId;
		this.sex = sex;
		this.specialArtworkId = specialArtworkId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.npcId);
			writer.writeBoolean(this.sex);
			writer.writeVarShort(this.specialArtworkId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.npcId = reader.readVarShort();
			this.sex = reader.readBoolean();
			this.specialArtworkId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "GameRolePlayNpcInformations [npcId=" + npcId + ", sex=" + sex + ", specialArtworkId=" + specialArtworkId + ", getContextualId()=" + getContextualId() + ", getLook()=" + getLook() + ", getDisposition()=" + getDisposition() + "]";
	}

}
