package protocol.network.messages.game.context.fight;

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
public class GameFightStartMessage extends NetworkMessage {
	public static final int ProtocolId = 712;

	private List<Idol> idols;

	public List<Idol> getIdols() { return this.idols; };
	public void setIdols(List<Idol> idols) { this.idols = idols; };

	public GameFightStartMessage(){
	}

	public GameFightStartMessage(List<Idol> idols){
		this.idols = idols;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.idols.size());
			int _loc2_ = 0;
			while( _loc2_ < this.idols.size()){
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
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.idols = new ArrayList<Idol>();
			while( _loc3_ <  _loc2_){
				Idol _loc15_ = new Idol();
				_loc15_.Deserialize(reader);
				this.idols.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
