package protocol.network.messages.game.context.mount;

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
public class MountSetXpRatioRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5989;

	private int xpRatio;

	public int getXpRatio() { return this.xpRatio; }
	public void setXpRatio(int xpRatio) { this.xpRatio = xpRatio; };

	public MountSetXpRatioRequestMessage(){
	}

	public MountSetXpRatioRequestMessage(int xpRatio){
		this.xpRatio = xpRatio;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.xpRatio);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.xpRatio = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
