package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareInformations;
import protocol.network.types.game.dare.DareVersatileInformations;

@SuppressWarnings("unused")
public class DareSubscribedListMessage extends NetworkMessage {
	public static final int ProtocolId = 6658;

	private List<DareInformations> daresFixedInfos;
	private List<DareVersatileInformations> daresVersatilesInfos;

	public List<DareInformations> getDaresFixedInfos() { return this.daresFixedInfos; }
	public void setDaresFixedInfos(List<DareInformations> daresFixedInfos) { this.daresFixedInfos = daresFixedInfos; };
	public List<DareVersatileInformations> getDaresVersatilesInfos() { return this.daresVersatilesInfos; }
	public void setDaresVersatilesInfos(List<DareVersatileInformations> daresVersatilesInfos) { this.daresVersatilesInfos = daresVersatilesInfos; };

	public DareSubscribedListMessage(){
	}

	public DareSubscribedListMessage(List<DareInformations> daresFixedInfos, List<DareVersatileInformations> daresVersatilesInfos){
		this.daresFixedInfos = daresFixedInfos;
		this.daresVersatilesInfos = daresVersatilesInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.daresFixedInfos.size());
			int _loc2_ = 0;
			while( _loc2_ < this.daresFixedInfos.size()){
				this.daresFixedInfos.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.daresVersatilesInfos.size());
			int _loc3_ = 0;
			while( _loc3_ < this.daresVersatilesInfos.size()){
				this.daresVersatilesInfos.get(_loc3_).Serialize(writer);
				_loc3_++;
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
			this.daresFixedInfos = new ArrayList<DareInformations>();
			while( _loc3_ <  _loc2_){
				DareInformations _loc15_ = new DareInformations();
				_loc15_.Deserialize(reader);
				this.daresFixedInfos.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.daresVersatilesInfos = new ArrayList<DareVersatileInformations>();
			while( _loc5_ <  _loc4_){
				DareVersatileInformations _loc16_ = new DareVersatileInformations();
				_loc16_.Deserialize(reader);
				this.daresVersatilesInfos.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
