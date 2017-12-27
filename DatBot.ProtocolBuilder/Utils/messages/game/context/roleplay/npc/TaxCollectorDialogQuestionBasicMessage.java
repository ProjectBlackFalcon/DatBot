package protocol.network.messages.game.context.roleplay.npc;

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
import protocol.network.types.game.context.roleplay.BasicGuildInformations;

@SuppressWarnings("unused")
public class TaxCollectorDialogQuestionBasicMessage extends NetworkMessage {
	public static final int ProtocolId = 5619;

	private BasicGuildInformations guildInfo;

	public BasicGuildInformations getGuildInfo() { return this.guildInfo; };
	public void setGuildInfo(BasicGuildInformations guildInfo) { this.guildInfo = guildInfo; };

	public TaxCollectorDialogQuestionBasicMessage(){
	}

	public TaxCollectorDialogQuestionBasicMessage(BasicGuildInformations guildInfo){
		this.guildInfo = guildInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			guildInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildInfo = new BasicGuildInformations();
			this.guildInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
