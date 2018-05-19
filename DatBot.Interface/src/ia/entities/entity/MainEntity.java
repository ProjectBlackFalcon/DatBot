package ia.entities.entity;

import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;

public class MainEntity extends Entity{
	
	public static final boolean RED_TEAM = false;
	public static final boolean BLUE_TEAM = true;
	
	private CharacterCharacteristicsInformations additionalInfo;
	
	public MainEntity(GameFightCharacterInformations info, CharacterCharacteristicsInformations additionalInfo) {
		super(info);
	}

	@Override
	public String toString() {
		return "MainEntity : "+getInfo().getName();
	}
}
