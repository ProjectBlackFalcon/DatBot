package protocol.network.messages.game.context.roleplay.houses;

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
import protocol.network.types.game.house.AccountHouseInformations;

@SuppressWarnings("unused")
public class AccountHouseMessage extends NetworkMessage {
	public static final int ProtocolId = 6315;

	private List<AccountHouseInformations> houses;

	public List<AccountHouseInformations> getHouses() { return this.houses; };
	public void setHouses(List<AccountHouseInformations> houses) { this.houses = houses; };

	public AccountHouseMessage(){
	}

	public AccountHouseMessage(List<AccountHouseInformations> houses){
		this.houses = houses;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.houses.size());
			int _loc2_ = 0;
			while( _loc2_ < this.houses.size()){
				this.houses.get(_loc2_).Serialize(writer);
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
			this.houses = new ArrayList<AccountHouseInformations>();
			while( _loc3_ <  _loc2_){
				AccountHouseInformations _loc15_ = new AccountHouseInformations();
				_loc15_.Deserialize(reader);
				this.houses.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
