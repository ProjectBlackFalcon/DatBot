package protocol.network.messages.game.guild;

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
import protocol.network.types.game.paddock.PaddockContentInformations;

@SuppressWarnings("unused")
public class GuildInformationsPaddocksMessage extends NetworkMessage {
	public static final int ProtocolId = 5959;

	private int nbPaddockMax;
	private List<PaddockContentInformations> paddocksInformations;

	public int getNbPaddockMax() { return this.nbPaddockMax; };
	public void setNbPaddockMax(int nbPaddockMax) { this.nbPaddockMax = nbPaddockMax; };
	public List<PaddockContentInformations> getPaddocksInformations() { return this.paddocksInformations; };
	public void setPaddocksInformations(List<PaddockContentInformations> paddocksInformations) { this.paddocksInformations = paddocksInformations; };

	public GuildInformationsPaddocksMessage(){
	}

	public GuildInformationsPaddocksMessage(int nbPaddockMax, List<PaddockContentInformations> paddocksInformations){
		this.nbPaddockMax = nbPaddockMax;
		this.paddocksInformations = paddocksInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.nbPaddockMax);
			writer.writeShort(this.paddocksInformations.size());
			int _loc2_ = 0;
			while( _loc2_ < this.paddocksInformations.size()){
				this.paddocksInformations.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.nbPaddockMax = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.paddocksInformations = new ArrayList<PaddockContentInformations>();
			while( _loc3_ <  _loc2_){
				PaddockContentInformations _loc15_ = new PaddockContentInformations();
				_loc15_.Deserialize(reader);
				this.paddocksInformations.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
