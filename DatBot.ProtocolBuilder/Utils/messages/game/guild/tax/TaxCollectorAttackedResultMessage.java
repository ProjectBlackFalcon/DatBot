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

	private boolean deadOrAlive;
	private TaxCollectorBasicInformations basicInfos;
	private BasicGuildInformations guild;

	public boolean isDeadOrAlive() { return this.deadOrAlive; }
	public void setDeadOrAlive(boolean deadOrAlive) { this.deadOrAlive = deadOrAlive; };
	public TaxCollectorBasicInformations getBasicInfos() { return this.basicInfos; }
	public void setBasicInfos(TaxCollectorBasicInformations basicInfos) { this.basicInfos = basicInfos; };
	public BasicGuildInformations getGuild() { return this.guild; }
	public void setGuild(BasicGuildInformations guild) { this.guild = guild; };

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
	}

}
