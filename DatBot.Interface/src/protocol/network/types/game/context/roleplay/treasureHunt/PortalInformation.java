package protocol.network.types.game.context.roleplay.treasureHunt;

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
public class PortalInformation extends NetworkMessage {
	public static final int ProtocolId = 466;

	public int portalId;
	public int areaId;

	public PortalInformation(){
	}

	public PortalInformation(int portalId, int areaId){
		this.portalId = portalId;
		this.areaId = areaId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.portalId);
			writer.writeShort(this.areaId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.portalId = reader.readInt();
			this.areaId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("portalId : " + this.portalId);
		//Network.appendDebug("areaId : " + this.areaId);
	//}
}
