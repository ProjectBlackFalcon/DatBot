package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.GameFightFighterLightInformations;

@SuppressWarnings("unused")
public class GameFightFighterCompanionLightInformations extends GameFightFighterLightInformations {
	public static final int ProtocolId = 454;

	public int companionId;
	public double masterId;

	public GameFightFighterCompanionLightInformations(){
	}

	public GameFightFighterCompanionLightInformations(int companionId, double masterId){
		this.companionId = companionId;
		this.masterId = masterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.companionId);
			writer.writeDouble(this.masterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.companionId = reader.readByte();
			this.masterId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("companionId : " + this.companionId);
		//Network.appendDebug("masterId : " + this.masterId);
	//}
}
