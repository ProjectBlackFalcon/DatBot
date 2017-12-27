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
import protocol.network.types.game.context.fight.GameFightMonsterInformations;
import protocol.network.types.game.character.alignment.ActorAlignmentInformations;

@SuppressWarnings("unused")
public class GameFightMonsterWithAlignmentInformations extends GameFightMonsterInformations {
	public static final int ProtocolId = 203;

	private ActorAlignmentInformations alignmentInfos;

	public ActorAlignmentInformations getAlignmentInfos() { return this.alignmentInfos; };
	public void setAlignmentInfos(ActorAlignmentInformations alignmentInfos) { this.alignmentInfos = alignmentInfos; };

	public GameFightMonsterWithAlignmentInformations(){
	}

	public GameFightMonsterWithAlignmentInformations(ActorAlignmentInformations alignmentInfos){
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

}
