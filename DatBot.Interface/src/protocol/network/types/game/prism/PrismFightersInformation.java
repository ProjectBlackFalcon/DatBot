package protocol.network.types.game.prism;

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
import protocol.network.types.game.fight.ProtectedEntityWaitingForHelpInfo;
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;

@SuppressWarnings("unused")
public class PrismFightersInformation extends NetworkMessage {
	public static final int ProtocolId = 443;

	public int subAreaId;
	public ProtectedEntityWaitingForHelpInfo waitingForHelpInfo;
	public List<CharacterMinimalPlusLookInformations> allyCharactersInformations;
	public List<CharacterMinimalPlusLookInformations> enemyCharactersInformations;

	public PrismFightersInformation(){
	}

	public PrismFightersInformation(int subAreaId, ProtectedEntityWaitingForHelpInfo waitingForHelpInfo, List<CharacterMinimalPlusLookInformations> allyCharactersInformations, List<CharacterMinimalPlusLookInformations> enemyCharactersInformations){
		this.subAreaId = subAreaId;
		this.waitingForHelpInfo = waitingForHelpInfo;
		this.allyCharactersInformations = allyCharactersInformations;
		this.enemyCharactersInformations = enemyCharactersInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			waitingForHelpInfo.Serialize(writer);
			writer.writeShort(this.allyCharactersInformations.size());
			int _loc2_ = 0;
			while( _loc2_ < this.allyCharactersInformations.size()){
				writer.writeShort(CharacterMinimalPlusLookInformations.ProtocolId);
				this.allyCharactersInformations.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.enemyCharactersInformations.size());
			int _loc3_ = 0;
			while( _loc3_ < this.enemyCharactersInformations.size()){
				writer.writeShort(CharacterMinimalPlusLookInformations.ProtocolId);
				this.enemyCharactersInformations.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.waitingForHelpInfo = new ProtectedEntityWaitingForHelpInfo();
			this.waitingForHelpInfo.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.allyCharactersInformations = new ArrayList<CharacterMinimalPlusLookInformations>();
			while( _loc3_ <  _loc2_){
				CharacterMinimalPlusLookInformations _loc15_ = (CharacterMinimalPlusLookInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.allyCharactersInformations.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.enemyCharactersInformations = new ArrayList<CharacterMinimalPlusLookInformations>();
			while( _loc5_ <  _loc4_){
				CharacterMinimalPlusLookInformations _loc16_ = (CharacterMinimalPlusLookInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.enemyCharactersInformations.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("waitingForHelpInfo : " + this.waitingForHelpInfo);
		//for(CharacterMinimalPlusLookInformations a : allyCharactersInformations) {
			//Network.appendDebug("allyCharactersInformations : " + a);
		//}
		//for(CharacterMinimalPlusLookInformations a : enemyCharactersInformations) {
			//Network.appendDebug("enemyCharactersInformations : " + a);
		//}
	//}
}
