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
import protocol.network.messages.game.context.GameMapMovementMessage;

@SuppressWarnings("unused")
public class GameCautiousMapMovementMessage extends GameMapMovementMessage {
	public static final int ProtocolId = 6497;

	@Override
	public void Serialize(DofusDataWriter writer) {
		super.Serialize(writer);
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		super.Deserialize(reader);
	}
}
