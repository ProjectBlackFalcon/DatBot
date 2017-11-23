package protocol.network.messages.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.types.game.house.HouseInformationsInside;

@SuppressWarnings("unused")
public class MapComplementaryInformationsDataInHouseMessage extends MapComplementaryInformationsDataMessage {
	public static final int ProtocolId = 6130;

	public HouseInformationsInside currentHouse;

	public MapComplementaryInformationsDataInHouseMessage(){
	}

	public MapComplementaryInformationsDataInHouseMessage(HouseInformationsInside currentHouse){
		this.currentHouse = currentHouse;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			currentHouse.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.currentHouse = new HouseInformationsInside();
			this.currentHouse.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("currentHouse : " + this.currentHouse);
	//}
}
