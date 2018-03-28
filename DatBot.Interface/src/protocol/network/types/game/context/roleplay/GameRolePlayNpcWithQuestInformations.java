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
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;
import protocol.network.types.game.context.roleplay.quest.GameRolePlayNpcQuestFlag;

@SuppressWarnings("unused")
public class GameRolePlayNpcWithQuestInformations extends GameRolePlayNpcInformations {
	public static final int ProtocolId = 383;

	private GameRolePlayNpcQuestFlag questFlag;

	public GameRolePlayNpcQuestFlag getQuestFlag() { return this.questFlag; }
	public void setQuestFlag(GameRolePlayNpcQuestFlag questFlag) { this.questFlag = questFlag; };

	public GameRolePlayNpcWithQuestInformations(){
	}

	public GameRolePlayNpcWithQuestInformations(GameRolePlayNpcQuestFlag questFlag){
		this.questFlag = questFlag;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			questFlag.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.questFlag = new GameRolePlayNpcQuestFlag();
			this.questFlag.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
