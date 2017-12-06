package protocol.network.types.game.prism;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.prism.PrismInformation;
import protocol.network.types.game.data.items.ObjectItem;

@SuppressWarnings("unused")
public class AllianceInsiderPrismInformation extends PrismInformation {
	public static final int ProtocolId = 431;

	public int lastTimeSlotModificationDate;
	public int lastTimeSlotModificationAuthorGuildId;
	public long lastTimeSlotModificationAuthorId;
	public String lastTimeSlotModificationAuthorName;
	public List<ObjectItem> modulesObjects;

	public AllianceInsiderPrismInformation(){
	}

	public AllianceInsiderPrismInformation(int lastTimeSlotModificationDate, int lastTimeSlotModificationAuthorGuildId, long lastTimeSlotModificationAuthorId, String lastTimeSlotModificationAuthorName, List<ObjectItem> modulesObjects){
		this.lastTimeSlotModificationDate = lastTimeSlotModificationDate;
		this.lastTimeSlotModificationAuthorGuildId = lastTimeSlotModificationAuthorGuildId;
		this.lastTimeSlotModificationAuthorId = lastTimeSlotModificationAuthorId;
		this.lastTimeSlotModificationAuthorName = lastTimeSlotModificationAuthorName;
		this.modulesObjects = modulesObjects;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.lastTimeSlotModificationDate);
			writer.writeVarInt(this.lastTimeSlotModificationAuthorGuildId);
			writer.writeVarLong(this.lastTimeSlotModificationAuthorId);
			writer.writeUTF(this.lastTimeSlotModificationAuthorName);
			writer.writeShort(this.modulesObjects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.modulesObjects.size()){
				this.modulesObjects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.lastTimeSlotModificationDate = reader.readInt();
			this.lastTimeSlotModificationAuthorGuildId = reader.readVarInt();
			this.lastTimeSlotModificationAuthorId = reader.readVarLong();
			this.lastTimeSlotModificationAuthorName = reader.readUTF();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.modulesObjects = new ArrayList<ObjectItem>();
			while( _loc3_ <  _loc2_){
				ObjectItem _loc15_ = new ObjectItem();
				_loc15_.Deserialize(reader);
				this.modulesObjects.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("lastTimeSlotModificationDate : " + this.lastTimeSlotModificationDate);
		//Network.appendDebug("lastTimeSlotModificationAuthorGuildId : " + this.lastTimeSlotModificationAuthorGuildId);
		//Network.appendDebug("lastTimeSlotModificationAuthorId : " + this.lastTimeSlotModificationAuthorId);
		//Network.appendDebug("lastTimeSlotModificationAuthorName : " + this.lastTimeSlotModificationAuthorName);
		//for(ObjectItem a : modulesObjects) {
			//Network.appendDebug("modulesObjects : " + a);
		//}
	//}
}
