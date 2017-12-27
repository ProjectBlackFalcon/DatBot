package protocol.network.messages.game.context.fight.arena;

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
import protocol.network.types.game.character.CharacterBasicMinimalInformations;

@SuppressWarnings("unused")
public class ArenaFighterLeaveMessage extends NetworkMessage {
	public static final int ProtocolId = 6700;

	private CharacterBasicMinimalInformations leaver;

	public CharacterBasicMinimalInformations getLeaver() { return this.leaver; };
	public void setLeaver(CharacterBasicMinimalInformations leaver) { this.leaver = leaver; };

	public ArenaFighterLeaveMessage(){
	}

	public ArenaFighterLeaveMessage(CharacterBasicMinimalInformations leaver){
		this.leaver = leaver;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			leaver.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.leaver = new CharacterBasicMinimalInformations();
			this.leaver.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
