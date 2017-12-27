package protocol.network.messages.game.idol;

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
public class IdolPartyRegisterRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6582;

	private boolean register;

	public boolean isRegister() { return this.register; };
	public void setRegister(boolean register) { this.register = register; };

	public IdolPartyRegisterRequestMessage(){
	}

	public IdolPartyRegisterRequestMessage(boolean register){
		this.register = register;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.register);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.register = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
