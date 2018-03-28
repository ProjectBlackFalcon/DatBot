package protocol.network.messages.game.context.roleplay;

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
public class ChangeMapMessage extends NetworkMessage {
	public static final int ProtocolId = 221;

	private double mapId;
	private boolean autopilot;

	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public boolean isAutopilot() { return this.autopilot; }
	public void setAutopilot(boolean autopilot) { this.autopilot = autopilot; };

	public ChangeMapMessage(){
	}

	public ChangeMapMessage(double mapId, boolean autopilot){
		this.mapId = mapId;
		this.autopilot = autopilot;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
			writer.writeBoolean(this.autopilot);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
			this.autopilot = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
