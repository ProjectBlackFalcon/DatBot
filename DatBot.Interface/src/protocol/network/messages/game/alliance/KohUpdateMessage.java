package protocol.network.messages.game.alliance;

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
import protocol.network.types.game.context.roleplay.AllianceInformations;
import protocol.network.types.game.context.roleplay.BasicAllianceInformations;

@SuppressWarnings("unused")
public class KohUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6439;

	public List<AllianceInformations> alliances;
	public List<Integer> allianceNbMembers;
	public List<Integer> allianceRoundWeigth;
	public List<Integer> allianceMatchScore;
	public List<BasicAllianceInformations> allianceMapWinners;
	public int allianceMapWinnerScore;
	public int allianceMapMyAllianceScore;
	public double nextTickTime;

	public KohUpdateMessage(){
	}

	public KohUpdateMessage(List<AllianceInformations> alliances, List<Integer> allianceNbMembers, List<Integer> allianceRoundWeigth, List<Integer> allianceMatchScore, List<BasicAllianceInformations> allianceMapWinners, int allianceMapWinnerScore, int allianceMapMyAllianceScore, double nextTickTime){
		this.alliances = alliances;
		this.allianceNbMembers = allianceNbMembers;
		this.allianceRoundWeigth = allianceRoundWeigth;
		this.allianceMatchScore = allianceMatchScore;
		this.allianceMapWinners = allianceMapWinners;
		this.allianceMapWinnerScore = allianceMapWinnerScore;
		this.allianceMapMyAllianceScore = allianceMapMyAllianceScore;
		this.nextTickTime = nextTickTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.alliances.size());
			int _loc2_ = 0;
			while( _loc2_ < this.alliances.size()){
				this.alliances.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.allianceNbMembers.size());
			int _loc3_ = 0;
			while( _loc3_ < this.allianceNbMembers.size()){
				writer.writeVarShort(this.allianceNbMembers.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.allianceRoundWeigth.size());
			int _loc4_ = 0;
			while( _loc4_ < this.allianceRoundWeigth.size()){
				writer.writeVarInt(this.allianceRoundWeigth.get(_loc4_));
				_loc4_++;
			}
			writer.writeShort(this.allianceMatchScore.size());
			int _loc5_ = 0;
			while( _loc5_ < this.allianceMatchScore.size()){
				writer.writeByte(this.allianceMatchScore.get(_loc5_));
				_loc5_++;
			}
			writer.writeShort(this.allianceMapWinners.size());
			int _loc6_ = 0;
			while( _loc6_ < this.allianceMapWinners.size()){
				this.allianceMapWinners.get(_loc6_).Serialize(writer);
				_loc6_++;
			}
			writer.writeVarInt(this.allianceMapWinnerScore);
			writer.writeVarInt(this.allianceMapMyAllianceScore);
			writer.writeDouble(this.nextTickTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.alliances = new ArrayList<AllianceInformations>();
			while( _loc3_ <  _loc2_){
				AllianceInformations _loc15_ = new AllianceInformations();
				_loc15_.Deserialize(reader);
				this.alliances.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.allianceNbMembers = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.allianceNbMembers.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.allianceRoundWeigth = new ArrayList<Integer>();
			while( _loc7_ <  _loc6_){
				int _loc17_ = reader.readVarInt();
				this.allianceRoundWeigth.add(_loc17_);
				_loc7_++;
			}
			int _loc8_  = reader.readShort();
			int _loc9_  = 0;
			this.allianceMatchScore = new ArrayList<Integer>();
			while( _loc9_ <  _loc8_){
				int _loc18_ = reader.readByte();
				this.allianceMatchScore.add(_loc18_);
				_loc9_++;
			}
			int _loc10_  = reader.readShort();
			int _loc11_  = 0;
			this.allianceMapWinners = new ArrayList<BasicAllianceInformations>();
			while( _loc11_ <  _loc10_){
				BasicAllianceInformations _loc19_ = new BasicAllianceInformations();
				_loc19_.Deserialize(reader);
				this.allianceMapWinners.add(_loc19_);
				_loc11_++;
			}
			this.allianceMapWinnerScore = reader.readVarInt();
			this.allianceMapMyAllianceScore = reader.readVarInt();
			this.nextTickTime = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(AllianceInformations a : alliances) {
			//Network.appendDebug("alliances : " + a);
		//}
		//for(Integer a : allianceNbMembers) {
			//Network.appendDebug("allianceNbMembers : " + a);
		//}
		//for(Integer a : allianceRoundWeigth) {
			//Network.appendDebug("allianceRoundWeigth : " + a);
		//}
		//for(Integer a : allianceMatchScore) {
			//Network.appendDebug("allianceMatchScore : " + a);
		//}
		//for(BasicAllianceInformations a : allianceMapWinners) {
			//Network.appendDebug("allianceMapWinners : " + a);
		//}
		//Network.appendDebug("allianceMapWinnerScore : " + this.allianceMapWinnerScore);
		//Network.appendDebug("allianceMapMyAllianceScore : " + this.allianceMapMyAllianceScore);
		//Network.appendDebug("nextTickTime : " + this.nextTickTime);
	//}
}
