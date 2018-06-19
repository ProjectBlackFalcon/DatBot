package ia.entities.entity;

import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;

public class MainEntity extends Entity{
	
	public static final boolean RED_TEAM = false;
	public static final boolean BLUE_TEAM = true;
	
	private CharacterCharacteristicsInformations additionalInfo;

	public MainEntity() {
		super();
	}

	@Override
	public String toString() {
		return "MainEntity : "+getInfo().getContextualId();
	}

    public CharacterCharacteristicsInformations getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(CharacterCharacteristicsInformations additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
