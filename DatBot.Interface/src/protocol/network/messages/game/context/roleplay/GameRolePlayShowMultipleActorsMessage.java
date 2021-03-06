package protocol.network.messages.game.context.roleplay;

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
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;

@SuppressWarnings("unused")
public class GameRolePlayShowMultipleActorsMessage extends NetworkMessage {
	public static final int ProtocolId = 6712;

	private List<GameRolePlayActorInformations> informationsList;

	public List<GameRolePlayActorInformations> getInformationsList() { return this.informationsList; }
	public void setInformationsList(List<GameRolePlayActorInformations> informationsList) { this.informationsList = informationsList; };

	public GameRolePlayShowMultipleActorsMessage(){
	}

	public GameRolePlayShowMultipleActorsMessage(List<GameRolePlayActorInformations> informationsList){
		this.informationsList = informationsList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.informationsList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.informationsList.size()){
				writer.writeShort(GameRolePlayActorInformations.ProtocolId);
				this.informationsList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.informationsList = new ArrayList<GameRolePlayActorInformations>();
			while( _loc3_ <  _loc2_){
				GameRolePlayActorInformations _loc15_ = (GameRolePlayActorInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.informationsList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
