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
public class GameRolePlayTreasureHintInformations extends GameRolePlayActorInformations {
	public static final int ProtocolId = 471;

	private int npcId;

	public int getNpcId() { return this.npcId; };
	public void setNpcId(int npcId) { this.npcId = npcId; };

	public GameRolePlayTreasureHintInformations(){
	}

	public GameRolePlayTreasureHintInformations(int npcId){
		this.npcId = npcId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.npcId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.npcId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
