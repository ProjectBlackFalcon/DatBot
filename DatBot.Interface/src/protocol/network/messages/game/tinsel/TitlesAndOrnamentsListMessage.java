package protocol.network.messages.game.tinsel;

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
public class TitlesAndOrnamentsListMessage extends NetworkMessage {
	public static final int ProtocolId = 6367;

	private List<Integer> titles;
	private List<Integer> ornaments;
	private int activeTitle;
	private int activeOrnament;

	public List<Integer> getTitles() { return this.titles; }
	public void setTitles(List<Integer> titles) { this.titles = titles; };
	public List<Integer> getOrnaments() { return this.ornaments; }
	public void setOrnaments(List<Integer> ornaments) { this.ornaments = ornaments; };
	public int getActiveTitle() { return this.activeTitle; }
	public void setActiveTitle(int activeTitle) { this.activeTitle = activeTitle; };
	public int getActiveOrnament() { return this.activeOrnament; }
	public void setActiveOrnament(int activeOrnament) { this.activeOrnament = activeOrnament; };

	public TitlesAndOrnamentsListMessage(){
	}

	public TitlesAndOrnamentsListMessage(List<Integer> titles, List<Integer> ornaments, int activeTitle, int activeOrnament){
		this.titles = titles;
		this.ornaments = ornaments;
		this.activeTitle = activeTitle;
		this.activeOrnament = activeOrnament;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.titles.size());
			int _loc2_ = 0;
			while( _loc2_ < this.titles.size()){
				writer.writeVarShort(this.titles.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.ornaments.size());
			int _loc3_ = 0;
			while( _loc3_ < this.ornaments.size()){
				writer.writeVarShort(this.ornaments.get(_loc3_));
				_loc3_++;
			}
			writer.writeVarShort(this.activeTitle);
			writer.writeVarShort(this.activeOrnament);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.titles = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.titles.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.ornaments = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.ornaments.add(_loc16_);
				_loc5_++;
			}
			this.activeTitle = reader.readVarShort();
			this.activeOrnament = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
