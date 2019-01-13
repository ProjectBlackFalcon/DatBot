package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMemberInFightMessage;
import protocol.network.types.game.context.MapCoordinatesExtended;

@SuppressWarnings("unused")
public class PartyMemberInStandardFightMessage extends AbstractPartyMemberInFightMessage {
	public static final int ProtocolId = 6826;

	private MapCoordinatesExtended fightMap;

	public MapCoordinatesExtended getFightMap() { return this.fightMap; }
	public void setFightMap(MapCoordinatesExtended fightMap) { this.fightMap = fightMap; };

	public PartyMemberInStandardFightMessage(){
	}

	public PartyMemberInStandardFightMessage(MapCoordinatesExtended fightMap){
		this.fightMap = fightMap;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			fightMap.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.fightMap = new MapCoordinatesExtended();
			this.fightMap.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
