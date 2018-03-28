package protocol.network.messages.web.ankabox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.web.ankabox.MailStatusMessage;

@SuppressWarnings("unused")
public class NewMailMessage extends MailStatusMessage {
	public static final int ProtocolId = 6292;

	private List<Integer> sendersAccountId;

	public List<Integer> getSendersAccountId() { return this.sendersAccountId; }
	public void setSendersAccountId(List<Integer> sendersAccountId) { this.sendersAccountId = sendersAccountId; };

	public NewMailMessage(){
	}

	public NewMailMessage(List<Integer> sendersAccountId){
		this.sendersAccountId = sendersAccountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.sendersAccountId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.sendersAccountId.size()){
				writer.writeInt(this.sendersAccountId.get(_loc2_));
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
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.sendersAccountId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.sendersAccountId.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
