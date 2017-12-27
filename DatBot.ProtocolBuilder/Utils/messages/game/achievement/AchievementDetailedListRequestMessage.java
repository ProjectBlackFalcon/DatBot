package protocol.network.messages.game.achievement;

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

@SuppressWarnings("unused")
public class AchievementDetailedListRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6357;

	private int categoryId;

	public int getCategoryId() { return this.categoryId; };
	public void setCategoryId(int categoryId) { this.categoryId = categoryId; };

	public AchievementDetailedListRequestMessage(){
	}

	public AchievementDetailedListRequestMessage(int categoryId){
		this.categoryId = categoryId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.categoryId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.categoryId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
