package ia.entities.entity;

import ia.entities.Effects;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;

public abstract class Entity {

	private GameFightCharacterInformations info;
	private Effects effects;
	
	public Entity(GameFightCharacterInformations info) {
		this.info = info;
	}

	public GameFightCharacterInformations getInfo() {
		return info;
	}

	public void setInfo(GameFightCharacterInformations info) {
		this.info = info;
	}

	public Effects getEffects() {
		return effects;
	}

	public void setEffects(Effects effects) {
		this.effects = effects;
	}
	
	public void startTurn() {
		effects.removeTurnDurationAll(1);
	}
	
	public void endTurn() {
		
	}
	
	public abstract String toString();
}
