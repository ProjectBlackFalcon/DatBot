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

@SuppressWarnings("unused")
public class GameRolePlayMountInformations extends GameRolePlayNamedActorInformations {
	public static final int ProtocolId = 180;

	public String ownerName;
	public int level;

	public GameRolePlayMountInformations(){
	}

	public GameRolePlayMountInformations(String ownerName, int level){
		this.ownerName = ownerName;
		this.level = level;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.ownerName);
			writer.writeByte(this.level);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.ownerName = reader.readUTF();
			this.level = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("ownerName : " + this.ownerName);
		//Network.appendDebug("level : " + this.level);
	//}
}
