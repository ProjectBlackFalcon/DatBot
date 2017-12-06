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

	public int npcId;
	public boolean sex;
	public int specialArtworkId;

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

	//private void append(){
		//Network.appendDebug("npcId : " + this.npcId);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("specialArtworkId : " + this.specialArtworkId);
	//}
}
