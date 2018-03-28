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
public class MountXpRatioMessage extends NetworkMessage {
	public static final int ProtocolId = 5970;

	private int ratio;

	public int getRatio() { return this.ratio; }
	public void setRatio(int ratio) { this.ratio = ratio; };

	public MountXpRatioMessage(){
	}

	public MountXpRatioMessage(int ratio){
		this.ratio = ratio;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.ratio);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.ratio = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
