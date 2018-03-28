package protocol.network.messages.game.context.roleplay.havenbag;

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
public class EnterHavenBagRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6636;

	private long havenBagOwner;

	public long getHavenBagOwner() { return this.havenBagOwner; }
	public void setHavenBagOwner(long havenBagOwner) { this.havenBagOwner = havenBagOwner; };

	public EnterHavenBagRequestMessage(){
	}

	public EnterHavenBagRequestMessage(long havenBagOwner){
		this.havenBagOwner = havenBagOwner;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.havenBagOwner);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.havenBagOwner = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
