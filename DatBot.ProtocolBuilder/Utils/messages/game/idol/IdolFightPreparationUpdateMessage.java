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
import protocol.network.types.game.idol.Idol;

@SuppressWarnings("unused")
public class IdolFightPreparationUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6586;

	private int idolSource;
	private List<Idol> idols;

	public int getIdolSource() { return this.idolSource; }
	public void setIdolSource(int idolSource) { this.idolSource = idolSource; };
	public List<Idol> getIdols() { return this.idols; }
	public void setIdols(List<Idol> idols) { this.idols = idols; };

	public IdolFightPreparationUpdateMessage(){
	}

	public IdolFightPreparationUpdateMessage(int idolSource, List<Idol> idols){
		this.idolSource = idolSource;
		this.idols = idols;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.idolSource);
			writer.writeShort(this.idols.size());
			int _loc2_ = 0;
			while( _loc2_ < this.idols.size()){
				writer.writeShort(Idol.ProtocolId);
				this.idols.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.idolSource = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.idols = new ArrayList<Idol>();
			while( _loc3_ <  _loc2_){
				Idol _loc15_ = (Idol) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.idols.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
