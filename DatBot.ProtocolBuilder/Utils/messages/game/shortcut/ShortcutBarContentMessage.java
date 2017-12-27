package protocol.network.messages.game.shortcut;

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
import protocol.network.types.game.shortcut.Shortcut;

@SuppressWarnings("unused")
public class ShortcutBarContentMessage extends NetworkMessage {
	public static final int ProtocolId = 6231;

	private int barType;
	private List<Shortcut> shortcuts;

	public int getBarType() { return this.barType; };
	public void setBarType(int barType) { this.barType = barType; };
	public List<Shortcut> getShortcuts() { return this.shortcuts; };
	public void setShortcuts(List<Shortcut> shortcuts) { this.shortcuts = shortcuts; };

	public ShortcutBarContentMessage(){
	}

	public ShortcutBarContentMessage(int barType, List<Shortcut> shortcuts){
		this.barType = barType;
		this.shortcuts = shortcuts;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.barType);
			writer.writeShort(this.shortcuts.size());
			int _loc2_ = 0;
			while( _loc2_ < this.shortcuts.size()){
				writer.writeShort(Shortcut.ProtocolId);
				this.shortcuts.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.barType = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.shortcuts = new ArrayList<Shortcut>();
			while( _loc3_ <  _loc2_){
				Shortcut _loc15_ = (Shortcut) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.shortcuts.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
