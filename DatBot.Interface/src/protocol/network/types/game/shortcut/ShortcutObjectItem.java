package protocol.network.types.game.shortcut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.shortcut.ShortcutObject;

@SuppressWarnings("unused")
public class ShortcutObjectItem extends ShortcutObject {
	public static final int ProtocolId = 371;

	public int itemUID;
	public int itemGID;

	public ShortcutObjectItem(){
	}

	public ShortcutObjectItem(int itemUID, int itemGID){
		this.itemUID = itemUID;
		this.itemGID = itemGID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.itemUID);
			writer.writeInt(this.itemGID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.itemUID = reader.readInt();
			this.itemGID = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("itemUID : " + this.itemUID);
		//Network.appendDebug("itemGID : " + this.itemGID);
	//}
}
