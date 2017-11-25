package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareVersatileInformations;

@SuppressWarnings("unused")
public class DareSubscribedMessage extends NetworkMessage {
	public static final int ProtocolId = 6660;

	public double dareId;
	public boolean success;
	public boolean subscribe;
	public DareVersatileInformations dareVersatilesInfos;

	public DareSubscribedMessage(){
	}

	public DareSubscribedMessage(double dareId, boolean success, boolean subscribe, DareVersatileInformations dareVersatilesInfos){
		this.dareId = dareId;
		this.success = success;
		this.subscribe = subscribe;
		this.dareVersatilesInfos = dareVersatilesInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, success);
			flag = BooleanByteWrapper.SetFlag(1, flag, subscribe);
			writer.writeByte(flag);
			writer.writeDouble(this.dareId);
			dareVersatilesInfos.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.success = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.subscribe = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.dareId = reader.readDouble();
			this.dareVersatilesInfos = new DareVersatileInformations();
			this.dareVersatilesInfos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dareId : " + this.dareId);
		//Network.appendDebug("success : " + this.success);
		//Network.appendDebug("subscribe : " + this.subscribe);
		//Network.appendDebug("dareVersatilesInfos : " + this.dareVersatilesInfos);
	//}
}