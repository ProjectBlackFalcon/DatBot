package protocol.network.messages.game.context.roleplay.lockable;

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
public class LockableChangeCodeMessage extends NetworkMessage {
	public static final int ProtocolId = 5666;

	private String code;

	public String getCode() { return this.code; }
	public void setCode(String code) { this.code = code; };

	public LockableChangeCodeMessage(){
	}

	public LockableChangeCodeMessage(String code){
		this.code = code;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.code);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.code = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
