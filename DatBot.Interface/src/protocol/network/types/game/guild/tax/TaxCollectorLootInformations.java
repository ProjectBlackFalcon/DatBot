package protocol.network.types.game.guild.tax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.guild.tax.TaxCollectorComplementaryInformations;

@SuppressWarnings("unused")
public class TaxCollectorLootInformations extends TaxCollectorComplementaryInformations {
	public static final int ProtocolId = 372;

	private long kamas;
	private long experience;
	private int pods;
	private long itemsValue;

	public long getKamas() { return this.kamas; }
	public void setKamas(long kamas) { this.kamas = kamas; };
	public long getExperience() { return this.experience; }
	public void setExperience(long experience) { this.experience = experience; };
	public int getPods() { return this.pods; }
	public void setPods(int pods) { this.pods = pods; };
	public long getItemsValue() { return this.itemsValue; }
	public void setItemsValue(long itemsValue) { this.itemsValue = itemsValue; };

	public TaxCollectorLootInformations(){
	}

	public TaxCollectorLootInformations(long kamas, long experience, int pods, long itemsValue){
		this.kamas = kamas;
		this.experience = experience;
		this.pods = pods;
		this.itemsValue = itemsValue;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.kamas);
			writer.writeVarLong(this.experience);
			writer.writeVarInt(this.pods);
			writer.writeVarLong(this.itemsValue);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.kamas = reader.readVarLong();
			this.experience = reader.readVarLong();
			this.pods = reader.readVarInt();
			this.itemsValue = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
