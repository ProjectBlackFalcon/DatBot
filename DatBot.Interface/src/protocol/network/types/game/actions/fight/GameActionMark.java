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

	public double markAuthorId;
	public int markTeamId;
	public int markSpellId;
	public int markSpellLevel;
	public int markId;
	public int markType;
	public int markimpactCell;
	public List<GameActionMarkedCell> cells;
	public boolean active;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("markAuthorId : " + this.markAuthorId);
		//Network.appendDebug("markTeamId : " + this.markTeamId);
		//Network.appendDebug("markSpellId : " + this.markSpellId);
		//Network.appendDebug("markSpellLevel : " + this.markSpellLevel);
		//Network.appendDebug("markId : " + this.markId);
		//Network.appendDebug("markType : " + this.markType);
		//Network.appendDebug("markimpactCell : " + this.markimpactCell);
		//for(GameActionMarkedCell a : cells) {
			//Network.appendDebug("cells : " + a);
		//}
		//Network.appendDebug("active : " + this.active);
	//}
}
