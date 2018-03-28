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
import protocol.network.types.game.context.fight.FightStartingPositions;

@SuppressWarnings("unused")
public class MapFightStartPositionsUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6716;

	private double mapId;
	private FightStartingPositions fightStartPositions;

	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public FightStartingPositions getFightStartPositions() { return this.fightStartPositions; }
	public void setFightStartPositions(FightStartingPositions fightStartPositions) { this.fightStartPositions = fightStartPositions; };

	public MapFightStartPositionsUpdateMessage(){
	}

	public MapFightStartPositionsUpdateMessage(double mapId, FightStartingPositions fightStartPositions){
		this.mapId = mapId;
		this.fightStartPositions = fightStartPositions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
			fightStartPositions.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
			this.fightStartPositions = new FightStartingPositions();
			this.fightStartPositions.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
