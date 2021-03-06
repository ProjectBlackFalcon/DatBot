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
import protocol.network.types.game.house.HouseInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.StatedElement;
import protocol.network.types.game.interactive.MapObstacle;
import protocol.network.types.game.context.fight.FightCommonInformations;
import protocol.network.types.game.context.fight.FightStartingPositions;

@SuppressWarnings("unused")
public class MapComplementaryInformationsDataMessage extends NetworkMessage {
	public static final int ProtocolId = 226;

	private int subAreaId;
	private double mapId;
	private List<HouseInformations> houses;
	private List<GameRolePlayActorInformations> actors;
	private List<InteractiveElement> interactiveElements;
	private List<StatedElement> statedElements;
	private List<MapObstacle> obstacles;
	private List<FightCommonInformations> fights;
	private boolean hasAggressiveMonsters;
	private FightStartingPositions fightStartPositions;

	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public List<HouseInformations> getHouses() { return this.houses; }
	public void setHouses(List<HouseInformations> houses) { this.houses = houses; };
	public List<GameRolePlayActorInformations> getActors() { return this.actors; }
	public void setActors(List<GameRolePlayActorInformations> actors) { this.actors = actors; };
	public List<InteractiveElement> getInteractiveElements() { return this.interactiveElements; }
	public void setInteractiveElements(List<InteractiveElement> interactiveElements) { this.interactiveElements = interactiveElements; };
	public List<StatedElement> getStatedElements() { return this.statedElements; }
	public void setStatedElements(List<StatedElement> statedElements) { this.statedElements = statedElements; };
	public List<MapObstacle> getObstacles() { return this.obstacles; }
	public void setObstacles(List<MapObstacle> obstacles) { this.obstacles = obstacles; };
	public List<FightCommonInformations> getFights() { return this.fights; }
	public void setFights(List<FightCommonInformations> fights) { this.fights = fights; };
	public boolean isHasAggressiveMonsters() { return this.hasAggressiveMonsters; }
	public void setHasAggressiveMonsters(boolean hasAggressiveMonsters) { this.hasAggressiveMonsters = hasAggressiveMonsters; };
	public FightStartingPositions getFightStartPositions() { return this.fightStartPositions; }
	public void setFightStartPositions(FightStartingPositions fightStartPositions) { this.fightStartPositions = fightStartPositions; };

	public MapComplementaryInformationsDataMessage(){
	}

	public MapComplementaryInformationsDataMessage(int subAreaId, double mapId, List<HouseInformations> houses, List<GameRolePlayActorInformations> actors, List<InteractiveElement> interactiveElements, List<StatedElement> statedElements, List<MapObstacle> obstacles, List<FightCommonInformations> fights, boolean hasAggressiveMonsters, FightStartingPositions fightStartPositions){
		this.subAreaId = subAreaId;
		this.mapId = mapId;
		this.houses = houses;
		this.actors = actors;
		this.interactiveElements = interactiveElements;
		this.statedElements = statedElements;
		this.obstacles = obstacles;
		this.fights = fights;
		this.hasAggressiveMonsters = hasAggressiveMonsters;
		this.fightStartPositions = fightStartPositions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeDouble(this.mapId);
			writer.writeShort(this.houses.size());
			int _loc2_ = 0;
			while( _loc2_ < this.houses.size()){
				writer.writeShort(HouseInformations.ProtocolId);
				this.houses.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.actors.size());
			int _loc3_ = 0;
			while( _loc3_ < this.actors.size()){
				writer.writeShort(GameRolePlayActorInformations.ProtocolId);
				this.actors.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
			writer.writeShort(this.interactiveElements.size());
			int _loc4_ = 0;
			while( _loc4_ < this.interactiveElements.size()){
				writer.writeShort(InteractiveElement.ProtocolId);
				this.interactiveElements.get(_loc4_).Serialize(writer);
				_loc4_++;
			}
			writer.writeShort(this.statedElements.size());
			int _loc5_ = 0;
			while( _loc5_ < this.statedElements.size()){
				this.statedElements.get(_loc5_).Serialize(writer);
				_loc5_++;
			}
			writer.writeShort(this.obstacles.size());
			int _loc6_ = 0;
			while( _loc6_ < this.obstacles.size()){
				this.obstacles.get(_loc6_).Serialize(writer);
				_loc6_++;
			}
			writer.writeShort(this.fights.size());
			int _loc7_ = 0;
			while( _loc7_ < this.fights.size()){
				this.fights.get(_loc7_).Serialize(writer);
				_loc7_++;
			}
			writer.writeBoolean(this.hasAggressiveMonsters);
			fightStartPositions.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.mapId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.houses = new ArrayList<HouseInformations>();
			while( _loc3_ <  _loc2_){
				HouseInformations _loc15_ = (HouseInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.houses.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.actors = new ArrayList<GameRolePlayActorInformations>();
			while( _loc5_ <  _loc4_){
				GameRolePlayActorInformations _loc16_ = (GameRolePlayActorInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.actors.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.interactiveElements = new ArrayList<InteractiveElement>();
			while( _loc7_ <  _loc6_){
				InteractiveElement _loc17_ = (InteractiveElement) ProtocolTypeManager.getInstance(reader.readShort());
				_loc17_.Deserialize(reader);
				this.interactiveElements.add(_loc17_);
				_loc7_++;
			}
			int _loc8_  = reader.readShort();
			int _loc9_  = 0;
			this.statedElements = new ArrayList<StatedElement>();
			while( _loc9_ <  _loc8_){
				StatedElement _loc18_ = new StatedElement();
				_loc18_.Deserialize(reader);
				this.statedElements.add(_loc18_);
				_loc9_++;
			}
			int _loc10_  = reader.readShort();
			int _loc11_  = 0;
			this.obstacles = new ArrayList<MapObstacle>();
			while( _loc11_ <  _loc10_){
				MapObstacle _loc19_ = new MapObstacle();
				_loc19_.Deserialize(reader);
				this.obstacles.add(_loc19_);
				_loc11_++;
			}
			int _loc12_  = reader.readShort();
			int _loc13_  = 0;
			this.fights = new ArrayList<FightCommonInformations>();
			while( _loc13_ <  _loc12_){
				FightCommonInformations _loc20_ = new FightCommonInformations();
				_loc20_.Deserialize(reader);
				this.fights.add(_loc20_);
				_loc13_++;
			}
			this.hasAggressiveMonsters = reader.readBoolean();
			this.fightStartPositions = new FightStartingPositions();
			this.fightStartPositions.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
