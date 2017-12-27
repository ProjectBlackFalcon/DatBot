package protocol.network.messages.debug;

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
public class DebugHighlightCellsMessage extends NetworkMessage {
	public static final int ProtocolId = 2001;

	private double color;
	private List<Integer> cells;

	public double getColor() { return this.color; };
	public void setColor(double color) { this.color = color; };
	public List<Integer> getCells() { return this.cells; };
	public void setCells(List<Integer> cells) { this.cells = cells; };

	public DebugHighlightCellsMessage(){
	}

	public DebugHighlightCellsMessage(double color, List<Integer> cells){
		this.color = color;
		this.cells = cells;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.color);
			writer.writeShort(this.cells.size());
			int _loc2_ = 0;
			while( _loc2_ < this.cells.size()){
				writer.writeVarShort(this.cells.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.color = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.cells = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.cells.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
