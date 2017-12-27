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
import protocol.network.types.game.context.roleplay.GameRolePlayNamedActorInformations;
import protocol.network.types.game.context.roleplay.HumanInformations;

@SuppressWarnings("unused")
public class GameRolePlayHumanoidInformations extends GameRolePlayNamedActorInformations {
	public static final int ProtocolId = 159;

	private HumanInformations humanoidInfo;
	private int accountId;

	public HumanInformations getHumanoidInfo() { return this.humanoidInfo; };
	public void setHumanoidInfo(HumanInformations humanoidInfo) { this.humanoidInfo = humanoidInfo; };
	public int getAccountId() { return this.accountId; };
	public void setAccountId(int accountId) { this.accountId = accountId; };

	public GameRolePlayHumanoidInformations(){
	}

	public GameRolePlayHumanoidInformations(HumanInformations humanoidInfo, int accountId){
		this.humanoidInfo = humanoidInfo;
		this.accountId = accountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(HumanInformations.ProtocolId);
			writer.writeInt(this.accountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.humanoidInfo = (HumanInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.humanoidInfo.Deserialize(reader);
			this.accountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
