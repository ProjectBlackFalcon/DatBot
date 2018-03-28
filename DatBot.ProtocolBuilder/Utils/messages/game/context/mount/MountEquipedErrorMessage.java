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
public class MountEquipedErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 5963;

	private int errorType;

	public int getErrorType() { return this.errorType; }
	public void setErrorType(int errorType) { this.errorType = errorType; };

	public MountEquipedErrorMessage(){
	}

	public MountEquipedErrorMessage(int errorType){
		this.errorType = errorType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.errorType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.errorType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
