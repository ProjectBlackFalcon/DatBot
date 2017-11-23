package protocol.network.messages.secure;

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
public class TrustStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6267;

	public boolean trusted;
	public boolean certified;

	public TrustStatusMessage(){
	}

	public TrustStatusMessage(boolean trusted, boolean certified){
		this.trusted = trusted;
		this.certified = certified;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, trusted);
			flag = BooleanByteWrapper.SetFlag(1, flag, certified);
			writer.writeByte(flag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.trusted = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.certified = BooleanByteWrapper.GetFlag(flag, (byte) 1);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("trusted : " + this.trusted);
		//Network.appendDebug("certified : " + this.certified);
	//}
}
