package protocol.network.messages.game.context.roleplay.havenbag;

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
import protocol.network.types.game.guild.HavenBagFurnitureInformation;

@SuppressWarnings("unused")
public class HavenBagFurnituresMessage extends NetworkMessage {
	public static final int ProtocolId = 6634;

	public List<HavenBagFurnitureInformation> furnituresInfos;

	public HavenBagFurnituresMessage(){
	}

	public HavenBagFurnituresMessage(List<HavenBagFurnitureInformation> furnituresInfos){
		this.furnituresInfos = furnituresInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.furnituresInfos.size());
			int _loc2_ = 0;
			while( _loc2_ < this.furnituresInfos.size()){
				this.furnituresInfos.get(_loc2_).Serialize(writer);
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
			this.furnituresInfos = new ArrayList<HavenBagFurnitureInformation>();
			while( _loc3_ <  _loc2_){
				HavenBagFurnitureInformation _loc15_ = new HavenBagFurnitureInformation();
				_loc15_.Deserialize(reader);
				this.furnituresInfos.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(HavenBagFurnitureInformation a : furnituresInfos) {
			//Network.appendDebug("furnituresInfos : " + a);
		//}
	//}
}
