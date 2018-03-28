package protocol.network.types.game.context;

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
import protocol.network.types.game.context.TaxCollectorStaticInformations;

@SuppressWarnings("unused")
public class GameRolePlayTaxCollectorInformations extends GameRolePlayActorInformations {
	public static final int ProtocolId = 148;

	private TaxCollectorStaticInformations identification;
	private int guildLevel;
	private int taxCollectorAttack;

	public TaxCollectorStaticInformations getIdentification() { return this.identification; }
	public void setIdentification(TaxCollectorStaticInformations identification) { this.identification = identification; };
	public int getGuildLevel() { return this.guildLevel; }
	public void setGuildLevel(int guildLevel) { this.guildLevel = guildLevel; };
	public int getTaxCollectorAttack() { return this.taxCollectorAttack; }
	public void setTaxCollectorAttack(int taxCollectorAttack) { this.taxCollectorAttack = taxCollectorAttack; };

	public GameRolePlayTaxCollectorInformations(){
	}

	public GameRolePlayTaxCollectorInformations(TaxCollectorStaticInformations identification, int guildLevel, int taxCollectorAttack){
		this.identification = identification;
		this.guildLevel = guildLevel;
		this.taxCollectorAttack = taxCollectorAttack;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(TaxCollectorStaticInformations.ProtocolId);
			writer.writeByte(this.guildLevel);
			writer.writeInt(this.taxCollectorAttack);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.identification = (TaxCollectorStaticInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.identification.Deserialize(reader);
			this.guildLevel = reader.readByte();
			this.taxCollectorAttack = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
