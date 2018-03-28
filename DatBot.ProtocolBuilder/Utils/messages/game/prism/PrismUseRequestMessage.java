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
public class PrismUseRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6041;

	private int moduleToUse;

	public int getModuleToUse() { return this.moduleToUse; }
	public void setModuleToUse(int moduleToUse) { this.moduleToUse = moduleToUse; };

	public PrismUseRequestMessage(){
	}

	public PrismUseRequestMessage(int moduleToUse){
		this.moduleToUse = moduleToUse;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.moduleToUse);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.moduleToUse = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
