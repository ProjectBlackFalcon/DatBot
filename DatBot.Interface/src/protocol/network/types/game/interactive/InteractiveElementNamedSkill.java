package protocol.network.types.game.interactive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.interactive.InteractiveElementSkill;

@SuppressWarnings("unused")
public class InteractiveElementNamedSkill extends InteractiveElementSkill {
	public static final int ProtocolId = 220;

	public int nameId;

	public InteractiveElementNamedSkill(){
	}

	public InteractiveElementNamedSkill(int nameId){
		this.nameId = nameId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.nameId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.nameId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("nameId : " + this.nameId);
	//}
}
