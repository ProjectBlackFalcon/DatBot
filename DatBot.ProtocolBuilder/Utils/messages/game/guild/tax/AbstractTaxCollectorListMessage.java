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
import protocol.network.NetworkMessage;
import protocol.network.types.game.guild.tax.TaxCollectorInformations;

@SuppressWarnings("unused")
public class AbstractTaxCollectorListMessage extends NetworkMessage {
	public static final int ProtocolId = 6568;

	private List<TaxCollectorInformations> informations;

	public List<TaxCollectorInformations> getInformations() { return this.informations; }
	public void setInformations(List<TaxCollectorInformations> informations) { this.informations = informations; };

	public AbstractTaxCollectorListMessage(){
	}

	public AbstractTaxCollectorListMessage(List<TaxCollectorInformations> informations){
		this.informations = informations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.informations.size());
			int _loc2_ = 0;
			while( _loc2_ < this.informations.size()){
				writer.writeShort(TaxCollectorInformations.ProtocolId);
				this.informations.get(_loc2_).Serialize(writer);
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
			this.informations = new ArrayList<TaxCollectorInformations>();
			while( _loc3_ <  _loc2_){
				TaxCollectorInformations _loc15_ = (TaxCollectorInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.informations.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
