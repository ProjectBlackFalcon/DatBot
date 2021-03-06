package protocol.network.types.game.paddock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.paddock.PaddockInformations;
import protocol.network.types.game.paddock.MountInformationsForPaddock;

@SuppressWarnings("unused")
public class PaddockContentInformations extends PaddockInformations {
	public static final int ProtocolId = 183;

	private double paddockId;
	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;
	private boolean abandonned;
	private List<MountInformationsForPaddock> mountsInformations;

	public double getPaddockId() { return this.paddockId; }
	public void setPaddockId(double paddockId) { this.paddockId = paddockId; };
	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public boolean isAbandonned() { return this.abandonned; }
	public void setAbandonned(boolean abandonned) { this.abandonned = abandonned; };
	public List<MountInformationsForPaddock> getMountsInformations() { return this.mountsInformations; }
	public void setMountsInformations(List<MountInformationsForPaddock> mountsInformations) { this.mountsInformations = mountsInformations; };

	public PaddockContentInformations(){
	}

	public PaddockContentInformations(double paddockId, int worldX, int worldY, double mapId, int subAreaId, boolean abandonned, List<MountInformationsForPaddock> mountsInformations){
		this.paddockId = paddockId;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.abandonned = abandonned;
		this.mountsInformations = mountsInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.paddockId);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
			writer.writeBoolean(this.abandonned);
			writer.writeShort(this.mountsInformations.size());
			int _loc2_ = 0;
			while( _loc2_ < this.mountsInformations.size()){
				this.mountsInformations.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.paddockId = reader.readDouble();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
			this.abandonned = reader.readBoolean();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.mountsInformations = new ArrayList<MountInformationsForPaddock>();
			while( _loc3_ <  _loc2_){
				MountInformationsForPaddock _loc15_ = new MountInformationsForPaddock();
				_loc15_.Deserialize(reader);
				this.mountsInformations.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
