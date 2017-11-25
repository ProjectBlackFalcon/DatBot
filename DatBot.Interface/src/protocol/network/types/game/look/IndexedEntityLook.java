package protocol.network.types.game.look;

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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class IndexedEntityLook extends NetworkMessage {
	public static final int ProtocolId = 405;

	public EntityLook look;
	public int index;

	public IndexedEntityLook(){
	}

	public IndexedEntityLook(EntityLook look, int index){
		this.look = look;
		this.index = index;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			look.Serialize(writer);
			writer.writeByte(this.index);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.look = new EntityLook();
			this.look.Deserialize(reader);
			this.index = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("look : " + this.look);
		//Network.appendDebug("index : " + this.index);
	//}
}