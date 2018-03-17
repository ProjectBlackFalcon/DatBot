package ia.entities.entity;

import ia.entities.Spells;
import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;
import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;

public class MainEntity extends Entity{
	
	public static final boolean RED_TEAM = false;
	public static final boolean BLUE_TEAM = true;
	
	private Spells spells;
	private CharacterCharacteristicsInformations additionalInfo;
	
	public MainEntity(Spells spells, GameFightCharacterInformations info, CharacterCharacteristicsInformations additionalInfo) {
		super(info);
		this.spells = spells;
	} 

	public Spells getSpells() {
		return spells;
	}

	public void setSpells(Spells spells) {
		this.spells = spells;
	}

	@Override
	public String toString() {
		return "MainEntity : "+getInfo().getName();
	}
}
