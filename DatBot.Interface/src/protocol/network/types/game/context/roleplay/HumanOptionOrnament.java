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
public class HumanOptionOrnament extends HumanOption {
	public static final int ProtocolId = 411;

	private int ornamentId;
	private int level;
	private int leagueId;
	private int ladderPosition;

	public int getOrnamentId() { return this.ornamentId; }
	public void setOrnamentId(int ornamentId) { this.ornamentId = ornamentId; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public int getLeagueId() { return this.leagueId; }
	public void setLeagueId(int leagueId) { this.leagueId = leagueId; };
	public int getLadderPosition() { return this.ladderPosition; }
	public void setLadderPosition(int ladderPosition) { this.ladderPosition = ladderPosition; };

	public HumanOptionOrnament(){
	}

	public HumanOptionOrnament(int ornamentId, int level, int leagueId, int ladderPosition){
		this.ornamentId = ornamentId;
		this.level = level;
		this.leagueId = leagueId;
		this.ladderPosition = ladderPosition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.ornamentId);
			writer.writeVarShort(this.level);
			writer.writeVarShort(this.leagueId);
			writer.writeInt(this.ladderPosition);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.ornamentId = reader.readVarShort();
			this.level = reader.readVarShort();
			this.leagueId = reader.readVarShort();
			this.ladderPosition = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
