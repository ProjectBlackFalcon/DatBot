package ia.entities.entity;

import ia.entities.FightEffects;
import ia.entities.Spell;
import ia.map.Position;
import protocol.network.types.game.context.fight.GameFightFighterInformations;

import java.util.List;

public abstract class Entity {

    private boolean isRdy;
	private Position position;
	private int breed;
	private int lvl;
    private GameFightFighterInformations info;
	private FightEffects effects;
	private List<Spell> spells;

	public Entity(GameFightFighterInformations info) {
		this.info = info;
	}

    public Entity() {

    }

    public GameFightFighterInformations getInfo() {
		return info;
	}

	public void setInfo(GameFightFighterInformations info) {
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
	
	@Override
	public String toString() {
		return "Entity [isRdy=" + isRdy + ", position=" + position + ", breed=" + breed + ", lvl=" + lvl + ", info=" + info + ", effects=" + effects + ", spells=" + spells + "]";
	}

	public List<Spell> getSpells() {
		return spells;
	}

	public void setSpells(List<Spell> spells) {
		this.spells = spells;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

    public int getBreed() {
        return breed;
    }

    public void setBreed(int breed) {
        this.breed = breed;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public boolean isRdy() {
        return isRdy;
    }

    public void setRdy(boolean rdy) {
        isRdy = rdy;
    }
}
