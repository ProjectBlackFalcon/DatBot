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

	public int maxPods;
	public int prospecting;
	public int wisdom;
	public int taxCollectorsCount;
	public int taxCollectorAttack;
	public long kamas;
	public long experience;
	public int pods;
	public long itemsValue;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("maxPods : " + this.maxPods);
		//Network.appendDebug("prospecting : " + this.prospecting);
		//Network.appendDebug("wisdom : " + this.wisdom);
		//Network.appendDebug("taxCollectorsCount : " + this.taxCollectorsCount);
		//Network.appendDebug("taxCollectorAttack : " + this.taxCollectorAttack);
		//Network.appendDebug("kamas : " + this.kamas);
		//Network.appendDebug("experience : " + this.experience);
		//Network.appendDebug("pods : " + this.pods);
		//Network.appendDebug("itemsValue : " + this.itemsValue);
	//}
}
