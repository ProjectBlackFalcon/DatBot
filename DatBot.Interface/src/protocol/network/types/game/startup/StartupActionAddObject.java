package protocol.network.types.game.startup;

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
import protocol.network.types.game.data.items.ObjectItemInformationWithQuantity;

@SuppressWarnings("unused")
public class StartupActionAddObject extends NetworkMessage {
	public static final int ProtocolId = 52;

	public int uid;
	public String title;
	public String text;
	public String descUrl;
	public String pictureUrl;
	public List<ObjectItemInformationWithQuantity> items;

	public StartupActionAddObject(){
	}

	public StartupActionAddObject(int uid, String title, String text, String descUrl, String pictureUrl, List<ObjectItemInformationWithQuantity> items){
		this.uid = uid;
		this.title = title;
		this.text = text;
		this.descUrl = descUrl;
		this.pictureUrl = pictureUrl;
		this.items = items;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.uid);
			writer.writeUTF(this.title);
			writer.writeUTF(this.text);
			writer.writeUTF(this.descUrl);
			writer.writeUTF(this.pictureUrl);
			writer.writeShort(this.items.size());
			int _loc2_ = 0;
			while( _loc2_ < this.items.size()){
				this.items.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.uid = reader.readInt();
			this.title = reader.readUTF();
			this.text = reader.readUTF();
			this.descUrl = reader.readUTF();
			this.pictureUrl = reader.readUTF();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.items = new ArrayList<ObjectItemInformationWithQuantity>();
			while( _loc3_ <  _loc2_){
				ObjectItemInformationWithQuantity _loc15_ = new ObjectItemInformationWithQuantity();
				_loc15_.Deserialize(reader);
				this.items.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("uid : " + this.uid);
		//Network.appendDebug("title : " + this.title);
		//Network.appendDebug("text : " + this.text);
		//Network.appendDebug("descUrl : " + this.descUrl);
		//Network.appendDebug("pictureUrl : " + this.pictureUrl);
		//for(ObjectItemInformationWithQuantity a : items) {
			//Network.appendDebug("items : " + a);
		//}
	//}
}
