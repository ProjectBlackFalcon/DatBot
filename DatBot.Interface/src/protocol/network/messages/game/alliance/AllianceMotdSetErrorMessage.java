package protocol.network.messages.game.alliance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.social.SocialNoticeSetErrorMessage;

@SuppressWarnings("unused")
public class AllianceMotdSetErrorMessage extends SocialNoticeSetErrorMessage {
	public static final int ProtocolId = 6683;

	@Override
	public void Serialize(DofusDataWriter writer) {
		super.Serialize(writer);
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		super.Deserialize(reader);
	}
}
