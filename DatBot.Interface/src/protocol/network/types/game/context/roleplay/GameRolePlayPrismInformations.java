package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.prism.PrismInformation;

@SuppressWarnings("unused")
public class GameRolePlayPrismInformations extends GameRolePlayActorInformations {
	public static final int ProtocolId = 161;

	public PrismInformation prism;

	public GameRolePlayPrismInformations(){
	}

	public GameRolePlayPrismInformations(PrismInformation prism){
		this.prism = prism;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(PrismInformation.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.prism = (PrismInformation) ProtocolTypeManager.getInstance(reader.readShort());
			this.prism.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("prism : " + this.prism);
	//}
}
