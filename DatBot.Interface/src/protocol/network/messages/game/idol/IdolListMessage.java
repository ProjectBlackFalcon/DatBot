package protocol.network.messages.game.idol;

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
import protocol.network.types.game.idol.PartyIdol;

@SuppressWarnings("unused")
public class IdolListMessage extends NetworkMessage {
	public static final int ProtocolId = 6585;

	public List<Integer> chosenIdols;
	public List<Integer> partyChosenIdols;
	public List<PartyIdol> partyIdols;

	public IdolListMessage(){
	}

	public IdolListMessage(List<Integer> chosenIdols, List<Integer> partyChosenIdols, List<PartyIdol> partyIdols){
		this.chosenIdols = chosenIdols;
		this.partyChosenIdols = partyChosenIdols;
		this.partyIdols = partyIdols;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.chosenIdols.size());
			int _loc2_ = 0;
			while( _loc2_ < this.chosenIdols.size()){
				writer.writeVarShort(this.chosenIdols.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.partyChosenIdols.size());
			int _loc3_ = 0;
			while( _loc3_ < this.partyChosenIdols.size()){
				writer.writeVarShort(this.partyChosenIdols.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.partyIdols.size());
			int _loc4_ = 0;
			while( _loc4_ < this.partyIdols.size()){
				writer.writeShort(PartyIdol.ProtocolId);
				this.partyIdols.get(_loc4_).Serialize(writer);
				_loc4_++;
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
			this.chosenIdols = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.chosenIdols.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.partyChosenIdols = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.partyChosenIdols.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.partyIdols = new ArrayList<PartyIdol>();
			while( _loc7_ <  _loc6_){
				PartyIdol _loc17_ = (PartyIdol) ProtocolTypeManager.getInstance(reader.readShort());
				_loc17_.Deserialize(reader);
				this.partyIdols.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : chosenIdols) {
			//Network.appendDebug("chosenIdols : " + a);
		//}
		//for(Integer a : partyChosenIdols) {
			//Network.appendDebug("partyChosenIdols : " + a);
		//}
		//for(PartyIdol a : partyIdols) {
			//Network.appendDebug("partyIdols : " + a);
		//}
	//}
}
