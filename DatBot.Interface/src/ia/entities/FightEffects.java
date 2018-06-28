package ia.entities;

import java.util.ArrayList;
import java.util.List;

import ia.entities.entity.Entity;
import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;

public class FightEffects {
	List<AbstractFightDispellableEffect> effects;
	Entity entity;
	
	public FightEffects(Entity entity) {
		effects = new ArrayList<>();
		this.entity = entity;
	}
	
	void addEffect(AbstractFightDispellableEffect effect){
		//TODO Modify the stats
		//this.entity.getInfo().getStats().
		
		this.effects.add(effect);
	}
	
	void clear(){
		this.effects.clear();
	}
	
	public void removeTurnDurationAll(int duration) {
		for(int i = 0; i < effects.size(); i++) {
			removeTurnDuration(effects.get(i), duration);
			if(effects.get(i).getTurnDuration() <= 0) {
				removeEffect(effects.get(i));
			}
		}
	}
	
	//TODO remove from list when <= 0
	public void removeTurnDuration(AbstractFightDispellableEffect effect, int duration) {
		
	}
	
	public void removeEffect(AbstractFightDispellableEffect effect) {
		//TODO handle different effects removal
		
		effects.remove(effect);
	}

	
}
