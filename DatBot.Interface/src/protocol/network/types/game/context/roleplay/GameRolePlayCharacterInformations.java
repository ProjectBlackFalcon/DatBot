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
import protocol.network.types.game.context.roleplay.GameRolePlayHumanoidInformations;
import protocol.network.types.game.character.alignment.ActorAlignmentInformations;

@SuppressWarnings("unused")
public class GameRolePlayCharacterInformations extends GameRolePlayHumanoidInformations {
	public static final int ProtocolId = 36;

	public ActorAlignmentInformations alignmentInfos;

	public GameRolePlayCharacterInformations(){
	}

	public GameRolePlayCharacterInformations(ActorAlignmentInformations alignmentInfos){
		this.alignmentInfos = alignmentInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			alignmentInfos.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.alignmentInfos = new ActorAlignmentInformations();
			this.alignmentInfos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("alignmentInfos : " + this.alignmentInfos);
	//}
}
