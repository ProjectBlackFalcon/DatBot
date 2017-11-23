package protocol.network.types.game.inventory.preset;

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
import protocol.network.types.game.inventory.preset.PresetItem;

@SuppressWarnings("unused")
public class Preset extends NetworkMessage {
	public static final int ProtocolId = 355;

	public int presetId;
	public int symbolId;
	public boolean mount;
	public List<PresetItem> objects;

	public Preset(){
	}

	public Preset(int presetId, int symbolId, boolean mount, List<PresetItem> objects){
		this.presetId = presetId;
		this.symbolId = symbolId;
		this.mount = mount;
		this.objects = objects;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.symbolId);
			writer.writeBoolean(this.mount);
			writer.writeShort(this.objects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objects.size()){
				this.objects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.symbolId = reader.readByte();
			this.mount = reader.readBoolean();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objects = new ArrayList<PresetItem>();
			while( _loc3_ <  _loc2_){
				PresetItem _loc15_ = new PresetItem();
				_loc15_.Deserialize(reader);
				this.objects.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("presetId : " + this.presetId);
		//Network.appendDebug("symbolId : " + this.symbolId);
		//Network.appendDebug("mount : " + this.mount);
		//for(PresetItem a : objects) {
			//Network.appendDebug("objects : " + a);
		//}
	//}
}
