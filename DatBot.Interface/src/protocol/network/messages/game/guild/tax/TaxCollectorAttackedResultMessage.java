package protocol.network.messages.game.guild.tax;

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
import protocol.network.types.game.guild.tax.TaxCollectorBasicInformations;
import protocol.network.types.game.context.roleplay.BasicGuildInformations;

@SuppressWarnings("unused")
public class TaxCollectorAttackedResultMessage extends NetworkMessage {
	public static final int ProtocolId = 5635;

	public boolean deadOrAlive;
	public TaxCollectorBasicInformations basicInfos;
	public BasicGuildInformations guild;

	public TaxCollectorAttackedResultMessage(){
	}

	public TaxCollectorAttackedResultMessage(boolean deadOrAlive, TaxCollectorBasicInformations basicInfos, BasicGuildInformations guild){
		this.deadOrAlive = deadOrAlive;
		this.basicInfos = basicInfos;
		this.guild = guild;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.deadOrAlive);
			basicInfos.Serialize(writer);
			guild.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.deadOrAlive = reader.readBoolean();
			this.basicInfos = new TaxCollectorBasicInformations();
			this.basicInfos.Deserialize(reader);
			this.guild = new BasicGuildInformations();
			this.guild.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("deadOrAlive : " + this.deadOrAlive);
		//Network.appendDebug("basicInfos : " + this.basicInfos);
		//Network.appendDebug("guild : " + this.guild);
	//}
}
