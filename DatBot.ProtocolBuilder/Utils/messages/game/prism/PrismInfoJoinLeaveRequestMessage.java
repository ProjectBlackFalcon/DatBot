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
public class PrismInfoJoinLeaveRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5844;

	private boolean join;

	public boolean isJoin() { return this.join; }
	public void setJoin(boolean join) { this.join = join; };

	public PrismInfoJoinLeaveRequestMessage(){
	}

	public PrismInfoJoinLeaveRequestMessage(boolean join){
		this.join = join;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.join);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.join = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
