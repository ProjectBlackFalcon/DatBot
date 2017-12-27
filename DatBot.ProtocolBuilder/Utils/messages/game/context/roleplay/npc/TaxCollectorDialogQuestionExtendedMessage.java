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
import protocol.network.messages.game.context.roleplay.npc.TaxCollectorDialogQuestionBasicMessage;

@SuppressWarnings("unused")
public class TaxCollectorDialogQuestionExtendedMessage extends TaxCollectorDialogQuestionBasicMessage {
	public static final int ProtocolId = 5615;

	private int maxPods;
	private int prospecting;
	private int wisdom;
	private int taxCollectorsCount;
	private int taxCollectorAttack;
	private long kamas;
	private long experience;
	private int pods;
	private long itemsValue;

	public int getMaxPods() { return this.maxPods; };
	public void setMaxPods(int maxPods) { this.maxPods = maxPods; };
	public int getProspecting() { return this.prospecting; };
	public void setProspecting(int prospecting) { this.prospecting = prospecting; };
	public int getWisdom() { return this.wisdom; };
	public void setWisdom(int wisdom) { this.wisdom = wisdom; };
	public int getTaxCollectorsCount() { return this.taxCollectorsCount; };
	public void setTaxCollectorsCount(int taxCollectorsCount) { this.taxCollectorsCount = taxCollectorsCount; };
	public int getTaxCollectorAttack() { return this.taxCollectorAttack; };
	public void setTaxCollectorAttack(int taxCollectorAttack) { this.taxCollectorAttack = taxCollectorAttack; };
	public long getKamas() { return this.kamas; };
	public void setKamas(long kamas) { this.kamas = kamas; };
	public long getExperience() { return this.experience; };
	public void setExperience(long experience) { this.experience = experience; };
	public int getPods() { return this.pods; };
	public void setPods(int pods) { this.pods = pods; };
	public long getItemsValue() { return this.itemsValue; };
	public void setItemsValue(long itemsValue) { this.itemsValue = itemsValue; };

	public TaxCollectorDialogQuestionExtendedMessage(){
	}

	public TaxCollectorDialogQuestionExtendedMessage(int maxPods, int prospecting, int wisdom, int taxCollectorsCount, int taxCollectorAttack, long kamas, long experience, int pods, long itemsValue){
		this.maxPods = maxPods;
		this.prospecting = prospecting;
		this.wisdom = wisdom;
		this.taxCollectorsCount = taxCollectorsCount;
		this.taxCollectorAttack = taxCollectorAttack;
		this.kamas = kamas;
		this.experience = experience;
		this.pods = pods;
		this.itemsValue = itemsValue;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.maxPods);
			writer.writeVarShort(this.prospecting);
			writer.writeVarShort(this.wisdom);
			writer.writeByte(this.taxCollectorsCount);
			writer.writeInt(this.taxCollectorAttack);
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
			this.maxPods = reader.readVarShort();
			this.prospecting = reader.readVarShort();
			this.wisdom = reader.readVarShort();
			this.taxCollectorsCount = reader.readByte();
			this.taxCollectorAttack = reader.readInt();
			this.kamas = reader.readVarLong();
			this.experience = reader.readVarLong();
			this.pods = reader.readVarInt();
			this.itemsValue = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
