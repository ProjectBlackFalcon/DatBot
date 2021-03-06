package protocol.network.messages.game.context.roleplay.quest;

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
import protocol.network.types.game.context.roleplay.quest.QuestActiveInformations;

@SuppressWarnings("unused")
public class QuestStepInfoMessage extends NetworkMessage {
	public static final int ProtocolId = 5625;

	private QuestActiveInformations infos;

	public QuestActiveInformations getInfos() { return this.infos; }
	public void setInfos(QuestActiveInformations infos) { this.infos = infos; };

	public QuestStepInfoMessage(){
	}

	public QuestStepInfoMessage(QuestActiveInformations infos){
		this.infos = infos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(QuestActiveInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.infos = (QuestActiveInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.infos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
