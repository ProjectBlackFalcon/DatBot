package protocol.network.types.game.actions.fight;

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
import protocol.network.types.game.actions.fight.GameActionMarkedCell;

@SuppressWarnings("unused")
public class GameActionMark extends NetworkMessage {
	public static final int ProtocolId = 351;

	private double markAuthorId;
	private int markTeamId;
	private int markSpellId;
	private int markSpellLevel;
	private int markId;
	private int markType;
	private int markimpactCell;
	private List<GameActionMarkedCell> cells;
	private boolean active;

	public double getMarkAuthorId() { return this.markAuthorId; }
	public void setMarkAuthorId(double markAuthorId) { this.markAuthorId = markAuthorId; };
	public int getMarkTeamId() { return this.markTeamId; }
	public void setMarkTeamId(int markTeamId) { this.markTeamId = markTeamId; };
	public int getMarkSpellId() { return this.markSpellId; }
	public void setMarkSpellId(int markSpellId) { this.markSpellId = markSpellId; };
	public int getMarkSpellLevel() { return this.markSpellLevel; }
	public void setMarkSpellLevel(int markSpellLevel) { this.markSpellLevel = markSpellLevel; };
	public int getMarkId() { return this.markId; }
	public void setMarkId(int markId) { this.markId = markId; };
	public int getMarkType() { return this.markType; }
	public void setMarkType(int markType) { this.markType = markType; };
	public int getMarkimpactCell() { return this.markimpactCell; }
	public void setMarkimpactCell(int markimpactCell) { this.markimpactCell = markimpactCell; };
	public List<GameActionMarkedCell> getCells() { return this.cells; }
	public void setCells(List<GameActionMarkedCell> cells) { this.cells = cells; };
	public boolean isActive() { return this.active; }
	public void setActive(boolean active) { this.active = active; };

	public GameActionMark(){
	}

	public GameActionMark(double markAuthorId, int markTeamId, int markSpellId, int markSpellLevel, int markId, int markType, int markimpactCell, List<GameActionMarkedCell> cells, boolean active){
		this.markAuthorId = markAuthorId;
		this.markTeamId = markTeamId;
		this.markSpellId = markSpellId;
		this.markSpellLevel = markSpellLevel;
		this.markId = markId;
		this.markType = markType;
		this.markimpactCell = markimpactCell;
		this.cells = cells;
		this.active = active;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.markAuthorId);
			writer.writeByte(this.markTeamId);
			writer.writeInt(this.markSpellId);
			writer.writeShort(this.markSpellLevel);
			writer.writeShort(this.markId);
			writer.writeByte(this.markType);
			writer.writeShort(this.markimpactCell);
			writer.writeShort(this.cells.size());
			int _loc2_ = 0;
			while( _loc2_ < this.cells.size()){
				this.cells.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeBoolean(this.active);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.markAuthorId = reader.readDouble();
			this.markTeamId = reader.readByte();
			this.markSpellId = reader.readInt();
			this.markSpellLevel = reader.readShort();
			this.markId = reader.readShort();
			this.markType = reader.readByte();
			this.markimpactCell = reader.readShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.cells = new ArrayList<GameActionMarkedCell>();
			while( _loc3_ <  _loc2_){
				GameActionMarkedCell _loc15_ = new GameActionMarkedCell();
				_loc15_.Deserialize(reader);
				this.cells.add(_loc15_);
				_loc3_++;
			}
			this.active = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
