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
import protocol.network.types.game.context.fight.GameFightFighterNamedInformations;
import protocol.network.types.game.character.alignment.ActorAlignmentInformations;

@SuppressWarnings("unused")
public class GameFightCharacterInformations extends GameFightFighterNamedInformations {
	public static final int ProtocolId = 46;

	public int level;
	public ActorAlignmentInformations alignmentInfos;
	public int breed;
	public boolean sex;

	public GameFightCharacterInformations(){
	}

	public GameFightCharacterInformations(int level, ActorAlignmentInformations alignmentInfos, int breed, boolean sex){
		this.level = level;
		this.alignmentInfos = alignmentInfos;
		this.breed = breed;
		this.sex = sex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.level);
			alignmentInfos.Serialize(writer);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.level = reader.readByte();
			this.alignmentInfos = new ActorAlignmentInformations();
			this.alignmentInfos.Deserialize(reader);
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("alignmentInfos : " + this.alignmentInfos);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
	//}
}
