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

@SuppressWarnings("unused")
public class ShortcutBarSwapRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6230;

	private int barType;
	private int firstSlot;
	private int secondSlot;

	public int getBarType() { return this.barType; };
	public void setBarType(int barType) { this.barType = barType; };
	public int getFirstSlot() { return this.firstSlot; };
	public void setFirstSlot(int firstSlot) { this.firstSlot = firstSlot; };
	public int getSecondSlot() { return this.secondSlot; };
	public void setSecondSlot(int secondSlot) { this.secondSlot = secondSlot; };

	public ShortcutBarSwapRequestMessage(){
	}

	public ShortcutBarSwapRequestMessage(int barType, int firstSlot, int secondSlot){
		this.barType = barType;
		this.firstSlot = firstSlot;
		this.secondSlot = secondSlot;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.barType);
			writer.writeByte(this.firstSlot);
			writer.writeByte(this.secondSlot);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.barType = reader.readByte();
			this.firstSlot = reader.readByte();
			this.secondSlot = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
