package protocol.network.types.game.context.roleplay.treasureHunt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStep;

@SuppressWarnings("unused")
public class TreasureHuntStepDig extends TreasureHuntStep {
	public static final int ProtocolId = 465;

	@Override
	public void Serialize(DofusDataWriter writer) {
		super.Serialize(writer);
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		super.Deserialize(reader);
	}
}
