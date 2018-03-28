package protocol.network.messages.game.context.fight;

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
import protocol.network.types.game.action.fight.FightDispellableEffectExtendedInformations;
import protocol.network.types.game.actions.fight.GameActionMark;
import protocol.network.types.game.idol.Idol;

@SuppressWarnings("unused")
public class GameFightSpectateMessage extends NetworkMessage {
	public static final int ProtocolId = 6069;

	private List<FightDispellableEffectExtendedInformations> effects;
	private List<GameActionMark> marks;
	private int gameTurn;
	private int fightStart;
	private List<Idol> idols;

	public List<FightDispellableEffectExtendedInformations> getEffects() { return this.effects; }
	public void setEffects(List<FightDispellableEffectExtendedInformations> effects) { this.effects = effects; };
	public List<GameActionMark> getMarks() { return this.marks; }
	public void setMarks(List<GameActionMark> marks) { this.marks = marks; };
	public int getGameTurn() { return this.gameTurn; }
	public void setGameTurn(int gameTurn) { this.gameTurn = gameTurn; };
	public int getFightStart() { return this.fightStart; }
	public void setFightStart(int fightStart) { this.fightStart = fightStart; };
	public List<Idol> getIdols() { return this.idols; }
	public void setIdols(List<Idol> idols) { this.idols = idols; };

	public GameFightSpectateMessage(){
	}

	public GameFightSpectateMessage(List<FightDispellableEffectExtendedInformations> effects, List<GameActionMark> marks, int gameTurn, int fightStart, List<Idol> idols){
		this.effects = effects;
		this.marks = marks;
		this.gameTurn = gameTurn;
		this.fightStart = fightStart;
		this.idols = idols;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.effects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.effects.size()){
				this.effects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.marks.size());
			int _loc3_ = 0;
			while( _loc3_ < this.marks.size()){
				this.marks.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
			writer.writeVarShort(this.gameTurn);
			writer.writeInt(this.fightStart);
			writer.writeShort(this.idols.size());
			int _loc4_ = 0;
			while( _loc4_ < this.idols.size()){
				this.idols.get(_loc4_).Serialize(writer);
				_loc4_++;
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
			this.effects = new ArrayList<FightDispellableEffectExtendedInformations>();
			while( _loc3_ <  _loc2_){
				FightDispellableEffectExtendedInformations _loc15_ = new FightDispellableEffectExtendedInformations();
				_loc15_.Deserialize(reader);
				this.effects.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.marks = new ArrayList<GameActionMark>();
			while( _loc5_ <  _loc4_){
				GameActionMark _loc16_ = new GameActionMark();
				_loc16_.Deserialize(reader);
				this.marks.add(_loc16_);
				_loc5_++;
			}
			this.gameTurn = reader.readVarShort();
			this.fightStart = reader.readInt();
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.idols = new ArrayList<Idol>();
			while( _loc7_ <  _loc6_){
				Idol _loc17_ = new Idol();
				_loc17_.Deserialize(reader);
				this.idols.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
