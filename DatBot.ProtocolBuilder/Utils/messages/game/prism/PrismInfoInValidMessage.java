package protocol.network.messages.game.prism;

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
public class PrismInfoInValidMessage extends NetworkMessage {
	public static final int ProtocolId = 5859;

	private int reason;

	public int getReason() { return this.reason; }
	public void setReason(int reason) { this.reason = reason; };

	public PrismInfoInValidMessage(){
	}

	public PrismInfoInValidMessage(int reason){
		this.reason = reason;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.reason);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.reason = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
