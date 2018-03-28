package protocol.network.messages.game.context.roleplay.fight;

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
import protocol.network.types.game.context.fight.FightCommonInformations;

@SuppressWarnings("unused")
public class GameRolePlayShowChallengeMessage extends NetworkMessage {
	public static final int ProtocolId = 301;

	private FightCommonInformations commonsInfos;

	public FightCommonInformations getCommonsInfos() { return this.commonsInfos; }
	public void setCommonsInfos(FightCommonInformations commonsInfos) { this.commonsInfos = commonsInfos; };

	public GameRolePlayShowChallengeMessage(){
	}

	public GameRolePlayShowChallengeMessage(FightCommonInformations commonsInfos){
		this.commonsInfos = commonsInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			commonsInfos.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.commonsInfos = new FightCommonInformations();
			this.commonsInfos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
