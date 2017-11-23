package protocol.network.messages.game.basic;

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
public class BasicNoOperationMessage extends NetworkMessage {
	public static final int ProtocolId = 176;

	@Override
	public void Serialize(DofusDataWriter writer) {
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
	}
}
