package protocol.network.messages.game.alliance;

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
import protocol.network.types.game.social.AllianceVersatileInformations;

@SuppressWarnings("unused")
public class AllianceVersatileInfoListMessage extends NetworkMessage {
	public static final int ProtocolId = 6436;

	private List<AllianceVersatileInformations> alliances;

	public List<AllianceVersatileInformations> getAlliances() { return this.alliances; }
	public void setAlliances(List<AllianceVersatileInformations> alliances) { this.alliances = alliances; };

	public AllianceVersatileInfoListMessage(){
	}

	public AllianceVersatileInfoListMessage(List<AllianceVersatileInformations> alliances){
		this.alliances = alliances;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.alliances.size());
			int _loc2_ = 0;
			while( _loc2_ < this.alliances.size()){
				this.alliances.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.alliances = new ArrayList<AllianceVersatileInformations>();
			while( _loc3_ <  _loc2_){
				AllianceVersatileInformations _loc15_ = new AllianceVersatileInformations();
				_loc15_.Deserialize(reader);
				this.alliances.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
