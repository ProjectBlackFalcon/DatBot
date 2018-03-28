package protocol.network.types.game.prism;

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
public class PrismSubareaEmptyInfo extends NetworkMessage {
	public static final int ProtocolId = 438;

	private int subAreaId;
	private int allianceId;

	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getAllianceId() { return this.allianceId; }
	public void setAllianceId(int allianceId) { this.allianceId = allianceId; };

	public PrismSubareaEmptyInfo(){
	}

	public PrismSubareaEmptyInfo(int subAreaId, int allianceId){
		this.subAreaId = subAreaId;
		this.allianceId = allianceId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeVarInt(this.allianceId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.allianceId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
