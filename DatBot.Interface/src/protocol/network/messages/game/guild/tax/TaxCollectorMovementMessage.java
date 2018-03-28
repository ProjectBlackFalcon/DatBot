package protocol.network.messages.game.guild.tax;

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
import protocol.network.types.game.guild.tax.TaxCollectorBasicInformations;

@SuppressWarnings("unused")
public class TaxCollectorMovementMessage extends NetworkMessage {
	public static final int ProtocolId = 5633;

	private int movementType;
	private TaxCollectorBasicInformations basicInfos;
	private long playerId;
	private String playerName;

	public int getMovementType() { return this.movementType; }
	public void setMovementType(int movementType) { this.movementType = movementType; };
	public TaxCollectorBasicInformations getBasicInfos() { return this.basicInfos; }
	public void setBasicInfos(TaxCollectorBasicInformations basicInfos) { this.basicInfos = basicInfos; };
	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };

	public TaxCollectorMovementMessage(){
	}

	public TaxCollectorMovementMessage(int movementType, TaxCollectorBasicInformations basicInfos, long playerId, String playerName){
		this.movementType = movementType;
		this.basicInfos = basicInfos;
		this.playerId = playerId;
		this.playerName = playerName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.movementType);
			basicInfos.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.movementType = reader.readByte();
			this.basicInfos = new TaxCollectorBasicInformations();
			this.basicInfos.Deserialize(reader);
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
