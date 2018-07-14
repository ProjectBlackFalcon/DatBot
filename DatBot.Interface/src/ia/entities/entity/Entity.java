package ia.entities.entity;

import java.util.List;

import ia.map.Position;
import protocol.network.types.game.context.fight.GameFightFighterInformations;
import utils.d2o.modules.SpellLevel;

public abstract class Entity {

    private boolean isRdy;
	private Position position;
	private int breed;
	private int lvl;
    private GameFightFighterInformations info;
	private List<SpellLevel> spells;

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
	
	public void endTurn() {
		
	}
	
	@Override
	public String toString() {
		return "Entity [isRdy=" + isRdy + ", position=" + position + ", breed=" + breed + ", lvl=" + lvl + ", info=" + info + ", spells=" + spells + "]";
	}

	public List<SpellLevel> getSpells() {
		return spells;
	}

	public void setSpells(List<SpellLevel> spells) {
		this.spells = spells;
	}
	
	public SpellLevel findSpell(int id){
		for (SpellLevel spell : spells) {
			if(spell.getId() == id){
				return spell;
			}
		}
		return null;
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
