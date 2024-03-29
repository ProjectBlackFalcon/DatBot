package protocol.network.messages.game.context;

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
public class GameEntityDispositionErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 5695;

	@Override
	public void Serialize(DofusDataWriter writer) {
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
	}
}
