package protocol.network.messages.game.chat.channel;

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
public class ChannelEnablingMessage extends NetworkMessage {
	public static final int ProtocolId = 890;

	public int channel;
	public boolean enable;

	public ChannelEnablingMessage(){
	}

	public ChannelEnablingMessage(int channel, boolean enable){
		this.channel = channel;
		this.enable = enable;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.channel);
			writer.writeBoolean(this.enable);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.channel = reader.readByte();
			this.enable = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("channel : " + this.channel);
		//Network.appendDebug("enable : " + this.enable);
	//}
}
