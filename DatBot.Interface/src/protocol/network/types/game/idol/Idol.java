package protocol.network.types.game.idol;

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

@SuppressWarnings("unused")
public class Idol extends NetworkMessage {
	public static final int ProtocolId = 489;

	public int id;
	public int xpBonusPercent;
	public int dropBonusPercent;

	public Idol(){
	}

	public Idol(int id, int xpBonusPercent, int dropBonusPercent){
		this.id = id;
		this.xpBonusPercent = xpBonusPercent;
		this.dropBonusPercent = dropBonusPercent;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeVarShort(this.xpBonusPercent);
			writer.writeVarShort(this.dropBonusPercent);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			this.xpBonusPercent = reader.readVarShort();
			this.dropBonusPercent = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("xpBonusPercent : " + this.xpBonusPercent);
		//Network.appendDebug("dropBonusPercent : " + this.dropBonusPercent);
	//}
}
