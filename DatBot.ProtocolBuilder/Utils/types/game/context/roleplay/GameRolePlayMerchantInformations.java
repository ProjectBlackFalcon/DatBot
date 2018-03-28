package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.GameRolePlayNamedActorInformations;
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class GameRolePlayMerchantInformations extends GameRolePlayNamedActorInformations {
	public static final int ProtocolId = 129;

	private int sellType;
	private List<HumanOption> options;

	public int getSellType() { return this.sellType; }
	public void setSellType(int sellType) { this.sellType = sellType; };
	public List<HumanOption> getOptions() { return this.options; }
	public void setOptions(List<HumanOption> options) { this.options = options; };

	public GameRolePlayMerchantInformations(){
	}

	public GameRolePlayMerchantInformations(int sellType, List<HumanOption> options){
		this.sellType = sellType;
		this.options = options;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.sellType);
			writer.writeShort(this.options.size());
			int _loc2_ = 0;
			while( _loc2_ < this.options.size()){
				writer.writeShort(HumanOption.ProtocolId);
				this.options.get(_loc2_).Serialize(writer);
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
			this.sellType = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.options = new ArrayList<HumanOption>();
			while( _loc3_ <  _loc2_){
				HumanOption _loc15_ = (HumanOption) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.options.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
