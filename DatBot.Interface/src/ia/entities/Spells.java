package ia.entities;

import java.util.ArrayList;
import java.util.List;

import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;
import protocol.network.types.game.data.items.SpellItem;

public class Spells {
	List<SpellItem> spells;
	
	public Spells() {
		spells = new ArrayList<>();
	}

}
