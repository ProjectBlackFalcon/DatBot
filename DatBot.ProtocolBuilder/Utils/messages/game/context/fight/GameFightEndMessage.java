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
import protocol.network.types.game.context.fight.FightResultListEntry;
import protocol.network.types.game.context.roleplay.party.NamedPartyTeamWithOutcome;

@SuppressWarnings("unused")
public class GameFightEndMessage extends NetworkMessage {
	public static final int ProtocolId = 720;

	private int duration;
	private int ageBonus;
	private int lootShareLimitMalus;
	private List<FightResultListEntry> results;
	private List<NamedPartyTeamWithOutcome> namedPartyTeamsOutcomes;

	public int getDuration() { return this.duration; };
	public void setDuration(int duration) { this.duration = duration; };
	public int getAgeBonus() { return this.ageBonus; };
	public void setAgeBonus(int ageBonus) { this.ageBonus = ageBonus; };
	public int getLootShareLimitMalus() { return this.lootShareLimitMalus; };
	public void setLootShareLimitMalus(int lootShareLimitMalus) { this.lootShareLimitMalus = lootShareLimitMalus; };
	public List<FightResultListEntry> getResults() { return this.results; };
	public void setResults(List<FightResultListEntry> results) { this.results = results; };
	public List<NamedPartyTeamWithOutcome> getNamedPartyTeamsOutcomes() { return this.namedPartyTeamsOutcomes; };
	public void setNamedPartyTeamsOutcomes(List<NamedPartyTeamWithOutcome> namedPartyTeamsOutcomes) { this.namedPartyTeamsOutcomes = namedPartyTeamsOutcomes; };

	public GameFightEndMessage(){
	}

	public GameFightEndMessage(int duration, int ageBonus, int lootShareLimitMalus, List<FightResultListEntry> results, List<NamedPartyTeamWithOutcome> namedPartyTeamsOutcomes){
		this.duration = duration;
		this.ageBonus = ageBonus;
		this.lootShareLimitMalus = lootShareLimitMalus;
		this.results = results;
		this.namedPartyTeamsOutcomes = namedPartyTeamsOutcomes;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.duration);
			writer.writeShort(this.ageBonus);
			writer.writeShort(this.lootShareLimitMalus);
			writer.writeShort(this.results.size());
			int _loc2_ = 0;
			while( _loc2_ < this.results.size()){
				writer.writeShort(FightResultListEntry.ProtocolId);
				this.results.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.namedPartyTeamsOutcomes.size());
			int _loc3_ = 0;
			while( _loc3_ < this.namedPartyTeamsOutcomes.size()){
				this.namedPartyTeamsOutcomes.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.duration = reader.readInt();
			this.ageBonus = reader.readShort();
			this.lootShareLimitMalus = reader.readShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.results = new ArrayList<FightResultListEntry>();
			while( _loc3_ <  _loc2_){
				FightResultListEntry _loc15_ = (FightResultListEntry) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.results.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.namedPartyTeamsOutcomes = new ArrayList<NamedPartyTeamWithOutcome>();
			while( _loc5_ <  _loc4_){
				NamedPartyTeamWithOutcome _loc16_ = new NamedPartyTeamWithOutcome();
				_loc16_.Deserialize(reader);
				this.namedPartyTeamsOutcomes.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
