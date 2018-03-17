package ia.entities.entity;

import protocol.network.types.game.context.fight.GameFightCharacterInformations;

public class OtherEntity extends Entity{

	public OtherEntity(GameFightCharacterInformations info) {
		super(info);
	}
	
	@Override
	public String toString() {
		return "OtherEntity : "+getInfo().getName();
	}
}
