package protocol.network.messages.game.guild.tax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.guild.tax.AbstractTaxCollectorListMessage;
import protocol.network.types.game.guild.tax.TaxCollectorFightersInformation;

@SuppressWarnings("unused")
public class TaxCollectorListMessage extends AbstractTaxCollectorListMessage {
	public static final int ProtocolId = 5930;

	private int nbcollectorMax;
	private List<TaxCollectorFightersInformation> fightersInformations;

	public int getNbcollectorMax() { return this.nbcollectorMax; }
	public void setNbcollectorMax(int nbcollectorMax) { this.nbcollectorMax = nbcollectorMax; };
	public List<TaxCollectorFightersInformation> getFightersInformations() { return this.fightersInformations; }
	public void setFightersInformations(List<TaxCollectorFightersInformation> fightersInformations) { this.fightersInformations = fightersInformations; };

	public TaxCollectorListMessage(){
	}

	public TaxCollectorListMessage(int nbcollectorMax, List<TaxCollectorFightersInformation> fightersInformations){
		this.nbcollectorMax = nbcollectorMax;
		this.fightersInformations = fightersInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.nbcollectorMax);
			writer.writeShort(this.fightersInformations.size());
			int _loc2_ = 0;
			while( _loc2_ < this.fightersInformations.size()){
				this.fightersInformations.get(_loc2_).Serialize(writer);
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
			this.nbcollectorMax = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.fightersInformations = new ArrayList<TaxCollectorFightersInformation>();
			while( _loc3_ <  _loc2_){
				TaxCollectorFightersInformation _loc15_ = new TaxCollectorFightersInformation();
				_loc15_.Deserialize(reader);
				this.fightersInformations.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
