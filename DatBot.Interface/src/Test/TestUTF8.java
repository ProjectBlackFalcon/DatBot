package Test;

import java.text.Normalizer;

import gamedata.d2o.modules.Item;
import gamedata.d2o.modules.MapPosition;
import gamedata.d2o.modules.Monster;
import gamedata.d2o.modules.MountBehavior;
import gamedata.d2o.modules.Npc;
import gamedata.d2o.modules.PointOfInterest;
import gamedata.d2o.modules.Server;
import gamedata.d2o.modules.Spell;
import gamedata.d2o.modules.SpellLevel;
import protocol.enums.BoostableCharacteristicEnum;
import utils.d2i.d2iManager;

public class TestUTF8 {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Spell.getSpellById(161).getSpellLevel(3));
		System.out.println(MapPosition.getMapPositionById(153880322));
		System.out.println(Monster.getMonsterById(2271));
		System.out.println(Server.getServerById(208).getName());
		System.out.println(Npc.getNpcById(23).getName());
		System.out.println(MountBehavior.getMountBehaviorById(1).getName());
		System.out.println(PointOfInterest.getPointOfInterestById(26).getName());
	}
}
