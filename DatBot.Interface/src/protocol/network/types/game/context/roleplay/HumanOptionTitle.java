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
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class HumanOptionTitle extends HumanOption {
	public static final int ProtocolId = 408;

	public int titleId;
	public String titleParam;

	public HumanOptionTitle(){
	}

	public HumanOptionTitle(int titleId, String titleParam){
		this.titleId = titleId;
		this.titleParam = titleParam;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.titleId);
			writer.writeUTF(this.titleParam);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.titleId = reader.readVarShort();
			this.titleParam = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("titleId : " + this.titleId);
		//Network.appendDebug("titleParam : " + this.titleParam);
	//}
}