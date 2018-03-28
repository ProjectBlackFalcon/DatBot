package protocol.network.messages.game.context.roleplay.treasureHunt;

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
public class PortalUseRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6492;

	private int portalId;

	public int getPortalId() { return this.portalId; }
	public void setPortalId(int portalId) { this.portalId = portalId; };

	public PortalUseRequestMessage(){
	}

	public PortalUseRequestMessage(int portalId){
		this.portalId = portalId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.portalId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.portalId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
