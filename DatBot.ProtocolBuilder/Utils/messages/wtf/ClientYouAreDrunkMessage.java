package protocol.network.messages.wtf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.debug.DebugInClientMessage;

@SuppressWarnings("unused")
public class ClientYouAreDrunkMessage extends DebugInClientMessage {
	public static final int ProtocolId = 6594;

	@Override
	public void Serialize(DofusDataWriter writer) {
		super.Serialize(writer);
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		super.Deserialize(reader);
	}
}
