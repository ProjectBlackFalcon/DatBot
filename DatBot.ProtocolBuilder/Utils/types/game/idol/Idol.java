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

	private int id;
	private int xpBonusPercent;
	private int dropBonusPercent;

	public int getId() { return this.id; };
	public void setId(int id) { this.id = id; };
	public int getXpBonusPercent() { return this.xpBonusPercent; };
	public void setXpBonusPercent(int xpBonusPercent) { this.xpBonusPercent = xpBonusPercent; };
	public int getDropBonusPercent() { return this.dropBonusPercent; };
	public void setDropBonusPercent(int dropBonusPercent) { this.dropBonusPercent = dropBonusPercent; };

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

}
