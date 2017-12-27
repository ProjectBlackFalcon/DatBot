package protocol.network.messages.game.context;

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
public class GameMapNoMovementMessage extends NetworkMessage {
	public static final int ProtocolId = 954;

	private int cellX;
	private int cellY;

	public int getCellX() { return this.cellX; };
	public void setCellX(int cellX) { this.cellX = cellX; };
	public int getCellY() { return this.cellY; };
	public void setCellY(int cellY) { this.cellY = cellY; };

	public GameMapNoMovementMessage(){
	}

	public GameMapNoMovementMessage(int cellX, int cellY){
		this.cellX = cellX;
		this.cellY = cellY;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.cellX);
			writer.writeShort(this.cellY);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cellX = reader.readShort();
			this.cellY = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
