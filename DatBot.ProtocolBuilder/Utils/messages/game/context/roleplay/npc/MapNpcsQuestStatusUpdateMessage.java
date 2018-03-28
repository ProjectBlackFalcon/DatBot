package protocol.network.messages.game.context.roleplay.npc;

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
import protocol.network.types.game.context.roleplay.quest.GameRolePlayNpcQuestFlag;

@SuppressWarnings("unused")
public class MapNpcsQuestStatusUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5642;

	private double mapId;
	private List<Integer> npcsIdsWithQuest;
	private List<GameRolePlayNpcQuestFlag> questFlags;
	private List<Integer> npcsIdsWithoutQuest;

	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public List<Integer> getNpcsIdsWithQuest() { return this.npcsIdsWithQuest; }
	public void setNpcsIdsWithQuest(List<Integer> npcsIdsWithQuest) { this.npcsIdsWithQuest = npcsIdsWithQuest; };
	public List<GameRolePlayNpcQuestFlag> getQuestFlags() { return this.questFlags; }
	public void setQuestFlags(List<GameRolePlayNpcQuestFlag> questFlags) { this.questFlags = questFlags; };
	public List<Integer> getNpcsIdsWithoutQuest() { return this.npcsIdsWithoutQuest; }
	public void setNpcsIdsWithoutQuest(List<Integer> npcsIdsWithoutQuest) { this.npcsIdsWithoutQuest = npcsIdsWithoutQuest; };

	public MapNpcsQuestStatusUpdateMessage(){
	}

	public MapNpcsQuestStatusUpdateMessage(double mapId, List<Integer> npcsIdsWithQuest, List<GameRolePlayNpcQuestFlag> questFlags, List<Integer> npcsIdsWithoutQuest){
		this.mapId = mapId;
		this.npcsIdsWithQuest = npcsIdsWithQuest;
		this.questFlags = questFlags;
		this.npcsIdsWithoutQuest = npcsIdsWithoutQuest;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
			writer.writeShort(this.npcsIdsWithQuest.size());
			int _loc2_ = 0;
			while( _loc2_ < this.npcsIdsWithQuest.size()){
				writer.writeInt(this.npcsIdsWithQuest.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.questFlags.size());
			int _loc3_ = 0;
			while( _loc3_ < this.questFlags.size()){
				this.questFlags.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
			writer.writeShort(this.npcsIdsWithoutQuest.size());
			int _loc4_ = 0;
			while( _loc4_ < this.npcsIdsWithoutQuest.size()){
				writer.writeInt(this.npcsIdsWithoutQuest.get(_loc4_));
				_loc4_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.npcsIdsWithQuest = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.npcsIdsWithQuest.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.questFlags = new ArrayList<GameRolePlayNpcQuestFlag>();
			while( _loc5_ <  _loc4_){
				GameRolePlayNpcQuestFlag _loc16_ = new GameRolePlayNpcQuestFlag();
				_loc16_.Deserialize(reader);
				this.questFlags.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.npcsIdsWithoutQuest = new ArrayList<Integer>();
			while( _loc7_ <  _loc6_){
				int _loc17_ = reader.readInt();
				this.npcsIdsWithoutQuest.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
