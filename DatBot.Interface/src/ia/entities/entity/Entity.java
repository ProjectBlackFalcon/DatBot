package ia.entities.entity;

import ia.entities.FightEffects;
import ia.entities.Spell;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;

import java.util.List;

public abstract class Entity {

	private GameFightCharacterInformations info;
	private FightEffects effects;
	private List<Spell> spells;

	public Entity(GameFightCharacterInformations info) {
		this.info = info;
	}

	public GameFightCharacterInformations getInfo() {
		return info;
	}

	public void setInfo(GameFightCharacterInformations info) {
		this.info = info;
	}

	public FightEffects getEffects() {
		return effects;
	}

	public void setEffects(FightEffects effects) {
		this.effects = effects;
	}
	
	public void startTurn() {
		effects.removeTurnDurationAll(1);
	}
	
	public void endTurn() {
		
	}
	
	public abstract String toString();

	public List<Spell> getSpells() {
		return spells;
	}

	public void setSpells(List<Spell> spells) {
		this.spells = spells;
	}
}
